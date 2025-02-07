package biz.picosoft.demo.controller;


import biz.picosoft.demo.controller.errors.BadRequestAlertException;
import biz.picosoft.demo.domain.*;
import biz.picosoft.demo.errors.ResourceNotFoundException;
import biz.picosoft.demo.repository.DemandeDevisRepository;
import biz.picosoft.demo.repository.ProduitDemandeeRepository;
import biz.picosoft.demo.service.ProduitDemandeeQueryService;
import biz.picosoft.demo.service.ProduitDemandeeService;
import biz.picosoft.demo.service.criteria.ProduitDemandeeCriteria;
import biz.picosoft.demo.service.dto.DemandeAchatDTO;
import biz.picosoft.demo.service.dto.ProduitDemandeeDTO;
import biz.picosoft.demo.service.dto.ProduitOffertDTO;
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

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

/**
 */
@RestController
@RequestMapping("/api/produit-demandees")
public class ProduitDemandeeResource {

    private final Logger log = LoggerFactory.getLogger(ProduitDemandeeResource.class);

    private static final String ENTITY_NAME = "gestionAchatPfeOffre";

  //  @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProduitDemandeeService produitDemandeeService;

    private final ProduitDemandeeRepository produitDemandeeRepository;

    private final ProduitDemandeeQueryService produitDemandeeQueryService;

    private final DemandeDevisRepository demandeDevisRepository;

    public ProduitDemandeeResource(
        ProduitDemandeeService produitDemandeeService,
        ProduitDemandeeRepository produitDemandeeRepository,
        ProduitDemandeeQueryService produitDemandeeQueryService,
        DemandeDevisRepository demandeDevisRepository
    ) {
        this.produitDemandeeService = produitDemandeeService;
        this.produitDemandeeRepository = produitDemandeeRepository;
        this.produitDemandeeQueryService = produitDemandeeQueryService;
        this.demandeDevisRepository = demandeDevisRepository;
    }

    /**
     * {@code POST  /produit-demandees} : Create a new produitDemandee.
     *
     * @param produitDemandeeDTO the produitDemandeeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new produitDemandeeDTO, or with status {@code 400 (Bad Request)} if the produitDemandee has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */


