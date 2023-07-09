package es.enrodpre.store.rest.adapters.exceptions;

import java.util.Date;
import java.util.NoSuchElementException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 *
 * @author enrodpre
 */
@RestControllerAdvice
public class ApiExceptionHandler extends  ResponseEntityExceptionHandler{

    private static final String PRODUCT_NOT_FOUND = "Product not found";
    private static final String WRONG_PARAMETER_TYPE = "Wrong type of some parameter";
    private static final String MALFORMED_URL = "Malformed URL";
    private static final String INTERNAL_SERVER_ERROR = "Internal server error";
    private static final String METHOD_NOT_VALID = "Method not valid. Only GET";
    private static final String UNKOWN_ERROR = "Something unknown went wrong";

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseBody
    public final ResponseEntity<ExceptionResponse> handleProductNotFoundException(NoSuchElementException ex, WebRequest request) {
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        return new ResponseEntity<>(buildExceptionResponse(request, PRODUCT_NOT_FOUND, status), status);
    }

    @ExceptionHandler(NumberFormatException.class)
    @ResponseBody
    public final ResponseEntity<ExceptionResponse> handleWrongParameterType(NumberFormatException ex, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(buildExceptionResponse(request, WRONG_PARAMETER_TYPE, status), status);
    }

    @Override
    public final ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request)  {
        return new ResponseEntity<>(buildExceptionResponse(request, METHOD_NOT_VALID, status), status);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status,  WebRequest request) {
        String message;
        switch (status.value()) {
            case 404:
                message = MALFORMED_URL;
                break;
            case 500:
                message = INTERNAL_SERVER_ERROR;
                break;
            default:
                message = UNKOWN_ERROR;
                break;
        }

        return new ResponseEntity<>(buildExceptionResponse(request, message, status), status);
    }

    private ExceptionResponse buildExceptionResponse(WebRequest request, String errorMessage, HttpStatus status) {
        ServletWebRequest webRequest = (ServletWebRequest) request;
        return ExceptionResponse.builder()
                .timestamp(new Date())
                .code(String.valueOf(status.value()))
                .error(errorMessage)
                .method(webRequest.getHttpMethod())
                .path(webRequest.getRequest().getRequestURI())
                .build();
    }
}
