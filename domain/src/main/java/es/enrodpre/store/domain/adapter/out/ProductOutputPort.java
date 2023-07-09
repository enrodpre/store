package es.enrodpre.store.domain.adapter.out;

import es.enrodpre.store.domain.model.Product;
import java.util.List;

/**
 *
 * @author enrodpre
 */
public interface ProductOutputPort {

    Product read(int id);

    List<Product> readAll();
}
