package biz.picosoft.demo.service;

import biz.picosoft.demo.domain.*; // for static metamodels
import biz.picosoft.demo.domain.Paiement;
import biz.picosoft.demo.repository.PaiementRepository;
import biz.picosoft.demo.service.criteria.PaiementCriteria;
import biz.picosoft.demo.service.dto.PaiementDTO;
import biz.picosoft.demo.service.mapper.PaiementMapper;
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
 * Service for executing complex queries for {@link Paiement} entities in the database.
 * The main input is a {@link PaiementCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PaiementDTO} or a {@link Page} of {@link PaiementDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PaiementQueryService extends QueryService<Paiement> {

    private final Logger log = LoggerFactory.getLogger(PaiementQueryService.class);

    private final PaiementRepository paiementRepository;

    private final PaiementMapper paiementMapper;

    public PaiementQueryService(PaiementRepository paiementRepository, PaiementMapper paiementMapper) {
        this.paiementRepository = paiementRepository;
        this.paiementMapper = paiementMapper;
    }

    /**
     * Return a {@link List} of {@link PaiementDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PaiementDTO> findByCriteria(PaiementCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Paiement> specification = createSpecification(criteria);
        return paiementMapper.toDto(paiementRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PaiementDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PaiementDTO> findByCriteria(PaiementCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Paiement> specification = createSpecification(criteria);
        return paiementRepository.findAll(specification, page).map(paiementMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PaiementCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Paiement> specification = createSpecification(criteria);
        return paiementRepository.count(specification);
    }

    /**
     * Function to convert {@link PaiementCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Paiement> createSpecification(PaiementCriteria criteria) {
        Specification<Paiement> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Paiement_.id));
            }

            if (criteria.getMontanttotal() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMontanttotal(), Paiement_.montanttotal));
            }
            if (criteria.getDatepaiement() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDatepaiement(), Paiement_.datepaiement));
            }
            if (criteria.getMethodepaiement() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMethodepaiement(), Paiement_.methodepaiement));
            }
            if (criteria.getStatuts() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatuts(), Paiement_.statuts));
            }
            if (criteria.getFactureId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getFactureId(), root -> root.join(Paiement_.facture, JoinType.LEFT).get(Facture_.id))
                    );
            }
        }
        return specification;
    }
}
