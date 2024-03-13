package biz.picosoft.demo.service;

import biz.picosoft.demo.domain.ProduitOffert;
import biz.picosoft.demo.repository.ProduitOffertRepository;
import biz.picosoft.demo.service.dto.ProduitOffertDTO;
import biz.picosoft.demo.service.mapper.ProduitOffertMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link biz.picosoft.demo.domain.ProduitOffert}.
 */
@Service
@Transactional
public class ProduitOffertService {

    private final Logger log = LoggerFactory.getLogger(ProduitOffertService.class);

    private final ProduitOffertRepository produitOffertRepository;

    private final ProduitOffertMapper produitOffertMapper;

    public ProduitOffertService(ProduitOffertRepository produitOffertRepository, ProduitOffertMapper produitOffertMapper) {
        this.produitOffertRepository = produitOffertRepository;
        this.produitOffertMapper = produitOffertMapper;
    }

    /**
     * Save a produitOffert.
     *
     * @param produitOffertDTO the entity to save.
     * @return the persisted entity.
     */
    public ProduitOffertDTO save(ProduitOffertDTO produitOffertDTO) {
        log.debug("Request to save ProduitOffert : {}", produitOffertDTO);
        ProduitOffert produitOffert = produitOffertMapper.toEntity(produitOffertDTO);
        produitOffert = produitOffertRepository.save(produitOffert);
        return produitOffertMapper.toDto(produitOffert);
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
}
