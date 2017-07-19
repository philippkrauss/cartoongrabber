package cartoongrabber.backend;

import cartoongrabber.model.CartoonStrip;
import cartoongrabber.tools.MockFileSystemPersistenceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.time.LocalDate;

import static cartoongrabber.tools.TestTools.imageEquals;
import static org.junit.Assert.assertEquals;

/**
 * Created by Philipp Krauß on 18.07.2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:/spring/integration-config-test.xml")
public class FileCollectionTest {

    @Autowired
    private FileCollection fileCollection;

    @Autowired
    private MockFileSystemPersistenceImpl fileSystemPersistence;

    private CartoonStrip createCartoon() {
        try {
            return new CartoonStrip("dilbert", new URL("http", "www.dilbert.com", "/"), new BufferedImage(10, 10, BufferedImage.TYPE_BYTE_INDEXED), LocalDate.of(2000, 1, 17));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testCreateDirectory() throws Exception {
        CartoonStrip cartoon = createCartoon();
        fileCollection.collect(cartoon);
        assertEquals(fileSystemPersistence.createdDirectory, "2000-01-17");
    }

    @Test
    public void testStoreImage() {
        CartoonStrip cartoon = createCartoon();
        fileCollection.collect(cartoon);
        assertEquals(fileSystemPersistence.directoryName, "2000-01-17");
        assertEquals(fileSystemPersistence.imageName, "dilbert");
        imageEquals(cartoon.getImage(), fileSystemPersistence.image);
    }

    @Test
    public void testStoreText() {
        CartoonStrip cartoon = createCartoon();
        fileCollection.collect(cartoon);
        assertEquals(fileSystemPersistence.directoryName, "2000-01-17");
        assertEquals(fileSystemPersistence.textFileName, "dilbert");
        assertEquals(fileSystemPersistence.text, "Cartoon strip \"dilbert\", downloaded from http://www.dilbert.com/");
    }
}
