package biz.picosoft.demo.repository;

import biz.picosoft.demo.domain.Fournisseur;
import biz.picosoft.demo.domain.Produit;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for the Fournisseur entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FournisseurRepository extends JpaRepository<Fournisseur, Long>, JpaSpecificationExecutor<Fournisseur> {
    @Query("SELECT p FROM Produit p JOIN p.fournisseurs f WHERE f.id = :fournisseurId")
    List<Produit> findProduitsByFournisseurId(Long fournisseurId);
}
