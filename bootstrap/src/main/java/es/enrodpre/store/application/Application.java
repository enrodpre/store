
package es.enrodpre.store.application;

import es.enrodpre.store.application.configuration.ApplicationConfig;
import es.enrodpre.store.adapters.rest.swagger.SwaggerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

/**
 *
 * @author enrodpre
 */
@SpringBootApplication
@ComponentScan(basePackageClasses = {ApplicationConfig.class, SwaggerConfig.class})
@Import(SwaggerConfig.class)
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
