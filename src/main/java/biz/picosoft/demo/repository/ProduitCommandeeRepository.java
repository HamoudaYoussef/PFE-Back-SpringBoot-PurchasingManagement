package biz.picosoft.demo.repository;

import biz.picosoft.demo.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Repository
public interface ProduitCommandeeRepository extends JpaRepository<ProduitCommandee, Long>, JpaSpecificationExecutor<ProduitCommandee> {

    List<ProduitCommandee> findByDemandeDevis_Id(Long demandeDevisId);

    @Query("SELECT pc.demandeDevis FROM ProduitCommandee pc WHERE pc.id = :id")
    DemandeDevis getDemandeDevisByProduitCommandeeId(Long id);

    List<ProduitCommandee> findByProduit(Produit produit);

    List<ProduitCommandee> findByProduitAndDemandeDevis_DemandeAchatId(Produit produit, Long demandeAchatId);
    List<ProduitCommandee> findByDemandeDevis_IdAndAndProduit_Id(Long demandeDevisId, Long produitId);  // Nouvelle méthode



}


