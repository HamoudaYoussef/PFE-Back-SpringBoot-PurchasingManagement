package biz.picosoft.demo.controller;

import biz.picosoft.demo.client.kernel.intercomm.KernelInterface;
import biz.picosoft.demo.client.kernel.intercomm.KernelService;
import biz.picosoft.demo.client.kernel.model.acl.AclClass;
import biz.picosoft.demo.client.kernel.model.global.CurrentUser;
import biz.picosoft.demo.domain.BonCommande;
import biz.picosoft.demo.domain.Offre;
import biz.picosoft.demo.domain.ennumeration.StatutBonCommande;
import biz.picosoft.demo.domain.ennumeration.StatutDA;
import biz.picosoft.demo.repository.BonCommandeRepository;
import biz.picosoft.demo.service.BonCommandeQueryService;
import biz.picosoft.demo.service.BonCommandeService;
import biz.picosoft.demo.service.criteria.BonCommandeCriteria;
import biz.picosoft.demo.service.dto.*;
import biz.picosoft.demo.controller.errors.BadRequestAlertException;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link biz.picosoft.demo.domain.BonCommande}.
 */
@RestController
@RequestMapping("/api/bon-commandes")
public class BonCommandeResource {

    private final Logger log = LoggerFactory.getLogger(BonCommandeResource.class);

    private static final String ENTITY_NAME = "gestionAchatPfeBonCommande";

//    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BonCommandeService bonCommandeService;

    private final BonCommandeRepository bonCommandeRepository;

    private final BonCommandeQueryService bonCommandeQueryService;

    private final KernelInterface kernelInterface;

    private final CurrentUser currentUser;


    private final KernelService kernelService;

    public BonCommandeResource(
        BonCommandeService bonCommandeService,
        BonCommandeRepository bonCommandeRepository,
        BonCommandeQueryService bonCommandeQueryService,
        KernelInterface kernelInterface,
        CurrentUser currentUser,
        KernelService kernelService
    ) {
        this.bonCommandeService = bonCommandeService;
        this.bonCommandeRepository = bonCommandeRepository;
        this.bonCommandeQueryService = bonCommandeQueryService;
        this.kernelInterface = kernelInterface;
        this.currentUser = currentUser;
        this.kernelService = kernelService;
    }

    /**
     * {@code POST  /bon-commandes} : Create a new bonCommande.
     *
     * @param bonCommandeDTO the bonCommandeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bonCommandeDTO, or with status {@code 400 (Bad Request)} if the bonCommande has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<BonCommandeDTO> createBonCommande(@RequestBody BonCommandeDTO bonCommandeDTO) throws URISyntaxException {
        log.debug("REST request to save BonCommande : {}", bonCommandeDTO);
        if (bonCommandeDTO.getId() != null) {
            throw new BadRequestAlertException("A new bonCommande cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BonCommandeDTO result = bonCommandeService.save(bonCommandeDTO);
        return ResponseEntity
            .created(new URI("/api/bon-commandes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /bon-commandes/:id} : Updates an existing bonCommande.
     *
     * @param id the id of the bonCommandeDTO to save.
     * @param bonCommandeDTO the bonCommandeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bonCommandeDTO,
     * or with status {@code 400 (Bad Request)} if the bonCommandeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bonCommandeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<BonCommandeDTO> updateBonCommande(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BonCommandeDTO bonCommandeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update BonCommande : {}, {}", id, bonCommandeDTO);
        if (bonCommandeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bonCommandeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bonCommandeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BonCommandeDTO result = bonCommandeService.update(bonCommandeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bonCommandeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /bon-commandes/:id} : Partial updates given fields of an existing bonCommande, field will ignore if it is null
     *
     * @param id the id of the bonCommandeDTO to save.
     * @param bonCommandeDTO the bonCommandeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bonCommandeDTO,
     * or with status {@code 400 (Bad Request)} if the bonCommandeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the bonCommandeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the bonCommandeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BonCommandeDTO> partialUpdateBonCommande(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BonCommandeDTO bonCommandeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update BonCommande partially : {}, {}", id, bonCommandeDTO);
        if (bonCommandeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bonCommandeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bonCommandeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BonCommandeDTO> result = bonCommandeService.partialUpdate(bonCommandeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bonCommandeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /bon-commandes} : get all the bonCommandes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bonCommandes in body.
     */
    @GetMapping("")
    public ResponseEntity<List<BonCommandeDTO>> getAllBonCommandes(
        BonCommandeCriteria criteria
    ) {
        log.debug("REST request to get BonCommandes by criteria: {}", criteria);

        List<BonCommandeDTO > list = bonCommandeQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(list);
    }

