package biz.picosoft.demo.service;

import biz.picosoft.demo.domain.Produit;
import biz.picosoft.demo.repository.ProduitRepository;
import biz.picosoft.demo.service.dto.ProduitDTO;
import biz.picosoft.demo.service.mapper.ProduitMapper;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class ProduitService {

    private final Logger log = LoggerFactory.getLogger(ProduitService.class);

    private final ProduitRepository produitRepository;


    private final ProduitMapper produitMapper;

    public ProduitService(ProduitRepository produitRepository, ProduitMapper produitMapper) {
        this.produitRepository = produitRepository;
        this.produitMapper = produitMapper;
    }

    /**
     * Save a produit.
     *
     * @param produitDTO the entity to save.
     * @return the persisted entity.
     */
    public ProduitDTO save(ProduitDTO produitDTO) {
        log.debug("Request to save Produit : {}", produitDTO);
        Produit produit = produitMapper.toEntity(produitDTO);
        produit = produitRepository.save(produit);
        return produitMapper.toDto(produit);
    }

    /**
     * Update a produit.
     *
     * @param produitDTO the entity to save.
     * @return the persisted entity.
     */
    public ProduitDTO update(ProduitDTO produitDTO) {
        log.debug("Request to update Produit : {}", produitDTO);
        Produit produit = produitMapper.toEntity(produitDTO);
        produit = produitRepository.save(produit);
        return produitMapper.toDto(produit);
    }

    /**
     * Partially update a produit.
     *
     * @param produitDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ProduitDTO> partialUpdate(ProduitDTO produitDTO) {
        log.debug("Request to partially update Produit : {}", produitDTO);

        return produitRepository
            .findById(produitDTO.getId())
            .map(existingProduit -> {
                produitMapper.partialUpdate(existingProduit, produitDTO);

                return existingProduit;
            })
            .map(produitRepository::save)
            .map(produitMapper::toDto);
    }

    /**
     * Get all the produits.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProduitDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Produits");
        return produitRepository.findAll(pageable).map(produitMapper::toDto);
    }
    public List<ProduitDTO> findAllSimple() {
        log.debug("Request to get all Produits");
        return produitMapper.toDto(produitRepository.findAll());
    }

    /**
     * Get one produit by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProduitDTO> findOne(Long id) {
        log.debug("Request to get Produit : {}", id);
        return produitRepository.findById(id).map(produitMapper::toDto);
    }

    /**
     * Delete the produit by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Produit : {}", id);
        produitRepository.deleteById(id);
    }

  /*  public List<Produit> getProduitsByDemandeAchatId(Long demandeAchatId) {
        List<Long> produitIds = produitDemandeeRepository.findDistinctProduitIdsByDemandeAchatId(demandeAchatId);
        return produitRepository.findByIdIn(produitIds);
    }
    public List<Produit> getProduitsByFournisseur(Long fournisseurId) {
        List<Long> produitIds = produitDemandeeRepository.findDistinctProduitIdsByDemandeAchatId(fournisseurId);
        return produitRepository.findByIdIn(produitIds);
    }*/
    public String verifierStock(Long id) {
        Optional<Produit> produitOpt = produitRepository.findById(id);

        if (!produitOpt.isPresent()) {
            return "Produit non trouvé";
        }

        Produit produit = produitOpt.get();

        if (produit.getQuantite() == 0) {
            return "Stock insuffisant : la quantité est 0";
        }

        if (produit.getQuantitedemandeur() > produit.getQuantite()) {
            return "Stock insuffisant : la quantité demandée est supérieure à la quantité disponible";
        }

        return "Stock suffisant";
    }

}
