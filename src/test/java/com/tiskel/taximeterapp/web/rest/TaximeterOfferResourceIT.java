package com.tiskel.taximeterapp.web.rest;

import static com.tiskel.taximeterapp.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.tiskel.taximeterapp.IntegrationTest;
import com.tiskel.taximeterapp.domain.TaximeterOffer;
import com.tiskel.taximeterapp.repository.TaximeterOfferRepository;
import com.tiskel.taximeterapp.service.dto.TaximeterOfferDTO;
import com.tiskel.taximeterapp.service.mapper.TaximeterOfferMapper;
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
 * Integration tests for the {@link TaximeterOfferResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TaximeterOfferResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_VALID_FROM = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_VALID_FROM = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_VALID_TO = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_VALID_TO = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_CREATED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_UPDATED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/taximeter-offers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TaximeterOfferRepository taximeterOfferRepository;

    @Autowired
    private TaximeterOfferMapper taximeterOfferMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTaximeterOfferMockMvc;

    private TaximeterOffer taximeterOffer;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TaximeterOffer createEntity(EntityManager em) {
        TaximeterOffer taximeterOffer = new TaximeterOffer()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .validFrom(DEFAULT_VALID_FROM)
            .validTo(DEFAULT_VALID_TO)
            .created(DEFAULT_CREATED)
            .updated(DEFAULT_UPDATED);
        return taximeterOffer;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TaximeterOffer createUpdatedEntity(EntityManager em) {
        TaximeterOffer taximeterOffer = new TaximeterOffer()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .validFrom(UPDATED_VALID_FROM)
            .validTo(UPDATED_VALID_TO)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        return taximeterOffer;
    }

    @BeforeEach
    public void initTest() {
        taximeterOffer = createEntity(em);
    }

    @Test
    @Transactional
    void createTaximeterOffer() throws Exception {
        int databaseSizeBeforeCreate = taximeterOfferRepository.findAll().size();
        // Create the TaximeterOffer
        TaximeterOfferDTO taximeterOfferDTO = taximeterOfferMapper.toDto(taximeterOffer);
        restTaximeterOfferMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(taximeterOfferDTO))
            )
            .andExpect(status().isCreated());

        // Validate the TaximeterOffer in the database
        List<TaximeterOffer> taximeterOfferList = taximeterOfferRepository.findAll();
        assertThat(taximeterOfferList).hasSize(databaseSizeBeforeCreate + 1);
        TaximeterOffer testTaximeterOffer = taximeterOfferList.get(taximeterOfferList.size() - 1);
        assertThat(testTaximeterOffer.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTaximeterOffer.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testTaximeterOffer.getValidFrom()).isEqualTo(DEFAULT_VALID_FROM);
        assertThat(testTaximeterOffer.getValidTo()).isEqualTo(DEFAULT_VALID_TO);
        assertThat(testTaximeterOffer.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testTaximeterOffer.getUpdated()).isEqualTo(DEFAULT_UPDATED);
    }

    @Test
    @Transactional
    void createTaximeterOfferWithExistingId() throws Exception {
        // Create the TaximeterOffer with an existing ID
        taximeterOffer.setId(1L);
        TaximeterOfferDTO taximeterOfferDTO = taximeterOfferMapper.toDto(taximeterOffer);

        int databaseSizeBeforeCreate = taximeterOfferRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTaximeterOfferMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(taximeterOfferDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TaximeterOffer in the database
        List<TaximeterOffer> taximeterOfferList = taximeterOfferRepository.findAll();
        assertThat(taximeterOfferList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = taximeterOfferRepository.findAll().size();
        // set the field null
        taximeterOffer.setName(null);

        // Create the TaximeterOffer, which fails.
        TaximeterOfferDTO taximeterOfferDTO = taximeterOfferMapper.toDto(taximeterOffer);

        restTaximeterOfferMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(taximeterOfferDTO))
            )
            .andExpect(status().isBadRequest());

        List<TaximeterOffer> taximeterOfferList = taximeterOfferRepository.findAll();
        assertThat(taximeterOfferList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTaximeterOffers() throws Exception {
        // Initialize the database
        taximeterOfferRepository.saveAndFlush(taximeterOffer);

        // Get all the taximeterOfferList
        restTaximeterOfferMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(taximeterOffer.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].validFrom").value(hasItem(sameInstant(DEFAULT_VALID_FROM))))
            .andExpect(jsonPath("$.[*].validTo").value(hasItem(sameInstant(DEFAULT_VALID_TO))))
            .andExpect(jsonPath("$.[*].created").value(hasItem(sameInstant(DEFAULT_CREATED))))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(sameInstant(DEFAULT_UPDATED))));
    }

    @Test
    @Transactional
    void getTaximeterOffer() throws Exception {
        // Initialize the database
        taximeterOfferRepository.saveAndFlush(taximeterOffer);

        // Get the taximeterOffer
        restTaximeterOfferMockMvc
            .perform(get(ENTITY_API_URL_ID, taximeterOffer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(taximeterOffer.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.validFrom").value(sameInstant(DEFAULT_VALID_FROM)))
            .andExpect(jsonPath("$.validTo").value(sameInstant(DEFAULT_VALID_TO)))
            .andExpect(jsonPath("$.created").value(sameInstant(DEFAULT_CREATED)))
            .andExpect(jsonPath("$.updated").value(sameInstant(DEFAULT_UPDATED)));
    }

    @Test
    @Transactional
    void getNonExistingTaximeterOffer() throws Exception {
        // Get the taximeterOffer
        restTaximeterOfferMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTaximeterOffer() throws Exception {
        // Initialize the database
        taximeterOfferRepository.saveAndFlush(taximeterOffer);

        int databaseSizeBeforeUpdate = taximeterOfferRepository.findAll().size();

        // Update the taximeterOffer
        TaximeterOffer updatedTaximeterOffer = taximeterOfferRepository.findById(taximeterOffer.getId()).get();
        // Disconnect from session so that the updates on updatedTaximeterOffer are not directly saved in db
        em.detach(updatedTaximeterOffer);
        updatedTaximeterOffer
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .validFrom(UPDATED_VALID_FROM)
            .validTo(UPDATED_VALID_TO)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        TaximeterOfferDTO taximeterOfferDTO = taximeterOfferMapper.toDto(updatedTaximeterOffer);

        restTaximeterOfferMockMvc
            .perform(
                put(ENTITY_API_URL_ID, taximeterOfferDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(taximeterOfferDTO))
            )
            .andExpect(status().isOk());

        // Validate the TaximeterOffer in the database
        List<TaximeterOffer> taximeterOfferList = taximeterOfferRepository.findAll();
        assertThat(taximeterOfferList).hasSize(databaseSizeBeforeUpdate);
        TaximeterOffer testTaximeterOffer = taximeterOfferList.get(taximeterOfferList.size() - 1);
        assertThat(testTaximeterOffer.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTaximeterOffer.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTaximeterOffer.getValidFrom()).isEqualTo(UPDATED_VALID_FROM);
        assertThat(testTaximeterOffer.getValidTo()).isEqualTo(UPDATED_VALID_TO);
        assertThat(testTaximeterOffer.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testTaximeterOffer.getUpdated()).isEqualTo(UPDATED_UPDATED);
    }

    @Test
    @Transactional
    void putNonExistingTaximeterOffer() throws Exception {
        int databaseSizeBeforeUpdate = taximeterOfferRepository.findAll().size();
        taximeterOffer.setId(count.incrementAndGet());

        // Create the TaximeterOffer
        TaximeterOfferDTO taximeterOfferDTO = taximeterOfferMapper.toDto(taximeterOffer);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTaximeterOfferMockMvc
            .perform(
                put(ENTITY_API_URL_ID, taximeterOfferDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(taximeterOfferDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TaximeterOffer in the database
        List<TaximeterOffer> taximeterOfferList = taximeterOfferRepository.findAll();
        assertThat(taximeterOfferList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTaximeterOffer() throws Exception {
        int databaseSizeBeforeUpdate = taximeterOfferRepository.findAll().size();
        taximeterOffer.setId(count.incrementAndGet());

        // Create the TaximeterOffer
        TaximeterOfferDTO taximeterOfferDTO = taximeterOfferMapper.toDto(taximeterOffer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaximeterOfferMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(taximeterOfferDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TaximeterOffer in the database
        List<TaximeterOffer> taximeterOfferList = taximeterOfferRepository.findAll();
        assertThat(taximeterOfferList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTaximeterOffer() throws Exception {
        int databaseSizeBeforeUpdate = taximeterOfferRepository.findAll().size();
        taximeterOffer.setId(count.incrementAndGet());

        // Create the TaximeterOffer
        TaximeterOfferDTO taximeterOfferDTO = taximeterOfferMapper.toDto(taximeterOffer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaximeterOfferMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(taximeterOfferDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TaximeterOffer in the database
        List<TaximeterOffer> taximeterOfferList = taximeterOfferRepository.findAll();
        assertThat(taximeterOfferList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTaximeterOfferWithPatch() throws Exception {
        // Initialize the database
        taximeterOfferRepository.saveAndFlush(taximeterOffer);

        int databaseSizeBeforeUpdate = taximeterOfferRepository.findAll().size();

        // Update the taximeterOffer using partial update
        TaximeterOffer partialUpdatedTaximeterOffer = new TaximeterOffer();
        partialUpdatedTaximeterOffer.setId(taximeterOffer.getId());

        partialUpdatedTaximeterOffer
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .validTo(UPDATED_VALID_TO)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);

        restTaximeterOfferMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTaximeterOffer.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTaximeterOffer))
            )
            .andExpect(status().isOk());

        // Validate the TaximeterOffer in the database
        List<TaximeterOffer> taximeterOfferList = taximeterOfferRepository.findAll();
        assertThat(taximeterOfferList).hasSize(databaseSizeBeforeUpdate);
        TaximeterOffer testTaximeterOffer = taximeterOfferList.get(taximeterOfferList.size() - 1);
        assertThat(testTaximeterOffer.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTaximeterOffer.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTaximeterOffer.getValidFrom()).isEqualTo(DEFAULT_VALID_FROM);
        assertThat(testTaximeterOffer.getValidTo()).isEqualTo(UPDATED_VALID_TO);
        assertThat(testTaximeterOffer.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testTaximeterOffer.getUpdated()).isEqualTo(UPDATED_UPDATED);
    }

    @Test
    @Transactional
    void fullUpdateTaximeterOfferWithPatch() throws Exception {
        // Initialize the database
        taximeterOfferRepository.saveAndFlush(taximeterOffer);

        int databaseSizeBeforeUpdate = taximeterOfferRepository.findAll().size();

        // Update the taximeterOffer using partial update
        TaximeterOffer partialUpdatedTaximeterOffer = new TaximeterOffer();
        partialUpdatedTaximeterOffer.setId(taximeterOffer.getId());

        partialUpdatedTaximeterOffer
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .validFrom(UPDATED_VALID_FROM)
            .validTo(UPDATED_VALID_TO)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);

        restTaximeterOfferMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTaximeterOffer.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTaximeterOffer))
            )
            .andExpect(status().isOk());

        // Validate the TaximeterOffer in the database
        List<TaximeterOffer> taximeterOfferList = taximeterOfferRepository.findAll();
        assertThat(taximeterOfferList).hasSize(databaseSizeBeforeUpdate);
        TaximeterOffer testTaximeterOffer = taximeterOfferList.get(taximeterOfferList.size() - 1);
        assertThat(testTaximeterOffer.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTaximeterOffer.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTaximeterOffer.getValidFrom()).isEqualTo(UPDATED_VALID_FROM);
        assertThat(testTaximeterOffer.getValidTo()).isEqualTo(UPDATED_VALID_TO);
        assertThat(testTaximeterOffer.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testTaximeterOffer.getUpdated()).isEqualTo(UPDATED_UPDATED);
    }

    @Test
    @Transactional
    void patchNonExistingTaximeterOffer() throws Exception {
        int databaseSizeBeforeUpdate = taximeterOfferRepository.findAll().size();
        taximeterOffer.setId(count.incrementAndGet());

        // Create the TaximeterOffer
        TaximeterOfferDTO taximeterOfferDTO = taximeterOfferMapper.toDto(taximeterOffer);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTaximeterOfferMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, taximeterOfferDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(taximeterOfferDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TaximeterOffer in the database
        List<TaximeterOffer> taximeterOfferList = taximeterOfferRepository.findAll();
        assertThat(taximeterOfferList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTaximeterOffer() throws Exception {
        int databaseSizeBeforeUpdate = taximeterOfferRepository.findAll().size();
        taximeterOffer.setId(count.incrementAndGet());

        // Create the TaximeterOffer
        TaximeterOfferDTO taximeterOfferDTO = taximeterOfferMapper.toDto(taximeterOffer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaximeterOfferMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(taximeterOfferDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TaximeterOffer in the database
        List<TaximeterOffer> taximeterOfferList = taximeterOfferRepository.findAll();
        assertThat(taximeterOfferList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTaximeterOffer() throws Exception {
        int databaseSizeBeforeUpdate = taximeterOfferRepository.findAll().size();
        taximeterOffer.setId(count.incrementAndGet());

        // Create the TaximeterOffer
        TaximeterOfferDTO taximeterOfferDTO = taximeterOfferMapper.toDto(taximeterOffer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaximeterOfferMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(taximeterOfferDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TaximeterOffer in the database
        List<TaximeterOffer> taximeterOfferList = taximeterOfferRepository.findAll();
        assertThat(taximeterOfferList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTaximeterOffer() throws Exception {
        // Initialize the database
        taximeterOfferRepository.saveAndFlush(taximeterOffer);

        int databaseSizeBeforeDelete = taximeterOfferRepository.findAll().size();

        // Delete the taximeterOffer
        restTaximeterOfferMockMvc
            .perform(delete(ENTITY_API_URL_ID, taximeterOffer.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TaximeterOffer> taximeterOfferList = taximeterOfferRepository.findAll();
        assertThat(taximeterOfferList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
