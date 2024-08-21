package biz.picosoft.demo.service;

import biz.picosoft.demo.domain.*;
import biz.picosoft.demo.repository.OffreRepository;
import biz.picosoft.demo.service.criteria.OffreCriteria;
import biz.picosoft.demo.service.dto.OffreDTO;
import biz.picosoft.demo.service.mapper.OffreMapper;

import java.util.List;
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
 * Service for executing complex queries for {@link Offre} entities in the database.
 * The main input is a {@link OffreCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link OffreDTO} or a {@link Page} of {@link OffreDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class OffreQueryService extends QueryService<Offre> {

    private final Logger log = LoggerFactory.getLogger(OffreQueryService.class);

    private final OffreRepository offreRepository;

    private final OffreMapper offreMapper;

    public OffreQueryService(OffreRepository offreRepository, OffreMapper offreMapper) {
        this.offreRepository = offreRepository;
        this.offreMapper = offreMapper;
    }

    /**
     * Return a {@link List} of {@link OffreDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<OffreDTO> findByCriteria(OffreCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Offre> specification = createSpecification(criteria);
        return offreMapper.toDto(offreRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link OffreDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<OffreDTO> findByCriteria(OffreCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Offre> specification = createSpecification(criteria);
        return offreRepository.findAll(specification, page).map(offreMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(OffreCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Offre> specification = createSpecification(criteria);
        return offreRepository.count(specification);
    }

    /**
     * Function to convert {@link OffreCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Offre> createSpecification(OffreCriteria criteria) {
        Specification<Offre> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Offre_.id));
            }
            if (criteria.getPrix() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPrix(), Offre_.prix));
            }
            if (criteria.getDateoffre() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateoffre(), Offre_.dateoffre));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Offre_.description));
            }
            if (criteria.getReferenceoffre() != null) {
                specification = specification.and(buildStringSpecification(criteria.getReferenceoffre(), Offre_.referenceoffre));
            }
            if (criteria.getNom() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNom(), Offre_.nom));
            }
            if (criteria.getBoncommandeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getBoncommandeId(),
                            root -> root.join(Offre_.boncommandes, JoinType.LEFT).get(BonCommande_.id)
                        )
                    );
            }
            if (criteria.getDemandeachatId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getDemandeachatId(),
                            root -> root.join(Offre_.demandeachat, JoinType.LEFT).get(DemandeAchat_.id)
                        )
                    );
            }
            if (criteria.getFournisseurId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getFournisseurId(),
                            root -> root.join(Offre_.fournisseur, JoinType.LEFT).get(Fournisseur_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
