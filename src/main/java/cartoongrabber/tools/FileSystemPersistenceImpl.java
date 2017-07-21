package cartoongrabber.tools;

import org.apache.commons.io.FileUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Created by Philipp Krauß on 18.07.2017.
 */
public class FileSystemPersistenceImpl implements FileSystemPersistenceService {

    private final File basePath;

    public FileSystemPersistenceImpl(String basePath) {
        this.basePath = new File(basePath);
        checkBasePath(this.basePath);
    }

    private void checkBasePath(File path) {
        if (!path.isDirectory()) {
            throw new IllegalArgumentException("Path " + path + " exists but is no directory!");
        }
        if (!path.exists()) {
            boolean success = path.mkdirs();
            if (!success) {
                throw new RuntimeException("Path " + path + " could not be created!");
            }
        }
    }

    @Override
    public void createDirectory(String name) {
        File newDirectory = new File(basePath, name);
        if (newDirectory.exists() && newDirectory.isDirectory()) {
            return;
        }
        boolean created = newDirectory.mkdir();
        if (!created) {
            throw new RuntimeException("Could not create directory " + newDirectory);
        }
    }

    @Override
    public void storeImage(String directoryName, String imageName, BufferedImage image) {
        File destination = createDestination(directoryName, imageName, "jpg");
        try {
            ImageIO.write(image, "jpg", destination);
        } catch (IOException e) {
            throw new RuntimeException("Error when storing image [" + imageName + "] to destination [" + destination + "]", e);
        }
    }

    private File createDestination(String directoryName, String imageName, String ending) {
        return new File(new File(basePath, directoryName), imageName + ((ending != null)?"." + ending:""));
    }

    @Override
    public void storeTextFile(String directoryName, String textFileName, String text) {
        File destination = createDestination(directoryName, textFileName, null);
        try {
            FileUtils.writeStringToFile(destination, text, Charset.forName("UTF8"));
        } catch (IOException e) {
            throw new RuntimeException("Error when storing text file [" + textFileName + "] to destination [" + destination + "]", e);
        }
    }
}
