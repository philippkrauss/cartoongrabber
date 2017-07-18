package cartoongrabber.transformer;

import cartoongrabber.model.CartoonStrip;
import cartoongrabber.model.SourceDefinition;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Philipp Krauß on 17.07.2017.
 */
@Component
public class DefinitionToCartoonTransformer {

    private final Logger log = LoggerFactory.getLogger(DefinitionToCartoonTransformer.class);

    private final MarkupDateFormatter formatter;
    private final UrlDownloaderService downloaderService;

    @Autowired
    public DefinitionToCartoonTransformer(MarkupDateFormatter formatter, UrlDownloaderService downloaderService) {
        this.formatter = formatter;
        this.downloaderService = downloaderService;
    }

    public CartoonStrip transform(SourceDefinition source) {
        if (source == null) {
            log.warn("Cannot transform null source to cartoon strip");
            return null;
        }
        log.debug("transforming source definition [{}] to cartoon strip", source);
        URL baseUrl = formatBaseUrl(source.getBaseUrl());
        String webPage = fetchBaseUrl(baseUrl);
        URL imgUrl = extractImgUrl(webPage, source.getImagePattern());
        BufferedImage image = downloadImage(imgUrl);
        //TODO: parse metadata
        return new CartoonStrip(source.getName(), baseUrl, image);
    }

    private BufferedImage downloadImage(URL imgUrl) {
        ByteArrayInputStream in = null;
        try {
            byte[] downloadedBytes = downloaderService.download(imgUrl);
            in = new ByteArrayInputStream(downloadedBytes);
            return ImageIO.read(in);
        } catch (IOException e) {
            log.error("Could not download image from [{}]", imgUrl);
            throw new RuntimeException(e);
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
            log.error("could not extract image URL using pattern [{}]", imagePattern);
            throw new RuntimeException(e);
        }
        throw new RuntimeException("Could not match image URL using pattern [" + imagePattern + "]");
    }

    private String fetchBaseUrl(URL baseUrl) {
        try {
            String fetched = new String(downloaderService.download(baseUrl), Charset.forName("UTF8"));
            log.trace("fetched String from [{}]: {}", baseUrl, fetched);
            return fetched;
        } catch (IOException e) {
            log.error("Could not download from URL [{}]", e);
            throw new RuntimeException(e);
        }
    }

    private URL formatBaseUrl(String baseUrl) {
        URL url = null;
        try {
            String formattedUrl = formatter.format(baseUrl);
            url = new URL(formattedUrl);
        } catch (Exception e) {
            log.error("caught exception when constructing URL", e);
            throw new RuntimeException(e);
        }
        log.debug("base URL: [{}]", url);
        return url;
    }
}
