package biz.picosoft.demo.service;

import biz.picosoft.demo.domain.*;
import biz.picosoft.demo.errors.ResourceNotFoundException;
import biz.picosoft.demo.repository.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import biz.picosoft.demo.service.dto.DemandeDevisDTO;
import biz.picosoft.demo.service.dto.ProduitOffertDTO;
import biz.picosoft.demo.service.mapper.FournisseurMapper;
import biz.picosoft.demo.service.mapper.OffreMapper;
import biz.picosoft.demo.service.mapper.ProduitMapper;
import biz.picosoft.demo.service.mapper.ProduitOffertMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

/**
 */
@Service
@Transactional
public class ProduitOffertService {

    private final Logger log = LoggerFactory.getLogger(ProduitOffertService.class);

    private final ProduitOffertRepository produitOffertRepository;
    private final OffreRepository offreRepository;

    private final ProduitRepository produitRepository;
    private final BonCommandeRepository bonCommandeRepository;


    private final ProduitOffertMapper produitOffertMapper;
    private final OffreMapper offreMapper;
    private final FournisseurMapper fournisseurMapper;

    private final ProduitMapper produitMapper;

    private final ProduitCommandeeRepository produitCommandeeRepository;


    public ProduitOffertService(ProduitOffertRepository produitOffertRepository, OffreRepository offreRepository, ProduitCommandeeRepository produitCommandeeRepository,
                                ProduitOffertMapper produitOffertMapper, ProduitRepository produitRepository, BonCommandeRepository bonCommandeRepository,
                                OffreMapper offreMapper, ProduitMapper produitMapper, FournisseurMapper fournisseurMapper) {
        this.produitOffertRepository = produitOffertRepository;
        this.produitOffertMapper = produitOffertMapper;
        this.offreRepository = offreRepository;
        this.produitRepository = produitRepository;
        this.offreMapper = offreMapper;
        this.produitMapper = produitMapper;
        this.fournisseurMapper = fournisseurMapper;
        this.bonCommandeRepository = bonCommandeRepository;
        this.produitCommandeeRepository = produitCommandeeRepository;
    }

    /**
     * Save a produitOffert.
     *
     * @param produitOffertDTO the entity to save.
     * @return the persisted entity.
     */
    public ProduitOffertDTO save(ProduitOffertDTO produitOffertDTO) {
        // Créer une instance de ProduitOffert
        ProduitOffert produitOffert = new ProduitOffert();

        // Assurez-vous que vous récupérez l'entité Offre à partir de l'ID
        Offre offre = offreRepository.findById(produitOffertDTO.getOffre().getId())
                .orElseThrow(() -> new EntityNotFoundException("Offre not found"));

        if (offre != null) {
            produitOffert.setOffre(offre); // Associer l'entité Offre au produit offert
        } else {
            throw new IllegalArgumentException("The given id must not be null!");
        }
        Produit produit = produitRepository.findById(produitOffertDTO.getProduit().getId())
                .orElseThrow(() -> new EntityNotFoundException("Offre not found"));

        if (produit != null) {
            produitOffert.setProduit(produit); // Associer l'entité Offre au produit offert
        } else {
            throw new IllegalArgumentException("The given id must not be null!");
        }

        // Affectez d'autres attributs
        produitOffert.setNom(produitOffertDTO.getNom());
        produitOffert.setDescription(produitOffertDTO.getDescription());
        produitOffert.setQuantite(produitOffertDTO.getQuantite());
        produitOffert.setPrix(produitOffertDTO.getPrix());
        produitOffert.setImg(produitOffertDTO.getImg());

        return produitOffertMapper.toDto(produitOffertRepository.save(produitOffert));
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
        return produitOffertRepository.findById(id).map(produitOffert -> {
            produitOffert.getOffre().getFournisseur(); // Force le chargement de la relation fournisseur
            produitOffert.getProduit(); // Force le chargement de la relation demandeAchat

            ProduitOffertDTO dto = produitOffertMapper.toDto(produitOffert);
            dto.setOffre(offreMapper.toDto(produitOffert.getOffre()));
            dto.getOffre().setFournisseur(fournisseurMapper.toDto(produitOffert.getOffre().getFournisseur()));
            // Mapper le fournisseur complet
            dto.setProduit(produitMapper.toDto(produitOffert.getProduit())); // Mapper la demande d'achat complète
            return dto;
        });
    }

