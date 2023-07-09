package es.enrodpre.store.domain.sorters;

import es.enrodpre.store.domain.model.Product;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * Interface for custom sorter
 *
 * @author enrodpre
 */
public interface ProductSorter {
     List<Product> sort(List<Product> list, Map<List<Field>, Double> weights);
}
