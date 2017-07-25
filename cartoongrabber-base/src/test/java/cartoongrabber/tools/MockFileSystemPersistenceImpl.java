package cartoongrabber.tools;

import org.springframework.stereotype.Component;

import java.awt.image.BufferedImage;

/**
 * Created by Philipp Krauß on 18.07.2017.
 */
@Component
public class MockFileSystemPersistenceImpl implements FileSystemPersistenceService {
    public String createdDirectory = null;
    public String directoryName = null;
    public String imageName = null;
    public BufferedImage image = null;
    public String textFileName = null;
    public String text = null;

    @Override
    public void createDirectory(String name) {
        createdDirectory = name;
    }

    @Override
    public void storeImage(String directoryName, String imageName, BufferedImage image) {
        this.directoryName = directoryName;
        this.imageName = imageName;
        this.image = image;
    }

    @Override
    public void storeTextFile(String directoryName, String textFileName, String text) {
        this.directoryName = directoryName;
        this.textFileName = textFileName;
        this.text = text;
    }

    @Override
    public void storeTextFile(String textFileName, String text) {
        this.textFileName = textFileName;
        this.text = text;
    }
}
