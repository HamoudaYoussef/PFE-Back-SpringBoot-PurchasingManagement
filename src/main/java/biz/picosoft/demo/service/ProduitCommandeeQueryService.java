package biz.picosoft.demo.service;


import biz.picosoft.demo.domain.ProduitCommandee;
import biz.picosoft.demo.domain.ProduitCommandee_;
import biz.picosoft.demo.repository.ProduitCommandeeRepository;
import biz.picosoft.demo.service.criteria.ProduitCommandeeCriteria;
import biz.picosoft.demo.service.dto.ProduitCommandeeDTO;
import biz.picosoft.demo.service.mapper.ProduitCommandeeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

import javax.persistence.criteria.JoinType;
import java.util.List;
@Service
@Transactional(readOnly = true)
public class ProduitCommandeeQueryService extends QueryService<ProduitCommandee> {

    private final Logger log = LoggerFactory.getLogger(ProduitDemandeeQueryService.class);

    private final ProduitCommandeeRepository produitCommandeeRepository;

    private final ProduitCommandeeMapper produitCommandeeMapper;

    public ProduitCommandeeQueryService(ProduitCommandeeRepository produitDemandeeRepository, ProduitCommandeeMapper produitDemandeeMapper) {
        this.produitCommandeeRepository = produitDemandeeRepository;
        this.produitCommandeeMapper = produitDemandeeMapper;
    }


    @Transactional(readOnly = true)
    public List<ProduitCommandeeDTO> findByCriteria(ProduitCommandeeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ProduitCommandee> specification = createSpecification(criteria);
        return produitCommandeeMapper.toDto(produitCommandeeRepository.findAll(specification));
    }

    /**
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProduitCommandeeDTO> findByCriteria(ProduitCommandeeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ProduitCommandee> specification = createSpecification(criteria);
        return produitCommandeeRepository.findAll(specification, page).map(produitCommandeeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProduitCommandeeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ProduitCommandee> specification = createSpecification(criteria);
        return produitCommandeeRepository.count(specification);
    }

    /**
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ProduitCommandee> createSpecification(ProduitCommandeeCriteria criteria) {
        Specification<ProduitCommandee> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ProduitCommandee_.id));
            }
            if (criteria.getNom() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNom(), ProduitCommandee_.nom));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), ProduitCommandee_.description));
            }
            if (criteria.getQuantite() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQuantite(), ProduitCommandee_.quantite));
            }


        }
        return specification;
    }
}
