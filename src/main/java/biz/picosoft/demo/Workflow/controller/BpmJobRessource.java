package biz.picosoft.demo.Workflow.controller;

import biz.picosoft.demo.Workflow.domain.BpmJob;
import biz.picosoft.demo.Workflow.repository.BpmJobRepository;
import biz.picosoft.demo.Workflow.service.BpmJobQueryService;
import biz.picosoft.demo.Workflow.service.BpmJobService;
import biz.picosoft.demo.Workflow.service.criteria.BpmJobCriteria;
import biz.picosoft.demo.Workflow.service.dto.BpmJobDTO;

import biz.picosoft.demo.controller.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;


//@PreAuthorize("isAuthenticated()")
@RestController
@RequestMapping(path = "/api/workflow")
@Api(tags = "Mail Manager")
public class BpmJobRessource {

    private final Logger log = LoggerFactory.getLogger(BpmJobRessource.class);

    private static final String ENTITY_NAME = "bpmJob";


    private final BpmJobService bpmJobService;

    private final BpmJobQueryService bpmJobQueryService;

    @Autowired
    private BpmJobRepository bpmJobRepository;

    public BpmJobRessource(BpmJobService bpmJobService, BpmJobQueryService bpmJobQueryService) {
        this.bpmJobService = bpmJobService;
        this.bpmJobQueryService = bpmJobQueryService;
    }

    /**
     * {@code POST  /bpmJob} : Create a new bpmJob.
     *
     * @param bpmJobDTO the bpmJob to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bpmJob, or with status {@code 400 (Bad Request)} if the bpmJob has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */


    @PostMapping("/bpmJob")
    public ResponseEntity<BpmJobDTO> createBpmJob(@RequestBody BpmJobDTO bpmJobDTO) throws URISyntaxException {
        log.debug("REST request to save BpmJob : {}", bpmJobDTO);
        if (bpmJobDTO.getId() != null) {
            throw new BadRequestAlertException("A new bpmJob cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BpmJobDTO result = bpmJobService.save(bpmJobDTO);
        return ResponseEntity.created(new URI("/api/bpmJob/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("", false, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    /**
     * {@code PUT  /bpmJob} : Updates an existing bpmJob.
     *
     * @param bpmJobDTO the bpmJob to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bpmJob,
     * or with status {@code 400 (Bad Request)} if the bpmJob is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bpmJob couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    //@PreAuthorize("hasAuthority(@globalServicesService.adminSysteme()) ")
    @PutMapping("/bpmJob")
    public ResponseEntity<BpmJobDTO> updateBpmJob(@RequestBody BpmJobDTO bpmJobDTO) throws URISyntaxException {
        log.debug("REST request to update BpmJob : {}", bpmJobDTO);
        if (bpmJobDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BpmJobDTO result = bpmJobService.save(bpmJobDTO);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("", false, ENTITY_NAME, bpmJobDTO.getId().toString()))
                .body(result);
    }

    /**
     * {@code GET  /bpmJob} : get all the bpmJob.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bpmJob in body.
     */
    //@PreAuthorize("hasAuthority(@globalServicesService.adminSysteme()) ")
    @GetMapping("/bpmJob")
    public ResponseEntity<List<BpmJobDTO>> getAllBpmJob(BpmJobCriteria criteria, Pageable pageable, @RequestParam(value = "size", required = false) Optional<Integer> size) {
        log.debug("REST request to get BpmJob by criteria: {}", criteria);
        if (size.isPresent()) {
            Page<BpmJobDTO> page = bpmJobQueryService.findByCriteria(criteria, pageable, size.get());
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
            return ResponseEntity.ok().headers(headers).body(page.getContent());
        } else {
            Page<BpmJobDTO> page = bpmJobQueryService.findByCriteria(criteria, pageable, 0);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
            return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
    }

    /**
     * {@code GET  /bpmJob/count} : count all the bpmJob.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    //@PreAuthorize("hasAuthority(@globalServicesService.adminSysteme()) ")
    @GetMapping("/bpmJob/count")
    public ResponseEntity<Long> countBpmJob(BpmJobCriteria criteria) {
        log.debug("REST request to count BpmJob by criteria: {}", criteria);
        return ResponseEntity.ok().body(bpmJobQueryService.countByCriteria(criteria));
    }

    @GetMapping("/findSteps-bpm-job")
    public ResponseEntity<List<String>> findSteps(@RequestParam Long classId) {
        log.debug("REST request to count BpmJob by criteria: {}", classId);
        return ResponseEntity.ok().body(bpmJobRepository.findSteps(classId));
    }


    /**
     * {@code GET  /bpmJob/:id} : get the "id" bpmJob.
     *
     * @param id the id of the bpmJob to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bpmJob, or with status {@code 404 (Not Found)}.
     */
    //@PreAuthorize("hasAuthority(@globalServicesService.adminSysteme()) ")
    @GetMapping("/bpmJob/{id}")
    public ResponseEntity<BpmJobDTO> getBpmJob(@PathVariable Long id) {
        log.debug("REST request to get BpmJob : {}", id);
        Optional<BpmJobDTO> bpmJobDTO = bpmJobService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bpmJobDTO);
    }

    @GetMapping("/find-bpmJob")
    public Optional<BpmJobDTO> getBpmJob(@RequestParam Long objectId, @RequestParam Long classId) {
      log.debug("REST request to get BpmJob : {}", objectId + ", " + classId);
      Optional<BpmJobDTO> bpmJobDTO = bpmJobService.findByObjectIdAndClassId(objectId, classId);
      return bpmJobDTO;
    }

    @GetMapping("/findBpmJobByAssigneeAndClassID")
    public List<BpmJob> findBpmJobByAssigneeAndClassID(@RequestParam String assigne, @RequestParam Long classId) {
        log.debug("REST request to get BpmJob : {}", assigne + ", " + classId);
        return bpmJobRepository.findAllByAssigneeAndClassID(assigne, classId);
    }

    /**
     * {@code DELETE  /bpmJob/:id} : delete the "id" bpmJob.
     *
     * @param id the id of the bpmJob to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    //@PreAuthorize("hasAuthority(@globalServicesService.adminSysteme()) ")
    @DeleteMapping("/bpmJob/{id}")
    public ResponseEntity<Void> deleteBpmJob(@PathVariable Long id) {
        log.debug("REST request to delete BpmJob : {}", id);
        try {
            bpmJobService.delete(id);
            return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert("", false, ENTITY_NAME, id.toString())).build();
        } catch (Exception e) {
            throw new BadRequestAlertException("you cannot delete this because it used", ENTITY_NAME, "you cannot delete this because it used");
        }
    }
}
