package biz.picosoft.demo.controller;

import biz.picosoft.demo.repository.DemandeAchatRepository;
import biz.picosoft.demo.service.DemandeAchatQueryService;
import biz.picosoft.demo.service.DemandeAchatService;
import biz.picosoft.demo.service.criteria.DemandeAchatCriteria;
import biz.picosoft.demo.service.dto.DemandeAchatDTO;
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

    public DemandeAchatResource(
        DemandeAchatService demandeAchatService,
        DemandeAchatRepository demandeAchatRepository,
        DemandeAchatQueryService demandeAchatQueryService
    ) {
        this.demandeAchatService = demandeAchatService;
        this.demandeAchatRepository = demandeAchatRepository;
        this.demandeAchatQueryService = demandeAchatQueryService;
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
        if (demandeAchatDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, demandeAchatDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!demandeAchatRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DemandeAchatDTO result = demandeAchatService.update(demandeAchatDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, demandeAchatDTO.getId().toString()))
            .body(result);
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
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of demandeAchats in body.
     */
    @GetMapping("/getDA")
    public ResponseEntity<List<DemandeAchatDTO>> getAllDemandeAchats(
        DemandeAchatCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get DemandeAchats by criteria: {}", criteria);

        Page<DemandeAchatDTO> page = demandeAchatQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
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
}
