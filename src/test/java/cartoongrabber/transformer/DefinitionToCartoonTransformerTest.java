package cartoongrabber.transformer;

import cartoongrabber.model.CartoonStrip;
import cartoongrabber.model.SourceDefinition;
import cartoongrabber.tools.MockDownloaderServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.net.URL;

import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Philipp Krauß on 17.07.2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:/spring/integration-config-test.xml")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class DefinitionToCartoonTransformerTest {

    @Autowired
    private DefinitionToCartoonTransformer transformer;

    @Autowired
    private MockDownloaderServiceImpl mockDownloaderService;

    private SourceDefinition source = null;
    private final BufferedImage image = new BufferedImage(10, 10, BufferedImage.TYPE_BYTE_INDEXED);

    @Before
    public void setUp() throws Exception {
        mockDownloaderService.addContent("download image from:\"http://here.com\"".getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write(image, "png", out);
        mockDownloaderService.addContent(out.toByteArray());
        source = new SourceDefinition("testing", "http://test.com/<yyyy>-<MM>-<dd>", "from:\"([^\"].*)\"");
    }

    @Test
    public void testNullSource() {
        CartoonStrip strip = transformer.transform(null);
        assertNull(strip);
    }

    @Test
    public void nameIsSet() {
        CartoonStrip strip = transformer.transform(source);
        assertEquals(source.getName(), strip.getName());
    }

    @Test
    public void urlNotNull() {
        CartoonStrip strip = transformer.transform(source);
        assertNotNull(strip.getSource());
    }

    @Test
    public void urlPatternExtended() throws Exception {
        CartoonStrip strip = transformer.transform(source);
        assertEquals(new URL("http://test.com/2000-01-25"), strip.getSource());
        assertEquals(new URL("http://test.com/2000-01-25"), mockDownloaderService.getUrls().get(0));
    }

    @Test
    public void downloadImageFromUrl() throws Exception {
        CartoonStrip strip = transformer.transform(source);
        assertEquals(2, mockDownloaderService.getUrls().size());
        assertEquals(new URL("http://here.com"), mockDownloaderService.getUrls().get(1));
        //image objects have to equals method, therefore comparing String version without object ID (substring(22))
        assertEquals(image.toString().substring(22), strip.getImage().toString().substring(22));
    }

}
