package cartoongrabber.backend;

import cartoongrabber.model.CartoonStrip;
import cartoongrabber.tools.MockFileSystemPersistenceImpl;
import cartoongrabber.tools.MockRenderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static cartoongrabber.tools.TestTools.createCartoon;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:/spring/integration/integration-config-test.xml")
public class HtmlCollectionTest {


    @Autowired
    private HtmlCollection htmlCollection;

    @Autowired
    private MockFileSystemPersistenceImpl fileSystemPersistence;

    @Autowired
    private MockRenderService renderService;

    private final List<CartoonStrip> oneCartoon = Collections.singletonList(createCartoon());

    @Test
    public void testNull() {
        try {
            htmlCollection.collect(null);
            fail("expected exception");
        } catch (RuntimeException e) {
            assertEquals(RuntimeException.class, e.getClass());
        }
    }

    @Test
    public void testEmpty() {
        try {
            htmlCollection.collect(new ArrayList<>());
            fail("expected exception");
        } catch (RuntimeException e) {
            assertEquals(RuntimeException.class, e.getClass());
        }
    }

    @Test
    public void testRender() {
        htmlCollection.collect(oneCartoon);
        assertEquals(renderService.renderedText, fileSystemPersistence.text);
    }

    @Test
    public void testFilename() {
        htmlCollection.collect(oneCartoon);
        assertEquals("cartoons.html", fileSystemPersistence.textFileName);
        assertNull(fileSystemPersistence.directoryName);
    }

}
