package cartoongrabber.transformer;

import cartoongrabber.model.CartoonStrip;
import cartoongrabber.model.SourceDefinition;
import cartoongrabber.tools.MockDateServiceImpl;
import cartoongrabber.tools.MockDownloaderServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.net.URL;

import static org.junit.Assert.*;

/**
 * Created by Philipp Krauß on 17.07.2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:/spring/integration/integration-config-test.xml")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class DefinitionToCartoonTransformerTest {

    @Autowired
    private DefinitionToCartoonTransformer transformer;

    @Autowired
    private MockDownloaderServiceImpl mockDownloaderService;

    @Autowired
    private MockDateServiceImpl mockDateService;

    private SourceDefinition source = null;

    @Before
    public void setUp() throws Exception {
        mockDownloaderService.addContent("download image from:\"http://here.com\"".getBytes());
        source = SourceDefinition.patternSource("testing", "http://test.com/<yyyy>-<MM>-<dd>", "from:\"([^\"].*)\"");
        mockDateService.setDate(2000, 1, 25);
    }

    @Test
    public void testNullSource() {
        try {
            transformer.transform(null);
            fail("expected exception");
        } catch (RuntimeException e) {
        }
    }

    @Test
    public void nameIsSet() {
        CartoonStrip strip = transformer.transform(source);
        assertEquals(source.getName(), strip.getName());
    }

    @Test
    public void urlNotNull() {
        CartoonStrip strip = transformer.transform(source);
        assertNotNull(strip.getSourceUrl());
    }

    @Test
    public void urlPatternExtended() throws Exception {
        CartoonStrip strip = transformer.transform(source);
        assertEquals(new URL("http://test.com/2000-01-25"), strip.getSourceUrl());
        assertEquals(new URL("http://test.com/2000-01-25"), mockDownloaderService.getUrls().get(0));
    }

    @Test
    public void testImagePattern() throws Exception {
        CartoonStrip strip = transformer.transform(source);
        assertEquals(new URL("http://here.com"), strip.getImageUrl());
    }

    @Test
    public void directImagePattern() throws Exception {
        SourceDefinition directSource = SourceDefinition.directSource("testing", "http://test.com/<yyyy>/<MM>/<dd>", "http://test.com/<yyyy>-<MM>-<dd>/image.jpg");
        CartoonStrip strip = transformer.transform(directSource);
        assertEquals(new URL("http://test.com/2000/01/25"), strip.getSourceUrl());
        assertEquals(new URL("http://test.com/2000-01-25/image.jpg"), strip.getImageUrl());
    }

}
