package biz.picosoft.demo.repository;

import biz.picosoft.demo.domain.ProduitOffert;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ProduitOffert entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProduitOffertRepository extends JpaRepository<ProduitOffert, Long>, JpaSpecificationExecutor<ProduitOffert> {}
