package cartoongrabber.tools;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

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
        boolean created = newDirectory.mkdir();
        if (!created) {
            throw new RuntimeException("Could not create directory " + newDirectory);
        }
    }

    @Override
    public void storeImage(String directoryName, String imageName, BufferedImage image) {
        try {
            ImageIO.write(image, "jpg", new File(new File(basePath, directoryName), imageName + ".jpg"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
