package cartoongrabber.splitter;

import cartoongrabber.tools.PropertyFileRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Properties;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:/spring/integration/integration-config-test.xml")
public class SourceSplitterTest {

    @Autowired
    private PropertyFileRepository repository;

    @Autowired
    private SourceSplitter splitter;

    @Test
    public void testOneArgument() {
        List<String> splitted = splitter.split("a");
        assertEquals(1, splitted.size());
        assertEquals("a", splitted.get(0));
    }

    @Test
    public void testTwoArguments() {
        List<String> splitted = splitter.split("a,b");
        assertEquals(2, splitted.size());
        assertEquals("a", splitted.get(0));
        assertEquals("b", splitted.get(1));
    }

    @Test
    public void testEmptyArguments() {
        List<String> splitted = splitter.split("a,,b");
        assertEquals(2, splitted.size());
        assertEquals("a", splitted.get(0));
        assertEquals("b", splitted.get(1));
    }

    @Test
    public void testComma() {
        assertEquals(0, splitter.split(",").size());
    }

    @Test
    public void testEmpty() {
        assertEquals(0, splitter.split("").size());
    }

    @Test
    public void testAll() {
        repository.put("a", new Properties());
        repository.put("b", new Properties());

        List<String> splitted = splitter.split("all");
        assertEquals(2, splitted.size());
        assertEquals("a", splitted.get(0));
        assertEquals("b", splitted.get(1));
    }
}
