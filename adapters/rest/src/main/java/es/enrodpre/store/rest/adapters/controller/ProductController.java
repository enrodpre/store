package es.enrodpre.store.rest.adapters.controller;

import es.enrodpre.store.domain.model.Product;
import java.util.List;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import es.enrodpre.store.usecases.ProductUseCases;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.constraints.Min;

/**
 *
 * @author enrodpre
 */
@RestController
@Tag(name = "Product API", description = "Api for retrieving products")
public class ProductController {

    private final ProductUseCases useCase;

    @Autowired
    public ProductController(ProductUseCases useCase) {
        this.useCase = useCase;
    }

    @Operation(summary = "Get a product by id", description = "Returns a product as per the id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Retrieved product", content = {
            @Content(schema = @Schema(implementation = Product.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "400", description = "Wrong input type"),
        @ApiResponse(responseCode = "404", description = "Not found - The product was not found")
    })
    @GetMapping(value = "/product/{id}")
    public ResponseEntity<Product> readById(
            @PathVariable("id")
            @Parameter(name = "id", description = "Product id", example = "1") int id
    ) {
        return ResponseEntity.ok(useCase.getById(id));
    }

    @Operation(summary = "Get a sorted list of product", description = "Takes two parameters which are the sales and stock weight for sorting. If they don't sum up to 1, they will be normalized")
    @GetMapping(value = "/products/{sales_weight}/{stock_weight}")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Retrieved list of products", content = {
                        @Content(array = @ArraySchema(schema = @Schema(implementation = Product.class)), mediaType = "application/json")}),
        @ApiResponse(responseCode = "400", description = "Wrong input type")
    })
    public ResponseEntity<List<Product>> weightedSortBySalesAndStock(
            @PathVariable("sales_weight") @Min(value = 0) @Parameter(name = "sales_weight", description = "Sort weight for sales", example = "0.5") double salesWeights,
            @PathVariable("stock_weight") @Min(value = 0) @Parameter(name = "stock_weight", description = "Sort weight for stock", example = "0.5") double stockWeights) {
        List<Product> res = useCase.weightedSortBySalesAndStock(salesWeights, stockWeights);
        return ResponseEntity.ok(res);
    }

}
