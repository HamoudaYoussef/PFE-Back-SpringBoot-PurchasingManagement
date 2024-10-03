package biz.picosoft.demo.controller;

import biz.picosoft.demo.client.kernel.intercomm.KernelInterface;
import biz.picosoft.demo.client.kernel.intercomm.KernelService;
import biz.picosoft.demo.client.kernel.model.acl.AclClass;
import biz.picosoft.demo.client.kernel.model.global.CurrentUser;
import biz.picosoft.demo.domain.DemandeAchat;
import biz.picosoft.demo.domain.ennumeration.StatutDA;
import biz.picosoft.demo.repository.DemandeAchatRepository;
import biz.picosoft.demo.service.DemandeAchatQueryService;
import biz.picosoft.demo.service.DemandeAchatService;
import biz.picosoft.demo.service.criteria.DemandeAchatCriteria;
import biz.picosoft.demo.service.dto.DemandeAchatDTO;
import biz.picosoft.demo.controller.errors.BadRequestAlertException;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import biz.picosoft.demo.service.dto.DemandeAchatInputDTO;
import biz.picosoft.demo.service.dto.DemandeAchatOutputDTO;
import biz.picosoft.demo.service.dto.PaiementDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link biz.picosoft.demo.domain.DemandeAchat}.
 */
@RestController
@RequestMapping("/api/demande-achats")
public class DemandeAchatResource {

    private final Logger log = LoggerFactory.getLogger(DemandeAchatResource.class);

    private static final String ENTITY_NAME = "gestionAchatPfeDemandeAchat";

 //   @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DemandeAchatService demandeAchatService;

    private final DemandeAchatRepository demandeAchatRepository;

    private final DemandeAchatQueryService demandeAchatQueryService;

    private final KernelInterface kernelInterface;

    private final CurrentUser currentUser;


    private final KernelService kernelService;
    public DemandeAchatResource(
        DemandeAchatService demandeAchatService,
        DemandeAchatRepository demandeAchatRepository,
        DemandeAchatQueryService demandeAchatQueryService,
        KernelService kernelService,
        KernelInterface kernelInterface,
        CurrentUser currentUser)
    {
        this.demandeAchatService = demandeAchatService;
        this.demandeAchatRepository = demandeAchatRepository;
        this.demandeAchatQueryService = demandeAchatQueryService;
        this.kernelService = kernelService;
        this.kernelInterface = kernelInterface;
        this.currentUser = currentUser;

    }

