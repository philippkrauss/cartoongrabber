package cartoongrabber.tools;

import org.junit.Test;

import java.io.StringReader;
import java.util.Map;
import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class SourcePropertiesParserTest {

    private final SourcePropertyParser parser = new SourcePropertyParser();

    private static final String ONE_CARTOON = "a.name=name a\na.baseUrl=base url a\na.imagePattern=image pattern a";
    private static final String TWO_CARTOONS = "a.name=name a\na.baseUrl=base url a\na.imagePattern=image pattern a\nb.name=name b\nb.baseUrl=base url b\nb.imagePattern=image pattern b";

    @Test
    public void testNotNull() {
        assertNotNull(parser.parse(new StringReader(ONE_CARTOON)));
    }

    @Test
    public void testOneParsed() {
        assertEquals(1, parser.parse(new StringReader(ONE_CARTOON)).size());
    }

    @Test
    public void testParseSourceName() {
        assertNotNull(parser.parse(new StringReader(ONE_CARTOON)).get("a"));
    }

    @Test
    public void testParseTwoSources() {
        Map<String, Properties> map = parser.parse(new StringReader(TWO_CARTOONS));
        assertEquals(2, map.size());
        assertNotNull(map.get("a"));
        assertNotNull(map.get("b"));
    }

    @Test
    public void testParseSourceContent() {
        Map<String, Properties> map = parser.parse(new StringReader(TWO_CARTOONS));
        Properties a = map.get("a");
        assertEquals(3, a.size());
        assertEquals("name a", a.getProperty("name"));
        assertEquals("base url a", a.getProperty("baseUrl"));
        assertEquals("image pattern a", a.getProperty("imagePattern"));

        Properties b = map.get("b");
        assertEquals(3, b.size());
        assertEquals("name b", b.getProperty("name"));
        assertEquals("base url b", b.getProperty("baseUrl"));
        assertEquals("image pattern b", b.getProperty("imagePattern"));
    }

}
