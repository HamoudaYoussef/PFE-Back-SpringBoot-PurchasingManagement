package biz.picosoft.demo.repository;

import biz.picosoft.demo.domain.BonLivraison;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the BonLivraison entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BonLivraisonRepository extends JpaRepository<BonLivraison, Long>, JpaSpecificationExecutor<BonLivraison> {}
