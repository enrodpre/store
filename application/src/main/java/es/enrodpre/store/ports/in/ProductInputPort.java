package es.enrodpre.store.ports.in;

import es.enrodpre.store.domain.exceptions.ReflectionException;
import es.enrodpre.store.domain.model.Product;
import es.enrodpre.store.domain.services.ProductService;
import es.enrodpre.store.domain.sorters.impl.WeightedProductSorter;
import es.enrodpre.store.usecases.ProductUseCases;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author enrodpre
 */
public class ProductInputPort implements ProductUseCases {

    private final ProductService service;

    @Autowired
    public ProductInputPort(ProductService service) {
        this.service = service;
    }

    @Override
    public Product getById(int id) {
        return service.read(id);
    }

    private Field getProductField(String name) {
        try {
            return Product.class.getDeclaredField(name);
        } catch (NoSuchFieldException | SecurityException ex) {
            throw new ReflectionException(ex);
        }
    }

    private Map<List<Field>, Double> createWeightMap(double salesWeight, double stockWeight) {
        Map<List<Field>, Double> weightMap = new HashMap<>();
        weightMap.put(List.of(getProductField("salesUnits")), salesWeight);
        weightMap.put(
                List.of(
                        getProductField("shortUnits"),
                        getProductField("mediumUnits"),
                        getProductField("largeUnits")
                ), stockWeight);
        return weightMap;
    }

    @Override
    public List<Product> weightedSortBySalesAndStock(double salesWeight, double stockWeight) {
        // Normalize weights so they sum 1
        double totalWeight = salesWeight + stockWeight;
        if (totalWeight != 1) {
            salesWeight = salesWeight / totalWeight;
            stockWeight = stockWeight / totalWeight;
        }
        
        Map<List<Field>, Double> weightMap = createWeightMap(salesWeight, stockWeight);

        return service.readAllSorted(WeightedProductSorter.class, weightMap);
    }

}
