package biz.picosoft.demo.service;

import biz.picosoft.demo.Workflow.service.WorkflowService;
import biz.picosoft.demo.client.kernel.intercomm.KernelInterface;
import biz.picosoft.demo.client.kernel.intercomm.KernelService;
import biz.picosoft.demo.client.kernel.model.global.CurrentUser;
import biz.picosoft.demo.client.kernel.model.pm.Role;
import biz.picosoft.demo.controller.errors.BadRequestAlertException;
import biz.picosoft.demo.controller.errors.RCErrors;
import biz.picosoft.demo.domain.BonCommande;
import biz.picosoft.demo.repository.BonCommandeRepository;
import biz.picosoft.demo.repository.ProduitOffertRepository;
import biz.picosoft.demo.service.dto.*;
import biz.picosoft.demo.service.mapper.*;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;


@Service
@Transactional
public class BonCommandeService {

    private final Logger log = LoggerFactory.getLogger(BonCommandeService.class);

    private final BonCommandeRepository bonCommandeRepository;
    private final ProduitOffertRepository produitOffertRepository;


    private final BonCommandeMapper bonCommandeMapper;

    private final BonCommandeOutputMapper bonCommandeOutputMapper;
    private final BonCommandeInputMapper bonCommandeInputMapper;

    private final CurrentUser currentUser;

    private final WorkflowService workflowService;

    private final KernelService kernelService;
    private final KernelInterface kernelInterface;
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int CODE_LENGTH = 30;
    private final SecureRandom random = new SecureRandom();

    public BonCommandeService(BonCommandeRepository bonCommandeRepository, BonCommandeMapper bonCommandeMapper,
                              WorkflowService workflowService,KernelInterface kernelInterface,
                              KernelService kernelService,BonCommandeOutputMapper bonCommandeOutputMapper,
                              BonCommandeInputMapper bonCommandeInputMapper,CurrentUser currentUser,ProduitOffertRepository produitOffertRepository) {
        this.bonCommandeRepository = bonCommandeRepository;
        this.bonCommandeMapper = bonCommandeMapper;
        this.workflowService = workflowService;
        this.kernelService = kernelService;
        this.kernelInterface = kernelInterface;
        this.currentUser = currentUser;
        this.bonCommandeInputMapper = bonCommandeInputMapper;
        this.bonCommandeOutputMapper = bonCommandeOutputMapper;
        this.produitOffertRepository = produitOffertRepository;

    }

    /**
     * Save a bonCommande.
     *
     * @param bonCommandeDTO the entity to save.
     * @return the persisted entity.
     */
    public BonCommandeDTO save(BonCommandeDTO bonCommandeDTO) {
        log.debug("Request to save BonCommande : {}", bonCommandeDTO);
        BonCommande bonCommande = bonCommandeMapper.toEntity(bonCommandeDTO);
        bonCommande = bonCommandeRepository.save(bonCommande);
        return bonCommandeMapper.toDto(bonCommande);
    }

    /**
     * Update a bonCommande.
     *
     * @param bonCommandeDTO the entity to save.
     * @return the persisted entity.
     */
    public BonCommandeDTO update(BonCommandeDTO bonCommandeDTO) {
        log.debug("Request to update BonCommande : {}", bonCommandeDTO);
        BonCommande bonCommande = bonCommandeMapper.toEntity(bonCommandeDTO);
        bonCommande = bonCommandeRepository.save(bonCommande);
        return bonCommandeMapper.toDto(bonCommande);
    }


    /**
     * Partially update a bonCommande.
     *
     * @param bonCommandeDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<BonCommandeDTO> partialUpdate(BonCommandeDTO bonCommandeDTO) {
        log.debug("Request to partially update BonCommande : {}", bonCommandeDTO);

        return bonCommandeRepository
            .findById(bonCommandeDTO.getId())
            .map(existingBonCommande -> {
                bonCommandeMapper.partialUpdate(existingBonCommande, bonCommandeDTO);

                return existingBonCommande;
            })
            .map(bonCommandeRepository::save)
            .map(bonCommandeMapper::toDto);
    }

    /**
     * Get all the bonCommandes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<BonCommandeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all BonCommandes");
        return bonCommandeRepository.findAll(pageable).map(bonCommandeMapper::toDto);
    }

    /**
     * Get one bonCommande by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BonCommandeDTO> findOne(Long id) {
        log.debug("Request to get BonCommande : {}", id);
        return bonCommandeRepository.findById(id).map(bonCommandeMapper::toDto);
    }

    /**
     * Delete the bonCommande by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete BonCommande : {}", id);
        bonCommandeRepository.deleteById(id);
    }

    public Boolean checkRole(String profile, String roleName) {
        if (profile == null)
            throw new BadRequestAlertException(
                    RCErrors.ERR_Msg_profile_not_found,
                    RCErrors.Entity_requestCase,
                    RCErrors.ERR_Key_profile_not_found);
        List<Role> roles = kernelService.findAllByProfiles(profile);
        List<String> rolesName = new ArrayList<>();
        for (Role role : roles)
            rolesName.add(role.getName());
        if (!rolesName.contains(roleName))
            throw new BadRequestAlertException(
                    RCErrors.ERR_Msg_not_authorized,
                    RCErrors.Entity_requestCase,
                    RCErrors.ERR_Key_not_authorized);
        return true;
    }

    @PersistenceContext(unitName = "externDSEmFactory")
    private EntityManager entityManager;
    // submit methods

   /* public Offre getOffreByBonCommandeId(Long bonCommandeId) {
        return bonCommandeRepository.findOffreByBonCommandeId(bonCommandeId);
    }*/
  /*  public String getReferenceOffreByBonCommandeId(Long bonCommandeId) {
        BonCommande bonCommande = bonCommandeRepository.findById(bonCommandeId)
                .orElseThrow(() -> new RuntimeException("Bon de commande non trouvé avec l'ID : " + bonCommandeId));

        return bonCommande.getProduitOffert().getOffre().getReferenceoffre();
    }
   /* public Float getPrixOffreByBonCommandeId(Long bonCommandeId) {
        BonCommande bonCommande = bonCommandeRepository.findById(bonCommandeId)
                .orElseThrow(() -> new RuntimeException("Bon de commande non trouvé avec l'ID : " + bonCommandeId));

        return bonCommande.getOffre().getPrix();
    }*/


}
