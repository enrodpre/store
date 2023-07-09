package es.enrodpre.store.repositories.h2.repository;

import es.enrodpre.store.repositories.h2.entities.StockEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author enrodpre
 */
@Repository
public interface StockRepository extends JpaRepository<StockEntity, Integer>{
}
