package biz.picosoft.demo.service;

import biz.picosoft.demo.domain.*; // for static metamodels
import biz.picosoft.demo.domain.Produit;
import biz.picosoft.demo.repository.ProduitRepository;
import biz.picosoft.demo.service.criteria.ProduitCriteria;
import biz.picosoft.demo.service.dto.ProduitDTO;
import biz.picosoft.demo.service.mapper.ProduitMapper;
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
 * Service for executing complex queries for {@link Produit} entities in the database.
 * The main input is a {@link ProduitCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProduitDTO} or a {@link Page} of {@link ProduitDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProduitQueryService extends QueryService<Produit> {

    private final Logger log = LoggerFactory.getLogger(ProduitQueryService.class);

    private final ProduitRepository produitRepository;

    private final ProduitMapper produitMapper;

    public ProduitQueryService(ProduitRepository produitRepository, ProduitMapper produitMapper) {
        this.produitRepository = produitRepository;
        this.produitMapper = produitMapper;
    }

    /**
     * Return a {@link List} of {@link ProduitDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProduitDTO> findByCriteria(ProduitCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Produit> specification = createSpecification(criteria);
        return produitMapper.toDto(produitRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ProduitDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProduitDTO> findByCriteria(ProduitCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Produit> specification = createSpecification(criteria);
        return produitRepository.findAll(specification, page).map(produitMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProduitCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Produit> specification = createSpecification(criteria);
        return produitRepository.count(specification);
    }

    /**
     * Function to convert {@link ProduitCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Produit> createSpecification(ProduitCriteria criteria) {
        Specification<Produit> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Produit_.id));
            }

            if (criteria.getDateachat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateachat(), Produit_.dateachat));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Produit_.description));
            }
            if (criteria.getNom() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNom(), Produit_.nom));
            }
            if (criteria.getPoids() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPoids(), Produit_.poids));
            }
            if (criteria.getForme() != null) {
                specification = specification.and(buildStringSpecification(criteria.getForme(), Produit_.forme));
            }
            if (criteria.getTaille() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTaille(), Produit_.taille));
            }
            if (criteria.getCouleur() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCouleur(), Produit_.couleur));
            }
            if (criteria.getUntietaille() != null) {
                specification = specification.and(buildSpecification(criteria.getUntietaille(), Produit_.untietaille));
            }
            if (criteria.getUnitepoids() != null) {
                specification = specification.and(buildSpecification(criteria.getUnitepoids(), Produit_.unitepoids));
            }
            if (criteria.getVolume() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVolume(), Produit_.volume));
            }
            if (criteria.getUnitevolume() != null) {
                specification = specification.and(buildSpecification(criteria.getUnitevolume(), Produit_.unitevolume));
            }
            if (criteria.getSurface() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSurface(), Produit_.surface));
            }
            if (criteria.getUnitesurface() != null) {
                specification = specification.and(buildSpecification(criteria.getUnitesurface(), Produit_.unitesurface));
            }
            if (criteria.getOffresId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getOffresId(), root -> root.join(Produit_.offres, JoinType.LEFT).get(Offre_.id))
                    );
            }
        }
        return specification;
    }
}
