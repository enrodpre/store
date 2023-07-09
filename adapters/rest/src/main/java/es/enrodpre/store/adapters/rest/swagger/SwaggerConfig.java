package es.enrodpre.store.adapters.rest.swagger;

import java.util.Collections;
import java.util.HashSet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 *
 * @author enrodpre
 */
@Configuration
public class SwaggerConfig implements WebMvcConfigurer {

    @Value("${app.name}")
    private String appName;

    @Value("${app.description}")
    private String appDescription;

    @Value("${app.version}")
    private String appVersion;

    @Value("${app.artifactId}")
    private String appArtifactId;

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.ant("/"))
                .build()
                .apiInfo(getApiInfo())
                .groupName(appArtifactId)
                .produces(new HashSet<>(Collections.singletonList(MediaType.APPLICATION_JSON_VALUE)))
                .useDefaultResponseMessages(true);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/swagger/index.html").addResourceLocations("classpath:/META-INF/resources/");
    }

    protected ApiInfo getApiInfo() {
        return new ApiInfoBuilder()
                .title(appName)
                .description(appDescription)
                .version(appVersion)
                .contact(new Contact(appName, "", ""))
                .build();
    }

}
