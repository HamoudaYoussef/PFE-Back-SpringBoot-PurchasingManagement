package biz.picosoft.demo.controller;

import biz.picosoft.demo.domain.DemandeAchat;
import biz.picosoft.demo.domain.Fournisseur;
import biz.picosoft.demo.domain.Offre;
import biz.picosoft.demo.repository.OffreRepository;
import biz.picosoft.demo.service.OffreQueryService;
import biz.picosoft.demo.service.OffreService;
import biz.picosoft.demo.service.criteria.DemandeDevisCriteria;
import biz.picosoft.demo.service.criteria.OffreCriteria;
import biz.picosoft.demo.service.dto.DemandeDevisDTO;
import biz.picosoft.demo.service.dto.OffreDTO;
import biz.picosoft.demo.controller.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link biz.picosoft.demo.domain.Offre}.
 */
@RestController
@RequestMapping("/api/offres")
public class OffreResource {

    private final Logger log = LoggerFactory.getLogger(OffreResource.class);

    private static final String ENTITY_NAME = "gestionAchatPfeOffre";

  //  @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OffreService offreService;

    private final OffreRepository offreRepository;

    private final OffreQueryService offreQueryService;

    public OffreResource(OffreService offreService, OffreRepository offreRepository, OffreQueryService offreQueryService) {
        this.offreService = offreService;
        this.offreRepository = offreRepository;
        this.offreQueryService = offreQueryService;
    }

    /**
     * {@code POST  /offres} : Create a new offre.
     *
     * @param offreDTO the offreDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new offreDTO, or with status {@code 400 (Bad Request)} if the offre has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<OffreDTO> createOffre(@RequestBody OffreDTO offreDTO) throws URISyntaxException {
        log.debug("REST request to save Offre : {}", offreDTO);
        if (offreDTO.getId() != null) {
            throw new BadRequestAlertException("A new offre cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OffreDTO result = offreService.save(offreDTO);
        return ResponseEntity
            .created(new URI("/api/offres/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }
    @PostMapping("/withoutdd")
    public ResponseEntity<OffreDTO> createOffreWithoutDD(@RequestBody OffreDTO offreDTO) throws URISyntaxException {
        log.debug("REST request to save Offre : {}", offreDTO);
        if (offreDTO.getId() != null) {
            throw new BadRequestAlertException("A new offre cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OffreDTO result = offreService.savewithoutdd(offreDTO);
        return ResponseEntity
                .created(new URI("/api/offres/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }
    @PostMapping("/ajouter")
    public ResponseEntity<Offre> ajouterOffre(@RequestBody OffreDTO offreDTO) {
        Offre nouvelleOffre = offreService.ajouterOffreAvecProduits(offreDTO);
        return ResponseEntity.ok(nouvelleOffre);
    }


    /**
     * {@code PUT  /offres/:id} : Updates an existing offre.
     *
     * @param id the id of the offreDTO to save.
     * @param offreDTO the offreDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated offreDTO,
     * or with status {@code 400 (Bad Request)} if the offreDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the offreDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<OffreDTO> updateOffre(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OffreDTO offreDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Offre : {}, {}", id, offreDTO);
        if (offreDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, offreDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!offreRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        OffreDTO result = offreService.update(offreDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, offreDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /offres/:id} : Partial updates given fields of an existing offre, field will ignore if it is null
     *
     * @param id the id of the offreDTO to save.
     * @param offreDTO the offreDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated offreDTO,
     * or with status {@code 400 (Bad Request)} if the offreDTO is not valid,
     * or with status {@code 404 (Not Found)} if the offreDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the offreDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<OffreDTO> partialUpdateOffre(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OffreDTO offreDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Offre partially : {}, {}", id, offreDTO);
        if (offreDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, offreDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!offreRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OffreDTO> result = offreService.partialUpdate(offreDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, offreDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /offres} : get all the offres.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of offres in body.
     */

    @GetMapping("/offres")
    public ResponseEntity<Page<OffreDTO>> getAllOffres(
            OffreCriteria criteria,
            @org.springdoc.api.annotations.ParameterObject Pageable pageable

    ) {
        log.debug("REST request to get all DemandeAchats by criteria: {}", criteria);
        Page<OffreDTO> page = offreQueryService.findByCriteria(criteria, pageable);

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page);

    }

    /**
     * {@code GET  /offres/count} : count all the offres.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countOffres(OffreCriteria criteria) {
        log.debug("REST request to count Offres by criteria: {}", criteria);
        return ResponseEntity.ok().body(offreQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /offres/:id} : get the "id" offre.
     *
     * @param id the id of the offreDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the offreDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<OffreDTO> getOffre(@PathVariable("id") Long id) {
        log.debug("REST request to get Offre : {}", id);
        Optional<OffreDTO> offreDTO = offreService.findOne(id);
        return ResponseUtil.wrapOrNotFound(offreDTO);
    }

    /**
     * {@code DELETE  /offres/:id} :  the "id" offre.
     *
     * @param id the id of the offreDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOffre(@PathVariable("id") Long id) {
        log.debug("REST request to delete Offre : {}", id);
        offreService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    @GetMapping("/recent")
    public List<Offre> getRecentOffres() {
        return offreService.findRecentOffres();
    }
    @GetMapping("/fournisseur/{fournisseurId}")
    public List<Offre> getOffresByFournisseur(@PathVariable Long fournisseurId) {
        return offreService.findOffresByFournisseur(fournisseurId);
    }

    @GetMapping("/demandeachat/{demandeachatId}")
    public List<Offre> getOffresByDemandeAchat(@PathVariable Long demandeachatId) {
        return offreService.findOffresByDemandeAchat(demandeachatId);
    }
    @GetMapping("/offres-par-fournisseur")
    public Map<Fournisseur, List<Offre>> getOffresParFournisseur() {
        return offreService.getOffresTriesParFournisseur();
    }
    @GetMapping("/offres-par-demandeachat")
    public Map<DemandeAchat, List<Offre>> getOffresParDemandeAchat() {
        return offreService.getOffresTriesParDemandeAchat();
    }

    @GetMapping("/{id}/fournisseur-name")
    public String getFournisseurName(@PathVariable Long id) {
        Offre offre = offreRepository.findById(id).orElseThrow();
        return offre.getFournisseur().getNom();
    }
    /*@PostMapping("/affecter")
    public ResponseEntity<Offre> affecterOffre(@RequestBody Offre offreRequest) {
        try {
            Offre offre = offreService.affecteroffre(offreRequest);
            return new ResponseEntity<>(offre, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND); // Retourne une erreur 404 si la DemandeDevis n'est pas trouvée
        }
    }*/
}
