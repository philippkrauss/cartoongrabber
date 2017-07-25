package cartoongrabber.transformer;

import cartoongrabber.model.SourceDefinition;
import cartoongrabber.tools.PropertyFileRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:/spring/integration/integration-config-test.xml")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class SourceToDefinitionTransformerTest {

    @Autowired
    private SourceToDefinitionTransformer transformer;

    @Autowired
    private PropertyFileRepository repository;

    @Before
    public void setUp() {
        Properties p = new Properties();
        p.put(SourceToDefinitionTransformer.NAME_PROPERTY, "dilbert");
        p.put(SourceToDefinitionTransformer.BASE_URL_PROPERTY, "http://dilbert.com/strip/<yyyy>-<MM>-<dd>");
        p.put(SourceToDefinitionTransformer.IMAGE_PATTERN_PROPERTY, "img-pattern1");
        repository.put("dilbert", p);
        p = new Properties();
        p.put(SourceToDefinitionTransformer.NAME_PROPERTY, "zits");
        p.put(SourceToDefinitionTransformer.BASE_URL_PROPERTY, "http://zitscomics.com/comics/<MMMMM>-<dd>-<yyyy>");
        p.put(SourceToDefinitionTransformer.IMAGE_PATTERN_PROPERTY, "img-pattern2");
        repository.put("zits", p);
    }

    @Test
    public void testNull() {
        try {
            transformer.transform(null);
            fail("expected exception");
        } catch (RuntimeException e) {
        }
    }

    @Test
    public void testOneDefinition() {
        SourceDefinition sourceDefinition = transformer.transform("dilbert");
        assertEquals("dilbert", sourceDefinition.getName());
        assertEquals("http://dilbert.com/strip/<yyyy>-<MM>-<dd>", sourceDefinition.getBaseUrl());
        assertEquals("img-pattern1", sourceDefinition.getImagePattern());
    }

    @Test
    public void testAnotherDefinition() {
        SourceDefinition sourceDefinition = transformer.transform("zits");
        assertEquals("zits", sourceDefinition.getName());
        assertEquals("http://zitscomics.com/comics/<MMMMM>-<dd>-<yyyy>", sourceDefinition.getBaseUrl());
        assertEquals("img-pattern2", sourceDefinition.getImagePattern());
    }
}
