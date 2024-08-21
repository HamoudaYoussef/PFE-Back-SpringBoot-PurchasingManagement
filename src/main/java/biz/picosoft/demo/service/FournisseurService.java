package biz.picosoft.demo.service;

import biz.picosoft.demo.controller.errors.BadRequestAlertException;
import biz.picosoft.demo.domain.Fournisseur;
import biz.picosoft.demo.domain.Produit;
import biz.picosoft.demo.repository.FournisseurRepository;
import biz.picosoft.demo.service.dto.FournisseurDTO;
import biz.picosoft.demo.service.mapper.FournisseurMapper;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.hibernate.id.IdentifierGenerator.ENTITY_NAME;

@Service
@Transactional
public class FournisseurService {

    private final Logger log = LoggerFactory.getLogger(FournisseurService.class);

    private final FournisseurRepository fournisseurRepository;

    private final FournisseurMapper fournisseurMapper;

    public FournisseurService(FournisseurRepository fournisseurRepository, FournisseurMapper fournisseurMapper) {
        this.fournisseurRepository = fournisseurRepository;
        this.fournisseurMapper = fournisseurMapper;
    }

    /**
     * Save a fournisseur.
     *
     * @param fournisseurDTO the entity to save.
     * @return the persisted entity.
     */
    public FournisseurDTO save(FournisseurDTO fournisseurDTO) {
        log.debug("Request to save Fournisseur : {}", fournisseurDTO);
        Fournisseur fournisseur = fournisseurMapper.toEntity(fournisseurDTO);
        fournisseur = fournisseurRepository.save(fournisseur);
        return fournisseurMapper.toDto(fournisseur);
    }

    /**
     * Update a fournisseur.
     *
     * @param fournisseurDTO the entity to save.
     * @return the persisted entity.
     */
    public FournisseurDTO update(FournisseurDTO fournisseurDTO) {
        if (fournisseurDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idnull");
        }

        Fournisseur fournisseur = fournisseurRepository.findById(fournisseurDTO.getId())
                .orElseThrow(() -> new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));

        // Mettez à jour les champs du fournisseur avec les données du DTO
        fournisseur.setNom(fournisseurDTO.getNom());
        fournisseur.setAdresse(fournisseurDTO.getAdresse());
        fournisseur.setTel(fournisseurDTO.getTel());

        // Mettez à jour d'autres champs si nécessaire

        fournisseur = fournisseurRepository.save(fournisseur);
        return FournisseurDTO.fromFournisseur(fournisseur);
    }

    /**
     * Partially update a fournisseur.
     *
     * @param fournisseurDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<FournisseurDTO> partialUpdate(FournisseurDTO fournisseurDTO) {
        log.debug("Request to partially update Fournisseur : {}", fournisseurDTO);

        return fournisseurRepository
            .findById(fournisseurDTO.getId())
            .map(existingFournisseur -> {
                fournisseurMapper.partialUpdate(existingFournisseur, fournisseurDTO);

                return existingFournisseur;
            })
            .map(fournisseurRepository::save)
            .map(fournisseurMapper::toDto);
    }

    /**
     * Get all the fournisseurs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<FournisseurDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Fournisseurs");
        return fournisseurRepository.findAll(pageable).map(fournisseurMapper::toDto);
    }

    /**
     * Get one fournisseur by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FournisseurDTO> findOne(Long id) {
        log.debug("Request to get Fournisseur : {}", id);
        return fournisseurRepository.findById(id).map(fournisseurMapper::toDto);
    }

    /**
     * Delete the fournisseur by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Fournisseur : {}", id);
        fournisseurRepository.deleteById(id);
    }

    public List<Produit> getProduitsByFournisseur(Long fournisseurId) {
        return fournisseurRepository.findProduitsByFournisseurId(fournisseurId);
    }
}
