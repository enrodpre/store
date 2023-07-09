package es.enrodpre.store.repositories.adapter;

import es.enrodpre.store.domain.adapter.out.ProductOutputPort;
import es.enrodpre.store.domain.model.Product;
import es.enrodpre.store.repositories.h2.repository.ProductRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author enrodpre
 */
public class ProductH2Adapter implements ProductOutputPort {

    private final ProductRepository repository;

    @Autowired
    public ProductH2Adapter(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public Product read(int id) {
        return repository
                .findById(id)
                .orElseThrow()
                .toProduct();
    }

    @Override
    public List<Product> readAll() {
        return repository
                .findAll()
                .stream()
                .map(entity -> entity.toProduct())
                .collect(Collectors.toList());
    }

}
