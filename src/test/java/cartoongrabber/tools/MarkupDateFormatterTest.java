package cartoongrabber.tools;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

/**
 * Created by Philipp Krauß on 17.07.2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:/spring/integration-config-test.xml")
public class MarkupDateFormatterTest {

    @Autowired
    private MarkupDateFormatter formatter;

    @Test
    public void testFormatYear() {
        String formatted = formatter.format("http://dilbert.com/strip/<yyyy>");
        assertEquals("http://dilbert.com/strip/2000", formatted);
    }

    @Test
    public void testFormatTwoPlaceholders() {
        String formatted = formatter.format("http://dilbert.com/strip/<yyyy>-<MM>");
        assertEquals("http://dilbert.com/strip/2000-01", formatted);
    }

    @Test
    public void testFormatMultiplePlaceholders() {
        String formatted = formatter.format("http://dilbert.com/strip/<yyyy>-<MM>-<dd>");
        assertEquals("http://dilbert.com/strip/2000-01-25", formatted);
    }
}
