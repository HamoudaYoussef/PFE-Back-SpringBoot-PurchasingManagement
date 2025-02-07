package biz.picosoft.demo.repository;

import biz.picosoft.demo.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for the ProduitOffert entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProduitOffertRepository extends JpaRepository<ProduitOffert, Long>, JpaSpecificationExecutor<ProduitOffert>  {
    @Query("SELECT po FROM ProduitOffert po WHERE po.offre.id = :offreId")
    List<ProduitOffert> findByOffreId(Long offreId);

    @Query("SELECT po.bonCommande FROM ProduitOffert po WHERE po.id = :produitOffertId")
    BonCommande getBonCommandeByProduitOffertId(Long produitOffertId);

    List<ProduitOffert> getProduitOffertsByBonCommande_Id(Long produitOffertId);

    @Query("SELECT po.offre FROM ProduitOffert po WHERE po.id = :id")
    Offre getOffreByProduitOffertId(Long id);

    List<ProduitOffert> findByProduitAndOffre_DemandeDevisId(Produit produit, Long demandeDevisId);


    @Query("SELECT DISTINCT po.bonCommande.id FROM ProduitOffert po WHERE po.offre.id = :offreId")
    List<Long> findDistinctBonCommandeIdsByOffre(Long offreId);


}

