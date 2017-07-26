package cartoongrabber;

import cartoongrabber.service.GrabberService;
import joptsimple.BuiltinHelpFormatter;
import joptsimple.OptionException;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.env.JOptCommandLinePropertySource;
import org.springframework.core.io.ClassPathResource;

import java.util.Arrays;

/**
 * Main class for cartoon grabber project.
 * Created by Philipp Krauß on 14.07.2017.
 */
@Configuration
@ComponentScan
public class Main {

    private final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String... args) throws Exception {
        Main main = new Main();
        main.run(args);
    }

    public void run(String... args) throws Exception {
        OptionParser parser = createOptionParser();
        try {
            OptionSet options = parser.parse(args);
            if (options.has("help")) {
                parser.printHelpOn(System.out);
                return;
            }
            log.info("starting cartoon grabber");
            GenericApplicationContext ctx = createSpringContext(options);
            GrabberService grabberService = ctx.getBean(GrabberService.class);
            grabberService.grab((String[]) options.nonOptionArguments().toArray(new String[0]));
        } catch (OptionException e) {
            parser.printHelpOn(System.out);
        }
    }

    private GenericApplicationContext createSpringContext(OptionSet options) {
        JOptCommandLinePropertySource jOptSource = new JOptCommandLinePropertySource(options);
        GenericApplicationContext ctx = new GenericApplicationContext();
        ctx.getEnvironment().getPropertySources().addFirst(jOptSource);
        XmlBeanDefinitionReader xmlReader = new XmlBeanDefinitionReader(ctx);
        xmlReader.loadBeanDefinitions(new ClassPathResource("spring/integration/html-backend-config.xml"));
        xmlReader.loadBeanDefinitions(new ClassPathResource("spring/cliBeans.xml"));
        ctx.refresh();
        return ctx;
    }

    private OptionParser createOptionParser() {
        OptionParser parser = new OptionParser();
        parser.nonOptions("sources to grab. Use 'all' to grab all sources")
                .ofType(String.class)
                .describedAs("sources");
        parser.accepts("outDir", "output directory")
                .withRequiredArg()
                .describedAs("directory")
                .defaultsTo("output");
        parser.accepts("configFile", "config file")
                .withRequiredArg()
                .describedAs("file")
                .defaultsTo("config/grabber.properties");
        parser.acceptsAll(Arrays.asList("help", "h", "?"), "show help")
                .forHelp();
        parser.formatHelpWith(new BuiltinHelpFormatter(120, 2));
        return parser;
    }
}
