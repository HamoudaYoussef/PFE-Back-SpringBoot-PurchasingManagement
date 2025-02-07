package biz.picosoft.demo.repository;


import biz.picosoft.demo.domain.DemandeDevis;
import biz.picosoft.demo.domain.Fournisseur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the DemandeDevis entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DemandeDevisRepository extends JpaRepository<DemandeDevis, Long>, JpaSpecificationExecutor<DemandeDevis> {

    List<DemandeDevis> findByDemandeAchat_Id(Long demandeAchatId);
    @Query("SELECT f.id FROM Fournisseur f  WHERE f.id = :fournisseurId")
    String findFournisseurName(Long fournisseurId);

    @Query("SELECT d.fournisseur FROM DemandeDevis d WHERE d.id = :demandeDevisId")
    Fournisseur findFournisseurByDemandeDevisId(Long demandeDevisId);

    @Query("SELECT d FROM DemandeDevis d ORDER BY d.id DESC")
    List<DemandeDevis> findLatestDemandeDevis();

    List<DemandeDevis> findByFournisseur(Fournisseur fournisseur);



}
