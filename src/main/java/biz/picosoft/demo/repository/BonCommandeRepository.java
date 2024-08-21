package biz.picosoft.demo.repository;

import biz.picosoft.demo.domain.BonCommande;
import biz.picosoft.demo.domain.Offre;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the BonCommande entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BonCommandeRepository extends JpaRepository<BonCommande, Long>, JpaSpecificationExecutor<BonCommande> {
    Offre findOffreById(Long bonCommandeId);

    @Query("SELECT COUNT(b) FROM BonCommande b WHERE MONTH(b.dateboncommande) =?1")
    Long countByDateCreationMois(int mois);

}
