package cartoongrabber.tools;

import cartoongrabber.model.CartoonStrip;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static cartoongrabber.tools.TestTools.createCartoon;
import static org.junit.Assert.assertNotNull;

public class TemplateEngineTest {

    private final List<CartoonStrip> oneCartoon = Collections.singletonList(createCartoon());

    @Test
    public void testRender() {
        TemplateEngine templateEngine = new TemplateEngine();
        String rendered = templateEngine.render(oneCartoon);
        assertNotNull(rendered);
    }
}
