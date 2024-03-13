package biz.picosoft.demo.config.logging;


import biz.picosoft.demo.config.logging.domain.AopLogging;
import biz.picosoft.demo.config.logging.repository.AopLoggingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link AopLogging}.
 */
@Service
public class AopLoggingService {

    private final Logger log = LoggerFactory.getLogger(AopLoggingService.class);

    private final AopLoggingRepository aopLoggingRepository;

    private final AopLoggingMapper aopLoggingMapper;

    public AopLoggingService(AopLoggingRepository aopLoggingRepository, AopLoggingMapper aopLoggingMapper) {
        this.aopLoggingRepository = aopLoggingRepository;
        this.aopLoggingMapper = aopLoggingMapper;
    }







    /**
     * Save a aopLogging.
     *
     * @param aopLoggingDTO the entity to save.
     * @return the persisted entity.
     */
    public AopLoggingDTO save(AopLoggingDTO aopLoggingDTO) {
        log.debug("Request to save AopLogging : {}", aopLoggingDTO);
        AopLogging aopLogging = aopLoggingMapper.toEntity(aopLoggingDTO);
        aopLogging = aopLoggingRepository.save(aopLogging);
        return aopLoggingMapper.toDto(aopLogging);
    }

    /**
     * Partially update a aopLogging.
     *
     * @param aopLoggingDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AopLoggingDTO> partialUpdate(AopLoggingDTO aopLoggingDTO) {
        log.debug("Request to partially update AopLogging : {}", aopLoggingDTO);

        return aopLoggingRepository
            .findById(aopLoggingDTO.getId())
//            .map(existingAopLogging -> {
//                aopLoggingMapper.partialUpdate(existingAopLogging, aopLoggingDTO);
//
//                return existingAopLogging;
//            })
            .map(aopLoggingRepository::save)
            .map(aopLoggingMapper::toDto);
    }

    /**
     * Get all the aopLoggings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AopLoggingDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AopLoggings");
        return aopLoggingRepository.findAll(pageable).map(aopLoggingMapper::toDto);
    }

    /**
     * Get one aopLogging by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AopLoggingDTO> findOne(Long id) {
        log.debug("Request to get AopLogging : {}", id);
        return aopLoggingRepository.findById(id).map(aopLoggingMapper::toDto);
    }

    /**
     * Delete the aopLogging by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AopLogging : {}", id);
        aopLoggingRepository.deleteById(id);
    }
}
