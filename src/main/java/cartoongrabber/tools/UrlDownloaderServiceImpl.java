package cartoongrabber.tools;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;

/**
 * Implementation of {@link UrlDownloaderService} using {@link IOUtils}
 * Created by Philipp Krauß on 17.07.2017.
 */
@Component
public class UrlDownloaderServiceImpl implements UrlDownloaderService {
    @Override
    public byte[] download(URL url) throws IOException {
        return IOUtils.toByteArray(url);
    }
}
