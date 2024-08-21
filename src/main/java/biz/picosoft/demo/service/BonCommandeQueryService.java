package biz.picosoft.demo.service;

import biz.picosoft.demo.domain.*;
import biz.picosoft.demo.repository.BonCommandeRepository;
import biz.picosoft.demo.service.criteria.BonCommandeCriteria;
import biz.picosoft.demo.service.dto.BonCommandeDTO;
import biz.picosoft.demo.service.mapper.BonCommandeMapper;

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
 * Service for executing complex queries for {@link BonCommande} entities in the database.
 * The main input is a {@link BonCommandeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link BonCommandeDTO} or a {@link Page} of {@link BonCommandeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class BonCommandeQueryService extends QueryService<BonCommande> {

    private final Logger log = LoggerFactory.getLogger(BonCommandeQueryService.class);

    private final BonCommandeRepository bonCommandeRepository;

    private final BonCommandeMapper bonCommandeMapper;

    public BonCommandeQueryService(BonCommandeRepository bonCommandeRepository, BonCommandeMapper bonCommandeMapper) {
        this.bonCommandeRepository = bonCommandeRepository;
        this.bonCommandeMapper = bonCommandeMapper;
    }

    /**
     * Return a {@link List} of {@link BonCommandeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<BonCommandeDTO> findByCriteria(BonCommandeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<BonCommande> specification = createSpecification(criteria);
        return bonCommandeMapper.toDto(bonCommandeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link BonCommandeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<BonCommandeDTO> findByCriteria(BonCommandeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<BonCommande> specification = createSpecification(criteria);
        return bonCommandeRepository.findAll(specification, page).map(bonCommandeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(BonCommandeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<BonCommande> specification = createSpecification(criteria);
        return bonCommandeRepository.count(specification);
    }

    /**
     * Function to convert {@link BonCommandeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<BonCommande> createSpecification(BonCommandeCriteria criteria) {
        Specification<BonCommande> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), BonCommande_.id));
            }
            if (criteria.getDateboncommande() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateboncommande(), BonCommande_.dateboncommande));
            }
            if (criteria.getReference() != null) {
                specification = specification.and(buildStringSpecification(criteria.getReference(), BonCommande_.reference));
            }
            if (criteria.getNomentreprise() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNomentreprise(), BonCommande_.nomentreprise));
            }
            if (criteria.getAdresseentreprise() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAdresseentreprise(), BonCommande_.adresseentreprise));
            }
            if (criteria.getDelailivraison() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDelailivraison(), BonCommande_.delailivraison));
            }
            if (criteria.getFraislivraison() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFraislivraison(), BonCommande_.fraislivraison));
            }
            if (criteria.getMontanttotal() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMontanttotal(), BonCommande_.montanttotal));
            }
            if (criteria.getTaxes() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTaxes(), BonCommande_.taxes));
            }
            if (criteria.getSignature() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSignature(), BonCommande_.signature));
            }
            if (criteria.getBonlivraisonId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getBonlivraisonId(),
                            root -> root.join(BonCommande_.bonlivraisons, JoinType.LEFT).get(BonLivraison_.id)
                        )
                    );
            }
            if (criteria.getFactureId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getFactureId(),
                            root -> root.join(BonCommande_.factures, JoinType.LEFT).get(Facture_.id)
                        )
                    );
            }
            if (criteria.getOffreId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getOffreId(), root -> root.join(BonCommande_.offre, JoinType.LEFT).get(Offre_.id))
                    );
            }

        }
        return specification;
    }
}
