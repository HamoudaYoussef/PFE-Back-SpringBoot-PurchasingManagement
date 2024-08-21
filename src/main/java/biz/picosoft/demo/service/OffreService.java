package biz.picosoft.demo.service;

import biz.picosoft.demo.domain.DemandeAchat;
import biz.picosoft.demo.domain.Fournisseur;
import biz.picosoft.demo.domain.Offre;
import biz.picosoft.demo.repository.DemandeAchatRepository;
import biz.picosoft.demo.repository.FournisseurRepository;
import biz.picosoft.demo.repository.OffreRepository;
import biz.picosoft.demo.service.dto.OffreDTO;
import biz.picosoft.demo.service.mapper.OffreMapper;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
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
public class OffreService {

    private final Logger log = LoggerFactory.getLogger(OffreService.class);

    private final OffreRepository offreRepository;
    private final FournisseurRepository fournisseurRepository;
    private final DemandeAchatRepository demandeAchatRepository;



    private final OffreMapper offreMapper;

    public OffreService(OffreRepository offreRepository,DemandeAchatRepository demandeAchatRepository,FournisseurRepository fournisseurRepository, OffreMapper offreMapper) {
        this.offreRepository = offreRepository;
        this.offreMapper = offreMapper;
        this.fournisseurRepository = fournisseurRepository;
        this.demandeAchatRepository = demandeAchatRepository;
    }

    /**
     * Save a offre.
     *
     * @param offreDTO the entity to save.
     * @return the persisted entity.
     */
    public OffreDTO save(OffreDTO offreDTO) {
        log.debug("Request to save Offre : {}", offreDTO);
        Offre offre = offreMapper.toEntity(offreDTO);
        offre = offreRepository.save(offre);
        return offreMapper.toDto(offre);
    }

    /**
     * Update a offre.
     *
     * @param offreDTO the entity to save.
     * @return the persisted entity.
     */
    public OffreDTO update(OffreDTO offreDTO) {
        log.debug("Request to update Offre : {}", offreDTO);
        Offre offre = offreMapper.toEntity(offreDTO);
        offre = offreRepository.save(offre);
        return offreMapper.toDto(offre);
    }

    /**
     * Partially update a offre.
     *
     * @param offreDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<OffreDTO> partialUpdate(OffreDTO offreDTO) {
        log.debug("Request to partially update Offre : {}", offreDTO);

        return offreRepository
            .findById(offreDTO.getId())
            .map(existingOffre -> {
                offreMapper.partialUpdate(existingOffre, offreDTO);

                return existingOffre;
            })
            .map(offreRepository::save)
            .map(offreMapper::toDto);
    }

    /**
     * Get all the offres.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<OffreDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Offres");
        return offreRepository.findAll(pageable).map(offreMapper::toDto);
    }

    /**
     * Get one offre by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<OffreDTO> findOne(Long id) {
        log.debug("Request to get Offre : {}", id);
        return offreRepository.findById(id).map(offreMapper::toDto);
    }

    /**
     * Delete the offre by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Offre : {}", id);
        offreRepository.deleteById(id);
    }
    public List<Offre> findRecentOffres() {
        return offreRepository.findRecentOffres();
    }
    public List<Offre> findOffresByFournisseur(Long fournisseurId) {
        return offreRepository.findOffresByFournisseur(fournisseurId);
    }

    public List<Offre> findOffresByDemandeAchat(Long demandeachatId) {
        return offreRepository.findOffresByDemandeAchat(demandeachatId);
    }
    public Map<Fournisseur, List<Offre>> getOffresTriesParFournisseur() {
        List<Fournisseur> fournisseurs = fournisseurRepository.findAll();
        return fournisseurs.stream()
                .filter(fournisseur -> fournisseur.getOffres() != null && !fournisseur.getOffres().isEmpty())
                .collect(Collectors.toMap(
                        fournisseur -> fournisseur,
                        fournisseur -> fournisseur.getOffres().stream()
                                .sorted(Comparator.comparing(Offre::getDescription))
                                .collect(Collectors.toList())
                ));
    }
    public Map<DemandeAchat, List<Offre>> getOffresTriesParDemandeAchat() {
        List<DemandeAchat> demandeachats = demandeAchatRepository.findAll();

        return demandeachats.stream()
                .filter(demandeAchat -> demandeAchat.getOffres() != null && !demandeAchat.getOffres().isEmpty())
                .collect(Collectors.toMap(
                        demandeAchat -> demandeAchat,
                        demandeAchat -> demandeAchat.getOffres().stream()
                                .sorted(Comparator.comparing(Offre::getDescription))
                                .collect(Collectors.toList())
                ));
    }
}
