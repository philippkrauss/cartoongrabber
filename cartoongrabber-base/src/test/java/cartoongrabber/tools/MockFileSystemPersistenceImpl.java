package cartoongrabber.tools;

import org.springframework.stereotype.Component;

/**
 * Created by Philipp Krauß on 18.07.2017.
 */
@Component
public class MockFileSystemPersistenceImpl implements FileSystemPersistenceService {

    public String textFileName = null;
    public String text = null;

    @Override
    public void storeTextFile(String textFileName, String text) {
        this.textFileName = textFileName;
        this.text = text;
    }
}
