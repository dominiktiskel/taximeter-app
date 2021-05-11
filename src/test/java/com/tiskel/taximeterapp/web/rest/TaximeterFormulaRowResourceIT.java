package com.tiskel.taximeterapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.tiskel.taximeterapp.IntegrationTest;
import com.tiskel.taximeterapp.domain.TaximeterFormulaRow;
import com.tiskel.taximeterapp.domain.enumeration.TaximeterFormulaRowType;
import com.tiskel.taximeterapp.repository.TaximeterFormulaRowRepository;
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
 * Integration tests for the {@link TaximeterFormulaRowResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TaximeterFormulaRowResourceIT {

    private static final TaximeterFormulaRowType DEFAULT_TYPE = TaximeterFormulaRowType.FLAT_RATE;
    private static final TaximeterFormulaRowType UPDATED_TYPE = TaximeterFormulaRowType.NUMBER_OF;

    private static final Double DEFAULT_VALUE = 1D;
    private static final Double UPDATED_VALUE = 2D;

    private static final Double DEFAULT_STEP = 1D;
    private static final Double UPDATED_STEP = 2D;

    private static final Double DEFAULT_GRANULATION = 1D;
    private static final Double UPDATED_GRANULATION = 2D;

    private static final String ENTITY_API_URL = "/api/taximeter-formula-rows";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TaximeterFormulaRowRepository taximeterFormulaRowRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTaximeterFormulaRowMockMvc;

    private TaximeterFormulaRow taximeterFormulaRow;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TaximeterFormulaRow createEntity(EntityManager em) {
        TaximeterFormulaRow taximeterFormulaRow = new TaximeterFormulaRow()
            .type(DEFAULT_TYPE)
            .value(DEFAULT_VALUE)
            .step(DEFAULT_STEP)
            .granulation(DEFAULT_GRANULATION);
        return taximeterFormulaRow;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TaximeterFormulaRow createUpdatedEntity(EntityManager em) {
        TaximeterFormulaRow taximeterFormulaRow = new TaximeterFormulaRow()
            .type(UPDATED_TYPE)
            .value(UPDATED_VALUE)
            .step(UPDATED_STEP)
            .granulation(UPDATED_GRANULATION);
        return taximeterFormulaRow;
    }

    @BeforeEach
    public void initTest() {
        taximeterFormulaRow = createEntity(em);
    }

    @Test
    @Transactional
    void createTaximeterFormulaRow() throws Exception {
        int databaseSizeBeforeCreate = taximeterFormulaRowRepository.findAll().size();
        // Create the TaximeterFormulaRow
        restTaximeterFormulaRowMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(taximeterFormulaRow))
            )
            .andExpect(status().isCreated());

        // Validate the TaximeterFormulaRow in the database
        List<TaximeterFormulaRow> taximeterFormulaRowList = taximeterFormulaRowRepository.findAll();
        assertThat(taximeterFormulaRowList).hasSize(databaseSizeBeforeCreate + 1);
        TaximeterFormulaRow testTaximeterFormulaRow = taximeterFormulaRowList.get(taximeterFormulaRowList.size() - 1);
        assertThat(testTaximeterFormulaRow.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testTaximeterFormulaRow.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testTaximeterFormulaRow.getStep()).isEqualTo(DEFAULT_STEP);
        assertThat(testTaximeterFormulaRow.getGranulation()).isEqualTo(DEFAULT_GRANULATION);
    }

    @Test
    @Transactional
    void createTaximeterFormulaRowWithExistingId() throws Exception {
        // Create the TaximeterFormulaRow with an existing ID
        taximeterFormulaRow.setId(1L);

        int databaseSizeBeforeCreate = taximeterFormulaRowRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTaximeterFormulaRowMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(taximeterFormulaRow))
            )
            .andExpect(status().isBadRequest());

        // Validate the TaximeterFormulaRow in the database
        List<TaximeterFormulaRow> taximeterFormulaRowList = taximeterFormulaRowRepository.findAll();
        assertThat(taximeterFormulaRowList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = taximeterFormulaRowRepository.findAll().size();
        // set the field null
        taximeterFormulaRow.setType(null);

        // Create the TaximeterFormulaRow, which fails.

        restTaximeterFormulaRowMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(taximeterFormulaRow))
            )
            .andExpect(status().isBadRequest());

        List<TaximeterFormulaRow> taximeterFormulaRowList = taximeterFormulaRowRepository.findAll();
        assertThat(taximeterFormulaRowList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = taximeterFormulaRowRepository.findAll().size();
        // set the field null
        taximeterFormulaRow.setValue(null);

        // Create the TaximeterFormulaRow, which fails.

        restTaximeterFormulaRowMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(taximeterFormulaRow))
            )
            .andExpect(status().isBadRequest());

        List<TaximeterFormulaRow> taximeterFormulaRowList = taximeterFormulaRowRepository.findAll();
        assertThat(taximeterFormulaRowList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStepIsRequired() throws Exception {
        int databaseSizeBeforeTest = taximeterFormulaRowRepository.findAll().size();
        // set the field null
        taximeterFormulaRow.setStep(null);

        // Create the TaximeterFormulaRow, which fails.

        restTaximeterFormulaRowMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(taximeterFormulaRow))
            )
            .andExpect(status().isBadRequest());

        List<TaximeterFormulaRow> taximeterFormulaRowList = taximeterFormulaRowRepository.findAll();
        assertThat(taximeterFormulaRowList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTaximeterFormulaRows() throws Exception {
        // Initialize the database
        taximeterFormulaRowRepository.saveAndFlush(taximeterFormulaRow);

        // Get all the taximeterFormulaRowList
        restTaximeterFormulaRowMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(taximeterFormulaRow.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].step").value(hasItem(DEFAULT_STEP.doubleValue())))
            .andExpect(jsonPath("$.[*].granulation").value(hasItem(DEFAULT_GRANULATION.doubleValue())));
    }

    @Test
    @Transactional
    void getTaximeterFormulaRow() throws Exception {
        // Initialize the database
        taximeterFormulaRowRepository.saveAndFlush(taximeterFormulaRow);

        // Get the taximeterFormulaRow
        restTaximeterFormulaRowMockMvc
            .perform(get(ENTITY_API_URL_ID, taximeterFormulaRow.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(taximeterFormulaRow.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.doubleValue()))
            .andExpect(jsonPath("$.step").value(DEFAULT_STEP.doubleValue()))
            .andExpect(jsonPath("$.granulation").value(DEFAULT_GRANULATION.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingTaximeterFormulaRow() throws Exception {
        // Get the taximeterFormulaRow
        restTaximeterFormulaRowMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTaximeterFormulaRow() throws Exception {
        // Initialize the database
        taximeterFormulaRowRepository.saveAndFlush(taximeterFormulaRow);

        int databaseSizeBeforeUpdate = taximeterFormulaRowRepository.findAll().size();

        // Update the taximeterFormulaRow
        TaximeterFormulaRow updatedTaximeterFormulaRow = taximeterFormulaRowRepository.findById(taximeterFormulaRow.getId()).get();
        // Disconnect from session so that the updates on updatedTaximeterFormulaRow are not directly saved in db
        em.detach(updatedTaximeterFormulaRow);
        updatedTaximeterFormulaRow.type(UPDATED_TYPE).value(UPDATED_VALUE).step(UPDATED_STEP).granulation(UPDATED_GRANULATION);

        restTaximeterFormulaRowMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTaximeterFormulaRow.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTaximeterFormulaRow))
            )
            .andExpect(status().isOk());

        // Validate the TaximeterFormulaRow in the database
        List<TaximeterFormulaRow> taximeterFormulaRowList = taximeterFormulaRowRepository.findAll();
        assertThat(taximeterFormulaRowList).hasSize(databaseSizeBeforeUpdate);
        TaximeterFormulaRow testTaximeterFormulaRow = taximeterFormulaRowList.get(taximeterFormulaRowList.size() - 1);
        assertThat(testTaximeterFormulaRow.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testTaximeterFormulaRow.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testTaximeterFormulaRow.getStep()).isEqualTo(UPDATED_STEP);
        assertThat(testTaximeterFormulaRow.getGranulation()).isEqualTo(UPDATED_GRANULATION);
    }

    @Test
    @Transactional
    void putNonExistingTaximeterFormulaRow() throws Exception {
        int databaseSizeBeforeUpdate = taximeterFormulaRowRepository.findAll().size();
        taximeterFormulaRow.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTaximeterFormulaRowMockMvc
            .perform(
                put(ENTITY_API_URL_ID, taximeterFormulaRow.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(taximeterFormulaRow))
            )
            .andExpect(status().isBadRequest());

        // Validate the TaximeterFormulaRow in the database
        List<TaximeterFormulaRow> taximeterFormulaRowList = taximeterFormulaRowRepository.findAll();
        assertThat(taximeterFormulaRowList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTaximeterFormulaRow() throws Exception {
        int databaseSizeBeforeUpdate = taximeterFormulaRowRepository.findAll().size();
        taximeterFormulaRow.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaximeterFormulaRowMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(taximeterFormulaRow))
            )
            .andExpect(status().isBadRequest());

        // Validate the TaximeterFormulaRow in the database
        List<TaximeterFormulaRow> taximeterFormulaRowList = taximeterFormulaRowRepository.findAll();
        assertThat(taximeterFormulaRowList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTaximeterFormulaRow() throws Exception {
        int databaseSizeBeforeUpdate = taximeterFormulaRowRepository.findAll().size();
        taximeterFormulaRow.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaximeterFormulaRowMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(taximeterFormulaRow))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TaximeterFormulaRow in the database
        List<TaximeterFormulaRow> taximeterFormulaRowList = taximeterFormulaRowRepository.findAll();
        assertThat(taximeterFormulaRowList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTaximeterFormulaRowWithPatch() throws Exception {
        // Initialize the database
        taximeterFormulaRowRepository.saveAndFlush(taximeterFormulaRow);

        int databaseSizeBeforeUpdate = taximeterFormulaRowRepository.findAll().size();

        // Update the taximeterFormulaRow using partial update
        TaximeterFormulaRow partialUpdatedTaximeterFormulaRow = new TaximeterFormulaRow();
        partialUpdatedTaximeterFormulaRow.setId(taximeterFormulaRow.getId());

        partialUpdatedTaximeterFormulaRow.type(UPDATED_TYPE).value(UPDATED_VALUE).step(UPDATED_STEP).granulation(UPDATED_GRANULATION);

        restTaximeterFormulaRowMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTaximeterFormulaRow.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTaximeterFormulaRow))
            )
            .andExpect(status().isOk());

        // Validate the TaximeterFormulaRow in the database
        List<TaximeterFormulaRow> taximeterFormulaRowList = taximeterFormulaRowRepository.findAll();
        assertThat(taximeterFormulaRowList).hasSize(databaseSizeBeforeUpdate);
        TaximeterFormulaRow testTaximeterFormulaRow = taximeterFormulaRowList.get(taximeterFormulaRowList.size() - 1);
        assertThat(testTaximeterFormulaRow.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testTaximeterFormulaRow.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testTaximeterFormulaRow.getStep()).isEqualTo(UPDATED_STEP);
        assertThat(testTaximeterFormulaRow.getGranulation()).isEqualTo(UPDATED_GRANULATION);
    }

    @Test
    @Transactional
    void fullUpdateTaximeterFormulaRowWithPatch() throws Exception {
        // Initialize the database
        taximeterFormulaRowRepository.saveAndFlush(taximeterFormulaRow);

        int databaseSizeBeforeUpdate = taximeterFormulaRowRepository.findAll().size();

        // Update the taximeterFormulaRow using partial update
        TaximeterFormulaRow partialUpdatedTaximeterFormulaRow = new TaximeterFormulaRow();
        partialUpdatedTaximeterFormulaRow.setId(taximeterFormulaRow.getId());

        partialUpdatedTaximeterFormulaRow.type(UPDATED_TYPE).value(UPDATED_VALUE).step(UPDATED_STEP).granulation(UPDATED_GRANULATION);

        restTaximeterFormulaRowMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTaximeterFormulaRow.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTaximeterFormulaRow))
            )
            .andExpect(status().isOk());

        // Validate the TaximeterFormulaRow in the database
        List<TaximeterFormulaRow> taximeterFormulaRowList = taximeterFormulaRowRepository.findAll();
        assertThat(taximeterFormulaRowList).hasSize(databaseSizeBeforeUpdate);
        TaximeterFormulaRow testTaximeterFormulaRow = taximeterFormulaRowList.get(taximeterFormulaRowList.size() - 1);
        assertThat(testTaximeterFormulaRow.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testTaximeterFormulaRow.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testTaximeterFormulaRow.getStep()).isEqualTo(UPDATED_STEP);
        assertThat(testTaximeterFormulaRow.getGranulation()).isEqualTo(UPDATED_GRANULATION);
    }

    @Test
    @Transactional
    void patchNonExistingTaximeterFormulaRow() throws Exception {
        int databaseSizeBeforeUpdate = taximeterFormulaRowRepository.findAll().size();
        taximeterFormulaRow.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTaximeterFormulaRowMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, taximeterFormulaRow.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(taximeterFormulaRow))
            )
            .andExpect(status().isBadRequest());

        // Validate the TaximeterFormulaRow in the database
        List<TaximeterFormulaRow> taximeterFormulaRowList = taximeterFormulaRowRepository.findAll();
        assertThat(taximeterFormulaRowList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTaximeterFormulaRow() throws Exception {
        int databaseSizeBeforeUpdate = taximeterFormulaRowRepository.findAll().size();
        taximeterFormulaRow.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaximeterFormulaRowMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(taximeterFormulaRow))
            )
            .andExpect(status().isBadRequest());

        // Validate the TaximeterFormulaRow in the database
        List<TaximeterFormulaRow> taximeterFormulaRowList = taximeterFormulaRowRepository.findAll();
        assertThat(taximeterFormulaRowList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTaximeterFormulaRow() throws Exception {
        int databaseSizeBeforeUpdate = taximeterFormulaRowRepository.findAll().size();
        taximeterFormulaRow.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaximeterFormulaRowMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(taximeterFormulaRow))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TaximeterFormulaRow in the database
        List<TaximeterFormulaRow> taximeterFormulaRowList = taximeterFormulaRowRepository.findAll();
        assertThat(taximeterFormulaRowList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTaximeterFormulaRow() throws Exception {
        // Initialize the database
        taximeterFormulaRowRepository.saveAndFlush(taximeterFormulaRow);

        int databaseSizeBeforeDelete = taximeterFormulaRowRepository.findAll().size();

        // Delete the taximeterFormulaRow
        restTaximeterFormulaRowMockMvc
            .perform(delete(ENTITY_API_URL_ID, taximeterFormulaRow.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TaximeterFormulaRow> taximeterFormulaRowList = taximeterFormulaRowRepository.findAll();
        assertThat(taximeterFormulaRowList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
