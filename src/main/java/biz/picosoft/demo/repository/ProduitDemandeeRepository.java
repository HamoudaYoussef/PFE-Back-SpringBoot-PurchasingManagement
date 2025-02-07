package biz.picosoft.demo.repository;

import biz.picosoft.demo.domain.DemandeAchat;
import biz.picosoft.demo.domain.ProduitCommandee;
import biz.picosoft.demo.domain.ProduitDemandee;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Spring Data JPA repository for the ProduitDemandee entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProduitDemandeeRepository extends JpaRepository<ProduitDemandee, Long>, JpaSpecificationExecutor<ProduitDemandee> {
    List<ProduitDemandee> findByDemandeAchatId(Long demandeAchatId);
    @Query("SELECT p FROM ProduitDemandee p WHERE p.demandeAchat.datedemande >= :twoWeeksAgo")
    List<ProduitDemandee> findRecentProduitsDemandee(LocalDate twoWeeksAgo);
    void deleteByDemandeAchatId(Long demandeachatId);

    @Query("SELECT pd.demandeAchat FROM ProduitDemandee pd WHERE pd.id = :id")
    DemandeAchat getDemandeAchatByProduitDemandeeId(Long id);

    List<ProduitDemandee> findByProduit_Id(Long produitId);

    List<ProduitDemandee> findByDemandeAchat_IdAndProduit_Id(Long demandeAchatId, Long produitId);  // Nouvelle méthode


    // List<ProduitDemandee> findByDemandeDevisId(Long demandeDevisId);
  /*  @Query("SELECT pd FROM ProduitDemandee pd WHERE pd.demandeDevis.id = :demandeDevisId")
    List<ProduitDemandee> findByDemandeDevis_Id(Long demandeDevisId);*/
}
