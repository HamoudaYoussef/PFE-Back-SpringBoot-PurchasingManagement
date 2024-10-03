package biz.picosoft.demo.repository;

import biz.picosoft.demo.domain.DemandeAchat;
import biz.picosoft.demo.domain.ennumeration.StatutDA;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for the DemandeAchat entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DemandeAchatRepository extends JpaRepository<DemandeAchat, Long>, JpaSpecificationExecutor<DemandeAchat> {

    @Query("SELECT d FROM DemandeAchat d WHERE d.statut = 'termine'")
    List<DemandeAchat> findByStatutTermine();
    @Query("SELECT d FROM DemandeAchat d WHERE d.statut = 'Rejetee'")
    List<DemandeAchat> findByStatutRejetee();

    @Query("SELECT COUNT(d) FROM DemandeAchat d WHERE MONTH(d.datedemande) =?1")
    Long countByDateCreationMois(int mois);


    @Query("SELECT COUNT(d) FROM DemandeAchat d WHERE d.statut = :statut")
    Long countByStatut(StatutDA statut);
}
