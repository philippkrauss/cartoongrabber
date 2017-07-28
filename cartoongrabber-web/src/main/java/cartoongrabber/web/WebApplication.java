package cartoongrabber.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ImportResource;

/**
 * The start class for the web application
 */
@SpringBootApplication
@ImportResource({"/spring/integration/memory-backend-config.xml", "/spring/webBeans.xml"})
public class WebApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(WebApplication.class);
    }

    public static void main(String... args) {
        SpringApplication.run(WebApplication.class, args);
    }
}
