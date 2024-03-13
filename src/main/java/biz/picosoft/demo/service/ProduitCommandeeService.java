package biz.picosoft.demo.service;

import biz.picosoft.demo.domain.ProduitCommandee;
import biz.picosoft.demo.repository.ProduitCommandeeRepository;
import biz.picosoft.demo.service.dto.ProduitCommandeeDTO;
import biz.picosoft.demo.service.mapper.ProduitCommandeeMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link biz.picosoft.demo.domain.ProduitCommandee}.
 */
@Service
@Transactional
public class ProduitCommandeeService {

    private final Logger log = LoggerFactory.getLogger(ProduitCommandeeService.class);

    private final ProduitCommandeeRepository produitCommandeeRepository;

    private final ProduitCommandeeMapper produitCommandeeMapper;

    public ProduitCommandeeService(ProduitCommandeeRepository produitCommandeeRepository, ProduitCommandeeMapper produitCommandeeMapper) {
        this.produitCommandeeRepository = produitCommandeeRepository;
        this.produitCommandeeMapper = produitCommandeeMapper;
    }

    /**
     * Save a produitCommandee.
     *
     * @param produitCommandeeDTO the entity to save.
     * @return the persisted entity.
     */
    public ProduitCommandeeDTO save(ProduitCommandeeDTO produitCommandeeDTO) {
        log.debug("Request to save ProduitCommandee : {}", produitCommandeeDTO);
        ProduitCommandee produitCommandee = produitCommandeeMapper.toEntity(produitCommandeeDTO);
        produitCommandee = produitCommandeeRepository.save(produitCommandee);
        return produitCommandeeMapper.toDto(produitCommandee);
    }

    /**
     * Update a produitCommandee.
     *
     * @param produitCommandeeDTO the entity to save.
     * @return the persisted entity.
     */
    public ProduitCommandeeDTO update(ProduitCommandeeDTO produitCommandeeDTO) {
        log.debug("Request to update ProduitCommandee : {}", produitCommandeeDTO);
        ProduitCommandee produitCommandee = produitCommandeeMapper.toEntity(produitCommandeeDTO);
        produitCommandee = produitCommandeeRepository.save(produitCommandee);
        return produitCommandeeMapper.toDto(produitCommandee);
    }

    /**
     * Partially update a produitCommandee.
     *
     * @param produitCommandeeDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ProduitCommandeeDTO> partialUpdate(ProduitCommandeeDTO produitCommandeeDTO) {
        log.debug("Request to partially update ProduitCommandee : {}", produitCommandeeDTO);

        return produitCommandeeRepository
            .findById(produitCommandeeDTO.getId())
            .map(existingProduitCommandee -> {
                produitCommandeeMapper.partialUpdate(existingProduitCommandee, produitCommandeeDTO);

                return existingProduitCommandee;
            })
            .map(produitCommandeeRepository::save)
            .map(produitCommandeeMapper::toDto);
    }

    /**
     * Get all the produitCommandees.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProduitCommandeeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProduitCommandees");
        return produitCommandeeRepository.findAll(pageable).map(produitCommandeeMapper::toDto);
    }

    /**
     * Get all the produitCommandees with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<ProduitCommandeeDTO> findAllWithEagerRelationships(Pageable pageable) {
        return produitCommandeeRepository.findAllWithEagerRelationships(pageable).map(produitCommandeeMapper::toDto);
    }

    /**
     * Get one produitCommandee by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProduitCommandeeDTO> findOne(Long id) {
        log.debug("Request to get ProduitCommandee : {}", id);
        return produitCommandeeRepository.findOneWithEagerRelationships(id).map(produitCommandeeMapper::toDto);
    }

    /**
     * Delete the produitCommandee by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ProduitCommandee : {}", id);
        produitCommandeeRepository.deleteById(id);
    }
}
