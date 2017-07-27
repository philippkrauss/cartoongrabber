package cartoongrabber.backend;

import cartoongrabber.model.CartoonStrip;
import cartoongrabber.tools.MockFileSystemPersistenceImpl;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static cartoongrabber.tools.TestTools.createCartoon;
import static cartoongrabber.tools.TestTools.imageEquals;
import static org.junit.Assert.*;

/**
 * Created by Philipp Krauß on 18.07.2017.
 */
public class FileCollectionTest {

    private FileCollection fileCollection;
    private MockFileSystemPersistenceImpl fileSystemPersistence;

    @Before
    public void setUp() {
        fileSystemPersistence = new MockFileSystemPersistenceImpl();
        fileCollection = new FileCollection(fileSystemPersistence);
    }

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
        assertEquals(fileSystemPersistence.createdDirectory, oneCartoon.get(0).getDate().toString());
    }

    @Test
    public void testStoreImage() {
        fileCollection.collect(oneCartoon);
        assertEquals(fileSystemPersistence.directoryName, oneCartoon.get(0).getDate().toString());
        assertEquals(fileSystemPersistence.imageName, "dilbert");
        imageEquals(oneCartoon.get(0).getImage(), fileSystemPersistence.image);
    }

    @Test
    public void testStoreText() {
        fileCollection.collect(oneCartoon);
        assertEquals(fileSystemPersistence.directoryName, oneCartoon.get(0).getDate().toString());
        assertEquals(fileSystemPersistence.textFileName, "dilbert.txt");
        assertNotNull(fileSystemPersistence.text);
    }
}
