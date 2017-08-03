package cartoongrabber.tools;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by Philipp Krauß on 17.07.2017.
 */
@Component
public class MockDownloaderServiceImpl implements UrlDownloaderService {

    private final List<URL> urls = new ArrayList<>();

    private final Queue<byte[]> content = new LinkedList<>();

    public void addContent(byte[] content) {
        this.content.add(content);
    }

    public boolean throwException = false;

    @Override
    public byte[] download(URL url) throws IOException {
        if (throwException) {
            throw new IOException();
        }
        urls.add(url);
        return content.poll();
    }

    public List<URL> getUrls() {
        return urls;
    }
}
