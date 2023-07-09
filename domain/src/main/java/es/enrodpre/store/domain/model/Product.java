package es.enrodpre.store.domain.model;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.Generated;

/**
 *
 * @author enrodpre
 */
@Builder
@Data
@Generated
@lombok.AllArgsConstructor
@Schema(description = "Product information")
public class Product {

    @NotNull(message = "Product name cannot be null")
    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "Product name", example = "Grey T-Shirt")
    private String name;
    
    @Min(value = 0, message = "Sales units cannot be negative")
    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "Number of sales", example = "50")
    private int salesUnits;
    
    @Min(value = 0, message = "Short units cannot be negative")
    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "Number of short-sized on the stock", example = "4")
    private int shortUnits;
    
    @Min(value = 0, message = "Medium units cannot be negative")
    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "Number of medium-sized on the stock", example = "5")
    private int mediumUnits;
    
    @Min(value = 0, message = "Large units cannot be negative")
    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "Number of large-sized on the stock", example = "7")
    private int largeUnits;
}
