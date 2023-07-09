package es.enrodpre.store.usecases;

import es.enrodpre.store.domain.model.Product;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 *
 * @author enrodpre
 */
@Component
public interface ProductUseCases {
    Product getById(int id);
    List<Product> weightedSortBySalesAndStock(double salesWeight, double stockWeight);
}
