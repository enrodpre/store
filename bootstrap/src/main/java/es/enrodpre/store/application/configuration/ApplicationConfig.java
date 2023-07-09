package es.enrodpre.store.application.configuration;

import es.enrodpre.store.domain.adapter.out.ProductOutputPort;
import es.enrodpre.store.domain.services.ProductService;
import es.enrodpre.store.domain.services.impl.ProductServiceImpl;
import es.enrodpre.store.ports.in.ProductInputPort;
import es.enrodpre.store.repositories.adapter.ProductH2Adapter;
import es.enrodpre.store.repositories.h2.entities.ProductEntity;
import es.enrodpre.store.repositories.h2.repository.ProductRepository;

import es.enrodpre.store.rest.adapters.controller.ProductController;
import es.enrodpre.store.rest.adapters.exceptions.ApiExceptionHandler;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import es.enrodpre.store.usecases.ProductUseCases;
import org.springframework.context.annotation.ComponentScan;

/**
 *
 * @author enrodpre
 */
@ComponentScan(basePackageClasses = {ApiExceptionHandler.class})
@EntityScan(basePackageClasses = {ProductEntity.class})
@EnableJpaRepositories(basePackageClasses = {ProductRepository.class})
@Configuration
public class ApplicationConfig {

    @Bean
    public ProductOutputPort productOutputPort(ProductRepository repository) {
        return new ProductH2Adapter(repository);
    }

    @Bean
    public ProductController productController(ProductUseCases useCase) {
        return new ProductController(useCase);
    }

    @Bean
    public ProductService productService(ProductOutputPort port) {
        return new ProductServiceImpl(port);
    }

    @Bean
    public ProductUseCases productUseCases(ProductService service) {
        return new ProductInputPort(service);
    }
}