    /**
     * {@code GET  /bon-commandes/count} : count all the bonCommandes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countBonCommandes(BonCommandeCriteria criteria) {
        log.debug("REST request to count BonCommandes by criteria: {}", criteria);
        return ResponseEntity.ok().body(bonCommandeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /bon-commandes/:id} : get the "id" bonCommande.
     *
     * @param id the id of the bonCommandeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bonCommandeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<BonCommandeDTO> getBonCommande(@PathVariable("id") Long id) {
        log.debug("REST request to get BonCommande : {}", id);
        Optional<BonCommandeDTO> bonCommandeDTO = bonCommandeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bonCommandeDTO);
    }

    /**
     * {@code DELETE  /bon-commandes/:id} : delete the "id" bonCommande.
     *
     * @param id the id of the bonCommandeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBonCommande(@PathVariable("id") Long id) {
        log.debug("REST request to delete BonCommande : {}", id);
        bonCommandeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
    @PatchMapping("/initBonCommande/{idoffre}")
    public BonCommandeOutputDTO initBonCommande(@PathVariable Long idoffre) throws Exception {

        // check if the conneceted person have role can create inbound
        bonCommandeService.checkRole(currentUser.getProfileName(), kernelService.canCreateBonCommande_achat);

        // extract acl class
        AclClass aclClass = kernelInterface.getaclClassByClassName(BonCommande.class.getName());
        return bonCommandeService.initProcessBonCommande(aclClass,idoffre);

    }

    @GetMapping("/bonCommandeDTO/{id}")
    BonCommandeOutputDTO getDemandeDTO(@PathVariable Long id) throws IOException, TemplateException {
        return bonCommandeService.getbcDTObyId(id);
    }
    /**
     * {@code PATCH  /submitInvoice} : submit invoice.
     *
     */
    @PatchMapping(value = {"/submitBonCommande"})
    public BonCommandeOutputDTO submitRequestCase(@RequestBody BonCommandeInputDTO requestCaseInputDTO) throws Exception {

        // check if the conneceted person have role can create inbound
        bonCommandeService.checkRole(currentUser.getProfileName(), kernelService.canCreateBonCommande_achat);

        // extract acl class
        AclClass aclClass = kernelInterface.getaclClassByClassName(BonCommande.class.getName());

        BonCommandeOutputDTO result = bonCommandeService.submitProcessBonCommande(requestCaseInputDTO, aclClass);

        return result;
    }

    @GetMapping("/{id}/offre")
    public ResponseEntity<Offre> getOffreByBonCommandeId(@PathVariable Long id) {
        Offre offre = bonCommandeService.getOffreByBonCommandeId(id);
        if (offre != null) {
            return new ResponseEntity<>(offre, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping(value = "/{id}/referenceoffre", produces = "text/plain")
    public ResponseEntity<String> getReferenceOffreByBonCommandeId(@PathVariable Long id) {
        String referenceOffre = bonCommandeService.getReferenceOffreByBonCommandeId(id);
        return ResponseEntity.ok(referenceOffre);
    }
    @GetMapping("/{id}/prixoffre")
    public ResponseEntity<Float> getPrixOffreByBonCommandeId(@PathVariable Long id) {
        Float prixOffre = bonCommandeService.getPrixOffreByBonCommandeId(id);
        return ResponseEntity.ok(prixOffre);
    }
    @PutMapping("/{id}/changer-statut")
    public ResponseEntity<String> changerStatutBC(@PathVariable long id, StatutBonCommande statutBC) {
        bonCommandeService.updateStatutBC(id, statutBC.VALIDE);
        return ResponseEntity.ok("Statut mis à jour avec succès !");
    }
}
