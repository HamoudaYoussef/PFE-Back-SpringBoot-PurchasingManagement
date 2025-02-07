package biz.picosoft.demo.service;

import biz.picosoft.demo.domain.*;
import biz.picosoft.demo.repository.DemandeDevisRepository;
import biz.picosoft.demo.service.criteria.DemandeDevisCriteria;

import java.util.List;

import biz.picosoft.demo.service.dto.DemandeDevisDTO;
import biz.picosoft.demo.service.mapper.DemandeAchatMapper;
import biz.picosoft.demo.service.mapper.DemandeDevisMapper;
import biz.picosoft.demo.service.mapper.FournisseurMapper;
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
    private final FournisseurMapper fournisseurMapper;
    private final DemandeAchatMapper demandeAchatMapper;



    public DemandeDevisQueryService(DemandeDevisRepository demandeDevisRepository,DemandeAchatMapper demandeAchatMapper,
                                    DemandeDevisMapper demandeDevisMapper,FournisseurMapper fournisseurMapper) {
        this.demandeDevisRepository = demandeDevisRepository;
        this.demandeDevisMapper = demandeDevisMapper;
        this.fournisseurMapper = fournisseurMapper;
        this.demandeAchatMapper = demandeAchatMapper;

    }

    /**
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DemandeDevisDTO> findByCriteria(DemandeDevisCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DemandeDevis> specification = createSpecification(criteria);
        return demandeDevisMapper.toDto(demandeDevisRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DemandeDevisDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DemandeDevisDTO> findByCriteria(DemandeDevisCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DemandeDevis> specification = createSpecification(criteria);
        return demandeDevisRepository.findAll(specification, page).map(demandeDevis -> {
            demandeDevis.getFournisseur().getNom(); // Force le chargement de la relation fournisseur
            demandeDevis.getDemandeAchat().getReference(); // Force le chargement de la relation demandeAchat

            DemandeDevisDTO dto = demandeDevisMapper.toDto(demandeDevis);
            dto.setFournisseur(fournisseurMapper.toDto(demandeDevis.getFournisseur())); // Mapper le fournisseur complet
            dto.setDemandeAchat(demandeAchatMapper.toDto(demandeDevis.getDemandeAchat())); // Mapper la demande d'achat complète
            return dto;
        });    }
   /* public Page<DemandeDevisDTO> findByCriteria(DemandeDevisCriteria criteria, Pageable pageable) {
        Specification<DemandeDevis> specification = createSpecification(criteria);

        // Ajouter des jointures pour récupérer les informations liées
        return demandeDevisRepository
                .findAll(specification, pageable)
                .map(demandeDevisMapper::toDto);
    }*/

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

            if (criteria.getNom() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNom(), DemandeDevis_.nom));
            }
            if (criteria.getDatedemande() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDatedemande(), DemandeDevis_.datedemande));
            }

            if (criteria.getFournisseurId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getFournisseurId(),
                            root -> root.join(DemandeDevis_.fournisseur, JoinType.LEFT).get(Fournisseur_.id)
                        )
                    );
            }
            if (criteria.getDemandeAchatId() != null) {
                specification =
                        specification.and(
                                buildSpecification(
                                        criteria.getDemandeAchatId(),
                                        root -> root.join(DemandeDevis_.demandeAchat, JoinType.LEFT).get(DemandeAchat_.id)
                                )
                        );
            }



        }
        return specification;
    }
}
