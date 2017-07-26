package cartoongrabber.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

/**
 * The start class for the web application
 */
@SpringBootApplication
@ImportResource({"/spring/integration/memory-backend-config.xml", "/spring/webBeans.xml"})
public class WebApplication {

    public static void main(String... args) {
        SpringApplication.run(WebApplication.class, args);
    }
}