    /**
     * {@code POST  /demande-achats} : Create a new demandeAchat.
     *
     * @param demandeAchatDTO the demandeAchatDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new demandeAchatDTO, or with status {@code 400 (Bad Request)} if the demandeAchat has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/demandeachat")
    public ResponseEntity<DemandeAchatDTO> createDemandeAchat(@RequestBody DemandeAchatDTO demandeAchatDTO) throws URISyntaxException {
        log.debug("REST request to save DemandeAchat : {}", demandeAchatDTO);
        if (demandeAchatDTO.getId() != null) {
            throw new BadRequestAlertException("A new demandeAchat cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DemandeAchatDTO result = demandeAchatService.save(demandeAchatDTO);
        return ResponseEntity
            .created(new URI("/api/demande-achats/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /demande-achats/:id} : Updates an existing demandeAchat.
     *
     * @param id the id of the demandeAchatDTO to save.
     * @param demandeAchatDTO the demandeAchatDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated demandeAchatDTO,
     * or with status {@code 400 (Bad Request)} if the demandeAchatDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the demandeAchatDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<DemandeAchatDTO> updateDemandeAchat(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DemandeAchatDTO demandeAchatDTO
    ) throws URISyntaxException {
        log.debug("REST request to update DemandeAchat : {}, {}", id, demandeAchatDTO);



        if (!demandeAchatRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        demandeAchatDTO.setId(id);
        DemandeAchatDTO result = demandeAchatService.update(demandeAchatDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, demandeAchatDTO.getId().toString()))
            .body(result);
    }

   @PutMapping("/{id}/changer-statut")
    public ResponseEntity<String> changerStatut(@PathVariable long id,StatutDA statutDA) {
        demandeAchatService.updateStatut(id, statutDA.termine);
        return ResponseEntity.ok("Statut mis à jour avec succès !");
    }
    @PutMapping("/{id}/changer-statut-annuler")
    public ResponseEntity<String> changerStatutAnnuler(@PathVariable long id,StatutDA statutDA) {
        demandeAchatService.updateStatutAnnuler(id, statutDA.Rejetee);
        return ResponseEntity.ok("Statut mis à jour avec succès !");
    }
    @PutMapping("/{id}/statut-en-attente")
    public ResponseEntity<String> StatutEnAttent(@PathVariable long id,StatutDA statutDA) {
        demandeAchatService.statutEnAttente(id, statutDA.en_attente);
        return ResponseEntity.ok("Statut mis à jour avec succès !");
    }


    /**
     * {@code PATCH  /demande-achats//:id} : Partial updates given fields of an existing demandeAchat, field will ignore if it is null
     *
     * @param id the id of the demandeAchatDTO to save.
     * @param demandeAchatDTO the demandeAchatDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated demandeAchatDTO,
     * or with status {@code 400 (Bad Request)} if the demandeAchatDTO is not valid,
     * or with status {@code 404 (Not Found)} if the demandeAchatDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the demandeAchatDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DemandeAchatDTO> partialUpdateDemandeAchat(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DemandeAchatDTO demandeAchatDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update DemandeAchat partially : {}, {}", id, demandeAchatDTO);
        if (demandeAchatDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, demandeAchatDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!demandeAchatRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DemandeAchatDTO> result = demandeAchatService.partialUpdate(demandeAchatDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, demandeAchatDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /demande-achats} : get all the demandeAchats.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of demandeAchats in body.
     */
    @GetMapping("/getDA")
    public ResponseEntity<Page<DemandeAchatDTO>> getAllDemandeAchats(
            DemandeAchatCriteria criteria,
            @org.springdoc.api.annotations.ParameterObject Pageable pageable

    ) {
        log.debug("REST request to get all DemandeAchats by criteria: {}", criteria);
        Page<DemandeAchatDTO> page = demandeAchatQueryService.findByCriteria(criteria, pageable);

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page);

    }
    /**
     * {@code GET  /demande-achats/count} : count all the demandeAchats.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countDemandeAchats(DemandeAchatCriteria criteria) {
        log.debug("REST request to count DemandeAchats by criteria: {}", criteria);
        return ResponseEntity.ok().body(demandeAchatQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /demande-achats/:id} : get the "id" demandeAchat.
     *
     * @param id the id of the demandeAchatDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the demandeAchatDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<DemandeAchatDTO> getDemandeAchat(@PathVariable("id") Long id) {
        log.debug("REST request to get DemandeAchat : {}", id);
        Optional<DemandeAchatDTO> demandeAchatDTO = demandeAchatService.findOne(id);
        return ResponseUtil.wrapOrNotFound(demandeAchatDTO);
    }

    @GetMapping("/demandes/termine")
    public ResponseEntity<Page<DemandeAchatDTO>> getAllDemandeAchatsTermine(
            DemandeAchatCriteria criteria,
            @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get all DemandeAchats by criteria: {}", criteria);

        // Créez un filtre et définissez le statut "TERMINE"
        DemandeAchatCriteria.StatutDAFilter statutFilter = new DemandeAchatCriteria.StatutDAFilter();
        statutFilter.setEquals(StatutDA.termine); // Ou VALIDE selon votre énumération

        // Utilisez le setter pour appliquer le filtre au critère
        criteria.setStatutDA(statutFilter);

        Page<DemandeAchatDTO> page = demandeAchatQueryService.findByCriteria(criteria, pageable);

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page);
    }
    @GetMapping("/demandes/rejetee")
    public ResponseEntity<Page<DemandeAchatDTO>> getDemandesRejetee(
            DemandeAchatCriteria criteria,
            @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get all DemandeAchats by criteria: {}", criteria);

        // Créez un filtre et définissez le statut "TERMINE"
        DemandeAchatCriteria.StatutDAFilter statutFilter = new DemandeAchatCriteria.StatutDAFilter();
        statutFilter.setEquals(StatutDA.Rejetee); // Ou VALIDE selon votre énumération

        // Utilisez le setter pour appliquer le filtre au critère
        criteria.setStatutDA(statutFilter);

        Page<DemandeAchatDTO> page = demandeAchatQueryService.findByCriteria(criteria, pageable);

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page);
    }
    /**
     * {@code DELETE  /demande-achats/:id} : delete the "id" demandeAchat.
     *
     * @param id the id of the demandeAchatDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteDemandeAchat(@PathVariable("id") Long id) {
        log.debug("REST request to delete DemandeAchat : {}", id);
        demandeAchatService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code PATCH  /initInvoice} : Create a new invoice.
     *
     * @return the {@link ResponseEntity< DemandeAchatOutputDTO >} with status {@code 201 (Created)} and with body the new invoice recement initiated, or with status {@code 400 (Bad Request)} if the dossier has already an ID.
     * @throws JsonProcessingException if the Location URI syntax is incorrect.
     */
    @PatchMapping("/initDA")
    public DemandeAchatOutputDTO initDemande() throws Exception {

        // check if the conneceted person have role can create inbound
        demandeAchatService.checkRole(currentUser.getProfileName(), kernelService.demande_achat_canCreateDA );

        // extract acl class
        AclClass aclClass = kernelInterface.getaclClassByClassName(DemandeAchat.class.getName());

        return demandeAchatService.initProcessDemandeAchat(aclClass);

    }

    @GetMapping("/demandeAchatDTO/{id}")
    DemandeAchatOutputDTO getDemandeDTO(@PathVariable Long id) throws IOException, TemplateException {
        return demandeAchatService.getDADTObyId(id);
    }
