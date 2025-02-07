package biz.picosoft.demo.repository;

import biz.picosoft.demo.domain.Produit;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for the Produit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProduitRepository extends JpaRepository<Produit, Long>, JpaSpecificationExecutor<Produit> {
    List<Produit> findByIdIn(List<Long> ids);
    List<Produit> findByCategorieId(Long categorieId);


}
