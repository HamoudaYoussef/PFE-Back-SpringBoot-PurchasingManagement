package biz.picosoft.demo.service;

import biz.picosoft.demo.domain.BonCommande;
import biz.picosoft.demo.repository.BonCommandeRepository;
import biz.picosoft.demo.service.dto.BonCommandeDTO;
import biz.picosoft.demo.service.mapper.BonCommandeMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link biz.picosoft.demo.domain.BonCommande}.
 */
@Service
@Transactional
public class BonCommandeService {

    private final Logger log = LoggerFactory.getLogger(BonCommandeService.class);

    private final BonCommandeRepository bonCommandeRepository;

    private final BonCommandeMapper bonCommandeMapper;

    public BonCommandeService(BonCommandeRepository bonCommandeRepository, BonCommandeMapper bonCommandeMapper) {
        this.bonCommandeRepository = bonCommandeRepository;
        this.bonCommandeMapper = bonCommandeMapper;
    }

    /**
     * Save a bonCommande.
     *
     * @param bonCommandeDTO the entity to save.
     * @return the persisted entity.
     */
    public BonCommandeDTO save(BonCommandeDTO bonCommandeDTO) {
        log.debug("Request to save BonCommande : {}", bonCommandeDTO);
        BonCommande bonCommande = bonCommandeMapper.toEntity(bonCommandeDTO);
        bonCommande = bonCommandeRepository.save(bonCommande);
        return bonCommandeMapper.toDto(bonCommande);
    }

    /**
     * Update a bonCommande.
     *
     * @param bonCommandeDTO the entity to save.
     * @return the persisted entity.
     */
    public BonCommandeDTO update(BonCommandeDTO bonCommandeDTO) {
        log.debug("Request to update BonCommande : {}", bonCommandeDTO);
        BonCommande bonCommande = bonCommandeMapper.toEntity(bonCommandeDTO);
        bonCommande = bonCommandeRepository.save(bonCommande);
        return bonCommandeMapper.toDto(bonCommande);
    }

    /**
     * Partially update a bonCommande.
     *
     * @param bonCommandeDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<BonCommandeDTO> partialUpdate(BonCommandeDTO bonCommandeDTO) {
        log.debug("Request to partially update BonCommande : {}", bonCommandeDTO);

        return bonCommandeRepository
            .findById(bonCommandeDTO.getId())
            .map(existingBonCommande -> {
                bonCommandeMapper.partialUpdate(existingBonCommande, bonCommandeDTO);

                return existingBonCommande;
            })
            .map(bonCommandeRepository::save)
            .map(bonCommandeMapper::toDto);
    }

    /**
     * Get all the bonCommandes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<BonCommandeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all BonCommandes");
        return bonCommandeRepository.findAll(pageable).map(bonCommandeMapper::toDto);
    }

    /**
     * Get one bonCommande by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BonCommandeDTO> findOne(Long id) {
        log.debug("Request to get BonCommande : {}", id);
        return bonCommandeRepository.findById(id).map(bonCommandeMapper::toDto);
    }

    /**
     * Delete the bonCommande by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete BonCommande : {}", id);
        bonCommandeRepository.deleteById(id);
    }
}
