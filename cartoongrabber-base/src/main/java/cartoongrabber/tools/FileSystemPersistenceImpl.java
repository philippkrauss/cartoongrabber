package cartoongrabber.tools;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Created by Philipp Krauß on 18.07.2017.
 */
public class FileSystemPersistenceImpl implements FileSystemPersistenceService {

    @Value("${outDir:output}")
    private String basePathString;

    private File getBasePath() {
        File basePath = new File(basePathString);
        checkBasePath(basePath);
        return basePath;
    }

    private void checkBasePath(File path) {
        if (!path.exists()) {
            boolean success = path.mkdirs();
            if (!success) {
                throw new RuntimeException("Path " + path + " could not be created!");
            }
        }
        if (!path.isDirectory()) {
            throw new IllegalArgumentException("Path " + path + " exists but is no directory!");
        }
    }

    @Override
    public void storeTextFile(String textFileName, String text) {
        File destination = new File(getBasePath(), textFileName);
        writeText(destination, text);
    }

    private void writeText(File destination, String text) {
        try {
            FileUtils.writeStringToFile(destination, text, Charset.forName("UTF8"));
        } catch (IOException e) {
            throw new RuntimeException("Error when storing text file [" + destination + "]", e);
        }
    }
}
