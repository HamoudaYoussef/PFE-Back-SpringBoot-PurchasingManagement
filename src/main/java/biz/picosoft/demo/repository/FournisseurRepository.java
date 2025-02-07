package biz.picosoft.demo.repository;

import biz.picosoft.demo.domain.DemandeDevis;
import biz.picosoft.demo.domain.Fournisseur;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for the Fournisseur entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FournisseurRepository extends JpaRepository<Fournisseur, Long>, JpaSpecificationExecutor<Fournisseur> {
    /*@Query("SELECT p FROM Produit p JOIN p.fournisseurs f WHERE f.id = :fournisseurId")
    List<Produit> findProduitsByFournisseurId(Long fournisseurId);*/
    @Query("SELECT f.nom FROM Fournisseur f  WHERE f.id = :fournisseurId")
    String findFournisseurName(Long fournisseurId);


}
