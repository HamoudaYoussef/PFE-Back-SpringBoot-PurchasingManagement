package biz.picosoft.demo.repository;


import biz.picosoft.demo.domain.DemandeDevis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the DemandeDevis entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DemandeDevisRepository extends JpaRepository<DemandeDevis, Long>, JpaSpecificationExecutor<DemandeDevis> {}
