package biz.picosoft.demo.repository;

import biz.picosoft.demo.domain.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CategorieRepository  extends JpaRepository<Categorie, Long>, JpaSpecificationExecutor<Categorie> {

}
