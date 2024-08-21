package biz.picosoft.demo.repository;

import biz.picosoft.demo.domain.ProduitDemandee;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
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
}
