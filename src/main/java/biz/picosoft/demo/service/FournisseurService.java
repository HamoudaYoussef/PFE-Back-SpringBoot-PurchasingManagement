package biz.picosoft.demo.service;

import biz.picosoft.demo.domain.DemandeDevis;
import biz.picosoft.demo.domain.Fournisseur;
import biz.picosoft.demo.domain.Offre;
import biz.picosoft.demo.errors.ResourceNotFoundException;
import biz.picosoft.demo.repository.DemandeDevisRepository;
import biz.picosoft.demo.repository.FournisseurRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import biz.picosoft.demo.service.dto.FournisseurDTO;
import biz.picosoft.demo.service.mapper.FournisseurMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
     * @return the persisted entity.
     */
    @Transactional
    public Fournisseur updateFournisseur(Long id, Fournisseur updatedFournisseur) {
        // Vérifiez si le fournisseur existe
        Fournisseur fournisseur = fournisseurRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Fournisseur not found with id " + id));

        // Mettez à jour uniquement les champs non nulls
        if (updatedFournisseur.getNom() != null) {
            fournisseur.setNom(updatedFournisseur.getNom());
        }
        if (updatedFournisseur.getAdresse() != null) {
            fournisseur.setAdresse(updatedFournisseur.getAdresse());
        }
        if (updatedFournisseur.getTel() != null) {
            fournisseur.setTel(updatedFournisseur.getTel());
        }
        if (updatedFournisseur.getEmail() != null) {
            fournisseur.setEmail(updatedFournisseur.getEmail());
        }

        // Ajoutez d'autres attributs à mettre à jour ici, en vérifiant si les valeurs sont non nulles

        // Sauvegardez les modifications
        return fournisseurRepository.save(fournisseur);
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
     * @return the list of entities.
     */
    public List<FournisseurDTO> findAll(Sort sort) {
        List<Fournisseur> fournisseurList = fournisseurRepository.findAll(sort);
        return fournisseurList.stream()
                .map(fournisseurMapper::toDto)
                .collect(Collectors.toList());
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

    public List<Fournisseur> getAll() {
        return fournisseurRepository.findAll();
    }

    public String geFournisseurName(Long fournisseurId) {
        return fournisseurRepository.findFournisseurName(fournisseurId);
    }



}
