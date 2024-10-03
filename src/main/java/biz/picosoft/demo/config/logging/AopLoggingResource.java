package biz.picosoft.demo.config.logging;


import biz.picosoft.demo.config.logging.domain.AopLogging;
import biz.picosoft.demo.config.logging.repository.AopLoggingRepository;
import biz.picosoft.demo.controller.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * REST controller for managing {@link AopLogging}.
 */
@RestController
@RequestMapping("/api")
public class AopLoggingResource {

    private final Logger log = LoggerFactory.getLogger(AopLoggingResource.class);

    private static final String ENTITY_NAME = "personAopLogging";

    @Value("${spring.application.name}")
    private String applicationName;

    private final AopLoggingService aopLoggingService;

    private final AopLoggingRepository aopLoggingRepository;

    private final AopLoggingQueryService aopLoggingQueryService;
    @Autowired
    LoggingAspect loggingAspect;

    public AopLoggingResource(
            AopLoggingService aopLoggingService,
            AopLoggingRepository aopLoggingRepository,
            AopLoggingQueryService aopLoggingQueryService
    ) {
        this.aopLoggingService = aopLoggingService;
        this.aopLoggingRepository = aopLoggingRepository;
        this.aopLoggingQueryService = aopLoggingQueryService;
    }

    /**
     * {@code POST  /aop-loggings} : Create a new aopLogging.
     *
     * @param aopLoggingDTO the aopLoggingDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new aopLoggingDTO, or with status {@code 400 (Bad Request)} if the aopLogging has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/aop-loggings")
    public ResponseEntity<AopLoggingDTO> createAopLogging(@RequestBody AopLoggingDTO aopLoggingDTO) throws URISyntaxException {
        log.debug("REST request to save AopLogging : {}", aopLoggingDTO);
        if (aopLoggingDTO.getId() != null) {
            throw new BadRequestAlertException("A new aopLogging cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AopLoggingDTO result = aopLoggingService.save(aopLoggingDTO);
        return ResponseEntity
                .created(new URI("/api/aop-loggings/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    /**
     * {@code PUT  /aop-loggings/:id} : Updates an existing aopLogging.
     *
     * @param id            the id of the aopLoggingDTO to save.
     * @param aopLoggingDTO the aopLoggingDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated aopLoggingDTO,
     * or with status {@code 400 (Bad Request)} if the aopLoggingDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the aopLoggingDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/aop-loggings/{id}")
    public ResponseEntity<AopLoggingDTO> updateAopLogging(
            @PathVariable(value = "id", required = false) final Long id,
            @RequestBody AopLoggingDTO aopLoggingDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AopLogging : {}, {}", id, aopLoggingDTO);
        if (aopLoggingDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, aopLoggingDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!aopLoggingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AopLoggingDTO result = aopLoggingService.save(aopLoggingDTO);
        return ResponseEntity
                .ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, aopLoggingDTO.getId().toString()))
                .body(result);
    }

    /**
     * {@code PATCH  /aop-loggings/:id} : Partial updates given fields of an existing aopLogging, field will ignore if it is null
     *
     * @param id            the id of the aopLoggingDTO to save.
     * @param aopLoggingDTO the aopLoggingDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated aopLoggingDTO,
     * or with status {@code 400 (Bad Request)} if the aopLoggingDTO is not valid,
     * or with status {@code 404 (Not Found)} if the aopLoggingDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the aopLoggingDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/aop-loggings/{id}", consumes = {"application/json", "application/merge-patch+json"})
    public ResponseEntity<AopLoggingDTO> partialUpdateAopLogging(
            @PathVariable(value = "id", required = false) final Long id,
            @RequestBody AopLoggingDTO aopLoggingDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AopLogging partially : {}, {}", id, aopLoggingDTO);
        if (aopLoggingDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, aopLoggingDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!aopLoggingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AopLoggingDTO> result = aopLoggingService.partialUpdate(aopLoggingDTO);

        return ResponseUtil.wrapOrNotFound(
                result,
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, aopLoggingDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /aop-loggings} : get all the aopLoggings.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of aopLoggings in body.
     */
    @GetMapping("/aop-loggings")
    public ResponseEntity<List<AopLoggingDTO>> getAllAopLoggings(AopLoggingCriteria criteria, Pageable pageable) {
        log.debug("REST request to get AopLoggings by criteria: {}", criteria);
        Page<AopLoggingDTO> page = aopLoggingQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /aop-loggings/count} : count all the aopLoggings.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/aop-loggings/count")
    public ResponseEntity<Long> countAopLoggings(AopLoggingCriteria criteria) {
        log.debug("REST request to count AopLoggings by criteria: {}", criteria);
        return ResponseEntity.ok().body(aopLoggingQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /aop-loggings/:id} : get the "id" aopLogging.
     *
     * @param id the id of the aopLoggingDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the aopLoggingDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/aop-loggings/{id}")
    public ResponseEntity<AopLoggingDTO> getAopLogging(@PathVariable Long id) {
        log.debug("REST request to get AopLogging : {}", id);
        Optional<AopLoggingDTO> aopLoggingDTO = aopLoggingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(aopLoggingDTO);
    }

    /**
     * {@code DELETE  /aop-loggings/:id} : delete the "id" aopLogging.
     *
     * @param id the id of the aopLoggingDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/aop-loggings/{id}")
    public ResponseEntity<Void> deleteAopLogging(@PathVariable Long id) {
        log.debug("REST request to delete AopLogging : {}", id);
        aopLoggingService.delete(id);
        return ResponseEntity
                .noContent()
                .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
                .build();
    }

    @GetMapping("/aopState")
    public Boolean getEnableAop() {
        return loggingAspect.getEnableAop();
    }

    @PutMapping("/aopEnable")
    public void enableAop() {
        loggingAspect.enableAop();
    }

    @PutMapping("/aopDisable")
    public void disableAop() {
        loggingAspect.disableAop();
    }

//    @PutMapping("/ListAop")
//    public void ListAop() {
//
//        myLoggingAspect.aopLoggings.removeAll(Collections.emptySet());
//
//    }

    @GetMapping("/getListAop")
    public List<AopLogging> getListAop() {
        return loggingAspect.aopLoggings;

    }

    //    @PostMapping("/createListAop")
    @Scheduled(fixedDelay = 300000)
    public void createListAop() {
       // System.out.println("walid +++++++" + ZonedDateTime.now());
        LinkedList<AopLogging> list = loggingAspect.aopLoggings;
        while (!list.isEmpty()) {
            aopLoggingRepository.save(list.peek());
            list.poll();
        }
    }


}
