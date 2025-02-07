package biz.picosoft.demo.service;

import biz.picosoft.demo.controller.errors.BadRequestAlertException;
import biz.picosoft.demo.domain.DemandeAchat;
import biz.picosoft.demo.domain.DemandeDevis;
import biz.picosoft.demo.domain.Fournisseur;
import biz.picosoft.demo.repository.DemandeAchatRepository;
import biz.picosoft.demo.repository.DemandeDevisRepository;
import biz.picosoft.demo.repository.FournisseurRepository;
import biz.picosoft.demo.repository.ProduitDemandeeRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import biz.picosoft.demo.service.dto.DemandeDevisDTO;
import biz.picosoft.demo.service.mapper.DemandeDevisMapper;
import biz.picosoft.demo.service.mapper.ProduitDemandeeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class DemandeDevisService {

    private final Logger log = LoggerFactory.getLogger(DemandeDevisService.class);

    private final DemandeDevisRepository demandeDevisRepository;
    private final DemandeAchatRepository demandeAchatRepository;
    private final FournisseurRepository fournisseurRepository;
    private final ProduitDemandeeRepository produitDemandeeRepository;

    private final ProduitDemandeeMapper produitDemandeeMapper;


    private final DemandeDevisMapper demandeDevisMapper;

    public DemandeDevisService(DemandeAchatRepository demandeAchatRepository,FournisseurRepository fournisseurRepository,
                               ProduitDemandeeRepository produitDemandeeRepository,ProduitDemandeeMapper produitDemandeeMapper,
                               DemandeDevisRepository demandeDevisRepository, DemandeDevisMapper demandeDevisMapper) {
        this.demandeDevisRepository = demandeDevisRepository;
        this.demandeDevisMapper = demandeDevisMapper;
        this.demandeAchatRepository = demandeAchatRepository;
        this.fournisseurRepository = fournisseurRepository;
        this.produitDemandeeRepository = produitDemandeeRepository;
        this.produitDemandeeMapper = produitDemandeeMapper;
    }

    /**
     * Save a demandeDevis.
     *
     * @param demandeDevisDTO the entity to save.
     * @return the persisted entity.
     */
    public DemandeDevisDTO save(DemandeDevisDTO demandeDevisDTO) {
        // Mapper le DTO vers une entité
        DemandeDevis demandeDevis = demandeDevisMapper.toEntity(demandeDevisDTO);

        // Associer le fournisseur s'il existe
        if (demandeDevisDTO.getFournisseur().getId() != null) {
            Fournisseur fournisseur = fournisseurRepository.findById(demandeDevisDTO.getFournisseur().getId())
                    .orElseThrow(() -> new BadRequestAlertException("Invalid Fournisseur ID", "demandeDevis", "idnotfound"));
            demandeDevis.setFournisseur(fournisseur);
        }

        // Associer la demande d'achat s'il existe
        if (demandeDevisDTO.getDemandeAchat().getId()!= null) {
            DemandeAchat demandeAchat = demandeAchatRepository.findById(demandeDevisDTO.getDemandeAchat().getId())
                    .orElseThrow(() -> new BadRequestAlertException("Invalid DemandeAchat ID", "demandeDevis", "idnotfound"));
            demandeDevis.setDemandeAchat(demandeAchat);
        } else {
            throw new BadRequestAlertException("DemandeAchat ID is required", "demandeDevis", "idnotfound");
        }

        // Sauvegarder la demande d'achat si elle n'est pas déjà persistée
        if (demandeDevis.getDemandeAchat().getId() == null) {
            demandeDevis.setDemandeAchat(demandeAchatRepository.save(demandeDevis.getDemandeAchat()));
        }

        demandeDevis.setDatedemande(LocalDate.now());

        // Sauvegarder la demande de devis
        demandeDevis = demandeDevisRepository.save(demandeDevis);

        // Récupérer les produits demandés via le service
       /* List<ProduitDemandee> produitsDemandes = produitDemandeeRepository.findByDemandeAchatId(demandeDevisDTO.getDemandeAchatId());

        // Associer les produits demandés à la demande de devis et les sauvegarder
        for (ProduitDemandee produitDemandee : produitsDemandes) {
            produitDemandee.setDemandeDevis(demandeDevis);  // Associer à la demande de devis
            produitDemandeeRepository.save(produitDemandee);  // Sauvegarder les modifications
        }*/

        return demandeDevisMapper.toDto(demandeDevis);
    }

    public String genererCodeDemandeDevis() {
        // Récupérer la liste des DemandeDevis
        List<DemandeDevis> demandeDevisList = demandeDevisRepository.findLatestDemandeDevis();

        // Prendre le premier élément si disponible
        Optional<DemandeDevis> dernierDemandeDevisOpt = demandeDevisList.stream().findFirst();

        String nouveauCode;

        if (dernierDemandeDevisOpt.isPresent()) {
            // Récupérer la référence du dernier DemandeDevis
            String dernierCode = dernierDemandeDevisOpt.get().getReference(); // Assurez-vous que getReference() est bien défini
            int dernierNumero = Integer.parseInt(dernierCode.split("_")[1]); // Extraire le numéro après le "_"

            // Générer le nouveau code en incrémentant le numéro
            nouveauCode = "dd_" + String.format("%04d", dernierNumero + 1); // Incrémente et formate en 4 chiffres
        } else {
            // Si aucune demande de devis n'existe, initialiser le code
            nouveauCode = "dd_0001"; // Code initial si aucun code n'existe
        }

        return nouveauCode;
    }

    public Optional<DemandeDevis> getLatestDemandeDevis() {
        List<DemandeDevis> demandeDevisList = demandeDevisRepository.findLatestDemandeDevis();
        return demandeDevisList.stream().findFirst();

    }
    public DemandeDevisDTO saveWithoutDemandeAchatId(DemandeDevisDTO demandeDevisDTO) {
        // Mapper le DTO vers une entité
        DemandeDevis demandeDevis = demandeDevisMapper.toEntity(demandeDevisDTO);

        // Associer le fournisseur s'il existe
        if (demandeDevisDTO.getFournisseur().getId() != null) {
            Fournisseur fournisseur = fournisseurRepository.findById(demandeDevisDTO.getFournisseur().getId())
                    .orElseThrow(() -> new BadRequestAlertException("Invalid Fournisseur ID", "demandeDevis", "idnotfound"));
            demandeDevis.setFournisseur(fournisseur);
        }

        // On ne lie pas la DemandeAchat à la DemandeDevis dans cette méthode
        demandeDevis.setDemandeAchat(null);
        demandeDevis.setDatedemande(LocalDate.now());

        // Sauvegarder la demande de devis sans les produits pour le moment
        demandeDevis = demandeDevisRepository.save(demandeDevis);

        // Récupérer les produits demandés depuis le DTO
   /*     Set<ProduitDemandeeDTO> produitsDemandesDTO = demandeDevisDTO.getProduitDemandees();

        if (produitsDemandesDTO != null && !produitsDemandesDTO.isEmpty()) {
            // Mapper chaque ProduitDemandeeDTO vers ProduitDemandee, les associer et les sauvegarder
            for (ProduitDemandeeDTO produitDemandeeDTO : produitsDemandesDTO) {
                ProduitDemandee produitDemandee = produitDemandeeMapper.toEntity(produitDemandeeDTO);
                produitDemandee.setDemandeDevis(demandeDevis);  // Associer chaque produit à la demande de devis
                produitDemandeeRepository.save(produitDemandee);  // Sauvegarder le produit
            }
        }*/

        // Retourner le DTO mis à jour après sauvegarde
        return demandeDevisMapper.toDto(demandeDevis);
    }

    public List<DemandeDevisDTO> saveAll(List<DemandeDevisDTO> demandeDevisDTOList) {
        List<DemandeDevis> savedDemandeDevisList = new ArrayList<>();

        for (DemandeDevisDTO demandeDevisDTO : demandeDevisDTOList) {
            DemandeDevis demandeDevis = demandeDevisMapper.toEntity(demandeDevisDTO);

            if (demandeDevisDTO.getFournisseur().getId() != null) {
                Fournisseur fournisseur = fournisseurRepository.findById(demandeDevisDTO.getFournisseur().getId())
                        .orElseThrow(() -> new BadRequestAlertException("Invalid Fournisseur ID", "demandeDevis", "idnotfound"));
                demandeDevis.setFournisseur(fournisseur);
            }

            if (demandeDevisDTO.getDemandeAchat().getId()!= null) {
                DemandeAchat demandeAchat = demandeAchatRepository.findById(demandeDevisDTO.getDemandeAchat().getId())
                        .orElseThrow(() -> new BadRequestAlertException("Invalid DemandeAchat ID", "demandeDevis", "idnotfound"));
                demandeDevis.setDemandeAchat(demandeAchat);
            }

            // Save the DemandeAchat if it's new
            if (demandeDevis.getDemandeAchat().getId() == null) {
                demandeAchatRepository.save(demandeDevis.getDemandeAchat());
            }

            DemandeDevis savedDemandeDevis = demandeDevisRepository.save(demandeDevis);
            savedDemandeDevisList.add(savedDemandeDevis);
        }

        return savedDemandeDevisList.stream()
                .map(demandeDevisMapper::toDto)
                .collect(Collectors.toList());
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
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DemandeDevisDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DemandeAchats");
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

    public List<DemandeDevis> getDemandesDevisByDemandeAchatId(Long demandeAchatId) {
        return demandeDevisRepository.findByDemandeAchat_Id(demandeAchatId);
    }
    @Transactional(readOnly = true)
    public Fournisseur getFournisseurByDemandeDevisId(Long demandeDevisId) {
        return demandeDevisRepository.findFournisseurByDemandeDevisId(demandeDevisId);
    }

    public List<DemandeDevis> getDemandeDevisByFournisseur(Fournisseur fournisseur) {
        return demandeDevisRepository.findByFournisseur(fournisseur);
    }
}
