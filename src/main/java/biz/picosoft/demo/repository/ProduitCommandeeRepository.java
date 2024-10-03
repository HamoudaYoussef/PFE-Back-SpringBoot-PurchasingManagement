package biz.picosoft.demo.repository;

import biz.picosoft.demo.domain.ProduitCommandee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ProduitCommandeeRepository extends JpaRepository<ProduitCommandee, Long>, JpaSpecificationExecutor<ProduitCommandee> {
}
