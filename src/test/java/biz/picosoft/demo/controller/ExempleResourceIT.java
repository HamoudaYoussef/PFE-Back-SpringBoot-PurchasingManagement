package biz.picosoft.demo.controller;//package biz.picosoft.demo.controller;
//
//import biz.picosoft.demo.client.kernel.intercomm.KernelService;
//import biz.picosoft.demo.client.kernel.model.global.AuthUser;
//import biz.picosoft.demo.config.IntegrationTest;
//import biz.picosoft.demo.config.SecurityConstants;
//import biz.picosoft.demo.config.TestUtil;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.TestPropertySource;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.persistence.EntityManager;
//import java.util.List;
//import java.util.Random;
//import java.util.concurrent.atomic.AtomicLong;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.hamcrest.Matchers.hasItem;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
///**
// * Integration tests for the {@link CategorieResource} REST controller.
// */
//@IntegrationTest
//@AutoConfigureMockMvc
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@RunWith(SpringRunner.class)
//@TestPropertySource(locations = "classpath:application-test.properties")
//class CategorieResourceIT {
//
//    private static final String DEFAULT_NAME = "AAAAAAAAAA";
//    private static final String UPDATED_NAME = "BBBBBBBBBB";
//
//    private static final String ENTITY_API_URL = "/api/categories";
//    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
//
//    private static Random random = new Random();
//    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
//
//    @Value("${login}")
//    private String login;
//
//    @Value("${password}")
//    private String password;
//
//    @Value("${application}")
//    private String application;
//
//    private String token;
//    @Autowired
//    private KernelService kernelService;
//
//    @Autowired
//    private CategorieRepository categorieRepository;
//
//    @Autowired
//    private CategorieMapper categorieMapper;
//
//    @Autowired
//    private EntityManager em;
//
//    @Autowired
//    private MockMvc restCategorieMockMvc;
//
//    private Categorie categorie;
//
//    /**
//     * Create an entity for this test.
//     * <p>
//     * This is a static method, as tests for other entities might also need it,
//     * if they test an entity which requires the current entity.
//     */
//    public static Categorie createEntity(EntityManager em) {
//        Categorie categorie = new Categorie().name(DEFAULT_NAME);
//        return categorie;
//    }
//
//    /**
//     * Create an updated entity for this test.
//     * <p>
//     * This is a static method, as tests for other entities might also need it,
//     * if they test an entity which requires the current entity.
//     */
//    public static Categorie createUpdatedEntity(EntityManager em) {
//        Categorie categorie = new Categorie().name(UPDATED_NAME);
//        return categorie;
//    }
//
//    @BeforeEach
//    public void initTest() {
//        AuthUser authUser = new AuthUser(login, password);
//        token = kernelService.Authorize(authUser);
//        categorie = createEntity(em);
//    }
//
//    @Test
//    @Transactional
//    void createCategorie() throws Exception {
//        int databaseSizeBeforeCreate = categorieRepository.findAll().size();
//        // Create the Categorie
//        CategorieDTO categorieDTO = categorieMapper.toDto(categorie);
//        restCategorieMockMvc
//                .perform(post(ENTITY_API_URL).
//                        header(SecurityConstants.HEADER_STRING_AUTHORIZATION, token).header(SecurityConstants.HEADER_STRING_APPLICATION, application)
//                        .contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(categorieDTO)))
//                .andExpect(status().isCreated());
//
//        // Validate the Categorie in the database
//        List<Categorie> categorieList = categorieRepository.findAll();
//        assertThat(categorieList).hasSize(databaseSizeBeforeCreate + 1);
//        Categorie testCategorie = categorieList.get(categorieList.size() - 1);
//        assertThat(testCategorie.getName()).isEqualTo(DEFAULT_NAME);
//    }
//
//    @Test
//    @Transactional
//    void createCategorieWithExistingId() throws Exception {
//        // Create the Categorie with an existing ID
//        categorie.setId(1L);
//        CategorieDTO categorieDTO = categorieMapper.toDto(categorie);
//
//        int databaseSizeBeforeCreate = categorieRepository.findAll().size();
//
//        // An entity with an existing ID cannot be created, so this API call must fail
//        restCategorieMockMvc
//                .perform(post(ENTITY_API_URL).
//                        header(SecurityConstants.HEADER_STRING_AUTHORIZATION, token).header(SecurityConstants.HEADER_STRING_APPLICATION, application).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(categorieDTO)))
//                .andExpect(status().isBadRequest());
//
//        // Validate the Categorie in the database
//        List<Categorie> categorieList = categorieRepository.findAll();
//        assertThat(categorieList).hasSize(databaseSizeBeforeCreate);
//    }
//
//    @Test
//    @Transactional
//    void checkNameIsRequired() throws Exception {
//        int databaseSizeBeforeTest = categorieRepository.findAll().size();
//        // set the field null
//        categorie.setName(null);
//
//        // Create the Categorie, which fails.
//        CategorieDTO categorieDTO = categorieMapper.toDto(categorie);
//
//        restCategorieMockMvc
//                .perform(post(ENTITY_API_URL).
//                        header(SecurityConstants.HEADER_STRING_AUTHORIZATION, token).header(SecurityConstants.HEADER_STRING_APPLICATION, application).contentType(MediaType.APPLICATION_JSON)
//                        .content(TestUtil.convertObjectToJsonBytes(categorieDTO)))
//                .andExpect(status().isBadRequest());
//
//        List<Categorie> categorieList = categorieRepository.findAll();
//        assertThat(categorieList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    void getAllCategories() throws Exception {
//        // Initialize the database
//        categorieRepository.saveAndFlush(categorie);
//
//        // Get all the categorieList
//        restCategorieMockMvc
//                .perform(get(ENTITY_API_URL + "?sort=id,desc").
//                        header(SecurityConstants.HEADER_STRING_AUTHORIZATION, token).header(SecurityConstants.HEADER_STRING_APPLICATION, application).contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//                .andExpect(jsonPath("$.[*].id").value(hasItem(categorie.getId().intValue())))
//                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
//    }
//
//    @Test
//    @Transactional
//    void getCategorie() throws Exception {
//        // Initialize the database
//        categorieRepository.saveAndFlush(categorie);
//
//        // Get the categorie
//        restCategorieMockMvc
//                .perform(get(ENTITY_API_URL_ID, categorie.getId()). header(SecurityConstants.HEADER_STRING_AUTHORIZATION, token).header(SecurityConstants.HEADER_STRING_APPLICATION, application).contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//                .andExpect(jsonPath("$.id").value(categorie.getId().intValue()))
//                .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
//    }
//
//    @Test
//    @Transactional
//    void getCategoriesByIdFiltering() throws Exception {
//        // Initialize the database
//        categorieRepository.saveAndFlush(categorie);
//
//        Long id = categorie.getId();
//
//        defaultCategorieShouldBeFound("id.equals=" + id);
//        defaultCategorieShouldNotBeFound("id.notEquals=" + id);
//
//        defaultCategorieShouldBeFound("id.greaterThanOrEqual=" + id);
//        defaultCategorieShouldNotBeFound("id.greaterThan=" + id);
//
//        defaultCategorieShouldBeFound("id.lessThanOrEqual=" + id);
//        defaultCategorieShouldNotBeFound("id.lessThan=" + id);
//    }
//
//    @Test
//    @Transactional
//    void getAllCategoriesByNameIsEqualToSomething() throws Exception {
//        // Initialize the database
//        categorieRepository.saveAndFlush(categorie);
//
//        // Get all the categorieList where name equals to DEFAULT_NAME
//        defaultCategorieShouldBeFound("name.equals=" + DEFAULT_NAME);
//
//        // Get all the categorieList where name equals to UPDATED_NAME
//        defaultCategorieShouldNotBeFound("name.equals=" + UPDATED_NAME);
//    }
//
//    @Test
//    @Transactional
//    void getAllCategoriesByNameIsInShouldWork() throws Exception {
//        // Initialize the database
//        categorieRepository.saveAndFlush(categorie);
//
//        // Get all the categorieList where name in DEFAULT_NAME or UPDATED_NAME
//        defaultCategorieShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);
//
//        // Get all the categorieList where name equals to UPDATED_NAME
//        defaultCategorieShouldNotBeFound("name.in=" + UPDATED_NAME);
//    }
//
//    @Test
//    @Transactional
//    void getAllCategoriesByNameIsNullOrNotNull() throws Exception {
//        // Initialize the database
//        categorieRepository.saveAndFlush(categorie);
//
//        // Get all the categorieList where name is not null
//        defaultCategorieShouldBeFound("name.specified=true");
//
//        // Get all the categorieList where name is null
//        defaultCategorieShouldNotBeFound("name.specified=false");
//    }
//
//    @Test
//    @Transactional
//    void getAllCategoriesByNameContainsSomething() throws Exception {
//        // Initialize the database
//        categorieRepository.saveAndFlush(categorie);
//
//        // Get all the categorieList where name contains DEFAULT_NAME
//        defaultCategorieShouldBeFound("name.contains=" + DEFAULT_NAME);
//
//        // Get all the categorieList where name contains UPDATED_NAME
//        defaultCategorieShouldNotBeFound("name.contains=" + UPDATED_NAME);
//    }
//
//    @Test
//    @Transactional
//    void getAllCategoriesByNameNotContainsSomething() throws Exception {
//        // Initialize the database
//        categorieRepository.saveAndFlush(categorie);
//
//        // Get all the categorieList where name does not contain DEFAULT_NAME
//        defaultCategorieShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);
//
//        // Get all the categorieList where name does not contain UPDATED_NAME
//        defaultCategorieShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
//    }
//
//    @Test
//    @Transactional
//    void getAllCategoriesByTarifIsEqualToSomething() throws Exception {
//        Tarif tarif;
//        if (TestUtil.findAll(em, Tarif.class).isEmpty()) {
//            categorieRepository.saveAndFlush(categorie);
//            tarif = TarifResourceIT.createEntity(em);
//        } else {
//            tarif = TestUtil.findAll(em, Tarif.class).get(0);
//        }
//        em.persist(tarif);
//        em.flush();
//        categorie.setTarif(tarif);
//        categorieRepository.saveAndFlush(categorie);
//        Long tarifId = tarif.getId();
//
//        // Get all the categorieList where tarif equals to tarifId
//        defaultCategorieShouldBeFound("tarifId.equals=" + tarifId);
//
//        // Get all the categorieList where tarif equals to (tarifId + 1)
//        defaultCategorieShouldNotBeFound("tarifId.equals=" + (tarifId + 1));
//    }
//
//    /**
//     * Executes the search, and checks that the default entity is returned.
//     */
//    private void defaultCategorieShouldBeFound(String filter) throws Exception {
//        restCategorieMockMvc
//                .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter). header(SecurityConstants.HEADER_STRING_AUTHORIZATION, token).header(SecurityConstants.HEADER_STRING_APPLICATION, application).contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//                .andExpect(jsonPath("$.[*].id").value(hasItem(categorie.getId().intValue())))
//                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
//
//        // Check, that the count call also returns 1
//        restCategorieMockMvc
//                .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter). header(SecurityConstants.HEADER_STRING_AUTHORIZATION, token).header(SecurityConstants.HEADER_STRING_APPLICATION, application).contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//                .andExpect(content().string("1"));
//    }
//
//    /**
//     * Executes the search, and checks that the default entity is not returned.
//     */
//    private void defaultCategorieShouldNotBeFound(String filter) throws Exception {
//        restCategorieMockMvc
//                .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter). header(SecurityConstants.HEADER_STRING_AUTHORIZATION, token).header(SecurityConstants.HEADER_STRING_APPLICATION, application).contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//                .andExpect(jsonPath("$").isArray())
//                .andExpect(jsonPath("$").isEmpty());
//
//        // Check, that the count call also returns 0
//        restCategorieMockMvc
//                .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter). header(SecurityConstants.HEADER_STRING_AUTHORIZATION, token).header(SecurityConstants.HEADER_STRING_APPLICATION, application).contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//                .andExpect(content().string("0"));
//    }
//
//    @Test
//    @Transactional
//    void getNonExistingCategorie() throws Exception {
//        // Get the categorie
//        restCategorieMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE). header(SecurityConstants.HEADER_STRING_AUTHORIZATION, token).header(SecurityConstants.HEADER_STRING_APPLICATION, application).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
//    }
//
//    @Test
//    @Transactional
//    void putExistingCategorie() throws Exception {
//        // Initialize the database
//        categorieRepository.saveAndFlush(categorie);
//
//        int databaseSizeBeforeUpdate = categorieRepository.findAll().size();
//
//        // Update the categorie
//        Categorie updatedCategorie = categorieRepository.findById(categorie.getId()).get();
//        // Disconnect from session so that the updates on updatedCategorie are not directly saved in db
//        em.detach(updatedCategorie);
//        updatedCategorie.name(UPDATED_NAME);
//        CategorieDTO categorieDTO = categorieMapper.toDto(updatedCategorie);
//
//        restCategorieMockMvc
//                .perform(
//                        put(ENTITY_API_URL_ID, categorieDTO.getId()). header(SecurityConstants.HEADER_STRING_AUTHORIZATION, token).header(SecurityConstants.HEADER_STRING_APPLICATION, application)
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .content(TestUtil.convertObjectToJsonBytes(categorieDTO))
//                )
//                .andExpect(status().isOk());
//
//        // Validate the Categorie in the database
//        List<Categorie> categorieList = categorieRepository.findAll();
//        assertThat(categorieList).hasSize(databaseSizeBeforeUpdate);
//        Categorie testCategorie = categorieList.get(categorieList.size() - 1);
//        assertThat(testCategorie.getName()).isEqualTo(UPDATED_NAME);
//    }
//
//    @Test
//    @Transactional
//    void putNonExistingCategorie() throws Exception {
//        int databaseSizeBeforeUpdate = categorieRepository.findAll().size();
//        categorie.setId(count.incrementAndGet());
//
//        // Create the Categorie
//        CategorieDTO categorieDTO = categorieMapper.toDto(categorie);
//
//        // If the entity doesn't have an ID, it will throw BadRequestAlertException
//        restCategorieMockMvc
//                .perform(
//                        put(ENTITY_API_URL_ID, categorieDTO.getId()). header(SecurityConstants.HEADER_STRING_AUTHORIZATION, token).header(SecurityConstants.HEADER_STRING_APPLICATION, application)
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .content(TestUtil.convertObjectToJsonBytes(categorieDTO))
//                )
//                .andExpect(status().isBadRequest());
//
//        // Validate the Categorie in the database
//        List<Categorie> categorieList = categorieRepository.findAll();
//        assertThat(categorieList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void putWithIdMismatchCategorie() throws Exception {
//        int databaseSizeBeforeUpdate = categorieRepository.findAll().size();
//        categorie.setId(count.incrementAndGet());
//
//        // Create the Categorie
//        CategorieDTO categorieDTO = categorieMapper.toDto(categorie);
//
//        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//        restCategorieMockMvc
//                .perform(
//                        put(ENTITY_API_URL_ID, count.incrementAndGet()). header(SecurityConstants.HEADER_STRING_AUTHORIZATION, token).header(SecurityConstants.HEADER_STRING_APPLICATION, application)
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .content(TestUtil.convertObjectToJsonBytes(categorieDTO))
//                )
//                .andExpect(status().isBadRequest());
//
//        // Validate the Categorie in the database
//        List<Categorie> categorieList = categorieRepository.findAll();
//        assertThat(categorieList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void putWithMissingIdPathParamCategorie() throws Exception {
//        int databaseSizeBeforeUpdate = categorieRepository.findAll().size();
//        categorie.setId(count.incrementAndGet());
//
//        // Create the Categorie
//        CategorieDTO categorieDTO = categorieMapper.toDto(categorie);
//
//        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//        restCategorieMockMvc
//                .perform(put(ENTITY_API_URL).
//                        header(SecurityConstants.HEADER_STRING_AUTHORIZATION, token).header(SecurityConstants.HEADER_STRING_APPLICATION, application).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(categorieDTO)))
//                .andExpect(status().isMethodNotAllowed());
//
//        // Validate the Categorie in the database
//        List<Categorie> categorieList = categorieRepository.findAll();
//        assertThat(categorieList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void partialUpdateCategorieWithPatch() throws Exception {
//        // Initialize the database
//        categorieRepository.saveAndFlush(categorie);
//
//        int databaseSizeBeforeUpdate = categorieRepository.findAll().size();
//
//        // Update the categorie using partial update
//        Categorie partialUpdatedCategorie = new Categorie();
//        partialUpdatedCategorie.setId(categorie.getId());
//
//        partialUpdatedCategorie.name(UPDATED_NAME);
//
//        restCategorieMockMvc
//                .perform(
//                        patch(ENTITY_API_URL_ID, partialUpdatedCategorie.getId()). header(SecurityConstants.HEADER_STRING_AUTHORIZATION, token).header(SecurityConstants.HEADER_STRING_APPLICATION, application)
//                                .contentType("application/merge-patch+json")
//                                .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCategorie))
//                )
//                .andExpect(status().isOk());
//
//        // Validate the Categorie in the database
//        List<Categorie> categorieList = categorieRepository.findAll();
//        assertThat(categorieList).hasSize(databaseSizeBeforeUpdate);
//        Categorie testCategorie = categorieList.get(categorieList.size() - 1);
//        assertThat(testCategorie.getName()).isEqualTo(UPDATED_NAME);
//    }
//
//    @Test
//    @Transactional
//    void fullUpdateCategorieWithPatch() throws Exception {
//        // Initialize the database
//        categorieRepository.saveAndFlush(categorie);
//
//        int databaseSizeBeforeUpdate = categorieRepository.findAll().size();
//
//        // Update the categorie using partial update
//        Categorie partialUpdatedCategorie = new Categorie();
//        partialUpdatedCategorie.setId(categorie.getId());
//
//        partialUpdatedCategorie.name(UPDATED_NAME);
//
//        restCategorieMockMvc
//                .perform(
//                        patch(ENTITY_API_URL_ID, partialUpdatedCategorie.getId()). header(SecurityConstants.HEADER_STRING_AUTHORIZATION, token).header(SecurityConstants.HEADER_STRING_APPLICATION, application)
//                                .contentType("application/merge-patch+json")
//                                .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCategorie))
//                )
//                .andExpect(status().isOk());
//
//        // Validate the Categorie in the database
//        List<Categorie> categorieList = categorieRepository.findAll();
//        assertThat(categorieList).hasSize(databaseSizeBeforeUpdate);
//        Categorie testCategorie = categorieList.get(categorieList.size() - 1);
//        assertThat(testCategorie.getName()).isEqualTo(UPDATED_NAME);
//    }
//
//    @Test
//    @Transactional
//    void patchNonExistingCategorie() throws Exception {
//        int databaseSizeBeforeUpdate = categorieRepository.findAll().size();
//        categorie.setId(count.incrementAndGet());
//
//        // Create the Categorie
//        CategorieDTO categorieDTO = categorieMapper.toDto(categorie);
//
//        // If the entity doesn't have an ID, it will throw BadRequestAlertException
//        restCategorieMockMvc
//                .perform(
//                        patch(ENTITY_API_URL_ID, categorieDTO.getId()). header(SecurityConstants.HEADER_STRING_AUTHORIZATION, token).header(SecurityConstants.HEADER_STRING_APPLICATION, application)
//                                .contentType("application/merge-patch+json")
//                                .content(TestUtil.convertObjectToJsonBytes(categorieDTO))
//                )
//                .andExpect(status().isBadRequest());
//
//        // Validate the Categorie in the database
//        List<Categorie> categorieList = categorieRepository.findAll();
//        assertThat(categorieList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void patchWithIdMismatchCategorie() throws Exception {
//        int databaseSizeBeforeUpdate = categorieRepository.findAll().size();
//        categorie.setId(count.incrementAndGet());
//
//        // Create the Categorie
//        CategorieDTO categorieDTO = categorieMapper.toDto(categorie);
//
//        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//        restCategorieMockMvc
//                .perform(
//                        patch(ENTITY_API_URL_ID, count.incrementAndGet()). header(SecurityConstants.HEADER_STRING_AUTHORIZATION, token).header(SecurityConstants.HEADER_STRING_APPLICATION, application)
//                                .contentType("application/merge-patch+json")
//                                .content(TestUtil.convertObjectToJsonBytes(categorieDTO))
//                )
//                .andExpect(status().isBadRequest());
//
//        // Validate the Categorie in the database
//        List<Categorie> categorieList = categorieRepository.findAll();
//        assertThat(categorieList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void patchWithMissingIdPathParamCategorie() throws Exception {
//        int databaseSizeBeforeUpdate = categorieRepository.findAll().size();
//        categorie.setId(count.incrementAndGet());
//
//        // Create the Categorie
//        CategorieDTO categorieDTO = categorieMapper.toDto(categorie);
//
//        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//        restCategorieMockMvc
//                .perform(
//                        patch(ENTITY_API_URL).
//                                header(SecurityConstants.HEADER_STRING_AUTHORIZATION, token).header(SecurityConstants.HEADER_STRING_APPLICATION, application).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(categorieDTO))
//                )
//                .andExpect(status().isMethodNotAllowed());
//
//        // Validate the Categorie in the database
//        List<Categorie> categorieList = categorieRepository.findAll();
//        assertThat(categorieList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void deleteCategorie() throws Exception {
//        // Initialize the database
//        categorieRepository.saveAndFlush(categorie);
//
//        int databaseSizeBeforeDelete = categorieRepository.findAll().size();
//
//        // Delete the categorie
//        restCategorieMockMvc
//                .perform(delete(ENTITY_API_URL_ID, categorie.getId()). header(SecurityConstants.HEADER_STRING_AUTHORIZATION, token).header(SecurityConstants.HEADER_STRING_APPLICATION, application).accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isNoContent());
//
//        // Validate the database contains one less item
//        List<Categorie> categorieList = categorieRepository.findAll();
//        assertThat(categorieList).hasSize(databaseSizeBeforeDelete - 1);
//    }
//}
