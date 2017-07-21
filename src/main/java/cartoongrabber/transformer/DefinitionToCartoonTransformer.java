package cartoongrabber.transformer;

import cartoongrabber.model.CartoonStrip;
import cartoongrabber.model.SourceDefinition;
import cartoongrabber.tools.DateService;
import cartoongrabber.tools.MarkupDateFormatter;
import cartoongrabber.tools.UrlDownloaderService;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Transformer component that transforms a {@link SourceDefinition} into a {@link CartoonStrip}.
 * Process of transformation is:
 * 1. format base URL
 * 2. download HTML from base URL
 * 3. extract URL of image
 * 4. download image from extracted URL
 * Created by Philipp Krauß on 17.07.2017.
 */
@Component
public class DefinitionToCartoonTransformer {

    private final Logger log = LoggerFactory.getLogger(DefinitionToCartoonTransformer.class);

    private final MarkupDateFormatter formatter;
    private final UrlDownloaderService downloaderService;
    private final DateService dateService;

    @Autowired
    public DefinitionToCartoonTransformer(MarkupDateFormatter formatter, UrlDownloaderService downloaderService, DateService dateService) {
        this.formatter = formatter;
        this.downloaderService = downloaderService;
        this.dateService = dateService;
    }

    public CartoonStrip transform(SourceDefinition source) {
        if (source == null) {
            throw new RuntimeException("Cannot transform null source to cartoon strip");
        }
        LocalDate date = dateService.getDate();
        log.debug("transforming source definition [{}] to cartoon strip", source);
        URL baseUrl = formatBaseUrl(source.getBaseUrl(), date);
        String webPage = fetchBaseUrl(baseUrl);
        URL imgUrl = extractImgUrl(webPage, source.getImagePattern());
        BufferedImage image = downloadImage(imgUrl);
        return new CartoonStrip(source.getName(), baseUrl, imgUrl, image, date);
    }

    private BufferedImage downloadImage(URL imgUrl) {
        ByteArrayInputStream in = null;
        try {
            byte[] downloadedBytes = downloaderService.download(imgUrl);
            in = new ByteArrayInputStream(downloadedBytes);
            return ImageIO.read(in);
        } catch (IOException e) {
            throw new RuntimeException("Could not download image from " + imgUrl, e);
        } finally {
            IOUtils.closeQuietly(in);
        }
    }

    private URL extractImgUrl(String content, String imagePattern) {
        try {
            Pattern p = Pattern.compile(imagePattern);
            Matcher m = p.matcher(content);
            if (m.find()) {
                String match = m.group(1);
                return new URL(match);
            }
        } catch (Exception e) {
            throw new RuntimeException("could not extract image URL using pattern " + imagePattern, e);
        }
        throw new RuntimeException("Could not match image URL using pattern [" + imagePattern + "]");
    }

    private String fetchBaseUrl(URL baseUrl) {
        try {
            String fetched = new String(downloaderService.download(baseUrl), Charset.forName("UTF8"));
            log.trace("fetched String from [{}]: {}", baseUrl, fetched);
            return fetched;
        } catch (IOException e) {
            throw new RuntimeException("Could not download from URL " + baseUrl, e);
        }
    }

    private URL formatBaseUrl(String baseUrl, LocalDate date) {
        try {
            String formattedUrl = formatter.format(baseUrl, date);
            URL url = new URL(formattedUrl);
            log.debug("base URL: [{}]", url);
            return url;
        } catch (Exception e) {
            throw new RuntimeException("An error occurred when formatting base URL " + baseUrl, e);
        }
    }
}
