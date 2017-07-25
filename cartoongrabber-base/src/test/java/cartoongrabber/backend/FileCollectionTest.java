package cartoongrabber.backend;

import cartoongrabber.model.CartoonStrip;
import cartoongrabber.tools.MockFileSystemPersistenceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static cartoongrabber.tools.TestTools.createCartoon;
import static cartoongrabber.tools.TestTools.imageEquals;
import static org.junit.Assert.*;

/**
 * Created by Philipp Krauß on 18.07.2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:/spring/integration/integration-config-test.xml")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class FileCollectionTest {

    @Autowired
    private FileCollection fileCollection;

    @Autowired
    private MockFileSystemPersistenceImpl fileSystemPersistence;

    private final List<CartoonStrip> oneCartoon = Collections.singletonList(createCartoon());

    @Test
    public void testNull() {
        try {
            fileCollection.collect(null);
            fail("expected exception");
        } catch (RuntimeException e) {
            assertEquals(RuntimeException.class, e.getClass());
        }
    }

    @Test
    public void testEmpty() {
        try {
            fileCollection.collect(new ArrayList<>());
            fail("expected exception");
        } catch (RuntimeException e) {
            assertEquals(RuntimeException.class, e.getClass());
        }
    }

    @Test
    public void testCreateDirectory() throws Exception {
        fileCollection.collect(oneCartoon);
        assertEquals(fileSystemPersistence.createdDirectory, "2000-01-17");
    }

    @Test
    public void testStoreImage() {
        fileCollection.collect(oneCartoon);
        assertEquals(fileSystemPersistence.directoryName, "2000-01-17");
        assertEquals(fileSystemPersistence.imageName, "dilbert");
        imageEquals(oneCartoon.get(0).getImage(), fileSystemPersistence.image);
    }

    @Test
    public void testStoreText() {
        fileCollection.collect(oneCartoon);
        assertEquals(fileSystemPersistence.directoryName, "2000-01-17");
        assertEquals(fileSystemPersistence.textFileName, "dilbert.txt");
        assertNotNull(fileSystemPersistence.text);
    }
}
