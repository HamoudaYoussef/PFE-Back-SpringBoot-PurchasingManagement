package biz.picosoft.demo.service;

import biz.picosoft.demo.domain.DemandeDevis;
import biz.picosoft.demo.repository.DemandeDevisRepository;
import biz.picosoft.demo.service.mapper.DemandeDevisMapper;
import com.mycompany.demo.service.dto.DemandeDevisDTO;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 */
@Service
@Transactional
public class DemandeDevisService {

    private final Logger log = LoggerFactory.getLogger(DemandeDevisService.class);

    private final DemandeDevisRepository demandeDevisRepository;

    private final DemandeDevisMapper demandeDevisMapper;

    public DemandeDevisService(DemandeDevisRepository demandeDevisRepository, DemandeDevisMapper demandeDevisMapper) {
        this.demandeDevisRepository = demandeDevisRepository;
        this.demandeDevisMapper = demandeDevisMapper;
    }

    /**
     * Save a demandeDevis.
     *
     * @param demandeDevisDTO the entity to save.
     * @return the persisted entity.
     */
    public DemandeDevisDTO save(DemandeDevisDTO demandeDevisDTO) {
        log.debug("Request to save DemandeDevis : {}", demandeDevisDTO);
        DemandeDevis demandeDevis = demandeDevisMapper.toEntity(demandeDevisDTO);
        demandeDevis = demandeDevisRepository.save(demandeDevis);
        return demandeDevisMapper.toDto(demandeDevis);
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
