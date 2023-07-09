package es.enrodpre.store.repositories.h2.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Generated;
import lombok.Getter;

/**
 *
 * @author enrodpre
 */

@Entity
@Generated
@Getter
@Table(name = "stock")
public class StockEntity implements Serializable {

    @Id
    @GeneratedValue
    private int id;

    private int shortUnits;
    
    private int mediumUnits;
    
    private int largeUnits;
}
