package cartoongrabber.tools;

/**
 * This interface provides some level of Filesystem abstraction
 * Created by Philipp Krauß on 18.07.2017.
 */
public interface FileSystemPersistenceService {

    void storeTextFile(String textFileName, String text);

}
