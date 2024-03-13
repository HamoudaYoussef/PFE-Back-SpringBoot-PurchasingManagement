package biz.picosoft.demo.config.logging;


import biz.picosoft.demo.config.logging.domain.AopLogging;
import biz.picosoft.demo.config.logging.domain.AopLogging_;
import biz.picosoft.demo.config.logging.repository.AopLoggingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

import java.util.List;

/**
 * Service for executing complex queries for {@link AopLogging} entities in the database.
 * The main input is a {@link biz.picosoft.demo.config.logging.AopLoggingCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AopLoggingDTO} or a {@link Page} of {@link AopLoggingDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AopLoggingQueryService extends QueryService<AopLogging> {

    private final Logger log = LoggerFactory.getLogger(AopLoggingQueryService.class);

    private final AopLoggingRepository aopLoggingRepository;

    private final AopLoggingMapper aopLoggingMapper;

    public AopLoggingQueryService(AopLoggingRepository aopLoggingRepository, AopLoggingMapper aopLoggingMapper) {
        this.aopLoggingRepository = aopLoggingRepository;
        this.aopLoggingMapper = aopLoggingMapper;
    }

    /**
     * Return a {@link List} of {@link AopLoggingDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AopLoggingDTO> findByCriteria(AopLoggingCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<AopLogging> specification = createSpecification(criteria);
        return aopLoggingMapper.toDto(aopLoggingRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AopLoggingDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AopLoggingDTO> findByCriteria(AopLoggingCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<AopLogging> specification = createSpecification(criteria);
        return aopLoggingRepository.findAll(specification, page).map(aopLoggingMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AopLoggingCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<AopLogging> specification = createSpecification(criteria);
        return aopLoggingRepository.count(specification);
    }

    /**
     * Function to convert {@link AopLoggingCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<AopLogging> createSpecification(AopLoggingCriteria criteria) {
        Specification<AopLogging> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
//            if (criteria.getDistinct() != null) {
//                specification = specification.and(distinct(criteria.getDistinct()));
//            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), AopLogging_.id));
            }
            if (criteria.getMicroserviceName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMicroserviceName(), AopLogging_.microserviceName));
            }
            if (criteria.getMethodName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMethodName(), AopLogging_.methodName));
            }
            if (criteria.getUuid() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUuid(), AopLogging_.uuid));
            }
            if (criteria.getPackageFullName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPackageFullName(), AopLogging_.packageFullName));
            }
            if (criteria.getPackageTitle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPackageTitle(), AopLogging_.packageTitle));
            }
            if (criteria.getDuration() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDuration(), AopLogging_.duration));
            }
            if (criteria.getUserName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUserName(), AopLogging_.userName));
            }
            if (criteria.getUniteName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUniteName(), AopLogging_.uniteName));
            }
        }
        return specification;
    }
}
