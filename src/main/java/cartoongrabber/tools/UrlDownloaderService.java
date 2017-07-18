package cartoongrabber.tools;

import java.io.IOException;
import java.net.URL;

/**
 * Service interface that provides download of a URL into a byte array
 * Created by Philipp Krauß on 17.07.2017.
 */
public interface UrlDownloaderService {

    byte[] download(URL url) throws IOException;
}
