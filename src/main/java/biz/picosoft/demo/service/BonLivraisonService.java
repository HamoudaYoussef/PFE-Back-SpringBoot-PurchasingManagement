package biz.picosoft.demo.service;

import biz.picosoft.demo.domain.BonLivraison;
import biz.picosoft.demo.repository.BonLivraisonRepository;
import biz.picosoft.demo.service.dto.BonLivraisonDTO;
import biz.picosoft.demo.service.mapper.BonLivraisonMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link biz.picosoft.demo.domain.BonLivraison}.
 */
@Service
@Transactional
public class BonLivraisonService {

    private final Logger log = LoggerFactory.getLogger(BonLivraisonService.class);

    private final BonLivraisonRepository bonLivraisonRepository;

    private final BonLivraisonMapper bonLivraisonMapper;

    public BonLivraisonService(BonLivraisonRepository bonLivraisonRepository, BonLivraisonMapper bonLivraisonMapper) {
        this.bonLivraisonRepository = bonLivraisonRepository;
        this.bonLivraisonMapper = bonLivraisonMapper;
    }

    /**
     * Save a bonLivraison.
     *
     * @param bonLivraisonDTO the entity to save.
     * @return the persisted entity.
     */
    public BonLivraisonDTO save(BonLivraisonDTO bonLivraisonDTO) {
        log.debug("Request to save BonLivraison : {}", bonLivraisonDTO);
        BonLivraison bonLivraison = bonLivraisonMapper.toEntity(bonLivraisonDTO);
        bonLivraison = bonLivraisonRepository.save(bonLivraison);
        return bonLivraisonMapper.toDto(bonLivraison);
    }

    /**
     * Update a bonLivraison.
     *
     * @param bonLivraisonDTO the entity to save.
     * @return the persisted entity.
     */
    public BonLivraisonDTO update(BonLivraisonDTO bonLivraisonDTO) {
        log.debug("Request to update BonLivraison : {}", bonLivraisonDTO);
        BonLivraison bonLivraison = bonLivraisonMapper.toEntity(bonLivraisonDTO);
        bonLivraison = bonLivraisonRepository.save(bonLivraison);
        return bonLivraisonMapper.toDto(bonLivraison);
    }

    /**
     * Partially update a bonLivraison.
     *
     * @param bonLivraisonDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<BonLivraisonDTO> partialUpdate(BonLivraisonDTO bonLivraisonDTO) {
        log.debug("Request to partially update BonLivraison : {}", bonLivraisonDTO);

        return bonLivraisonRepository
            .findById(bonLivraisonDTO.getId())
            .map(existingBonLivraison -> {
                bonLivraisonMapper.partialUpdate(existingBonLivraison, bonLivraisonDTO);

                return existingBonLivraison;
            })
            .map(bonLivraisonRepository::save)
            .map(bonLivraisonMapper::toDto);
    }

    /**
     * Get all the bonLivraisons.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<BonLivraisonDTO> findAll(Pageable pageable) {
        log.debug("Request to get all BonLivraisons");
        return bonLivraisonRepository.findAll(pageable).map(bonLivraisonMapper::toDto);
    }

    /**
     * Get one bonLivraison by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BonLivraisonDTO> findOne(Long id) {
        log.debug("Request to get BonLivraison : {}", id);
        return bonLivraisonRepository.findById(id).map(bonLivraisonMapper::toDto);
    }

    /**
     * Delete the bonLivraison by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete BonLivraison : {}", id);
        bonLivraisonRepository.deleteById(id);
    }
}
