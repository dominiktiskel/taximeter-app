package com.tiskel.taximeterapp.web.rest;

import static com.tiskel.taximeterapp.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.tiskel.taximeterapp.IntegrationTest;
import com.tiskel.taximeterapp.domain.TaximeterTimeRange;
import com.tiskel.taximeterapp.repository.TaximeterTimeRangeRepository;
import com.tiskel.taximeterapp.service.dto.TaximeterTimeRangeDTO;
import com.tiskel.taximeterapp.service.mapper.TaximeterTimeRangeMapper;
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
 * Integration tests for the {@link TaximeterTimeRangeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TaximeterTimeRangeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_UPDATED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/taximeter-time-ranges";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TaximeterTimeRangeRepository taximeterTimeRangeRepository;

    @Autowired
    private TaximeterTimeRangeMapper taximeterTimeRangeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTaximeterTimeRangeMockMvc;

    private TaximeterTimeRange taximeterTimeRange;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TaximeterTimeRange createEntity(EntityManager em) {
        TaximeterTimeRange taximeterTimeRange = new TaximeterTimeRange()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .created(DEFAULT_CREATED)
            .updated(DEFAULT_UPDATED);
        return taximeterTimeRange;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TaximeterTimeRange createUpdatedEntity(EntityManager em) {
        TaximeterTimeRange taximeterTimeRange = new TaximeterTimeRange()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        return taximeterTimeRange;
    }

    @BeforeEach
    public void initTest() {
        taximeterTimeRange = createEntity(em);
    }

    @Test
    @Transactional
    void createTaximeterTimeRange() throws Exception {
        int databaseSizeBeforeCreate = taximeterTimeRangeRepository.findAll().size();
        // Create the TaximeterTimeRange
        TaximeterTimeRangeDTO taximeterTimeRangeDTO = taximeterTimeRangeMapper.toDto(taximeterTimeRange);
        restTaximeterTimeRangeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(taximeterTimeRangeDTO))
            )
            .andExpect(status().isCreated());

        // Validate the TaximeterTimeRange in the database
        List<TaximeterTimeRange> taximeterTimeRangeList = taximeterTimeRangeRepository.findAll();
        assertThat(taximeterTimeRangeList).hasSize(databaseSizeBeforeCreate + 1);
        TaximeterTimeRange testTaximeterTimeRange = taximeterTimeRangeList.get(taximeterTimeRangeList.size() - 1);
        assertThat(testTaximeterTimeRange.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTaximeterTimeRange.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testTaximeterTimeRange.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testTaximeterTimeRange.getUpdated()).isEqualTo(DEFAULT_UPDATED);
    }

    @Test
    @Transactional
    void createTaximeterTimeRangeWithExistingId() throws Exception {
        // Create the TaximeterTimeRange with an existing ID
        taximeterTimeRange.setId(1L);
        TaximeterTimeRangeDTO taximeterTimeRangeDTO = taximeterTimeRangeMapper.toDto(taximeterTimeRange);

        int databaseSizeBeforeCreate = taximeterTimeRangeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTaximeterTimeRangeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(taximeterTimeRangeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TaximeterTimeRange in the database
        List<TaximeterTimeRange> taximeterTimeRangeList = taximeterTimeRangeRepository.findAll();
        assertThat(taximeterTimeRangeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = taximeterTimeRangeRepository.findAll().size();
        // set the field null
        taximeterTimeRange.setName(null);

        // Create the TaximeterTimeRange, which fails.
        TaximeterTimeRangeDTO taximeterTimeRangeDTO = taximeterTimeRangeMapper.toDto(taximeterTimeRange);

        restTaximeterTimeRangeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(taximeterTimeRangeDTO))
            )
            .andExpect(status().isBadRequest());

        List<TaximeterTimeRange> taximeterTimeRangeList = taximeterTimeRangeRepository.findAll();
        assertThat(taximeterTimeRangeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTaximeterTimeRanges() throws Exception {
        // Initialize the database
        taximeterTimeRangeRepository.saveAndFlush(taximeterTimeRange);

        // Get all the taximeterTimeRangeList
        restTaximeterTimeRangeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(taximeterTimeRange.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(sameInstant(DEFAULT_CREATED))))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(sameInstant(DEFAULT_UPDATED))));
    }

    @Test
    @Transactional
    void getTaximeterTimeRange() throws Exception {
        // Initialize the database
        taximeterTimeRangeRepository.saveAndFlush(taximeterTimeRange);

        // Get the taximeterTimeRange
        restTaximeterTimeRangeMockMvc
            .perform(get(ENTITY_API_URL_ID, taximeterTimeRange.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(taximeterTimeRange.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.created").value(sameInstant(DEFAULT_CREATED)))
            .andExpect(jsonPath("$.updated").value(sameInstant(DEFAULT_UPDATED)));
    }

    @Test
    @Transactional
    void getNonExistingTaximeterTimeRange() throws Exception {
        // Get the taximeterTimeRange
        restTaximeterTimeRangeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTaximeterTimeRange() throws Exception {
        // Initialize the database
        taximeterTimeRangeRepository.saveAndFlush(taximeterTimeRange);

        int databaseSizeBeforeUpdate = taximeterTimeRangeRepository.findAll().size();

        // Update the taximeterTimeRange
        TaximeterTimeRange updatedTaximeterTimeRange = taximeterTimeRangeRepository.findById(taximeterTimeRange.getId()).get();
        // Disconnect from session so that the updates on updatedTaximeterTimeRange are not directly saved in db
        em.detach(updatedTaximeterTimeRange);
        updatedTaximeterTimeRange.name(UPDATED_NAME).description(UPDATED_DESCRIPTION).created(UPDATED_CREATED).updated(UPDATED_UPDATED);
        TaximeterTimeRangeDTO taximeterTimeRangeDTO = taximeterTimeRangeMapper.toDto(updatedTaximeterTimeRange);

        restTaximeterTimeRangeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, taximeterTimeRangeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(taximeterTimeRangeDTO))
            )
            .andExpect(status().isOk());

        // Validate the TaximeterTimeRange in the database
        List<TaximeterTimeRange> taximeterTimeRangeList = taximeterTimeRangeRepository.findAll();
        assertThat(taximeterTimeRangeList).hasSize(databaseSizeBeforeUpdate);
        TaximeterTimeRange testTaximeterTimeRange = taximeterTimeRangeList.get(taximeterTimeRangeList.size() - 1);
        assertThat(testTaximeterTimeRange.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTaximeterTimeRange.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTaximeterTimeRange.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testTaximeterTimeRange.getUpdated()).isEqualTo(UPDATED_UPDATED);
    }

    @Test
    @Transactional
    void putNonExistingTaximeterTimeRange() throws Exception {
        int databaseSizeBeforeUpdate = taximeterTimeRangeRepository.findAll().size();
        taximeterTimeRange.setId(count.incrementAndGet());

        // Create the TaximeterTimeRange
        TaximeterTimeRangeDTO taximeterTimeRangeDTO = taximeterTimeRangeMapper.toDto(taximeterTimeRange);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTaximeterTimeRangeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, taximeterTimeRangeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(taximeterTimeRangeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TaximeterTimeRange in the database
        List<TaximeterTimeRange> taximeterTimeRangeList = taximeterTimeRangeRepository.findAll();
        assertThat(taximeterTimeRangeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTaximeterTimeRange() throws Exception {
        int databaseSizeBeforeUpdate = taximeterTimeRangeRepository.findAll().size();
        taximeterTimeRange.setId(count.incrementAndGet());

        // Create the TaximeterTimeRange
        TaximeterTimeRangeDTO taximeterTimeRangeDTO = taximeterTimeRangeMapper.toDto(taximeterTimeRange);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaximeterTimeRangeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(taximeterTimeRangeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TaximeterTimeRange in the database
        List<TaximeterTimeRange> taximeterTimeRangeList = taximeterTimeRangeRepository.findAll();
        assertThat(taximeterTimeRangeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTaximeterTimeRange() throws Exception {
        int databaseSizeBeforeUpdate = taximeterTimeRangeRepository.findAll().size();
        taximeterTimeRange.setId(count.incrementAndGet());

        // Create the TaximeterTimeRange
        TaximeterTimeRangeDTO taximeterTimeRangeDTO = taximeterTimeRangeMapper.toDto(taximeterTimeRange);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaximeterTimeRangeMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(taximeterTimeRangeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TaximeterTimeRange in the database
        List<TaximeterTimeRange> taximeterTimeRangeList = taximeterTimeRangeRepository.findAll();
        assertThat(taximeterTimeRangeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTaximeterTimeRangeWithPatch() throws Exception {
        // Initialize the database
        taximeterTimeRangeRepository.saveAndFlush(taximeterTimeRange);

        int databaseSizeBeforeUpdate = taximeterTimeRangeRepository.findAll().size();

        // Update the taximeterTimeRange using partial update
        TaximeterTimeRange partialUpdatedTaximeterTimeRange = new TaximeterTimeRange();
        partialUpdatedTaximeterTimeRange.setId(taximeterTimeRange.getId());

        partialUpdatedTaximeterTimeRange.name(UPDATED_NAME).created(UPDATED_CREATED);

        restTaximeterTimeRangeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTaximeterTimeRange.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTaximeterTimeRange))
            )
            .andExpect(status().isOk());

        // Validate the TaximeterTimeRange in the database
        List<TaximeterTimeRange> taximeterTimeRangeList = taximeterTimeRangeRepository.findAll();
        assertThat(taximeterTimeRangeList).hasSize(databaseSizeBeforeUpdate);
        TaximeterTimeRange testTaximeterTimeRange = taximeterTimeRangeList.get(taximeterTimeRangeList.size() - 1);
        assertThat(testTaximeterTimeRange.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTaximeterTimeRange.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testTaximeterTimeRange.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testTaximeterTimeRange.getUpdated()).isEqualTo(DEFAULT_UPDATED);
    }

    @Test
    @Transactional
    void fullUpdateTaximeterTimeRangeWithPatch() throws Exception {
        // Initialize the database
        taximeterTimeRangeRepository.saveAndFlush(taximeterTimeRange);

        int databaseSizeBeforeUpdate = taximeterTimeRangeRepository.findAll().size();

        // Update the taximeterTimeRange using partial update
        TaximeterTimeRange partialUpdatedTaximeterTimeRange = new TaximeterTimeRange();
        partialUpdatedTaximeterTimeRange.setId(taximeterTimeRange.getId());

        partialUpdatedTaximeterTimeRange
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);

        restTaximeterTimeRangeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTaximeterTimeRange.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTaximeterTimeRange))
            )
            .andExpect(status().isOk());

        // Validate the TaximeterTimeRange in the database
        List<TaximeterTimeRange> taximeterTimeRangeList = taximeterTimeRangeRepository.findAll();
        assertThat(taximeterTimeRangeList).hasSize(databaseSizeBeforeUpdate);
        TaximeterTimeRange testTaximeterTimeRange = taximeterTimeRangeList.get(taximeterTimeRangeList.size() - 1);
        assertThat(testTaximeterTimeRange.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTaximeterTimeRange.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTaximeterTimeRange.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testTaximeterTimeRange.getUpdated()).isEqualTo(UPDATED_UPDATED);
    }

    @Test
    @Transactional
    void patchNonExistingTaximeterTimeRange() throws Exception {
        int databaseSizeBeforeUpdate = taximeterTimeRangeRepository.findAll().size();
        taximeterTimeRange.setId(count.incrementAndGet());

        // Create the TaximeterTimeRange
        TaximeterTimeRangeDTO taximeterTimeRangeDTO = taximeterTimeRangeMapper.toDto(taximeterTimeRange);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTaximeterTimeRangeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, taximeterTimeRangeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(taximeterTimeRangeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TaximeterTimeRange in the database
        List<TaximeterTimeRange> taximeterTimeRangeList = taximeterTimeRangeRepository.findAll();
        assertThat(taximeterTimeRangeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTaximeterTimeRange() throws Exception {
        int databaseSizeBeforeUpdate = taximeterTimeRangeRepository.findAll().size();
        taximeterTimeRange.setId(count.incrementAndGet());

        // Create the TaximeterTimeRange
        TaximeterTimeRangeDTO taximeterTimeRangeDTO = taximeterTimeRangeMapper.toDto(taximeterTimeRange);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaximeterTimeRangeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(taximeterTimeRangeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TaximeterTimeRange in the database
        List<TaximeterTimeRange> taximeterTimeRangeList = taximeterTimeRangeRepository.findAll();
        assertThat(taximeterTimeRangeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTaximeterTimeRange() throws Exception {
        int databaseSizeBeforeUpdate = taximeterTimeRangeRepository.findAll().size();
        taximeterTimeRange.setId(count.incrementAndGet());

        // Create the TaximeterTimeRange
        TaximeterTimeRangeDTO taximeterTimeRangeDTO = taximeterTimeRangeMapper.toDto(taximeterTimeRange);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaximeterTimeRangeMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(taximeterTimeRangeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TaximeterTimeRange in the database
        List<TaximeterTimeRange> taximeterTimeRangeList = taximeterTimeRangeRepository.findAll();
        assertThat(taximeterTimeRangeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTaximeterTimeRange() throws Exception {
        // Initialize the database
        taximeterTimeRangeRepository.saveAndFlush(taximeterTimeRange);

        int databaseSizeBeforeDelete = taximeterTimeRangeRepository.findAll().size();

        // Delete the taximeterTimeRange
        restTaximeterTimeRangeMockMvc
            .perform(delete(ENTITY_API_URL_ID, taximeterTimeRange.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TaximeterTimeRange> taximeterTimeRangeList = taximeterTimeRangeRepository.findAll();
        assertThat(taximeterTimeRangeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
