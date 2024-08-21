package biz.picosoft.demo.service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import biz.picosoft.demo.domain.DemandeAchat;
import biz.picosoft.demo.domain.Offre;
import biz.picosoft.demo.domain.ProduitDemandee;
import biz.picosoft.demo.repository.DemandeAchatRepository;
import biz.picosoft.demo.repository.ProduitDemandeeRepository;
import biz.picosoft.demo.service.dto.ProduitDemandeeDTO;
import biz.picosoft.demo.service.mapper.ProduitDemandeeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.persistence.EntityManager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 */
@Service
@Transactional
public class ProduitDemandeeService {

    private final Logger log = LoggerFactory.getLogger(ProduitDemandeeService.class);

    private final ProduitDemandeeRepository produitDemandeeRepository;
    private final DemandeAchatRepository demandeAchatRepository;


    private final ProduitDemandeeMapper produitDemandeeMapper;

    public ProduitDemandeeService(DemandeAchatRepository demandeAchatRepository,ProduitDemandeeRepository produitDemandeeRepository, ProduitDemandeeMapper produitDemandeeMapper) {
        this.produitDemandeeRepository = produitDemandeeRepository;
        this.produitDemandeeMapper = produitDemandeeMapper;
        this.demandeAchatRepository = demandeAchatRepository;
    }

    /**
     * Save a produitDemandee.
     *
     * @param produitDemandeeDTO the entity to save.
     * @return the persisted entity.
     */
    public ProduitDemandeeDTO save(Long demandeAchatId, ProduitDemandeeDTO produitDemandeeDTO) {
        DemandeAchat demandeAchat = demandeAchatRepository.findById(demandeAchatId).get();
        ProduitDemandee produitDemandee = produitDemandeeMapper.toEntity(produitDemandeeDTO);
        produitDemandee.setDemandeAchat(demandeAchat);
        produitDemandee = produitDemandeeRepository.save(produitDemandee);
        return produitDemandeeMapper.toDto(produitDemandee);
    }

    /**
     * Update a produitDemandee.
     *
     * @param produitDemandeeDTO the entity to save.
     * @return the persisted entity.
     */
    public ProduitDemandeeDTO update(ProduitDemandeeDTO produitDemandeeDTO) {
        log.debug("Request to update ProduitDemandee : {}", produitDemandeeDTO);
        ProduitDemandee produitDemandee = produitDemandeeMapper.toEntity(produitDemandeeDTO);
        produitDemandee = produitDemandeeRepository.save(produitDemandee);
        return produitDemandeeMapper.toDto(produitDemandee);
    }

    /**
     * Partially update a produitDemandee.
     *
     * @param produitDemandeeDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ProduitDemandeeDTO> partialUpdate(ProduitDemandeeDTO produitDemandeeDTO) {
        log.debug("Request to partially update ProduitDemandee : {}", produitDemandeeDTO);

        return produitDemandeeRepository
            .findById(produitDemandeeDTO.getId())
            .map(existingProduitDemandee -> {
                produitDemandeeMapper.partialUpdate(existingProduitDemandee, produitDemandeeDTO);

                return existingProduitDemandee;
            })
            .map(produitDemandeeRepository::save)
            .map(produitDemandeeMapper::toDto);
    }

    /**
     * Get all the produitDemandees.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProduitDemandeeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProduitDemandees");
        return produitDemandeeRepository.findAll(pageable).map(produitDemandeeMapper::toDto);
    }

    /**
     * Get one produitDemandee by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */

    @Transactional(readOnly = true)
    public Optional<ProduitDemandeeDTO> findOne(Long id) {
        log.debug("Request to get ProduitDemandee : {}", id);
        return produitDemandeeRepository.findById(id).map(produitDemandeeMapper::toDto);
    }

    /**
     * Delete the produitDemandee by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ProduitDemandee : {}", id);
        produitDemandeeRepository.deleteById(id);
    }

    public List<ProduitDemandee> getProduitDemandeByDemandeAchatId(Long demandeAchatId) {
        return produitDemandeeRepository.findByDemandeAchatId(demandeAchatId);
    }
    public List<ProduitDemandee> getRecentProduitDemandee() {
        LocalDate twoWeeksAgo = LocalDate.now().minusWeeks(2);
        return produitDemandeeRepository.findRecentProduitsDemandee(twoWeeksAgo);
    }

    public Map<DemandeAchat, List<ProduitDemandee>> getProduitsGroupedByDemandeAchat() {
        List<ProduitDemandee> produits = produitDemandeeRepository.findAll();
        return produits.stream().collect(Collectors.groupingBy(ProduitDemandee::getDemandeAchat));
    }
}
