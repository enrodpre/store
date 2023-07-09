package es.enrodpre.store.domain.exceptions;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author enrodpre
 */
public class ReflectionExceptionTest {

    @Test
    public void testConstructor() {
        Exception ex = new Exception("Exception message");
        ReflectionException reflectionException = new ReflectionException(ex);
        assertEquals(ex, reflectionException.getCause());
    }
    
}
