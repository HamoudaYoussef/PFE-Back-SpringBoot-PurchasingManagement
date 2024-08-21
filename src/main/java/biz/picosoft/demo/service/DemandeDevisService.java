package biz.picosoft.demo.service;

import biz.picosoft.demo.controller.errors.BadRequestAlertException;
import biz.picosoft.demo.domain.DemandeAchat;
import biz.picosoft.demo.domain.DemandeDevis;
import biz.picosoft.demo.domain.Fournisseur;
import biz.picosoft.demo.repository.DemandeAchatRepository;
import biz.picosoft.demo.repository.DemandeDevisRepository;
import biz.picosoft.demo.repository.FournisseurRepository;
import biz.picosoft.demo.service.dto.DemandeDevisDTO;
import biz.picosoft.demo.service.mapper.DemandeDevisMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class DemandeDevisService {

    private final Logger log = LoggerFactory.getLogger(DemandeDevisService.class);

    private final DemandeDevisRepository demandeDevisRepository;
    private final DemandeAchatRepository demandeAchatRepository;
    private final FournisseurRepository fournisseurRepository;


    private final DemandeDevisMapper demandeDevisMapper;

    public DemandeDevisService(DemandeAchatRepository demandeAchatRepository,FournisseurRepository fournisseurRepository,DemandeDevisRepository demandeDevisRepository, DemandeDevisMapper demandeDevisMapper) {
        this.demandeDevisRepository = demandeDevisRepository;
        this.demandeDevisMapper = demandeDevisMapper;
        this.demandeAchatRepository = demandeAchatRepository;
        this.fournisseurRepository = fournisseurRepository;
    }

    /**
     * Save a demandeDevis.
     *
     * @param demandeDevisDTO the entity to save.
     * @return the persisted entity.
     */
    public DemandeDevisDTO save(DemandeDevisDTO demandeDevisDTO) {
        DemandeDevis demandeDevis = demandeDevisMapper.toEntity(demandeDevisDTO);

        if (demandeDevisDTO.getFournisseurId() != null) {
            Fournisseur fournisseur = fournisseurRepository.findById(demandeDevisDTO.getFournisseurId())
                    .orElseThrow(() -> new BadRequestAlertException("Invalid Fournisseur ID", "demandeDevis", "idnotfound"));
            demandeDevis.setFournisseur(fournisseur);
        }

        if (demandeDevisDTO.getDemandeAchatId() != null) {
            DemandeAchat demandeAchat = demandeAchatRepository.findById(demandeDevisDTO.getDemandeAchatId())
                    .orElseThrow(() -> new BadRequestAlertException("Invalid DemandeAchat ID", "demandeDevis", "idnotfound"));
            demandeDevis.setDemandeAchat(demandeAchat);
        } else {
            throw new BadRequestAlertException("DemandeAchat ID is required", "demandeDevis", "idnotfound");
        }

        // Save the DemandeAchat entity first if it's not already persisted
        if (demandeDevis.getDemandeAchat().getId() == null) {
            demandeDevis.setDemandeAchat(demandeAchatRepository.save(demandeDevis.getDemandeAchat()));
        }

        // Save the DemandeDevis entity
        demandeDevis = demandeDevisRepository.save(demandeDevis);
        return demandeDevisMapper.toDto(demandeDevis);
    }

    public List<DemandeDevisDTO> saveAll(List<DemandeDevisDTO> demandeDevisDTOList) {
        List<DemandeDevis> savedDemandeDevisList = new ArrayList<>();

        for (DemandeDevisDTO demandeDevisDTO : demandeDevisDTOList) {
            DemandeDevis demandeDevis = demandeDevisMapper.toEntity(demandeDevisDTO);

            if (demandeDevisDTO.getFournisseurId() != null) {
                Fournisseur fournisseur = fournisseurRepository.findById(demandeDevisDTO.getFournisseurId())
                        .orElseThrow(() -> new BadRequestAlertException("Invalid Fournisseur ID", "demandeDevis", "idnotfound"));
                demandeDevis.setFournisseur(fournisseur);
            }

            if (demandeDevisDTO.getDemandeAchatId() != null) {
                DemandeAchat demandeAchat = demandeAchatRepository.findById(demandeDevisDTO.getDemandeAchatId())
                        .orElseThrow(() -> new BadRequestAlertException("Invalid DemandeAchat ID", "demandeDevis", "idnotfound"));
                demandeDevis.setDemandeAchat(demandeAchat);
            }

            // Save the DemandeAchat if it's new
            if (demandeDevis.getDemandeAchat().getId() == null) {
                demandeAchatRepository.save(demandeDevis.getDemandeAchat());
            }

            DemandeDevis savedDemandeDevis = demandeDevisRepository.save(demandeDevis);
            savedDemandeDevisList.add(savedDemandeDevis);
        }

        return savedDemandeDevisList.stream()
                .map(demandeDevisMapper::toDto)
                .collect(Collectors.toList());
    }


    /**
     * Update a demandeDevis.
     *
     * @param demandeDevisDTO the entity to save.
     * @return the persisted entity.
     */
    public DemandeDevisDTO update(DemandeDevisDTO demandeDevisDTO) {
        log.debug("Request to update DemandeDevis : {}", demandeDevisDTO);
        DemandeDevis demandeDevis = demandeDevisMapper.toEntity(demandeDevisDTO);
        demandeDevis = demandeDevisRepository.save(demandeDevis);
        return demandeDevisMapper.toDto(demandeDevis);
    }

    /**
     * Partially update a demandeDevis.
     *
     * @param demandeDevisDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DemandeDevisDTO> partialUpdate(DemandeDevisDTO demandeDevisDTO) {
        log.debug("Request to partially update DemandeDevis : {}", demandeDevisDTO);

        return demandeDevisRepository
            .findById(demandeDevisDTO.getId())
            .map(existingDemandeDevis -> {
                demandeDevisMapper.partialUpdate(existingDemandeDevis, demandeDevisDTO);

                return existingDemandeDevis;
            })
            .map(demandeDevisRepository::save)
            .map(demandeDevisMapper::toDto);
    }

    /**
     * Get all the demandeDevis.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DemandeDevisDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DemandeDevis");
        return demandeDevisRepository.findAll(pageable).map(demandeDevisMapper::toDto);
    }

    /**
     * Get one demandeDevis by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DemandeDevisDTO> findOne(Long id) {
        log.debug("Request to get DemandeDevis : {}", id);
        return demandeDevisRepository.findById(id).map(demandeDevisMapper::toDto);
    }

    /**
     * Delete the demandeDevis by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DemandeDevis : {}", id);
        demandeDevisRepository.deleteById(id);
    }
}
