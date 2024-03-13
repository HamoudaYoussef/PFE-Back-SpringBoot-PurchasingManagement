package biz.picosoft.demo.service;

import biz.picosoft.demo.domain.*; // for static metamodels
import biz.picosoft.demo.domain.DemandeAchat;
import biz.picosoft.demo.repository.DemandeAchatRepository;
import biz.picosoft.demo.service.criteria.DemandeAchatCriteria;
import biz.picosoft.demo.service.dto.DemandeAchatDTO;
import biz.picosoft.demo.service.mapper.DemandeAchatMapper;
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
 * Service for executing complex queries for {@link DemandeAchat} entities in the database.
 * The main input is a {@link DemandeAchatCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DemandeAchatDTO} or a {@link Page} of {@link DemandeAchatDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DemandeAchatQueryService extends QueryService<DemandeAchat> {

    private final Logger log = LoggerFactory.getLogger(DemandeAchatQueryService.class);

    private final DemandeAchatRepository demandeAchatRepository;

    private final DemandeAchatMapper demandeAchatMapper;

    public DemandeAchatQueryService(DemandeAchatRepository demandeAchatRepository, DemandeAchatMapper demandeAchatMapper) {
        this.demandeAchatRepository = demandeAchatRepository;
        this.demandeAchatMapper = demandeAchatMapper;
    }

    /**
     * Return a {@link List} of {@link DemandeAchatDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DemandeAchatDTO> findByCriteria(DemandeAchatCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DemandeAchat> specification = createSpecification(criteria);
        return demandeAchatMapper.toDto(demandeAchatRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DemandeAchatDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DemandeAchatDTO> findByCriteria(DemandeAchatCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DemandeAchat> specification = createSpecification(criteria);
        return demandeAchatRepository.findAll(specification, page).map(demandeAchatMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DemandeAchatCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DemandeAchat> specification = createSpecification(criteria);
        return demandeAchatRepository.count(specification);
    }

    /**
     * Function to convert {@link DemandeAchatCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<DemandeAchat> createSpecification(DemandeAchatCriteria criteria) {
        Specification<DemandeAchat> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), DemandeAchat_.id));
            }

            if (criteria.getDatedemande() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDatedemande(), DemandeAchat_.datedemande));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), DemandeAchat_.description));
            }
            if (criteria.getOffresId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getOffresId(), root -> root.join(DemandeAchat_.offres, JoinType.LEFT).get(Offre_.id))
                    );
            }
        }
        return specification;
    }
}
