package biz.picosoft.demo.controller;

import biz.picosoft.demo.repository.ProduitCommandeeRepository;
import biz.picosoft.demo.service.ProduitCommandeeQueryService;
import biz.picosoft.demo.service.ProduitCommandeeService;
import biz.picosoft.demo.service.criteria.ProduitCommandeeCriteria;
import biz.picosoft.demo.service.dto.ProduitCommandeeDTO;
import biz.picosoft.demo.controller.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
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
 * REST controller for managing {@link biz.picosoft.demo.domain.ProduitCommandee}.
 */
@RestController
@RequestMapping("/api/produit-commandees")
public class ProduitCommandeeResource {

    private final Logger log = LoggerFactory.getLogger(ProduitCommandeeResource.class);

    private static final String ENTITY_NAME = "gestionAchatPfeProduitCommandee";

   // @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProduitCommandeeService produitCommandeeService;

    private final ProduitCommandeeRepository produitCommandeeRepository;

    private final ProduitCommandeeQueryService produitCommandeeQueryService;

    public ProduitCommandeeResource(
        ProduitCommandeeService produitCommandeeService,
        ProduitCommandeeRepository produitCommandeeRepository,
        ProduitCommandeeQueryService produitCommandeeQueryService
    ) {
        this.produitCommandeeService = produitCommandeeService;
        this.produitCommandeeRepository = produitCommandeeRepository;
        this.produitCommandeeQueryService = produitCommandeeQueryService;
    }

    /**
     * {@code POST  /produit-commandees} : Create a new produitCommandee.
     *
     * @param produitCommandeeDTO the produitCommandeeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new produitCommandeeDTO, or with status {@code 400 (Bad Request)} if the produitCommandee has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ProduitCommandeeDTO> createProduitCommandee(@RequestBody ProduitCommandeeDTO produitCommandeeDTO)
        throws URISyntaxException {
        log.debug("REST request to save ProduitCommandee : {}", produitCommandeeDTO);
        if (produitCommandeeDTO.getId() != null) {
            throw new BadRequestAlertException("A new produitCommandee cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProduitCommandeeDTO result = produitCommandeeService.save(produitCommandeeDTO);
        return ResponseEntity
            .created(new URI("/api/produit-commandees/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /produit-commandees/:id} : Updates an existing produitCommandee.
     *
     * @param id the id of the produitCommandeeDTO to save.
     * @param produitCommandeeDTO the produitCommandeeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated produitCommandeeDTO,
     * or with status {@code 400 (Bad Request)} if the produitCommandeeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the produitCommandeeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProduitCommandeeDTO> updateProduitCommandee(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ProduitCommandeeDTO produitCommandeeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ProduitCommandee : {}, {}", id, produitCommandeeDTO);
        if (produitCommandeeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, produitCommandeeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!produitCommandeeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProduitCommandeeDTO result = produitCommandeeService.update(produitCommandeeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, produitCommandeeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /produit-commandees/:id} : Partial updates given fields of an existing produitCommandee, field will ignore if it is null
     *
     * @param id the id of the produitCommandeeDTO to save.
     * @param produitCommandeeDTO the produitCommandeeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated produitCommandeeDTO,
     * or with status {@code 400 (Bad Request)} if the produitCommandeeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the produitCommandeeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the produitCommandeeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ProduitCommandeeDTO> partialUpdateProduitCommandee(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ProduitCommandeeDTO produitCommandeeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ProduitCommandee partially : {}, {}", id, produitCommandeeDTO);
        if (produitCommandeeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, produitCommandeeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!produitCommandeeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProduitCommandeeDTO> result = produitCommandeeService.partialUpdate(produitCommandeeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, produitCommandeeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /produit-commandees} : get all the produitCommandees.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of produitCommandees in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ProduitCommandeeDTO>> getAllProduitCommandees(
        ProduitCommandeeCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get ProduitCommandees by criteria: {}", criteria);

        Page<ProduitCommandeeDTO> page = produitCommandeeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /produit-commandees/count} : count all the produitCommandees.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countProduitCommandees(ProduitCommandeeCriteria criteria) {
        log.debug("REST request to count ProduitCommandees by criteria: {}", criteria);
        return ResponseEntity.ok().body(produitCommandeeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /produit-commandees/:id} : get the "id" produitCommandee.
     *
     * @param id the id of the produitCommandeeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the produitCommandeeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProduitCommandeeDTO> getProduitCommandee(@PathVariable("id") Long id) {
        log.debug("REST request to get ProduitCommandee : {}", id);
        Optional<ProduitCommandeeDTO> produitCommandeeDTO = produitCommandeeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(produitCommandeeDTO);
    }

    /**
     * {@code DELETE  /produit-commandees/:id} : delete the "id" produitCommandee.
     *
     * @param id the id of the produitCommandeeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduitCommandee(@PathVariable("id") Long id) {
        log.debug("REST request to delete ProduitCommandee : {}", id);
        produitCommandeeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
