package es.enrodpre.store.repositories.adapter;

import es.enrodpre.store.repositories.h2.entities.ProductEntity;
import es.enrodpre.store.repositories.h2.entities.StockEntity;
import es.enrodpre.store.repositories.h2.repository.ProductRepository;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 *
 * @author enrodpre
 */
public class ProductH2AdapterTest {

    private static ProductRepository repository;
    private static ProductH2Adapter adapter;

    @BeforeClass
    public static void setUpClass() {
        repository = mock(ProductRepository.class);
        adapter = new ProductH2Adapter(repository);
    }

    /**
     * Test of read method, of class ProductH2Adapter.
     */
    @Test
    public void testRead() {
        StockEntity stock = new StockEntity();
        ProductEntity product = new ProductEntity();
        product.setStock(stock);
        Optional<ProductEntity> optional = Optional.of(product);

        when(repository.findById(0)).thenReturn(optional);
        assertEquals(optional.get().toProduct(), adapter.read(0));
    }
    
    @Test(expected = NoSuchElementException.class)
    public void testReadNotFound() {
        when(repository.findById(1)).thenReturn(Optional.empty());
        adapter.read(1);
    }
            

    /**
     * Test of readAll method, of class ProductH2Adapter.
     */
    @Test
    public void testReadAll() {
        StockEntity stock = new StockEntity();
        ProductEntity product = new ProductEntity();
        product.setStock(stock);

        when(repository.findAll()).thenReturn(List.of(product));
        assertEquals(List.of(product.toProduct()), adapter.readAll());
    }

}
