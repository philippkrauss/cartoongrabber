package cartoongrabber.transformer;

import java.io.IOException;
import java.net.URL;

/**
 * Created by Philipp Krauß on 17.07.2017.
 */
public interface UrlDownloaderService {

    byte[] download(URL url) throws IOException;
}
