package biz.picosoft.demo.Workflow.service;

import biz.picosoft.demo.Workflow.domain.BpmJob;
import biz.picosoft.demo.Workflow.repository.BpmJobRepository;
import biz.picosoft.demo.Workflow.service.criteria.BpmJobCriteria;
import biz.picosoft.demo.Workflow.service.dto.BpmJobDTO;
import biz.picosoft.demo.Workflow.service.mapper.BpmJobMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import io.github.jhipster.service.QueryService;

import java.util.List;


@Service
@Transactional(readOnly = true)
public class BpmJobQueryService extends QueryService<BpmJob> {

    private final Logger log = LoggerFactory.getLogger(BpmJobQueryService.class);


    private final BpmJobRepository bpmJobRepository;

    private final BpmJobMapper bpmJobMapper;

    public BpmJobQueryService(BpmJobRepository bpmJobRepository, BpmJobMapper bpmJobMapper) {
        this.bpmJobRepository = bpmJobRepository;
        this.bpmJobMapper = bpmJobMapper;
    }

    /**
     * Return a {@link List} of {@link BpmJobDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<BpmJobDTO> findByCriteria(BpmJobCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<BpmJob> specification = createSpecification(criteria);
        return bpmJobMapper.toDto(bpmJobRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link BpmJobDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<BpmJobDTO> findByCriteria(BpmJobCriteria criteria, Pageable page,int size) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<BpmJob> specification = createSpecification(criteria);
        if (size == 0) {
            page =  PageRequest.of(0, Integer.MAX_VALUE);
        }
        return bpmJobRepository.findAll(specification, page)
                .map(bpmJobMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(BpmJobCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<BpmJob> specification = createSpecification(criteria);
        return bpmJobRepository.count(specification);
    }


    /**
     * Function to convert {@link BpmJobCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<BpmJob> createSpecification(BpmJobCriteria criteria) {
        Specification<BpmJob> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
               // specification = specification.and(buildSpecification(criteria.getId(), BpmJob_.id));
            }
        }
        return specification;
    }
}
