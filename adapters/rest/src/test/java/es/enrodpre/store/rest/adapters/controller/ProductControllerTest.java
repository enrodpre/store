package es.enrodpre.store.rest.adapters.controller;

import es.enrodpre.store.usecases.ProductUseCases;
import java.util.NoSuchElementException;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 *
 * @author enrodpre
 */
public class ProductControllerTest {

    private static ProductUseCases useCases;

    private static ProductController controller;

    @BeforeClass
    public static void setUpClass() {
        useCases = mock(ProductUseCases.class);
        controller = new ProductController(useCases);
    }

    @Test
    public void testReadByIdThatExists() {
        // Valid request
        controller.readById(1);
        verify(useCases).getById(1);

    }

    @Test(expected = NoSuchElementException.class)
    public void testReadByIdThatNotExists() {
        // Product not found
        when(useCases.getById(2)).thenThrow(NoSuchElementException.class);
        controller.readById(2);
    }

    /**
     * Test of readById method, of class ProductController.
     */
    @Test
    public void testWeightedSortBySalesAndStock() {
        // Valid request
        controller.weightedSortBySalesAndStock(0.1, 0.9);
        verify(useCases).weightedSortBySalesAndStock(0.1, 0.9);

    }

}
