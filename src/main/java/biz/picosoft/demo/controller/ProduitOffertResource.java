package biz.picosoft.demo.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import biz.picosoft.demo.controller.errors.BadRequestAlertException;
import biz.picosoft.demo.repository.ProduitOffertRepository;
import biz.picosoft.demo.service.ProduitOffertService;
import biz.picosoft.demo.service.dto.ProduitOffertDTO;
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
 */
@RestController
@RequestMapping("/api/produit-offerts")
public class ProduitOffertResource {

    private final Logger log = LoggerFactory.getLogger(ProduitOffertResource.class);

    private static final String ENTITY_NAME = "produitOffert";

   // @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProduitOffertService produitOffertService;

    private final ProduitOffertRepository produitOffertRepository;

    public ProduitOffertResource(ProduitOffertService produitOffertService, ProduitOffertRepository produitOffertRepository) {
        this.produitOffertService = produitOffertService;
        this.produitOffertRepository = produitOffertRepository;
    }

    /**
     * {@code POST  /produit-offerts} : Create a new produitOffert.
     *
     * @param produitOffertDTO the produitOffertDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new produitOffertDTO, or with status {@code 400 (Bad Request)} if the produitOffert has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ProduitOffertDTO> createProduitOffert(@RequestBody ProduitOffertDTO produitOffertDTO) throws URISyntaxException {
        log.debug("REST request to save ProduitOffert : {}", produitOffertDTO);
        if (produitOffertDTO.getId() != null) {
            throw new BadRequestAlertException("A new produitOffert cannot already have an ID", ENTITY_NAME, "idexists");
        }

        // Affectez l'ID de l'offre
        if (produitOffertDTO.getOffreId() == null) {
            throw new BadRequestAlertException("L'ID de l'offre doit être fourni", ENTITY_NAME, "offreIdnull");
        }

        ProduitOffertDTO result = produitOffertService.save(produitOffertDTO);
        return ResponseEntity
                .created(new URI("/api/produit-offerts/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }


    /**
     * {@code PUT  /produit-offerts/:id} : Updates an existing produitOffert.
     *
     * @param id the id of the produitOffertDTO to save.
     * @param produitOffertDTO the produitOffertDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated produitOffertDTO,
     * or with status {@code 400 (Bad Request)} if the produitOffertDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the produitOffertDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProduitOffertDTO> updateProduitOffert(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ProduitOffertDTO produitOffertDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ProduitOffert : {}, {}", id, produitOffertDTO);
        if (produitOffertDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, produitOffertDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!produitOffertRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProduitOffertDTO result = produitOffertService.update(produitOffertDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, produitOffertDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /produit-offerts/:id} : Partial updates given fields of an existing produitOffert, field will ignore if it is null
     *
     * @param id the id of the produitOffertDTO to save.
     * @param produitOffertDTO the produitOffertDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated produitOffertDTO,
     * or with status {@code 400 (Bad Request)} if the produitOffertDTO is not valid,
     * or with status {@code 404 (Not Found)} if the produitOffertDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the produitOffertDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ProduitOffertDTO> partialUpdateProduitOffert(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ProduitOffertDTO produitOffertDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ProduitOffert partially : {}, {}", id, produitOffertDTO);
        if (produitOffertDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, produitOffertDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!produitOffertRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProduitOffertDTO> result = produitOffertService.partialUpdate(produitOffertDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, produitOffertDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /produit-offerts} : get all the produitOfferts.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of produitOfferts in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ProduitOffertDTO>> getAllProduitOfferts(Pageable pageable) {
        log.debug("REST request to get a page of ProduitOfferts");
        Page<ProduitOffertDTO> page = produitOffertService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /produit-offerts/:id} : get the "id" produitOffert.
     *
     * @param id the id of the produitOffertDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the produitOffertDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProduitOffertDTO> getProduitOffert(@PathVariable("id") Long id) {
        log.debug("REST request to get ProduitOffert : {}", id);
        Optional<ProduitOffertDTO> produitOffertDTO = produitOffertService.findOne(id);
        return ResponseUtil.wrapOrNotFound(produitOffertDTO);
    }

    /**
     * {@code DELETE  /produit-offerts/:id} : delete the "id" produitOffert.
     *
     * @param id the id of the produitOffertDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduitOffert(@PathVariable("id") Long id) {
        log.debug("REST request to delete ProduitOffert : {}", id);
        produitOffertService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    @GetMapping("/by-offre/{offreId}")
    public ResponseEntity<List<ProduitOffertDTO>> getProduitOffertsByOffreId(@PathVariable Long offreId) {
        List<ProduitOffertDTO> produitOfferts = produitOffertService.findByOffreId(offreId);
        return ResponseEntity.ok().body(produitOfferts);
    }
}
