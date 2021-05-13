package com.tiskel.taximeterapp.web.rest;

import static com.tiskel.taximeterapp.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.tiskel.taximeterapp.IntegrationTest;
import com.tiskel.taximeterapp.domain.TaximeterFormula;
import com.tiskel.taximeterapp.domain.enumeration.TaximeterChargeByType;
import com.tiskel.taximeterapp.domain.enumeration.TaximeterFormulaType;
import com.tiskel.taximeterapp.repository.TaximeterFormulaRepository;
import com.tiskel.taximeterapp.service.dto.TaximeterFormulaDTO;
import com.tiskel.taximeterapp.service.mapper.TaximeterFormulaMapper;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
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

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final TaximeterFormulaType DEFAULT_TYPE = TaximeterFormulaType.FLAT;
    private static final TaximeterFormulaType UPDATED_TYPE = TaximeterFormulaType.IMPULSE;

    private static final TaximeterChargeByType DEFAULT_CHARGE_BY = TaximeterChargeByType.DISTANCE;
    private static final TaximeterChargeByType UPDATED_CHARGE_BY = TaximeterChargeByType.TIME;

    private static final String DEFAULT_JSON_DATA = "AAAAAAAAAA";
    private static final String UPDATED_JSON_DATA = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_UPDATED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

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
        TaximeterFormula taximeterFormula = new TaximeterFormula()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .type(DEFAULT_TYPE)
            .chargeBy(DEFAULT_CHARGE_BY)
            .jsonData(DEFAULT_JSON_DATA)
            .created(DEFAULT_CREATED)
            .updated(DEFAULT_UPDATED);
        return taximeterFormula;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TaximeterFormula createUpdatedEntity(EntityManager em) {
        TaximeterFormula taximeterFormula = new TaximeterFormula()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .type(UPDATED_TYPE)
            .chargeBy(UPDATED_CHARGE_BY)
            .jsonData(UPDATED_JSON_DATA)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
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
        assertThat(testTaximeterFormula.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTaximeterFormula.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testTaximeterFormula.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testTaximeterFormula.getChargeBy()).isEqualTo(DEFAULT_CHARGE_BY);
        assertThat(testTaximeterFormula.getJsonData()).isEqualTo(DEFAULT_JSON_DATA);
        assertThat(testTaximeterFormula.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testTaximeterFormula.getUpdated()).isEqualTo(DEFAULT_UPDATED);
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
    void checkChargeByIsRequired() throws Exception {
        int databaseSizeBeforeTest = taximeterFormulaRepository.findAll().size();
        // set the field null
        taximeterFormula.setChargeBy(null);

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
    void checkJsonDataIsRequired() throws Exception {
        int databaseSizeBeforeTest = taximeterFormulaRepository.findAll().size();
        // set the field null
        taximeterFormula.setJsonData(null);

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
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].chargeBy").value(hasItem(DEFAULT_CHARGE_BY.toString())))
            .andExpect(jsonPath("$.[*].jsonData").value(hasItem(DEFAULT_JSON_DATA)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(sameInstant(DEFAULT_CREATED))))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(sameInstant(DEFAULT_UPDATED))));
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
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.chargeBy").value(DEFAULT_CHARGE_BY.toString()))
            .andExpect(jsonPath("$.jsonData").value(DEFAULT_JSON_DATA))
            .andExpect(jsonPath("$.created").value(sameInstant(DEFAULT_CREATED)))
            .andExpect(jsonPath("$.updated").value(sameInstant(DEFAULT_UPDATED)));
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
        updatedTaximeterFormula
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .type(UPDATED_TYPE)
            .chargeBy(UPDATED_CHARGE_BY)
            .jsonData(UPDATED_JSON_DATA)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
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
        assertThat(testTaximeterFormula.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTaximeterFormula.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTaximeterFormula.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testTaximeterFormula.getChargeBy()).isEqualTo(UPDATED_CHARGE_BY);
        assertThat(testTaximeterFormula.getJsonData()).isEqualTo(UPDATED_JSON_DATA);
        assertThat(testTaximeterFormula.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testTaximeterFormula.getUpdated()).isEqualTo(UPDATED_UPDATED);
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

        partialUpdatedTaximeterFormula.type(UPDATED_TYPE).jsonData(UPDATED_JSON_DATA);

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
        assertThat(testTaximeterFormula.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTaximeterFormula.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testTaximeterFormula.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testTaximeterFormula.getChargeBy()).isEqualTo(DEFAULT_CHARGE_BY);
        assertThat(testTaximeterFormula.getJsonData()).isEqualTo(UPDATED_JSON_DATA);
        assertThat(testTaximeterFormula.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testTaximeterFormula.getUpdated()).isEqualTo(DEFAULT_UPDATED);
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

        partialUpdatedTaximeterFormula
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .type(UPDATED_TYPE)
            .chargeBy(UPDATED_CHARGE_BY)
            .jsonData(UPDATED_JSON_DATA)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);

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
        assertThat(testTaximeterFormula.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTaximeterFormula.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTaximeterFormula.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testTaximeterFormula.getChargeBy()).isEqualTo(UPDATED_CHARGE_BY);
        assertThat(testTaximeterFormula.getJsonData()).isEqualTo(UPDATED_JSON_DATA);
        assertThat(testTaximeterFormula.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testTaximeterFormula.getUpdated()).isEqualTo(UPDATED_UPDATED);
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
