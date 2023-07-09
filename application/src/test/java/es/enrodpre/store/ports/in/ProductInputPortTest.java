package es.enrodpre.store.ports.in;

import es.enrodpre.store.domain.services.ProductService;
import es.enrodpre.store.domain.sorters.impl.WeightedProductSorter;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 *
 * @author enrodpre
 */
public class ProductInputPortTest {

    private static ProductService service;
    private static ProductInputPort port;

    @Before
    public void setUp() {
        service = mock(ProductService.class);
        port = new ProductInputPort(service);
    }

    /**
     * Test of getById method, of class ProductInputPort.
     */
    @Test
    public void testGetById() {
        port.getById(0);
        verify(service).read(0);
    }

    /**
     * Test of weightedSortBySalesAndStock method, of class ProductInputPort.
     */
    @Test
    public void testNormalizedWeightedSortBySalesAndStock() {
        port.weightedSortBySalesAndStock(0.5, 0.5);

        ArgumentCaptor<Map<List<Field>, Double>> argument = ArgumentCaptor.forClass(Map.class);
        verify(service).readAllSorted(eq(WeightedProductSorter.class), argument.capture());
        assertEquals(List.of(0.5, 0.5), List.copyOf(argument.getValue().values()));
    }

    @Test
    public void testNotNormalizedWeightedSortBySalesAndStock() {
        port.weightedSortBySalesAndStock(3, 4);

        ArgumentCaptor<Map<List<Field>, Double>> argument = ArgumentCaptor.forClass(Map.class);
        verify(service).readAllSorted(eq(WeightedProductSorter.class), argument.capture());
        assertEquals(List.of((double) 3 / 7, (double) 4 / 7), List.copyOf(argument.getValue().values()));
    }
}