    @Transactional
    public ProduitOffert updateProduitOffert(Long id, ProduitOffertDTO produitOffertDTO, Long bonCommandeId) {
        // Vérifier si le produit offert existe
        ProduitOffert produitOffert = produitOffertRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Produit offert non trouvé"));

        BonCommande bonCommande = bonCommandeRepository.findById(bonCommandeId)
                .orElseThrow(() -> new EntityNotFoundException("Bon de commande non trouvé"));

        // Mettre à jour les détails du produit offert
        produitOffert.setNom(produitOffertDTO.getNom());
        produitOffert.setPrix(produitOffertDTO.getPrix());
        produitOffert.setQuantite(produitOffertDTO.getQuantite());
        produitOffert.setDescription(produitOffertDTO.getDescription());
        produitOffert.setImg(produitOffertDTO.getImg());
        produitOffert.setOffre(produitOffert.getOffre());
        produitOffertDTO.setProduit(produitOffertDTO.getProduit());
        produitOffert.setBonCommande(bonCommande); // Mettre à jour la référence au bon de commande


        // Mettez à jour d'autres attributs si nécessaire

        // Enregistrer les mises à jour
        return produitOffertRepository.save(produitOffert);
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

    public List<ProduitOffertDTO> findByOffreId(Long offreId) {
        List<ProduitOffert> produitOfferts = produitOffertRepository.findByOffreId(offreId);
        return produitOfferts.stream()
                .map(produitOffertMapper::toDto)
                .collect(Collectors.toList());
    }

    public String getReferenceOffre(Long produitOffertId) {
        ProduitOffert produitOffert = produitOffertRepository.findById(produitOffertId)
                .orElseThrow(() -> new RuntimeException("Bon de commande non trouvé avec l'ID : " + produitOffertId));

        return produitOffert.getOffre().getReferenceoffre();
    }

    public String getDateOffre(Long produitOffertId) {
        ProduitOffert produitOffert = produitOffertRepository.findById(produitOffertId)
                .orElseThrow(() -> new RuntimeException("Bon de commande non trouvé avec l'ID : " + produitOffertId));

        // Conversion de LocalDate en String
        LocalDate dateOffre = produitOffert.getOffre().getDateoffre();
        return dateOffre.toString(); // Utilise le format ISO par défaut (ex : "2024-10-16")
    }

    public BonCommande getBonCommandeByProduitOffertId(Long produitOffertId) {
        return produitOffertRepository.getBonCommandeByProduitOffertId(produitOffertId);
    }

    public List<ProduitOffertDTO> getProduitOffertsByBonCommande_Id(Long bonCommandeId) {
        return produitOffertRepository.getProduitOffertsByBonCommande_Id(bonCommandeId)
                .stream()
                .map(produitOffert -> {
                    // Forcer le chargement des relations
                    produitOffert.getOffre().getFournisseur(); // Charger le fournisseur
                    produitOffert.getProduit(); // Charger le produit

                    // Mapper ProduitOffert vers ProduitOffertDTO
                    ProduitOffertDTO dto = produitOffertMapper.toDto(produitOffert);

                    // Mapper Offre et Fournisseur vers leurs DTO respectifs
                    dto.setOffre(offreMapper.toDto(produitOffert.getOffre()));
                    dto.getOffre().setFournisseur(fournisseurMapper.toDto(produitOffert.getOffre().getFournisseur()));

                    // Mapper Produit vers son DTO
                    dto.setProduit(produitMapper.toDto(produitOffert.getProduit()));

                    return dto;
                })
                .collect(Collectors.toList()); // Retourner la liste des DTOs
    }

    @Transactional(readOnly = true)
    public Offre getOffreByProduitOffert(Long produitOffertId) {
        return produitOffertRepository.getOffreByProduitOffertId(produitOffertId);
    }

    public List<ProduitOffert> findProduitOffertByProduitCommandeeAndDemandeDevis(Long produitCommandeeId, Long demandeDevisId) {
        // Récupère le ProduitDemandee par ID
        Optional<ProduitCommandee> produitCommandeeOpt = produitCommandeeRepository.findById(produitCommandeeId);

        if (produitCommandeeOpt.isPresent()) {
            Produit produit = produitCommandeeOpt.get().getProduit();

            // Vérifie si produit n'est pas nul
            if (produit != null) {
                // Récupère les ProduitCommandee associés au Produit et qui appartiennent aux DemandeDevis de la DemandeAchat donnée
                return produitOffertRepository.findByProduitAndOffre_DemandeDevisId(produit, demandeDevisId);
            }
        }

        return Collections.emptyList(); // Renvoie une liste vide si aucun ProduitCommandee n'est trouvé
    }


    public List<ProduitCommandee> getProduitsCommandesByOffre(Long offreId) {
        // Charger l'offre
        Offre offre = offreRepository.findById(offreId)
                .orElseThrow(() -> new ResourceNotFoundException("Offre not found"));

        // Récupérer l'id de la demande de devis associée à cette offre
        DemandeDevis demandeDevis = offre.getDemandeDevis();

        if (demandeDevis == null) {
            return Collections.emptyList();
        }

        // Utiliser l'id de DemandeDevis pour trouver tous les ProduitsCommandees associés
        return produitCommandeeRepository.findByDemandeDevis_Id(demandeDevis.getId());
    }


    public Map<Long, List<ProduitOffert>> getProduitsGroupedByBonCommande(Long offreId) {
        List<Long> bonCommandeIds = produitOffertRepository.findDistinctBonCommandeIdsByOffre(offreId);

        // Si aucun bon de commande n'est trouvé, renvoyer une carte vide
        if (bonCommandeIds == null || bonCommandeIds.isEmpty()) {
            return Collections.emptyMap(); // Retourne une map vide
        }

        Map<Long, List<ProduitOffert>> groupedProduits = new HashMap<>();
        for (Long bonCommandeId : bonCommandeIds) {
            // Ignorer les valeurs nulles sans lever une exception
            if (bonCommandeId != null) {
                List<ProduitOffert> produits = produitOffertRepository.getProduitOffertsByBonCommande_Id(bonCommandeId);
                groupedProduits.put(bonCommandeId, produits);
            }
        }

        return groupedProduits;
    }

}
