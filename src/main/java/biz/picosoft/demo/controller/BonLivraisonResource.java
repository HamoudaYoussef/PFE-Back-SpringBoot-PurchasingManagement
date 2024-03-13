package biz.picosoft.demo.controller;

import biz.picosoft.demo.repository.BonLivraisonRepository;
import biz.picosoft.demo.service.BonLivraisonQueryService;
import biz.picosoft.demo.service.BonLivraisonService;
import biz.picosoft.demo.service.criteria.BonLivraisonCriteria;
import biz.picosoft.demo.service.dto.BonLivraisonDTO;
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
 * REST controller for managing {@link biz.picosoft.demo.domain.BonLivraison}.
 */
@RestController
@RequestMapping("/api/bon-livraisons")
public class BonLivraisonResource {

    private final Logger log = LoggerFactory.getLogger(BonLivraisonResource.class);

    private static final String ENTITY_NAME = "gestionAchatPfeBonLivraison";

  //  @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BonLivraisonService bonLivraisonService;

    private final BonLivraisonRepository bonLivraisonRepository;

    private final BonLivraisonQueryService bonLivraisonQueryService;

    public BonLivraisonResource(
        BonLivraisonService bonLivraisonService,
        BonLivraisonRepository bonLivraisonRepository,
        BonLivraisonQueryService bonLivraisonQueryService
    ) {
        this.bonLivraisonService = bonLivraisonService;
        this.bonLivraisonRepository = bonLivraisonRepository;
        this.bonLivraisonQueryService = bonLivraisonQueryService;
    }

    /**
     * {@code POST  /bon-livraisons} : Create a new bonLivraison.
     *
     * @param bonLivraisonDTO the bonLivraisonDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bonLivraisonDTO, or with status {@code 400 (Bad Request)} if the bonLivraison has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<BonLivraisonDTO> createBonLivraison(@RequestBody BonLivraisonDTO bonLivraisonDTO) throws URISyntaxException {
        log.debug("REST request to save BonLivraison : {}", bonLivraisonDTO);
        if (bonLivraisonDTO.getId() != null) {
            throw new BadRequestAlertException("A new bonLivraison cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BonLivraisonDTO result = bonLivraisonService.save(bonLivraisonDTO);
        return ResponseEntity
            .created(new URI("/api/bon-livraisons/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /bon-livraisons/:id} : Updates an existing bonLivraison.
     *
     * @param id the id of the bonLivraisonDTO to save.
     * @param bonLivraisonDTO the bonLivraisonDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bonLivraisonDTO,
     * or with status {@code 400 (Bad Request)} if the bonLivraisonDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bonLivraisonDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<BonLivraisonDTO> updateBonLivraison(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BonLivraisonDTO bonLivraisonDTO
    ) throws URISyntaxException {
        log.debug("REST request to update BonLivraison : {}, {}", id, bonLivraisonDTO);
        if (bonLivraisonDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bonLivraisonDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bonLivraisonRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BonLivraisonDTO result = bonLivraisonService.update(bonLivraisonDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bonLivraisonDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /bon-livraisons/:id} : Partial updates given fields of an existing bonLivraison, field will ignore if it is null
     *
     * @param id the id of the bonLivraisonDTO to save.
     * @param bonLivraisonDTO the bonLivraisonDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bonLivraisonDTO,
     * or with status {@code 400 (Bad Request)} if the bonLivraisonDTO is not valid,
     * or with status {@code 404 (Not Found)} if the bonLivraisonDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the bonLivraisonDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BonLivraisonDTO> partialUpdateBonLivraison(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BonLivraisonDTO bonLivraisonDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update BonLivraison partially : {}, {}", id, bonLivraisonDTO);
        if (bonLivraisonDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bonLivraisonDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bonLivraisonRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BonLivraisonDTO> result = bonLivraisonService.partialUpdate(bonLivraisonDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bonLivraisonDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /bon-livraisons} : get all the bonLivraisons.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bonLivraisons in body.
     */
    @GetMapping("")
    public ResponseEntity<List<BonLivraisonDTO>> getAllBonLivraisons(
        BonLivraisonCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get BonLivraisons by criteria: {}", criteria);

        Page<BonLivraisonDTO> page = bonLivraisonQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /bon-livraisons/count} : count all the bonLivraisons.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countBonLivraisons(BonLivraisonCriteria criteria) {
        log.debug("REST request to count BonLivraisons by criteria: {}", criteria);
        return ResponseEntity.ok().body(bonLivraisonQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /bon-livraisons/:id} : get the "id" bonLivraison.
     *
     * @param id the id of the bonLivraisonDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bonLivraisonDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<BonLivraisonDTO> getBonLivraison(@PathVariable("id") Long id) {
        log.debug("REST request to get BonLivraison : {}", id);
        Optional<BonLivraisonDTO> bonLivraisonDTO = bonLivraisonService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bonLivraisonDTO);
    }

    /**
     * {@code DELETE  /bon-livraisons/:id} : delete the "id" bonLivraison.
     *
     * @param id the id of the bonLivraisonDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBonLivraison(@PathVariable("id") Long id) {
        log.debug("REST request to delete BonLivraison : {}", id);
        bonLivraisonService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
