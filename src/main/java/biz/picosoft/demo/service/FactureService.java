package biz.picosoft.demo.service;

import biz.picosoft.demo.domain.Facture;
import biz.picosoft.demo.repository.FactureRepository;
import biz.picosoft.demo.service.dto.FactureDTO;
import biz.picosoft.demo.service.mapper.FactureMapper;
;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class FactureService {

    private final Logger log = LoggerFactory.getLogger(FactureService.class);

    private final FactureRepository factureRepository;

    private final FactureMapper factureMapper;

    public FactureService(FactureRepository factureRepository, FactureMapper factureMapper) {
        this.factureRepository = factureRepository;
        this.factureMapper = factureMapper;
    }

    /**
     * Save a facture.
     *
     * @param factureDTO the entity to save.
     * @return the persisted entity.
     */
    public FactureDTO save(FactureDTO factureDTO) {
        log.debug("Request to save Facture : {}", factureDTO);
        Facture facture = factureMapper.toEntity(factureDTO);
        facture = factureRepository.save(facture);
        return factureMapper.toDto(facture);
    }

    /**
     * Update a facture.
     *
     * @param factureDTO the entity to save.
     * @return the persisted entity.
     */
    public FactureDTO update(FactureDTO factureDTO) {
        log.debug("Request to update Facture : {}", factureDTO);
        Facture facture = factureMapper.toEntity(factureDTO);
        facture = factureRepository.save(facture);
        return factureMapper.toDto(facture);
    }

    /**
     * Partially update a facture.
     *
     * @param factureDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<FactureDTO> partialUpdate(FactureDTO factureDTO) {
        log.debug("Request to partially update Facture : {}", factureDTO);

        return factureRepository
            .findById(factureDTO.getId())
            .map(existingFacture -> {
                factureMapper.partialUpdate(existingFacture, factureDTO);

                return existingFacture;
            })
            .map(factureRepository::save)
            .map(factureMapper::toDto);
    }

    /**
     * Get all the factures.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<FactureDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Factures");
        return factureRepository.findAll(pageable).map(factureMapper::toDto);
    }

    /**
     * Get one facture by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FactureDTO> findOne(Long id) {
        log.debug("Request to get Facture : {}", id);
        return factureRepository.findById(id).map(factureMapper::toDto);
    }

    /**
     * Delete the facture by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Facture : {}", id);
        factureRepository.deleteById(id);
    }
}
