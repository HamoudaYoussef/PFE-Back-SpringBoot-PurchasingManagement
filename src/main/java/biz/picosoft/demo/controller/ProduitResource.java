package biz.picosoft.demo.controller;

import biz.picosoft.demo.domain.Produit;
import biz.picosoft.demo.domain.Categorie;
import biz.picosoft.demo.errors.ResourceNotFoundException;
import biz.picosoft.demo.repository.ProduitRepository;
import biz.picosoft.demo.service.ProduitQueryService;
import biz.picosoft.demo.service.ProduitService;
import biz.picosoft.demo.service.criteria.ProduitCriteria;
import biz.picosoft.demo.controller.errors.BadRequestAlertException;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import biz.picosoft.demo.service.dto.ProduitDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link biz.picosoft.demo.domain.Produit}.
 */
@RestController
@RequestMapping("/api/produits")
public class ProduitResource {

    private final Logger log = LoggerFactory.getLogger(ProduitResource.class);

    private static final String ENTITY_NAME = "gestionAchatPfeProduit";

//    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProduitService produitService;

    private final ProduitRepository produitRepository;

    private final ProduitQueryService produitQueryService;

    public ProduitResource(ProduitService produitService, ProduitRepository produitRepository,
     ProduitQueryService produitQueryService) {
        this.produitService = produitService;
        this.produitRepository = produitRepository;
        this.produitQueryService = produitQueryService;
    }

    /**
     * {@code POST  /produits} : Create a new produit.
     *
     * @param produitDTO the produitDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new produitDTO, or with status {@code 400 (Bad Request)} if the produit has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ProduitDTO> createProduit(@RequestBody ProduitDTO produitDTO) throws URISyntaxException {
        log.debug("REST request to save Produit : {}", produitDTO);
        if (produitDTO.getId() != null) {
            throw new BadRequestAlertException("A new produit cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProduitDTO result = produitService.save(produitDTO);
        return ResponseEntity
            .created(new URI("api/produits/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }
    @PostMapping("/add-produit-with-image")
    public ResponseEntity<ProduitDTO> addProduitWIthImage(
            @RequestParam("couleur") String couleur,
            @RequestParam("description") String description,
            @RequestParam("nom") String nom,
            @RequestParam("quantite") long quantite,
            @RequestParam("categorie") Categorie categorie,
            @RequestParam(value = "img", required = false) MultipartFile img
    ) throws URISyntaxException, IOException {
        log.debug("REST request to save Produit with image: {}", couleur, description, nom, quantite, categorie);


        // Create a new ProduitDTO object
        ProduitDTO produitDTO = new ProduitDTO();
        produitDTO.setDescription(description);
        produitDTO.setNom(nom);
        produitDTO.setQuantite(quantite);
        produitDTO.setCategorie(categorie);

        // Handle image upload
        if (img != null && !img.isEmpty()) {
            // Obtenez le nom du fichier sans le chemin
            String imageName = img.getOriginalFilename(); // Récupère le nom de l'image
            produitDTO.setImg(imageName); // Stocke uniquement le nom de l'image
        }

        ProduitDTO result = produitService.save(produitDTO);
        return ResponseEntity
                .created(new URI("api/produits/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    /**M
     * {@code PUT  /produits/:id} : Updates an existing produit.
     *
     * @param id the id of the produitDTO to save.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated produitDTO,
     * or with status {@code 400 (Bad Request)} if the produitDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the produitDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<Produit> updateProduit(
            @PathVariable("id") Long id,
            @RequestBody Produit updatedProduit) {
        try {
            Produit updated = produitService.updateProduit(id, updatedProduit);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    /**
     * {@code PATCH  /produits/:id} : Partial updates given fields of an existing produit, field will ignore if it is null
     *
     * @param id the id of the produitDTO to save.
     * @param produitDTO the produitDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated produitDTO,
     * or with status {@code 400 (Bad Request)} if the produitDTO is not valid,
     * or with status {@code 404 (Not Found)} if the produitDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the produitDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ProduitDTO> partialUpdateProduit(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ProduitDTO produitDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Produit partially : {}, {}", id, produitDTO);
        if (produitDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, produitDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!produitRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProduitDTO> result = produitService.partialUpdate(produitDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, produitDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /produits} : get all the produits.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of produits in body.
     */

    @GetMapping("/getProduits")
 public ResponseEntity<Page<ProduitDTO>> getAllProduits(
            ProduitCriteria criteria,
            @org.springdoc.api.annotations.ParameterObject Pageable pageable

    ) {
        log.debug("REST request to get all DemandeAchats by criteria: {}", criteria);
        Page<ProduitDTO> page = produitQueryService.findByCriteria(criteria, pageable);

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page);

    }

    @GetMapping("/produits")
    public List<ProduitDTO> getAllProduitsSimple() {
        return produitService.findAllSimple();
    }

    /**
     * {@code GET  /produits/count} : count all the produits.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countProduits(ProduitCriteria criteria) {
        log.debug("REST request to count Produits by criteria: {}", criteria);
        return ResponseEntity.ok().body(produitQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /produits/:id} : get the "id" produit.
     *
     * @param id the id of the produitDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the produitDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProduitDTO> getProduit(@PathVariable("id") Long id) {
        log.debug("REST request to get Produit : {}", id);
        Optional<ProduitDTO> produitDTO = produitService.findOne(id);
        return ResponseUtil.wrapOrNotFound(produitDTO);
    }

    /**
     * {@code DELETE  /produits/:id} : delete the "id" produit.
     *
     * @param id the id of the produitDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduit(@PathVariable("id") Long id) {
        log.debug("REST request to delete Produit : {}", id);
        produitService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    @GetMapping("/byCategorie/{categorieId}")
    public List<Produit> getProduitsByCategorie(@PathVariable Long categorieId) {
        return produitService.getProduitsByCategorie(categorieId);
    }

   /* @GetMapping("/demande-achat/{demandeAchatId}")
    public List<Produit> getProduitsByDemandeAchatId(@PathVariable Long demandeAchatId) {
        return produitService.getProduitsByDemandeAchatId(demandeAchatId);
    }
*/
 /*   @GetMapping("/verifier/{id}")
    public String verifierStock(@PathVariable Long id) {
        return produitService.verifierStock(id);
    }
*/
}

