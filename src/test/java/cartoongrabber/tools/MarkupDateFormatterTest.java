package cartoongrabber.tools;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

/**
 * Created by Philipp Krauß on 17.07.2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:/spring/integration/integration-config-test.xml")
public class MarkupDateFormatterTest {

    @Autowired
    private MarkupDateFormatter formatter;

    private final LocalDate date = LocalDate.of(2000, 1, 25);

    @Test
    public void testFormatYear() {
        String formatted = formatter.format("http://dilbert.com/strip/<yyyy>", date);
        assertEquals("http://dilbert.com/strip/2000", formatted);
    }

    @Test
    public void testFormatTwoPlaceholders() {
        String formatted = formatter.format("http://dilbert.com/strip/<yyyy>-<MM>", date);
        assertEquals("http://dilbert.com/strip/2000-01", formatted);
    }

    @Test
    public void testFormatMultiplePlaceholders() {
        String formatted = formatter.format("http://dilbert.com/strip/<yyyy>-<MM>-<dd>", date);
        assertEquals("http://dilbert.com/strip/2000-01-25", formatted);
    }

    @Test
    public void testNameOfMonth() {
        String formatted = formatter.format("<MMMM>", date);
        assertEquals("January", formatted);
    }
}
