package biz.picosoft.demo.repository;

import java.util.List;
import java.util.Optional;

import biz.picosoft.demo.domain.Facture;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Facture entity.
 *
 * When extending this class, extend FactureRepositoryWithBagRelationships too.
 * For more information refer to https://github.com/jhipster/generator-jhipster/issues/17990.
 */
@Repository
public interface FactureRepository
    extends FactureRepositoryWithBagRelationships, JpaRepository<Facture, Long>, JpaSpecificationExecutor<Facture> {
    default Optional<Facture> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findById(id));
    }

    default List<Facture> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAll());
    }

    default Page<Facture> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAll(pageable));
    }
}