    @PostMapping("")
    public ResponseEntity<ProduitDemandeeDTO> createProduitDemandee(@RequestBody ProduitDemandeeDTO produitDemandeeDTO) throws URISyntaxException {
        log.debug("REST request to save ProduitDemandee : {}", produitDemandeeDTO);
        if (produitDemandeeDTO.getId() != null) {
            throw new BadRequestAlertException("A new produitDemandee cannot already have an ID", ENTITY_NAME, "idexists");
        }

        ProduitDemandeeDTO result = produitDemandeeService.save(produitDemandeeDTO);
        return ResponseEntity
                .created(new URI("/api/produit-demandees/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }


    /**
     * {@code PUT  /produit-demandees/:id} : Updates an existing produitDemandee.
     *
     * @param id the id of the produitDemandeeDTO to save.
     * @param produitDemandeeDTO the produitDemandeeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated produitDemandeeDTO,
     * or with status {@code 400 (Bad Request)} if the produitDemandeeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the produitDemandeeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProduitDemandeeDTO> updateProduitDemandee(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ProduitDemandeeDTO produitDemandeeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ProduitDemandee : {}, {}", id, produitDemandeeDTO);
        if (produitDemandeeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, produitDemandeeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!produitDemandeeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProduitDemandeeDTO result = produitDemandeeService.update(produitDemandeeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, produitDemandeeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /produit-demandees/:id} : Partial updates given fields of an existing produitDemandee, field will ignore if it is null
     *
     * @param id the id of the produitDemandeeDTO to save.
     * @param produitDemandeeDTO the produitDemandeeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated produitDemandeeDTO,
     * or with status {@code 400 (Bad Request)} if the produitDemandeeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the produitDemandeeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the produitDemandeeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ProduitDemandeeDTO> partialUpdateProduitDemandee(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ProduitDemandeeDTO produitDemandeeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ProduitDemandee partially : {}, {}", id, produitDemandeeDTO);
        if (produitDemandeeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, produitDemandeeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!produitDemandeeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProduitDemandeeDTO> result = produitDemandeeService.partialUpdate(produitDemandeeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, produitDemandeeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /produit-demandees} : get all the produitDemandees.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of produitDemandees in body.
     */
    @GetMapping("")
    public ResponseEntity<Page<ProduitDemandeeDTO>> getAllProduitDemandees(
            ProduitDemandeeCriteria criteria,
            @org.springdoc.api.annotations.ParameterObject Pageable pageable

    ) {
        log.debug("REST request to get all DemandeAchats by criteria: {}", criteria);
        Page<ProduitDemandeeDTO> page = produitDemandeeQueryService.findByCriteria(criteria, pageable);

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page);

    }

    /**
     * {@code GET  /produit-demandees/count} : count all the produitDemandees.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countProduitDemandees(ProduitDemandeeCriteria criteria) {
        log.debug("REST request to count ProduitDemandees by criteria: {}", criteria);
        return ResponseEntity.ok().body(produitDemandeeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /produit-demandees/:id} : get the "id" produitDemandee.
     *
     * @param id the id of the produitDemandeeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the produitDemandeeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProduitDemandeeDTO> getProduitDemandee(@PathVariable("id") Long id) {
        log.debug("REST request to get ProduitDemandee : {}", id);
        Optional<ProduitDemandeeDTO> produitDemandeeDTO = produitDemandeeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(produitDemandeeDTO);
    }

    /**
     * {@code DELETE  /produit-demandees/:id} : delete the "id" produitDemandee.
     *
     * @param id the id of the produitDemandeeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduitDemandee(@PathVariable("id") Long id) {
        log.debug("REST request to delete ProduitDemandee : {}", id);
        produitDemandeeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    @GetMapping("/{id}/demande-achat")
    public ResponseEntity<DemandeAchat> getDemandeAchatByProduitDemandee(@PathVariable Long id) {
        DemandeAchat demandeAchat = produitDemandeeService.getDemandeAchatByProduitDemandee(id);
        return ResponseEntity.ok(demandeAchat);
    }
    @GetMapping("/{id}/produits")
    public ResponseEntity<List<ProduitDemandee>> getProduitDemandeByDemandeAchatId(@PathVariable Long id) {
        List<ProduitDemandee> produitsDemandes = produitDemandeeService.getProduitDemandeByDemandeAchatId(id);
        return ResponseEntity.ok(produitsDemandes);
    }
  
  /*  @GetMapping("/byDemandeDevis/{id}")
    public List<ProduitDemandee> getProduitDemandeByDemandeDevis(@PathVariable Long id) {
        return produitDemandeeService.getProduitDemandeByDemandeDevisId(id);
    }*/
    @GetMapping("/produits/grouped")
    public ResponseEntity<Map<DemandeAchat, List<ProduitDemandee>>> getProduitsGroupedByDemandeAchat() {
        Map<DemandeAchat, List<ProduitDemandee>> groupedProduits = produitDemandeeService.getProduitsGroupedByDemandeAchat();
        return ResponseEntity.ok(groupedProduits);
    }

    @GetMapping("/recent")
    public List<ProduitDemandee> getRecentProduitDemandee() {
        return produitDemandeeService.getRecentProduitDemandee();
    }

    @GetMapping("/produit-commandee/by-produit-demandee/{produitDemandeeId}/demande-achat/{demandeAchatId}")
    public ResponseEntity<List<ProduitCommandee>> getProduitCommandeesByProduitDemandeeAndDemandeAchat(
            @PathVariable Long produitDemandeeId,
            @PathVariable Long demandeAchatId) {
        List<ProduitCommandee> produitCommandees = produitDemandeeService.findProduitCommandeesByProduitDemandeeAndDemandeAchat(produitDemandeeId, demandeAchatId);
        return ResponseEntity.ok(produitCommandees);
    }

    @GetMapping("/{demandeDevisId}/{produitId}")
    public List<ProduitDemandee> findProduitByDemandeAchat_IdAndProduit_Id(
            @PathVariable Long demandeDevisId, @PathVariable Long produitId) {

        // Charger l'offre
        DemandeDevis demandeDevis = demandeDevisRepository.findById(demandeDevisId)
                .orElseThrow(() -> new ResourceNotFoundException("Demande devis not found"));

        // Récupérer la demande de devis associée à cette offre
        DemandeAchat demandeAchat = demandeDevis.getDemandeAchat();

        // Si aucune demande de devis n'est associée
        if (demandeAchat == null) {
            return Collections.emptyList();
        }

        // Rechercher les produits commandés par demande de devis et produit spécifiques
        return produitDemandeeRepository.findByDemandeAchat_IdAndProduit_Id(demandeAchat.getId(), produitId);
    }
 /*   @GetMapping("/by-produit/{produitId}")
    public List<ProduitDemandee> getProduitDemandeeByProduit(@PathVariable Long produitId) {
        return produitDemandeeService.getProduitDemandeeByProduit(produitId);
    }*/
}
