package biz.picosoft.demo.repository;

import biz.picosoft.demo.domain.Offre;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Offre entity.
 */
@Repository
public interface OffreRepository extends JpaRepository<Offre, Long>, JpaSpecificationExecutor<Offre> {
    default Optional<Offre> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Offre> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Offre> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(value = "select offre from Offre offre left join fetch offre.fournisseur", countQuery = "select count(offre) from Offre offre")
    Page<Offre> findAllWithToOneRelationships(Pageable pageable);

    @Query("select offre from Offre offre left join fetch offre.fournisseur")
    List<Offre> findAllWithToOneRelationships();

    @Query("select offre from Offre offre left join fetch offre.fournisseur where offre.id =:id")
    Optional<Offre> findOneWithToOneRelationships(@Param("id") Long id);
}
