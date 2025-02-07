package biz.picosoft.demo.service;


import java.util.List;

import biz.picosoft.demo.domain.DemandeAchat_;
import biz.picosoft.demo.domain.ProduitDemandee;
import biz.picosoft.demo.domain.ProduitDemandee_;
import biz.picosoft.demo.repository.ProduitDemandeeRepository;
import biz.picosoft.demo.service.criteria.ProduitDemandeeCriteria;
import biz.picosoft.demo.service.dto.ProduitDemandeeDTO;
import biz.picosoft.demo.service.mapper.ProduitDemandeeMapper;
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
 * in a way that all the filters must apply.
 */
@Service
@Transactional(readOnly = true)
public class ProduitDemandeeQueryService extends QueryService<ProduitDemandee> {

    private final Logger log = LoggerFactory.getLogger(ProduitDemandeeQueryService.class);

    private final ProduitDemandeeRepository produitDemandeeRepository;

    private final ProduitDemandeeMapper produitDemandeeMapper;

    public ProduitDemandeeQueryService(ProduitDemandeeRepository produitDemandeeRepository, ProduitDemandeeMapper produitDemandeeMapper) {
        this.produitDemandeeRepository = produitDemandeeRepository;
        this.produitDemandeeMapper = produitDemandeeMapper;
    }

    /**
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProduitDemandeeDTO> findByCriteria(ProduitDemandeeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ProduitDemandee> specification = createSpecification(criteria);
        return produitDemandeeMapper.toDto(produitDemandeeRepository.findAll(specification));
    }

    /**
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProduitDemandeeDTO> findByCriteria(ProduitDemandeeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ProduitDemandee> specification = createSpecification(criteria);
        return produitDemandeeRepository.findAll(specification, page).map(produitDemandeeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProduitDemandeeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ProduitDemandee> specification = createSpecification(criteria);
        return produitDemandeeRepository.count(specification);
    }

    /**
     * Function to convert {@link ProduitDemandeeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ProduitDemandee> createSpecification(ProduitDemandeeCriteria criteria) {
        Specification<ProduitDemandee> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ProduitDemandee_.id));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNom(), ProduitDemandee_.nom));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), ProduitDemandee_.description));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQuantite(), ProduitDemandee_.quantite));
            }
            if (criteria.getDemandeAchatId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getDemandeAchatId(),
                            root -> root.join(ProduitDemandee_.demandeAchat, JoinType.LEFT).get(DemandeAchat_.id)
                        )
                    );
            }

        }
        return specification;
    }
}
