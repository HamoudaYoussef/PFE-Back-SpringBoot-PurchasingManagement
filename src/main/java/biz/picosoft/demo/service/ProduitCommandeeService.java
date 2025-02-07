package biz.picosoft.demo.service;

import biz.picosoft.demo.domain.*;
import biz.picosoft.demo.errors.ResourceNotFoundException;
import biz.picosoft.demo.repository.DemandeDevisRepository;
import biz.picosoft.demo.repository.OffreRepository;
import biz.picosoft.demo.repository.ProduitCommandeeRepository;
import biz.picosoft.demo.repository.ProduitOffertRepository;
import biz.picosoft.demo.service.dto.ProduitCommandeeDTO;
import biz.picosoft.demo.service.mapper.ProduitCommandeeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProduitCommandeeService {

    private final Logger log = LoggerFactory.getLogger(ProduitCommandeeService.class);

    private final ProduitCommandeeRepository produitCommandeeRepository;
    private final ProduitOffertRepository produitOffertRepository;
    private final OffreRepository offreRepository;


    private final DemandeDevisRepository demandeDevisRepository;

    private final ProduitCommandeeMapper produitCommandeeMapper;

    public ProduitCommandeeService(ProduitCommandeeRepository produitCommandeeRepository,ProduitOffertRepository produitOffertRepository,
                                   DemandeDevisRepository demandeDevisRepository,ProduitCommandeeMapper produitCommandeeMapper,OffreRepository offreRepository) {
        this.produitCommandeeRepository = produitCommandeeRepository;
        this.produitCommandeeMapper = produitCommandeeMapper;
        this.demandeDevisRepository = demandeDevisRepository;
        this.produitOffertRepository = produitOffertRepository;
        this.offreRepository = offreRepository;
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
     * Get all the produitCommandeess.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProduitCommandeeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProduitCommandees");
        return produitCommandeeRepository.findAll(pageable).map(produitCommandeeMapper::toDto);

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
        return produitCommandeeRepository.findById(id)
                .map(produitCommandeeMapper::toDto);
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

    public List<ProduitCommandee> getProduitCommandeByDemandeDevisId(Long demandeDevisId) {
        return produitCommandeeRepository.findByDemandeDevis_Id(demandeDevisId);
    }
    @Transactional(readOnly = true)
    public DemandeDevis getDemandeDevisByProduitCommandee(Long produitCommandeeId) {
        return produitCommandeeRepository.getDemandeDevisByProduitCommandeeId(produitCommandeeId);
    }


}
