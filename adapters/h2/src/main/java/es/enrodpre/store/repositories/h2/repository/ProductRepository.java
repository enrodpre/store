package es.enrodpre.store.repositories.h2.repository;

import es.enrodpre.store.repositories.h2.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author enrodpre
 */
@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Integer>{
}
