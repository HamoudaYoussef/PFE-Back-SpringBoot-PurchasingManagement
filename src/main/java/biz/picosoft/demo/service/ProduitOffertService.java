package biz.picosoft.demo.service;

import biz.picosoft.demo.controller.errors.BadRequestAlertException;
import biz.picosoft.demo.domain.Offre;
import biz.picosoft.demo.domain.ProduitOffert;
import biz.picosoft.demo.repository.OffreRepository;
import biz.picosoft.demo.repository.ProduitOffertRepository;
import biz.picosoft.demo.service.dto.ProduitOffertDTO;
import biz.picosoft.demo.service.mapper.ProduitOffertMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

import static org.hibernate.id.IdentifierGenerator.ENTITY_NAME;

/**
 */
@Service
@Transactional
public class ProduitOffertService {

    private final Logger log = LoggerFactory.getLogger(ProduitOffertService.class);

    private final ProduitOffertRepository produitOffertRepository;
    private final OffreRepository offreRepository;


    private final ProduitOffertMapper produitOffertMapper;

    public ProduitOffertService(ProduitOffertRepository produitOffertRepository, OffreRepository offreRepository,ProduitOffertMapper produitOffertMapper) {
        this.produitOffertRepository = produitOffertRepository;
        this.produitOffertMapper = produitOffertMapper;
        this.offreRepository =offreRepository;
    }

    /**
     * Save a produitOffert.
     *
     * @param produitOffertDTO the entity to save.
     * @return the persisted entity.
     */
    public ProduitOffertDTO save(ProduitOffertDTO produitOffertDTO) {
        // Créer une instance de ProduitOffert
        ProduitOffert produitOffert = new ProduitOffert();

        // Assurez-vous que vous récupérez l'entité Offre à partir de l'ID
        Offre offre = offreRepository.findById(produitOffertDTO.getOffreId())
                .orElseThrow(() -> new EntityNotFoundException("Offre not found"));

        if (offre != null) {
            produitOffert.setOffre(offre); // Associer l'entité Offre au produit offert
        } else {
            throw new IllegalArgumentException("The given id must not be null!");
        }

        // Affectez d'autres attributs
        produitOffert.setNom(produitOffertDTO.getNom());
        produitOffert.setDescription(produitOffertDTO.getDescription());
        produitOffert.setQuantite(produitOffertDTO.getQuantite());
        produitOffert.setPrix(produitOffertDTO.getPrix());

        return produitOffertMapper.toDto(produitOffertRepository.save(produitOffert));
    }


    /**
     * Update a produitOffert.
     *
     * @param produitOffertDTO the entity to save.
     * @return the persisted entity.
     */
    public ProduitOffertDTO update(ProduitOffertDTO produitOffertDTO) {
        log.debug("Request to update ProduitOffert : {}", produitOffertDTO);
        ProduitOffert produitOffert = produitOffertMapper.toEntity(produitOffertDTO);
        produitOffert = produitOffertRepository.save(produitOffert);
        return produitOffertMapper.toDto(produitOffert);
    }

    /**
     * Partially update a produitOffert.
     *
     * @param produitOffertDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ProduitOffertDTO> partialUpdate(ProduitOffertDTO produitOffertDTO) {
        log.debug("Request to partially update ProduitOffert : {}", produitOffertDTO);

        return produitOffertRepository
            .findById(produitOffertDTO.getId())
            .map(existingProduitOffert -> {
                produitOffertMapper.partialUpdate(existingProduitOffert, produitOffertDTO);

                return existingProduitOffert;
            })
            .map(produitOffertRepository::save)
            .map(produitOffertMapper::toDto);
    }

    /**
     * Get all the produitOfferts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProduitOffertDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProduitOfferts");
        return produitOffertRepository.findAll(pageable).map(produitOffertMapper::toDto);
    }

    /**
     * Get one produitOffert by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProduitOffertDTO> findOne(Long id) {
        log.debug("Request to get ProduitOffert : {}", id);
        return produitOffertRepository.findById(id).map(produitOffertMapper::toDto);
    }

    /**
     * Delete the produitOffert by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ProduitOffert : {}", id);
        produitOffertRepository.deleteById(id);
    }
    public List<ProduitOffertDTO> findByOffreId(Long offreId) {
        List<ProduitOffert> produitOfferts = produitOffertRepository.findByOffreId(offreId);
        return produitOfferts.stream()
                .map(produitOffertMapper::toDto)
                .collect(Collectors.toList());
    }
}
