package biz.picosoft.demo.service;

import biz.picosoft.demo.domain.*; // for static metamodels
import biz.picosoft.demo.domain.ProduitOffert;
import biz.picosoft.demo.repository.ProduitOffertRepository;
import biz.picosoft.demo.service.criteria.ProduitOffertCriteria;
import biz.picosoft.demo.service.dto.ProduitOffertDTO;
import biz.picosoft.demo.service.mapper.ProduitOffertMapper;
import javax.persistence.criteria.JoinType;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link ProduitOffert} entities in the database.
 * The main input is a {@link ProduitOffertCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProduitOffertDTO} or a {@link Page} of {@link ProduitOffertDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProduitOffertQueryService extends QueryService<ProduitOffert> {

    private final Logger log = LoggerFactory.getLogger(ProduitOffertQueryService.class);

    private final ProduitOffertRepository produitOffertRepository;

    private final ProduitOffertMapper produitOffertMapper;

    public ProduitOffertQueryService(ProduitOffertRepository produitOffertRepository, ProduitOffertMapper produitOffertMapper) {
        this.produitOffertRepository = produitOffertRepository;
        this.produitOffertMapper = produitOffertMapper;
    }

    /**
     * Return a {@link List} of {@link ProduitOffertDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProduitOffertDTO> findByCriteria(ProduitOffertCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ProduitOffert> specification = createSpecification(criteria);
        return produitOffertMapper.toDto(produitOffertRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ProduitOffertDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProduitOffertDTO> findByCriteria(ProduitOffertCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ProduitOffert> specification = createSpecification(criteria);
        return produitOffertRepository.findAll(specification, page).map(produitOffertMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProduitOffertCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ProduitOffert> specification = createSpecification(criteria);
        return produitOffertRepository.count(specification);
    }

    /**
     * Function to convert {@link ProduitOffertCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ProduitOffert> createSpecification(ProduitOffertCriteria criteria) {
        Specification<ProduitOffert> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ProduitOffert_.id));
            }


            if (criteria.getQuantiteofferte() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQuantiteofferte(), ProduitOffert_.quantiteofferte));
            }
            if (criteria.getOffreId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getOffreId(), root -> root.join(ProduitOffert_.offre, JoinType.LEFT).get(Offre_.id))
                    );
            }
            if (criteria.getProduitId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProduitId(),
                            root -> root.join(ProduitOffert_.produit, JoinType.LEFT).get(Produit_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
