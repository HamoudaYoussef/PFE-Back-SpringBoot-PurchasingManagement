package biz.picosoft.demo.service;

import biz.picosoft.demo.domain.*; // for static metamodels
import biz.picosoft.demo.domain.ProduitCommandee;
import biz.picosoft.demo.repository.ProduitCommandeeRepository;
import biz.picosoft.demo.service.criteria.ProduitCommandeeCriteria;
import biz.picosoft.demo.service.dto.ProduitCommandeeDTO;
import biz.picosoft.demo.service.mapper.ProduitCommandeeMapper;
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
 * Service for executing complex queries for {@link ProduitCommandee} entities in the database.
 * The main input is a {@link ProduitCommandeeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProduitCommandeeDTO} or a {@link Page} of {@link ProduitCommandeeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProduitCommandeeQueryService extends QueryService<ProduitCommandee> {

    private final Logger log = LoggerFactory.getLogger(ProduitCommandeeQueryService.class);

    private final ProduitCommandeeRepository produitCommandeeRepository;

    private final ProduitCommandeeMapper produitCommandeeMapper;

    public ProduitCommandeeQueryService(
        ProduitCommandeeRepository produitCommandeeRepository,
        ProduitCommandeeMapper produitCommandeeMapper
    ) {
        this.produitCommandeeRepository = produitCommandeeRepository;
        this.produitCommandeeMapper = produitCommandeeMapper;
    }

    /**
     * Return a {@link List} of {@link ProduitCommandeeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProduitCommandeeDTO> findByCriteria(ProduitCommandeeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ProduitCommandee> specification = createSpecification(criteria);
        return produitCommandeeMapper.toDto(produitCommandeeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ProduitCommandeeDTO} which matches the criteria from the database.
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
     * Function to convert {@link ProduitCommandeeCriteria} to a {@link Specification}
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


            if (criteria.getDateboncommande() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getDateboncommande(), ProduitCommandee_.dateboncommande));
            }
            if (criteria.getQuantitecommandee() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getQuantitecommandee(), ProduitCommandee_.quantitecommandee));
            }
            if (criteria.getBoncommandeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getBoncommandeId(),
                            root -> root.join(ProduitCommandee_.boncommande, JoinType.LEFT).get(BonCommande_.id)
                        )
                    );
            }
            if (criteria.getProduitId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProduitId(),
                            root -> root.join(ProduitCommandee_.produit, JoinType.LEFT).get(Produit_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
