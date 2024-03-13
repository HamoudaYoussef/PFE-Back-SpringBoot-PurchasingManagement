package biz.picosoft.demo.service;

import biz.picosoft.demo.domain.*; // for static metamodels
import biz.picosoft.demo.domain.BonLivraison;
import biz.picosoft.demo.repository.BonLivraisonRepository;
import biz.picosoft.demo.service.criteria.BonLivraisonCriteria;
import biz.picosoft.demo.service.dto.BonLivraisonDTO;
import biz.picosoft.demo.service.mapper.BonLivraisonMapper;
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
 * Service for executing complex queries for {@link BonLivraison} entities in the database.
 * The main input is a {@link BonLivraisonCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link BonLivraisonDTO} or a {@link Page} of {@link BonLivraisonDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class BonLivraisonQueryService extends QueryService<BonLivraison> {

    private final Logger log = LoggerFactory.getLogger(BonLivraisonQueryService.class);

    private final BonLivraisonRepository bonLivraisonRepository;

    private final BonLivraisonMapper bonLivraisonMapper;

    public BonLivraisonQueryService(BonLivraisonRepository bonLivraisonRepository, BonLivraisonMapper bonLivraisonMapper) {
        this.bonLivraisonRepository = bonLivraisonRepository;
        this.bonLivraisonMapper = bonLivraisonMapper;
    }

    /**
     * Return a {@link List} of {@link BonLivraisonDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<BonLivraisonDTO> findByCriteria(BonLivraisonCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<BonLivraison> specification = createSpecification(criteria);
        return bonLivraisonMapper.toDto(bonLivraisonRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link BonLivraisonDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<BonLivraisonDTO> findByCriteria(BonLivraisonCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<BonLivraison> specification = createSpecification(criteria);
        return bonLivraisonRepository.findAll(specification, page).map(bonLivraisonMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(BonLivraisonCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<BonLivraison> specification = createSpecification(criteria);
        return bonLivraisonRepository.count(specification);
    }

    /**
     * Function to convert {@link BonLivraisonCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<BonLivraison> createSpecification(BonLivraisonCriteria criteria) {
        Specification<BonLivraison> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), BonLivraison_.id));
            }

            if (criteria.getNumerobonlivraison() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getNumerobonlivraison(), BonLivraison_.numerobonlivraison));
            }
            if (criteria.getDatelivraion() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDatelivraion(), BonLivraison_.datelivraion));
            }
            if (criteria.getFactureId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getFactureId(),
                            root -> root.join(BonLivraison_.factures, JoinType.LEFT).get(Facture_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
