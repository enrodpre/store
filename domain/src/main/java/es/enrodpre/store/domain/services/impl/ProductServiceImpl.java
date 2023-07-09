package es.enrodpre.store.domain.services.impl;

import es.enrodpre.store.domain.adapter.out.ProductOutputPort;
import es.enrodpre.store.domain.exceptions.ReflectionException;
import es.enrodpre.store.domain.model.Product;
import es.enrodpre.store.domain.services.ProductService;
import es.enrodpre.store.domain.sorters.ProductSorter;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author enrodpre
 */

public class ProductServiceImpl implements ProductService {

    private final ProductOutputPort port;
    
    @Autowired
    public ProductServiceImpl(ProductOutputPort port) {
        this.port = port;
    }
    
    @Override
    public Product read(int id) {
        return port.read(id);
    }

    @Override
    public List<Product> readAll() {
        return port.readAll();
    }

    @Override
    public List<Product> readAllSorted(Class<? extends ProductSorter> sorterClass, Map<List<Field>, Double> weights) {
        try {
            return sorterClass.getDeclaredConstructor()
                    .newInstance()
                    .sort(readAll(), weights);
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            throw new ReflectionException(ex);
        }
    }

}
