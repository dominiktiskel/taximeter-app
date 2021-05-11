package com.tiskel.taximeterapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.tiskel.taximeterapp.IntegrationTest;
import com.tiskel.taximeterapp.domain.TaximeterFormula;
import com.tiskel.taximeterapp.domain.enumeration.TaximeterFormulaType;
import com.tiskel.taximeterapp.repository.TaximeterFormulaRepository;
import com.tiskel.taximeterapp.service.dto.TaximeterFormulaDTO;
import com.tiskel.taximeterapp.service.mapper.TaximeterFormulaMapper;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link TaximeterFormulaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TaximeterFormulaResourceIT {

    private static final TaximeterFormulaType DEFAULT_TYPE = TaximeterFormulaType.DISTANCE;
    private static final TaximeterFormulaType UPDATED_TYPE = TaximeterFormulaType.TIME;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final String ENTITY_API_URL = "/api/taximeter-formulas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TaximeterFormulaRepository taximeterFormulaRepository;

    @Autowired
    private TaximeterFormulaMapper taximeterFormulaMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTaximeterFormulaMockMvc;

    private TaximeterFormula taximeterFormula;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TaximeterFormula createEntity(EntityManager em) {
        TaximeterFormula taximeterFormula = new TaximeterFormula().type(DEFAULT_TYPE).name(DEFAULT_NAME).active(DEFAULT_ACTIVE);
        return taximeterFormula;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TaximeterFormula createUpdatedEntity(EntityManager em) {
        TaximeterFormula taximeterFormula = new TaximeterFormula().type(UPDATED_TYPE).name(UPDATED_NAME).active(UPDATED_ACTIVE);
        return taximeterFormula;
    }

    @BeforeEach
    public void initTest() {
        taximeterFormula = createEntity(em);
    }

    @Test
    @Transactional
    void createTaximeterFormula() throws Exception {
        int databaseSizeBeforeCreate = taximeterFormulaRepository.findAll().size();
        // Create the TaximeterFormula
        TaximeterFormulaDTO taximeterFormulaDTO = taximeterFormulaMapper.toDto(taximeterFormula);
        restTaximeterFormulaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(taximeterFormulaDTO))
            )
            .andExpect(status().isCreated());

        // Validate the TaximeterFormula in the database
        List<TaximeterFormula> taximeterFormulaList = taximeterFormulaRepository.findAll();
        assertThat(taximeterFormulaList).hasSize(databaseSizeBeforeCreate + 1);
        TaximeterFormula testTaximeterFormula = taximeterFormulaList.get(taximeterFormulaList.size() - 1);
        assertThat(testTaximeterFormula.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testTaximeterFormula.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTaximeterFormula.getActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    void createTaximeterFormulaWithExistingId() throws Exception {
        // Create the TaximeterFormula with an existing ID
        taximeterFormula.setId(1L);
        TaximeterFormulaDTO taximeterFormulaDTO = taximeterFormulaMapper.toDto(taximeterFormula);

        int databaseSizeBeforeCreate = taximeterFormulaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTaximeterFormulaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(taximeterFormulaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TaximeterFormula in the database
        List<TaximeterFormula> taximeterFormulaList = taximeterFormulaRepository.findAll();
        assertThat(taximeterFormulaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = taximeterFormulaRepository.findAll().size();
        // set the field null
        taximeterFormula.setType(null);

        // Create the TaximeterFormula, which fails.
        TaximeterFormulaDTO taximeterFormulaDTO = taximeterFormulaMapper.toDto(taximeterFormula);

        restTaximeterFormulaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(taximeterFormulaDTO))
            )
            .andExpect(status().isBadRequest());

        List<TaximeterFormula> taximeterFormulaList = taximeterFormulaRepository.findAll();
        assertThat(taximeterFormulaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = taximeterFormulaRepository.findAll().size();
        // set the field null
        taximeterFormula.setName(null);

        // Create the TaximeterFormula, which fails.
        TaximeterFormulaDTO taximeterFormulaDTO = taximeterFormulaMapper.toDto(taximeterFormula);

        restTaximeterFormulaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(taximeterFormulaDTO))
            )
            .andExpect(status().isBadRequest());

        List<TaximeterFormula> taximeterFormulaList = taximeterFormulaRepository.findAll();
        assertThat(taximeterFormulaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = taximeterFormulaRepository.findAll().size();
        // set the field null
        taximeterFormula.setActive(null);

        // Create the TaximeterFormula, which fails.
        TaximeterFormulaDTO taximeterFormulaDTO = taximeterFormulaMapper.toDto(taximeterFormula);

        restTaximeterFormulaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(taximeterFormulaDTO))
            )
            .andExpect(status().isBadRequest());

        List<TaximeterFormula> taximeterFormulaList = taximeterFormulaRepository.findAll();
        assertThat(taximeterFormulaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTaximeterFormulas() throws Exception {
        // Initialize the database
        taximeterFormulaRepository.saveAndFlush(taximeterFormula);

        // Get all the taximeterFormulaList
        restTaximeterFormulaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(taximeterFormula.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    void getTaximeterFormula() throws Exception {
        // Initialize the database
        taximeterFormulaRepository.saveAndFlush(taximeterFormula);

        // Get the taximeterFormula
        restTaximeterFormulaMockMvc
            .perform(get(ENTITY_API_URL_ID, taximeterFormula.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(taximeterFormula.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingTaximeterFormula() throws Exception {
        // Get the taximeterFormula
        restTaximeterFormulaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTaximeterFormula() throws Exception {
        // Initialize the database
        taximeterFormulaRepository.saveAndFlush(taximeterFormula);

        int databaseSizeBeforeUpdate = taximeterFormulaRepository.findAll().size();

        // Update the taximeterFormula
        TaximeterFormula updatedTaximeterFormula = taximeterFormulaRepository.findById(taximeterFormula.getId()).get();
        // Disconnect from session so that the updates on updatedTaximeterFormula are not directly saved in db
        em.detach(updatedTaximeterFormula);
        updatedTaximeterFormula.type(UPDATED_TYPE).name(UPDATED_NAME).active(UPDATED_ACTIVE);
        TaximeterFormulaDTO taximeterFormulaDTO = taximeterFormulaMapper.toDto(updatedTaximeterFormula);

        restTaximeterFormulaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, taximeterFormulaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(taximeterFormulaDTO))
            )
            .andExpect(status().isOk());

        // Validate the TaximeterFormula in the database
        List<TaximeterFormula> taximeterFormulaList = taximeterFormulaRepository.findAll();
        assertThat(taximeterFormulaList).hasSize(databaseSizeBeforeUpdate);
        TaximeterFormula testTaximeterFormula = taximeterFormulaList.get(taximeterFormulaList.size() - 1);
        assertThat(testTaximeterFormula.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testTaximeterFormula.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTaximeterFormula.getActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void putNonExistingTaximeterFormula() throws Exception {
        int databaseSizeBeforeUpdate = taximeterFormulaRepository.findAll().size();
        taximeterFormula.setId(count.incrementAndGet());

        // Create the TaximeterFormula
        TaximeterFormulaDTO taximeterFormulaDTO = taximeterFormulaMapper.toDto(taximeterFormula);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTaximeterFormulaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, taximeterFormulaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(taximeterFormulaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TaximeterFormula in the database
        List<TaximeterFormula> taximeterFormulaList = taximeterFormulaRepository.findAll();
        assertThat(taximeterFormulaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTaximeterFormula() throws Exception {
        int databaseSizeBeforeUpdate = taximeterFormulaRepository.findAll().size();
        taximeterFormula.setId(count.incrementAndGet());

        // Create the TaximeterFormula
        TaximeterFormulaDTO taximeterFormulaDTO = taximeterFormulaMapper.toDto(taximeterFormula);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaximeterFormulaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(taximeterFormulaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TaximeterFormula in the database
        List<TaximeterFormula> taximeterFormulaList = taximeterFormulaRepository.findAll();
        assertThat(taximeterFormulaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTaximeterFormula() throws Exception {
        int databaseSizeBeforeUpdate = taximeterFormulaRepository.findAll().size();
        taximeterFormula.setId(count.incrementAndGet());

        // Create the TaximeterFormula
        TaximeterFormulaDTO taximeterFormulaDTO = taximeterFormulaMapper.toDto(taximeterFormula);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaximeterFormulaMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(taximeterFormulaDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TaximeterFormula in the database
        List<TaximeterFormula> taximeterFormulaList = taximeterFormulaRepository.findAll();
        assertThat(taximeterFormulaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTaximeterFormulaWithPatch() throws Exception {
        // Initialize the database
        taximeterFormulaRepository.saveAndFlush(taximeterFormula);

        int databaseSizeBeforeUpdate = taximeterFormulaRepository.findAll().size();

        // Update the taximeterFormula using partial update
        TaximeterFormula partialUpdatedTaximeterFormula = new TaximeterFormula();
        partialUpdatedTaximeterFormula.setId(taximeterFormula.getId());

        partialUpdatedTaximeterFormula.active(UPDATED_ACTIVE);

        restTaximeterFormulaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTaximeterFormula.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTaximeterFormula))
            )
            .andExpect(status().isOk());

        // Validate the TaximeterFormula in the database
        List<TaximeterFormula> taximeterFormulaList = taximeterFormulaRepository.findAll();
        assertThat(taximeterFormulaList).hasSize(databaseSizeBeforeUpdate);
        TaximeterFormula testTaximeterFormula = taximeterFormulaList.get(taximeterFormulaList.size() - 1);
        assertThat(testTaximeterFormula.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testTaximeterFormula.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTaximeterFormula.getActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void fullUpdateTaximeterFormulaWithPatch() throws Exception {
        // Initialize the database
        taximeterFormulaRepository.saveAndFlush(taximeterFormula);

        int databaseSizeBeforeUpdate = taximeterFormulaRepository.findAll().size();

        // Update the taximeterFormula using partial update
        TaximeterFormula partialUpdatedTaximeterFormula = new TaximeterFormula();
        partialUpdatedTaximeterFormula.setId(taximeterFormula.getId());

        partialUpdatedTaximeterFormula.type(UPDATED_TYPE).name(UPDATED_NAME).active(UPDATED_ACTIVE);

        restTaximeterFormulaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTaximeterFormula.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTaximeterFormula))
            )
            .andExpect(status().isOk());

        // Validate the TaximeterFormula in the database
        List<TaximeterFormula> taximeterFormulaList = taximeterFormulaRepository.findAll();
        assertThat(taximeterFormulaList).hasSize(databaseSizeBeforeUpdate);
        TaximeterFormula testTaximeterFormula = taximeterFormulaList.get(taximeterFormulaList.size() - 1);
        assertThat(testTaximeterFormula.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testTaximeterFormula.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTaximeterFormula.getActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void patchNonExistingTaximeterFormula() throws Exception {
        int databaseSizeBeforeUpdate = taximeterFormulaRepository.findAll().size();
        taximeterFormula.setId(count.incrementAndGet());

        // Create the TaximeterFormula
        TaximeterFormulaDTO taximeterFormulaDTO = taximeterFormulaMapper.toDto(taximeterFormula);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTaximeterFormulaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, taximeterFormulaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(taximeterFormulaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TaximeterFormula in the database
        List<TaximeterFormula> taximeterFormulaList = taximeterFormulaRepository.findAll();
        assertThat(taximeterFormulaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTaximeterFormula() throws Exception {
        int databaseSizeBeforeUpdate = taximeterFormulaRepository.findAll().size();
        taximeterFormula.setId(count.incrementAndGet());

        // Create the TaximeterFormula
        TaximeterFormulaDTO taximeterFormulaDTO = taximeterFormulaMapper.toDto(taximeterFormula);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaximeterFormulaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(taximeterFormulaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TaximeterFormula in the database
        List<TaximeterFormula> taximeterFormulaList = taximeterFormulaRepository.findAll();
        assertThat(taximeterFormulaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTaximeterFormula() throws Exception {
        int databaseSizeBeforeUpdate = taximeterFormulaRepository.findAll().size();
        taximeterFormula.setId(count.incrementAndGet());

        // Create the TaximeterFormula
        TaximeterFormulaDTO taximeterFormulaDTO = taximeterFormulaMapper.toDto(taximeterFormula);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaximeterFormulaMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(taximeterFormulaDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TaximeterFormula in the database
        List<TaximeterFormula> taximeterFormulaList = taximeterFormulaRepository.findAll();
        assertThat(taximeterFormulaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTaximeterFormula() throws Exception {
        // Initialize the database
        taximeterFormulaRepository.saveAndFlush(taximeterFormula);

        int databaseSizeBeforeDelete = taximeterFormulaRepository.findAll().size();

        // Delete the taximeterFormula
        restTaximeterFormulaMockMvc
            .perform(delete(ENTITY_API_URL_ID, taximeterFormula.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TaximeterFormula> taximeterFormulaList = taximeterFormulaRepository.findAll();
        assertThat(taximeterFormulaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
