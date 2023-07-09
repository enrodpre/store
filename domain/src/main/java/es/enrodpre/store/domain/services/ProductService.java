package es.enrodpre.store.domain.services;

import es.enrodpre.store.domain.model.Product;
import es.enrodpre.store.domain.sorters.ProductSorter;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

/**
 *
 * @author enrodpre
 */
@Service
public interface ProductService {

    Product read(final int id);

    List<Product> readAll();
    
    List<Product> readAllSorted(Class<? extends ProductSorter> sorter, Map<List<Field>, Double> weights);

}
