package biz.picosoft.demo.service;

import biz.picosoft.demo.domain.Offre_;
import biz.picosoft.demo.domain.ProduitOffert;
import biz.picosoft.demo.domain.ProduitOffert_;
import biz.picosoft.demo.domain.Produit_;
import biz.picosoft.demo.repository.ProduitOffertRepository;
import biz.picosoft.demo.service.criteria.ProduitOffertCritereia;
import biz.picosoft.demo.service.dto.ProduitOffertDTO;
import biz.picosoft.demo.service.mapper.ProduitOffertMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

import javax.persistence.criteria.JoinType;

/**
 * Service for executing complex queries for {@link ProduitOffert} entities in the database.
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link ProduitOffertDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProduitOffertQueryService extends QueryService<ProduitOffert> {

    private static final Logger LOG = LoggerFactory.getLogger(ProduitOffertQueryService.class);

    private final ProduitOffertRepository produitOffertRepository;

    private final ProduitOffertMapper produitOffertMapper;

    public ProduitOffertQueryService(ProduitOffertRepository produitOffertRepository, ProduitOffertMapper produitOffertMapper) {
        this.produitOffertRepository = produitOffertRepository;
        this.produitOffertMapper = produitOffertMapper;
    }

    /**
     * Return a {@link Page} of {@link ProduitOffertDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProduitOffertDTO> findByCriteria(ProduitOffertCritereia criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ProduitOffert> specification = createSpecification(criteria);
        return produitOffertRepository.findAll(specification, page).map(produitOffertMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProduitOffertCritereia criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<ProduitOffert> specification = createSpecification(criteria);
        return produitOffertRepository.count(specification);
    }

    /**
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ProduitOffert> createSpecification(ProduitOffertCritereia criteria) {
        Specification<ProduitOffert> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ProduitOffert_.id));
            }
            if (criteria.getNom() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNom(), ProduitOffert_.nom));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), ProduitOffert_.description));
            }
            if (criteria.getQuantite() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQuantite(), ProduitOffert_.quantite));
            }
            if (criteria.getPrix() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPrix(), ProduitOffert_.prix));
            }
            if (criteria.getProduitId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getProduitId(), root -> root.join(ProduitOffert_.produit, JoinType.LEFT).get(Produit_.id))
                );
            }
            if (criteria.getOffreId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getOffreId(), root -> root.join(ProduitOffert_.offre, JoinType.LEFT).get(Offre_.id))
                );
            }
        }
        return specification;
    }
}
