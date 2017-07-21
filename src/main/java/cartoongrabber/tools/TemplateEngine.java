package cartoongrabber.tools;

import cartoongrabber.model.CartoonStrip;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import java.io.StringWriter;
import java.util.List;
import java.util.Properties;

public class TemplateEngine implements RenderService {

    //    private final VelocityEngine velocityEngine;
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
