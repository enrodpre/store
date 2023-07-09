package es.enrodpre.store.repositories.h2.entities;


import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import es.enrodpre.store.domain.model.Product;
import java.io.Serializable;
import javax.persistence.Column;
import lombok.Generated;

/**
 *
 * @author enrodpre
 */
@Entity
@Generated
@Table(name = "product")
@lombok.EqualsAndHashCode
public class ProductEntity implements Serializable {

    @Id
    private int id;

    @Column(unique = true)
    private String productName;

    private int sales;

    @lombok.Setter
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "stock", referencedColumnName = "id")
    private StockEntity stock;

    public Product toProduct() {
        return Product.builder()
                .name(this.productName)
                .salesUnits(this.sales)
                .shortUnits(this.stock.getShortUnits())
                .mediumUnits(this.stock.getMediumUnits())
                .largeUnits(this.stock.getLargeUnits())
                .build();

    }

}
