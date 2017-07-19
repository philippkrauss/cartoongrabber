package cartoongrabber.tools;

import java.awt.image.BufferedImage; /**
 * This interface provides some level of Filesystem abstraction
 * Created by Philipp Krauß on 18.07.2017.
 */
public interface FileSystemPersistenceService {
    void createDirectory(String name);

    void storeImage(String directoryName, String imageName, BufferedImage image);
}
