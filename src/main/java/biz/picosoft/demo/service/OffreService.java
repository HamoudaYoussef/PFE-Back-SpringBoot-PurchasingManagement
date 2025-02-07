package biz.picosoft.demo.service;

import biz.picosoft.demo.domain.*;
import biz.picosoft.demo.repository.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import biz.picosoft.demo.service.dto.OffreDTO;
import biz.picosoft.demo.service.dto.ProduitOffertDTO;
import biz.picosoft.demo.service.mapper.OffreMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
@Transactional
public class OffreService {

    private final Logger log = LoggerFactory.getLogger(OffreService.class);

    private final OffreRepository offreRepository;
    private final FournisseurRepository fournisseurRepository;
    private final DemandeAchatRepository demandeAchatRepository;
    private final ProduitOffertRepository produitOffertRepository;

    private final DemandeDevisRepository demandeDevisRepository;




    private final OffreMapper offreMapper;

    public OffreService(OffreRepository offreRepository,DemandeDevisRepository demandeDevisRepository,
                        DemandeAchatRepository demandeAchatRepository,ProduitOffertRepository produitOffertRepository,FournisseurRepository fournisseurRepository, OffreMapper offreMapper) {
        this.offreRepository = offreRepository;
        this.offreMapper = offreMapper;
        this.fournisseurRepository = fournisseurRepository;
        this.demandeAchatRepository = demandeAchatRepository;
        this.demandeDevisRepository = demandeDevisRepository;
        this.produitOffertRepository= produitOffertRepository;
    }

    /**
     * Save a offre.
     *
     * @param offreDTO the entity to save.
     * @return the persisted entity.
     */
    public OffreDTO save(OffreDTO offreDTO) {
        log.debug("Request to save Offre : {}", offreDTO);
        Offre offre = offreMapper.toEntity(offreDTO);

        DemandeDevis demandeDevis = demandeDevisRepository.findById(offreDTO.getDemandeDevis().getId())
                .orElseThrow(() -> new RuntimeException("DemandeDevis not found"));

        // Créer l'offre et l'associer à la demande de devis
        offre.setDemandeDevis(demandeDevis);
        offre.setDateoffre(LocalDate.now());
        offre = offreRepository.save(offre);
        return offreMapper.toDto(offre);
    }
    public Offre ajouterOffreAvecProduits(OffreDTO offreDTO) {
        // Vérifiez si l'offreDTO est null
        if (offreDTO == null) {
            throw new IllegalArgumentException("Le DTO de l'offre ne peut pas être nul");
        }

        // Créer et initialiser l'entité Offre
        Offre offre = new Offre();
        offre.setNom(offreDTO.getNom());
        offre.setPrix(offreDTO.getPrix());
        offre.setDateoffre(offreDTO.getDateoffre());
        offre.setDescription(offreDTO.getDescription());
        offre.setReferenceoffre(offreDTO.getReferenceoffre());

        // Vérifications supplémentaires pour l'offre
        if (offre.getNom() == null || offre.getNom().isEmpty()) {
            throw new IllegalArgumentException("Le nom de l'offre ne peut pas être nul ou vide");
        }
        if (offre.getPrix() == null) {
            throw new IllegalArgumentException("Le prix de l'offre ne peut pas être nul");
        }

        // Créer et associer les produits offerts
        Set<ProduitOffert> produits = new HashSet<>();
        for (ProduitOffert produitOffert : produitOffertRepository.findByOffreId(offreDTO.getId())) {
            Produit produit = produitOffert.getProduit();

            if (produit == null) {
                throw new IllegalArgumentException("Le produit ne peut pas être nul");
            }
            if (produit.getNom() == null || produit.getNom().isEmpty()) {
                throw new IllegalArgumentException("Le nom du produit ne peut pas être nul ou vide");
            }
            if (produitOffert.getPrix() == null || produitOffert.getQuantite() == null) {
                throw new IllegalArgumentException("Le prix et la quantité du produit ne peuvent pas être nuls");
            }

            // Créer une nouvelle instance de ProduitOffert et non de ProduitOffertDTO
            ProduitOffert produitOffertEntity = new ProduitOffert();
            produitOffertEntity.setProduit(produit); // Associer le produit existant
            produitOffertEntity.setQuantite(produitOffert.getQuantite());
            produitOffertEntity.setPrix(produitOffert.getPrix());
            produits.add(produitOffertEntity);
        }

        // Sauvegarder l'offre et les produits offerts
        Offre savedOffre = offreRepository.save(offre);
        produitOffertRepository.saveAll(produits);

        return savedOffre;
    }


    public OffreDTO savewithoutdd(OffreDTO offreDTO) {
        log.debug("Request to save Offre : {}", offreDTO);
        Offre offre = offreMapper.toEntity(offreDTO);

        offre = offreRepository.save(offre);
        return offreMapper.toDto(offre);
    }

    /**
     * Update a offre.
     *
     * @param offreDTO the entity to save.
     * @return the persisted entity.
     */
    public OffreDTO update(OffreDTO offreDTO) {
        log.debug("Request to update Offre : {}", offreDTO);
        Offre offre = offreMapper.toEntity(offreDTO);
        offre = offreRepository.save(offre);
        return offreMapper.toDto(offre);
    }

    /**
     * Partially update a offre.
     *
     * @param offreDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<OffreDTO> partialUpdate(OffreDTO offreDTO) {
        log.debug("Request to partially update Offre : {}", offreDTO);

        return offreRepository
            .findById(offreDTO.getId())
            .map(existingOffre -> {
                offreMapper.partialUpdate(existingOffre, offreDTO);

                return existingOffre;
            })
            .map(offreRepository::save)
            .map(offreMapper::toDto);
    }

    /**
     * Get all the offres.
     *
     * @return the list of entities.
     */
    public List<OffreDTO> findAll(Sort sort) {
        List<Offre> offreList = offreRepository.findAll(sort);
        return offreList.stream()
                .map(offreMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Get one offre by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<OffreDTO> findOne(Long id) {
        log.debug("Request to get Offre : {}", id);
        return offreRepository.findById(id).map(offreMapper::toDto);
    }

    /**
     * Delete the offre by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Offre : {}", id);
        offreRepository.deleteById(id);
    }
    public List<Offre> findRecentOffres() {
        return offreRepository.findRecentOffres();
    }
    public List<Offre> findOffresByFournisseur(Long fournisseurId) {
        return offreRepository.findOffresByFournisseur(fournisseurId);
    }

    public Offre findOffreByDemandeDevis(Long demanedDevisId) {
        return offreRepository.getOffreByDemandeDevis_Id(demanedDevisId);
    }
    public Fournisseur getFournisseurByOffreId(Long offreId) {
        Offre offre = offreRepository.findById(offreId)
                .orElseThrow(() -> new EntityNotFoundException("Offre not found with id " + offreId));
        return offre.getFournisseur();
    }

}
