package es.enrodpre.store.rest.adapters.exceptions;

import java.util.NoSuchElementException;
import javax.servlet.http.HttpServletRequest;
import static org.junit.Assert.assertEquals;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.mockito.Mockito.mock;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 *
 * @author enrodpre
 */
public class ApiExceptionHandlerTest {

    private static ApiExceptionHandler handler;
    private static ServletWebRequest request;

    @BeforeClass
    public static void setUpClass() {
        handler = new ApiExceptionHandler();
        request = new ServletWebRequest(mock(HttpServletRequest.class));
    }

    @Test
    public void testHandleProductNotFoundException() {
        ResponseEntity<ExceptionResponse> response
                = handler.handleProductNotFoundException(
                        new NoSuchElementException(),
                        request
                );
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
        assertEquals("Product not found", response.getBody().getError());
    }

    @Test
    public void testHandleWrongParameterType() {
        ResponseEntity<ExceptionResponse> response
                = handler.handleWrongParameterType(
                        new NumberFormatException(),
                        request
                );
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Wrong type of some parameter", response.getBody().getError());
    }

    @Test
    public void testHandleMethodArgumentNotValid() {
        ResponseEntity<Object> response
                = handler.handleMethodArgumentNotValid(
                        mock(MethodArgumentNotValidException.class),
                        mock(HttpHeaders.class),
                        HttpStatus.BAD_GATEWAY,
                        request
                );
        assertEquals(HttpStatus.BAD_GATEWAY, response.getStatusCode());
        assertEquals("Method not valid. Only GET", ((ExceptionResponse) response.getBody()).getError());
    }

    @Test
    public void testHandleNoHandlerFoundException() {
        ResponseEntity<Object> response
                = handler.handleNoHandlerFoundException(
                        mock(NoHandlerFoundException.class),
                        mock(HttpHeaders.class),
                        HttpStatus.NOT_FOUND,
                        request
                );
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Malformed URL", ((ExceptionResponse) response.getBody()).getError());
        
        response = handler.handleNoHandlerFoundException(
                        mock(NoHandlerFoundException.class),
                        mock(HttpHeaders.class),
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        request
                );
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Internal server error", ((ExceptionResponse) response.getBody()).getError());
        
        response = handler.handleNoHandlerFoundException(
                        mock(NoHandlerFoundException.class),
                        mock(HttpHeaders.class),
                        HttpStatus.BANDWIDTH_LIMIT_EXCEEDED, // Whatever status
                        request
                );
        assertEquals(HttpStatus.BANDWIDTH_LIMIT_EXCEEDED, response.getStatusCode());
        assertEquals("Something unknown went wrong", ((ExceptionResponse) response.getBody()).getError());
    }

}
