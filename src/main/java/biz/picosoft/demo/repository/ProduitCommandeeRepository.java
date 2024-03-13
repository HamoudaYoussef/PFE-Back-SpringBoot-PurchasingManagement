package biz.picosoft.demo.repository;

import biz.picosoft.demo.domain.ProduitCommandee;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ProduitCommandee entity.
 */
@Repository
public interface ProduitCommandeeRepository extends JpaRepository<ProduitCommandee, Long>, JpaSpecificationExecutor<ProduitCommandee> {
    default Optional<ProduitCommandee> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<ProduitCommandee> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<ProduitCommandee> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select produitCommandee from ProduitCommandee produitCommandee left join fetch produitCommandee.boncommande left join fetch produitCommandee.produit",
        countQuery = "select count(produitCommandee) from ProduitCommandee produitCommandee"
    )
    Page<ProduitCommandee> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select produitCommandee from ProduitCommandee produitCommandee left join fetch produitCommandee.boncommande left join fetch produitCommandee.produit"
    )
    List<ProduitCommandee> findAllWithToOneRelationships();

    @Query(
        "select produitCommandee from ProduitCommandee produitCommandee left join fetch produitCommandee.boncommande left join fetch produitCommandee.produit where produitCommandee.id =:id"
    )
    Optional<ProduitCommandee> findOneWithToOneRelationships(@Param("id") Long id);
}