//    @GetMapping("/demandeAchatValidDTO/{id}")
//    DemandeAchatOutputDTO getDemandeAchatValidDTO(@PathVariable Long id) throws IOException, TemplateException {
//        return demandeAchatService.getDADTObyId(id);
//    }

    /**
     * {@code PATCH  /submitInvoice} : submit invoice.
     *
     * @return the {@link ResponseEntity<DemandeAchatOutputDTO>}  with status {@code 201 (Created)} and with body the identified invoice recement initiated, or with status {@code 400 (Bad Request)} if the invoice no already has an ID.
     */
    @PatchMapping(value = {"/submitDA"})
    public DemandeAchatOutputDTO submitRequestCase(@RequestBody DemandeAchatInputDTO requestCaseInputDTO) throws Exception {

        // check if the conneceted person have role can create inbound
        demandeAchatService.checkRole(currentUser.getProfileName(), kernelService.demande_achat_canCreateDA);

        // extract acl class
        AclClass aclClass = kernelInterface.getaclClassByClassName(DemandeAchat.class.getName());

        DemandeAchatOutputDTO result = demandeAchatService.submitProcessDemandeAchat(requestCaseInputDTO, aclClass);

        return result;
    }

    @PatchMapping(value = {"/submitValidDA"})
    public DemandeAchatOutputDTO submitValidRequestCase(@RequestBody DemandeAchatInputDTO requestCaseInputDTO, StatutDA statutDA) throws Exception {


        // check if the conneceted person have role can create inbound
        demandeAchatService.checkRole(currentUser.getProfileName(), kernelService.demande_achat_canCreateDA);

        // extract acl class
        AclClass aclClass = kernelInterface.getaclClassByClassName(DemandeAchat.class.getName());
        requestCaseInputDTO.setStatut(statutDA.termine);
        DemandeAchatOutputDTO result = demandeAchatService.submitProcessDemandeAchat(requestCaseInputDTO, aclClass);

        return result;
    }
}
