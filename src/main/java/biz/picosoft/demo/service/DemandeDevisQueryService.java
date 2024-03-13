package biz.picosoft.demo.service;

import biz.picosoft.demo.domain.*; // for static metamodels

import javax.persistence.criteria.JoinType;
import java.util.List;

import biz.picosoft.demo.repository.DemandeDevisRepository;
import biz.picosoft.demo.service.criteria.DemandeDevisCriteria;
import biz.picosoft.demo.service.mapper.DemandeDevisMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link DemandeDevis} entities in the database.
 * The main input is a {@link DemandeDevisCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 */
@Service
@Transactional(readOnly = true)
public class DemandeDevisQueryService extends QueryService<DemandeDevis> {

    private final Logger log = LoggerFactory.getLogger(DemandeDevisQueryService.class);

    private final DemandeDevisRepository demandeDevisRepository;

    private final DemandeDevisMapper demandeDevisMapper;

    public DemandeDevisQueryService(DemandeDevisRepository demandeDevisRepository, DemandeDevisMapper demandeDevisMapper) {
        this.demandeDevisRepository = demandeDevisRepository;
        this.demandeDevisMapper = demandeDevisMapper;
    }

    /**
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<com.mycompany.demo.service.dto.DemandeDevisDTO> findByCriteria(DemandeDevisCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DemandeDevis> specification = createSpecification(criteria);
        return demandeDevisMapper.toDto(demandeDevisRepository.findAll(specification));
    }

    /**
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<com.mycompany.demo.service.dto.DemandeDevisDTO> findByCriteria(DemandeDevisCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DemandeDevis> specification = createSpecification(criteria);
        return demandeDevisRepository.findAll(specification, page).map(demandeDevisMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DemandeDevisCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DemandeDevis> specification = createSpecification(criteria);
        return demandeDevisRepository.count(specification);
    }

    /**
     * Function to convert {@link DemandeDevisCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<DemandeDevis> createSpecification(DemandeDevisCriteria criteria) {
        Specification<DemandeDevis> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), DemandeDevis_.id));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), DemandeDevis_.description));
            }
            if (criteria.getProduitsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProduitsId(),
                            root -> root.join(DemandeDevis_.produits, JoinType.LEFT).get(Produit_.id)
                        )
                    );
            }
            if (criteria.getDemandesachatId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getDemandesachatId(),
                            root -> root.join(DemandeDevis_.demandesachats, JoinType.LEFT).get(DemandeAchat_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
