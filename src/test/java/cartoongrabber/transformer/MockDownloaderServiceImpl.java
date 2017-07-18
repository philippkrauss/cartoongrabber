package cartoongrabber.transformer;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by Philipp Krauß on 17.07.2017.
 */
public class MockDownloaderServiceImpl implements UrlDownloaderService {

    private List<URL> urls = new ArrayList<>();

    private Queue<byte[]> content = new LinkedList<>();

    public void addContent(byte[] content) {
        this.content.add(content);
    }

    @Override
    public byte[] download(URL url) throws IOException {
        urls.add(url);
        return content.poll();
    }

    public List<URL> getUrls() {
        return urls;
    }
}
