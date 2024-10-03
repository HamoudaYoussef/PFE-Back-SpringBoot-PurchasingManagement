package biz.picosoft.demo.controller;

import biz.picosoft.demo.service.ProduitCommandeeQueryService;
import biz.picosoft.demo.service.ProduitCommandeeService;
import biz.picosoft.demo.service.criteria.ProduitCommandeeCriteria;
import biz.picosoft.demo.service.criteria.ProduitDemandeeCriteria;
import biz.picosoft.demo.service.dto.ProduitCommandeeDTO;
import biz.picosoft.demo.service.dto.ProduitDemandeeDTO;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import java.awt.print.Pageable;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api")
public class ProduitCommandeeRessource {
    private final Logger log = LoggerFactory.getLogger(ProduitCommandeeRessource.class);

    private static final String ENTITY_NAME = "gestionAchatPfeOffre";

  //  @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProduitCommandeeService produitCommandeeService;
    private final ProduitCommandeeQueryService produitCommandeeQueryService;


    public ProduitCommandeeRessource(ProduitCommandeeService produitCommandeeService,
                                     ProduitCommandeeQueryService produitCommandeeQueryService) {
        this.produitCommandeeService = produitCommandeeService;
        this.produitCommandeeQueryService = produitCommandeeQueryService;
    }

    /**
     * {@code POST  /produit-commandees} : Create a new produitCommandee.
     *

     */
    @PostMapping("/produit-commandees")
    public ResponseEntity<ProduitCommandeeDTO> createProduitCommandee(@RequestBody ProduitCommandeeDTO produitCommandeeDTO) throws URISyntaxException {
        log.debug("REST request to save ProduitCommandee : {}", produitCommandeeDTO);
        if (produitCommandeeDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(applicationName, false, ENTITY_NAME, "idexists", "A new produitCommandee cannot already have an ID")).body(null);
        }
        ProduitCommandeeDTO result = produitCommandeeService.save(produitCommandeeDTO);
        return ResponseEntity.created(new URI("/api/produit-commandees/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    /**
     * {@code PUT  /produit-commandees} : Updates an existing produitCommandee.
     *
     * @param produitCommandeeDTO the produitCommandeeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated produitCommandeeDTO,
     * or with status {@code 400 (Bad Request)} if the produitCommandeeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the produitCommandeeDTO couldn't be updated.
     */
    @PutMapping("/produit-commandees")
    public ResponseEntity<ProduitCommandeeDTO> updateProduitCommandee(@RequestBody ProduitCommandeeDTO produitCommandeeDTO) {
        log.debug("REST request to update ProduitCommandee : {}", produitCommandeeDTO);
        if (produitCommandeeDTO.getId() == null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(applicationName, false, ENTITY_NAME, "idnull", "Invalid id")).body(null);
        }
        ProduitCommandeeDTO result = produitCommandeeService.save(produitCommandeeDTO);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, produitCommandeeDTO.getId().toString()))
                .body(result);
    }

    /**
     * {@code GET  /produit-commandees} : get all the produitCommandeess.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of produitCommandeess in body.
     */
    @GetMapping("/produit-commandees")
    public ResponseEntity<Page<ProduitCommandeeDTO>> getAllProduitCommandeess(
            ProduitCommandeeCriteria criteria,
            @org.springdoc.api.annotations.ParameterObject org.springframework.data.domain.Pageable pageable

    ) {
        log.debug("REST request to get all DemandeAchats by criteria: {}", criteria);
        Page<ProduitCommandeeDTO> page = produitCommandeeQueryService.findByCriteria(criteria, pageable);

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page);

    }

    /**
     * {@code GET  /produit-commandees/:id} : get the "id" produitCommandee.
     *
     * @param id the id of the produitCommandeeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the produitCommandeeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/produit-commandees/{id}")
    public ResponseEntity<ProduitCommandeeDTO> getProduitCommandee(@PathVariable Long id) {
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
    @DeleteMapping("/produit-commandees/{id}")
    public ResponseEntity<Void> deleteProduitCommandee(@PathVariable Long id) {
        log.debug("REST request to delete ProduitCommandee : {}", id);
        produitCommandeeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
