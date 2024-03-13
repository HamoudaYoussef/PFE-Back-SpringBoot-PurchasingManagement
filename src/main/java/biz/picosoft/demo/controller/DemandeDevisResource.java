package biz.picosoft.demo.controller;

import biz.picosoft.demo.repository.DemandeDevisRepository;
import biz.picosoft.demo.service.DemandeDevisQueryService;
import biz.picosoft.demo.service.DemandeDevisService;
import biz.picosoft.demo.service.criteria.DemandeDevisCriteria;
import biz.picosoft.demo.controller.errors.BadRequestAlertException;

import com.mycompany.demo.service.dto.DemandeDevisDTO;
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
 */
@RestController
@RequestMapping("/api/demande-devis")
public class DemandeDevisResource {

    private final Logger log = LoggerFactory.getLogger(DemandeDevisResource.class);

    private static final String ENTITY_NAME = "gestionAchatPfeProduitOffert";

   // @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DemandeDevisService demandeDevisService;

    private final DemandeDevisRepository demandeDevisRepository;

    private final DemandeDevisQueryService demandeDevisQueryService;

    public DemandeDevisResource(
        DemandeDevisService demandeDevisService,
        DemandeDevisRepository demandeDevisRepository,
        DemandeDevisQueryService demandeDevisQueryService
    ) {
        this.demandeDevisService = demandeDevisService;
        this.demandeDevisRepository = demandeDevisRepository;
        this.demandeDevisQueryService = demandeDevisQueryService;
    }

    /**
     * {@code POST  /demande-devis} : Create a new demandeDevis.
     *
     * @param demandeDevisDTO the demandeDevisDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new demandeDevisDTO, or with status {@code 400 (Bad Request)} if the demandeDevis has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<DemandeDevisDTO> createDemandeDevis(@RequestBody DemandeDevisDTO demandeDevisDTO) throws URISyntaxException {
        log.debug("REST request to save DemandeDevis : {}", demandeDevisDTO);
        if (demandeDevisDTO.getId() != null) {
            throw new BadRequestAlertException("A new demandeDevis cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DemandeDevisDTO result = demandeDevisService.save(demandeDevisDTO);
        return ResponseEntity
            .created(new URI("/api/demande-devis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /demande-devis/:id} : Updates an existing demandeDevis.
     *
     * @param id the id of the demandeDevisDTO to save.
     * @param demandeDevisDTO the demandeDevisDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated demandeDevisDTO,
     * or with status {@code 400 (Bad Request)} if the demandeDevisDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the demandeDevisDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<DemandeDevisDTO> updateDemandeDevis(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DemandeDevisDTO demandeDevisDTO
    ) throws URISyntaxException {
        log.debug("REST request to update DemandeDevis : {}, {}", id, demandeDevisDTO);
        if (demandeDevisDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, demandeDevisDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!demandeDevisRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DemandeDevisDTO result = demandeDevisService.update(demandeDevisDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, demandeDevisDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /demande-devis/:id} : Partial updates given fields of an existing demandeDevis, field will ignore if it is null
     *
     * @param id the id of the demandeDevisDTO to save.
     * @param demandeDevisDTO the demandeDevisDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated demandeDevisDTO,
     * or with status {@code 400 (Bad Request)} if the demandeDevisDTO is not valid,
     * or with status {@code 404 (Not Found)} if the demandeDevisDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the demandeDevisDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DemandeDevisDTO> partialUpdateDemandeDevis(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DemandeDevisDTO demandeDevisDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update DemandeDevis partially : {}, {}", id, demandeDevisDTO);
        if (demandeDevisDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, demandeDevisDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!demandeDevisRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DemandeDevisDTO> result = demandeDevisService.partialUpdate(demandeDevisDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, demandeDevisDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /demande-devis} : get all the demandeDevis.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of demandeDevis in body.
     */
    @GetMapping("")
    public ResponseEntity<List<DemandeDevisDTO>> getAllDemandeDevis(
        DemandeDevisCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get DemandeDevis by criteria: {}", criteria);

        Page<DemandeDevisDTO> page = demandeDevisQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /demande-devis/count} : count all the demandeDevis.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countDemandeDevis(DemandeDevisCriteria criteria) {
        log.debug("REST request to count DemandeDevis by criteria: {}", criteria);
        return ResponseEntity.ok().body(demandeDevisQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /demande-devis/:id} : get the "id" demandeDevis.
     *
     * @param id the id of the demandeDevisDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the demandeDevisDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<DemandeDevisDTO> getDemandeDevis(@PathVariable("id") Long id) {
        log.debug("REST request to get DemandeDevis : {}", id);
        Optional<DemandeDevisDTO> demandeDevisDTO = demandeDevisService.findOne(id);
        return ResponseUtil.wrapOrNotFound(demandeDevisDTO);
    }

    /**
     * {@code DELETE  /demande-devis/:id} : delete the "id" demandeDevis.
     *
     * @param id the id of the demandeDevisDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDemandeDevis(@PathVariable("id") Long id) {
        log.debug("REST request to delete DemandeDevis : {}", id);
        demandeDevisService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
