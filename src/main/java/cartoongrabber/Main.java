package cartoongrabber;

import cartoongrabber.cli.CartoonGrabberCli;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotatedBeanDefinitionReader;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.io.ClassPathResource;

/**
 * Created by Philipp Krauß on 14.07.2017.
 */
@Configuration
@ComponentScan
public class Main {

    private static final Logger LOG = LoggerFactory.getLogger(Main.class);

    public static void main(String... args) {
        LOG.info("starting cartoon grabber");
        GenericApplicationContext ctx = new GenericApplicationContext();
        XmlBeanDefinitionReader xmlReader = new XmlBeanDefinitionReader(ctx);
        xmlReader.loadBeanDefinitions(new ClassPathResource("applicationContext.xml"));
        xmlReader.loadBeanDefinitions(new ClassPathResource("applicationContext-integration.xml"));
        AnnotatedBeanDefinitionReader annotationReader = new AnnotatedBeanDefinitionReader(ctx);
        annotationReader.register(Main.class);
        ctx.refresh();

        CartoonGrabberCli cli = ctx.getBean(CartoonGrabberCli.class);
        cli.run(args);
    }
}
