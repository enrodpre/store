package es.enrodpre.store.domain.services.impl;

import es.enrodpre.store.domain.adapter.out.ProductOutputPort;
import es.enrodpre.store.domain.exceptions.ReflectionException;
import es.enrodpre.store.domain.model.Product;
import es.enrodpre.store.domain.services.ProductService;
import es.enrodpre.store.domain.sorters.ProductSorter;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 *
 * @author enrodpre
 */
public class ProductServiceImplTest {

    private static ProductService service;
    private static ProductOutputPort port;

    @BeforeClass
    public static void setUpClass() {
        port = mock(ProductOutputPort.class);
        service = new ProductServiceImpl(port);
    }

    @Test
    public void testReadAllSorted() {
        List<Product> expected = new ArrayList<>();
        when(port.readAll()).thenReturn(expected);
        assertEquals(expected, service.readAll());
    }

    private abstract class TestNoConstructorSorter implements ProductSorter {

    }

    @Test(expected = ReflectionException.class)
    public void testReadAllSortedWrongClass() {
        service.readAllSorted(TestNoConstructorSorter.class, null);
    }

}
