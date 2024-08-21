package biz.picosoft.demo.service;

import biz.picosoft.demo.domain.BonCommande_;
import biz.picosoft.demo.domain.Facture;
import biz.picosoft.demo.domain.Facture_;
import biz.picosoft.demo.domain.Paiement_;
import biz.picosoft.demo.repository.FactureRepository;
import biz.picosoft.demo.service.criteria.FactureCriteria;
import biz.picosoft.demo.service.dto.FactureDTO;
import biz.picosoft.demo.service.mapper.FactureMapper;

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
 * Service for executing complex queries for {@link Facture} entities in the database.
 * The main input is a {@link FactureCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link FactureDTO} or a {@link Page} of {@link FactureDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FactureQueryService extends QueryService<Facture> {

    private final Logger log = LoggerFactory.getLogger(FactureQueryService.class);

    private final FactureRepository factureRepository;

    private final FactureMapper factureMapper;

    public FactureQueryService(FactureRepository factureRepository, FactureMapper factureMapper) {
        this.factureRepository = factureRepository;
        this.factureMapper = factureMapper;
    }

    /**
     * Return a {@link List} of {@link FactureDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<FactureDTO> findByCriteria(FactureCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Facture> specification = createSpecification(criteria);
        return factureMapper.toDto(factureRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link FactureDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<FactureDTO> findByCriteria(FactureCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Facture> specification = createSpecification(criteria);
        return factureRepository.findAll(specification, page).map(factureMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FactureCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Facture> specification = createSpecification(criteria);
        return factureRepository.count(specification);
    }

    /**
     * Function to convert {@link FactureCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Facture> createSpecification(FactureCriteria criteria) {
        Specification<Facture> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Facture_.id));
            }
            if (criteria.getDatefacture() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDatefacture(), Facture_.datefacture));
            }
            if (criteria.getDesignation() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDesignation(), Facture_.designation));
            }
            if (criteria.getMontanttotalfacture() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMontanttotalfacture(), Facture_.montanttotalfacture));
            }
            if (criteria.getNomentrprise() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNomentrprise(), Facture_.nomentrprise));
            }
            if (criteria.getAdresseentreprise() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAdresseentreprise(), Facture_.adresseentreprise));
            }
            if (criteria.getTelentreprise() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTelentreprise(), Facture_.telentreprise));
            }
            if (criteria.getEmailentreprise() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmailentreprise(), Facture_.emailentreprise));
            }
            if (criteria.getNomclient() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNomclient(), Facture_.nomclient));
            }
            if (criteria.getAdresseclient() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAdresseclient(), Facture_.adresseclient));
            }
            if (criteria.getTelclient() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTelclient(), Facture_.telclient));
            }
            if (criteria.getEmailclien() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmailclien(), Facture_.emailclien));
            }
            if (criteria.getPrixunitaire() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPrixunitaire(), Facture_.prixunitaire));
            }
            if (criteria.getTotalht() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalht(), Facture_.totalht));
            }
            if (criteria.getTva() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTva(), Facture_.tva));
            }
            if (criteria.getTotalttc() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalttc(), Facture_.totalttc));
            }
            if (criteria.getReference() != null) {
                specification = specification.and(buildStringSpecification(criteria.getReference(), Facture_.reference));
            }
            if (criteria.getPaiementId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getPaiementId(), root -> root.join(Facture_.paiements, JoinType.LEFT).get(Paiement_.id))
                    );
            }
            if (criteria.getBonCommandeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getBonCommandeId(),
                            root -> root.join(Facture_.bonCommande, JoinType.LEFT).get(BonCommande_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
