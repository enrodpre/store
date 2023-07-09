package es.enrodpre.store.domain.sorters.impl;

import es.enrodpre.store.domain.model.Product;
import es.enrodpre.store.domain.services.impl.ProductServiceImpl;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author enrodpre
 */
public class WeightedProductSorterTest {

    private static List<Product> products;

    @BeforeClass
    public static void setUpClass() {
        Product p1 = Product.builder()
                .name("P1")
                .salesUnits(100)
                .shortUnits(3)
                .mediumUnits(54)
                .largeUnits(7)
                .build();
        Product p2 = Product.builder()
                .name("P2")
                .salesUnits(50)
                .shortUnits(42)
                .mediumUnits(5)
                .largeUnits(100)
                .build();
        Product p3 = Product.builder()
                .name("P3")
                .salesUnits(150)
                .shortUnits(1)
                .mediumUnits(5)
                .largeUnits(7)
                .build();
        Product p4 = Product.builder()
                .name("P4")
                .salesUnits(150)
                .shortUnits(1)
                .mediumUnits(6)
                .largeUnits(7)
                .build();
        Product p5 = Product.builder()
                .name("P5")
                .salesUnits(700)
                .shortUnits(1)
                .mediumUnits(5)
                .largeUnits(7)
                .build();

        products = List.of(p1, p2, p3, p4, p5);
    }

    private Field getProductField(String name) {
        try {
            return Product.class.getDeclaredField(name);
        } catch (NoSuchFieldException | SecurityException ex) {
            Logger.getLogger(ProductServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException(ex);
        }
    }

    private Map<List<Field>, Double> getSalesStockWeightMap(double salesWeight, double stockWeight) {
        return Map.of(
                List.of(getProductField("salesUnits")), salesWeight,
                List.of(
                        getProductField("shortUnits"),
                        getProductField("mediumUnits"),
                        getProductField("largeUnits")
                ), stockWeight
        );
    }

    /**
     * Test of sort method, of class WeightedProductSorter.
     */
    @Test
    public void testEquitativeSort() {
        List<Product> result = new WeightedProductSorter().sort(products, getSalesStockWeightMap(0.5, 0.5));

        assertEquals("P3", result.get(0).getName());
        assertEquals("P1", result.get(1).getName());
        assertEquals("P4", result.get(2).getName());
        assertEquals("P2", result.get(3).getName());
        assertEquals("P5", result.get(4).getName());
    }

    /**
     * Test of sort method, of class WeightedProductSorter.
     */
    @Test
    public void testInequitativeSort() {
        List<Product> result = new WeightedProductSorter().sort(products, getSalesStockWeightMap(0.7, 0.3));

        assertEquals("P2", result.get(0).getName());
        assertEquals("P1", result.get(1).getName());
        assertEquals("P3", result.get(2).getName());
        assertEquals("P4", result.get(3).getName());
        assertEquals("P5", result.get(4).getName());
    }
}
