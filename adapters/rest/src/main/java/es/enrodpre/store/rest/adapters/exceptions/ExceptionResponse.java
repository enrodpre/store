package es.enrodpre.store.rest.adapters.exceptions;

import java.util.Date;
import lombok.Builder;
import lombok.Data;
import lombok.Generated;
import org.springframework.http.HttpMethod;

/**
 *
 * @author enrodpre
 */
@Builder
@Generated
@Data
public class ExceptionResponse {

    private Date timestamp;
    private String code;
    private String error;
    private HttpMethod method;
    private String path;
}
