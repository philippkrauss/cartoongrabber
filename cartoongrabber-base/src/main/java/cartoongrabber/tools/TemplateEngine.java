package cartoongrabber.tools;

import cartoongrabber.model.CartoonStrip;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.stereotype.Component;

import java.io.StringWriter;
import java.util.List;
import java.util.Properties;

/**
 * Uses apache velocity template engine to render a list of {@link CartoonStrip} into HTML format.
 */
@Component
public class TemplateEngine implements RenderService {

    private final Template template;

    public TemplateEngine() {
        VelocityEngine velocityEngine = new VelocityEngine();
        Properties p = new Properties();
        p.setProperty("resource.loader", "classpath");
        p.setProperty("classpath.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        velocityEngine.init(p);
        template = velocityEngine.getTemplate("htmlTemplate.vm");
    }

    public String render(List<CartoonStrip> cartoons) {
        VelocityContext context = new VelocityContext();
        context.put("cartoons", cartoons);
        StringWriter writer = new StringWriter();
        template.merge(context, writer);
        return writer.toString();
    }

}
