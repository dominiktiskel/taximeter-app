package com.tiskel.taximeterapp.web.rest;

import static com.tiskel.taximeterapp.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.tiskel.taximeterapp.IntegrationTest;
import com.tiskel.taximeterapp.domain.TaximeterTimeRange;
import com.tiskel.taximeterapp.domain.TaximeterTimeRangeItem;
import com.tiskel.taximeterapp.repository.TaximeterTimeRangeItemRepository;
import com.tiskel.taximeterapp.service.dto.TaximeterTimeRangeItemDTO;
import com.tiskel.taximeterapp.service.mapper.TaximeterTimeRangeItemMapper;
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
 * Integration tests for the {@link TaximeterTimeRangeItemResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TaximeterTimeRangeItemResourceIT {

    private static final String DEFAULT_DAY = "AAAAAAAAAA";
    private static final String UPDATED_DAY = "BBBBBBBBBB";

    private static final String DEFAULT_HOURS = "AAAAAAAAAA";
    private static final String UPDATED_HOURS = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_UPDATED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/taximeter-time-range-items";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TaximeterTimeRangeItemRepository taximeterTimeRangeItemRepository;

    @Autowired
    private TaximeterTimeRangeItemMapper taximeterTimeRangeItemMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTaximeterTimeRangeItemMockMvc;

    private TaximeterTimeRangeItem taximeterTimeRangeItem;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TaximeterTimeRangeItem createEntity(EntityManager em) {
        TaximeterTimeRangeItem taximeterTimeRangeItem = new TaximeterTimeRangeItem()
            .day(DEFAULT_DAY)
            .hours(DEFAULT_HOURS)
            .created(DEFAULT_CREATED)
            .updated(DEFAULT_UPDATED);
        // Add required entity
        TaximeterTimeRange taximeterTimeRange;
        if (TestUtil.findAll(em, TaximeterTimeRange.class).isEmpty()) {
            taximeterTimeRange = TaximeterTimeRangeResourceIT.createEntity(em);
            em.persist(taximeterTimeRange);
            em.flush();
        } else {
            taximeterTimeRange = TestUtil.findAll(em, TaximeterTimeRange.class).get(0);
        }
        taximeterTimeRangeItem.setRange(taximeterTimeRange);
        return taximeterTimeRangeItem;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TaximeterTimeRangeItem createUpdatedEntity(EntityManager em) {
        TaximeterTimeRangeItem taximeterTimeRangeItem = new TaximeterTimeRangeItem()
            .day(UPDATED_DAY)
            .hours(UPDATED_HOURS)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        // Add required entity
        TaximeterTimeRange taximeterTimeRange;
        if (TestUtil.findAll(em, TaximeterTimeRange.class).isEmpty()) {
            taximeterTimeRange = TaximeterTimeRangeResourceIT.createUpdatedEntity(em);
            em.persist(taximeterTimeRange);
            em.flush();
        } else {
            taximeterTimeRange = TestUtil.findAll(em, TaximeterTimeRange.class).get(0);
        }
        taximeterTimeRangeItem.setRange(taximeterTimeRange);
        return taximeterTimeRangeItem;
    }

    @BeforeEach
    public void initTest() {
        taximeterTimeRangeItem = createEntity(em);
    }

    @Test
    @Transactional
    void createTaximeterTimeRangeItem() throws Exception {
        int databaseSizeBeforeCreate = taximeterTimeRangeItemRepository.findAll().size();
        // Create the TaximeterTimeRangeItem
        TaximeterTimeRangeItemDTO taximeterTimeRangeItemDTO = taximeterTimeRangeItemMapper.toDto(taximeterTimeRangeItem);
        restTaximeterTimeRangeItemMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(taximeterTimeRangeItemDTO))
            )
            .andExpect(status().isCreated());

        // Validate the TaximeterTimeRangeItem in the database
        List<TaximeterTimeRangeItem> taximeterTimeRangeItemList = taximeterTimeRangeItemRepository.findAll();
        assertThat(taximeterTimeRangeItemList).hasSize(databaseSizeBeforeCreate + 1);
        TaximeterTimeRangeItem testTaximeterTimeRangeItem = taximeterTimeRangeItemList.get(taximeterTimeRangeItemList.size() - 1);
        assertThat(testTaximeterTimeRangeItem.getDay()).isEqualTo(DEFAULT_DAY);
        assertThat(testTaximeterTimeRangeItem.getHours()).isEqualTo(DEFAULT_HOURS);
        assertThat(testTaximeterTimeRangeItem.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testTaximeterTimeRangeItem.getUpdated()).isEqualTo(DEFAULT_UPDATED);
    }

    @Test
    @Transactional
    void createTaximeterTimeRangeItemWithExistingId() throws Exception {
        // Create the TaximeterTimeRangeItem with an existing ID
        taximeterTimeRangeItem.setId(1L);
        TaximeterTimeRangeItemDTO taximeterTimeRangeItemDTO = taximeterTimeRangeItemMapper.toDto(taximeterTimeRangeItem);

        int databaseSizeBeforeCreate = taximeterTimeRangeItemRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTaximeterTimeRangeItemMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(taximeterTimeRangeItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TaximeterTimeRangeItem in the database
        List<TaximeterTimeRangeItem> taximeterTimeRangeItemList = taximeterTimeRangeItemRepository.findAll();
        assertThat(taximeterTimeRangeItemList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDayIsRequired() throws Exception {
        int databaseSizeBeforeTest = taximeterTimeRangeItemRepository.findAll().size();
        // set the field null
        taximeterTimeRangeItem.setDay(null);

        // Create the TaximeterTimeRangeItem, which fails.
        TaximeterTimeRangeItemDTO taximeterTimeRangeItemDTO = taximeterTimeRangeItemMapper.toDto(taximeterTimeRangeItem);

        restTaximeterTimeRangeItemMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(taximeterTimeRangeItemDTO))
            )
            .andExpect(status().isBadRequest());

        List<TaximeterTimeRangeItem> taximeterTimeRangeItemList = taximeterTimeRangeItemRepository.findAll();
        assertThat(taximeterTimeRangeItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkHoursIsRequired() throws Exception {
        int databaseSizeBeforeTest = taximeterTimeRangeItemRepository.findAll().size();
        // set the field null
        taximeterTimeRangeItem.setHours(null);

        // Create the TaximeterTimeRangeItem, which fails.
        TaximeterTimeRangeItemDTO taximeterTimeRangeItemDTO = taximeterTimeRangeItemMapper.toDto(taximeterTimeRangeItem);

        restTaximeterTimeRangeItemMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(taximeterTimeRangeItemDTO))
            )
            .andExpect(status().isBadRequest());

        List<TaximeterTimeRangeItem> taximeterTimeRangeItemList = taximeterTimeRangeItemRepository.findAll();
        assertThat(taximeterTimeRangeItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTaximeterTimeRangeItems() throws Exception {
        // Initialize the database
        taximeterTimeRangeItemRepository.saveAndFlush(taximeterTimeRangeItem);

        // Get all the taximeterTimeRangeItemList
        restTaximeterTimeRangeItemMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(taximeterTimeRangeItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].day").value(hasItem(DEFAULT_DAY)))
            .andExpect(jsonPath("$.[*].hours").value(hasItem(DEFAULT_HOURS)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(sameInstant(DEFAULT_CREATED))))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(sameInstant(DEFAULT_UPDATED))));
    }

    @Test
    @Transactional
    void getTaximeterTimeRangeItem() throws Exception {
        // Initialize the database
        taximeterTimeRangeItemRepository.saveAndFlush(taximeterTimeRangeItem);

        // Get the taximeterTimeRangeItem
        restTaximeterTimeRangeItemMockMvc
            .perform(get(ENTITY_API_URL_ID, taximeterTimeRangeItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(taximeterTimeRangeItem.getId().intValue()))
            .andExpect(jsonPath("$.day").value(DEFAULT_DAY))
            .andExpect(jsonPath("$.hours").value(DEFAULT_HOURS))
            .andExpect(jsonPath("$.created").value(sameInstant(DEFAULT_CREATED)))
            .andExpect(jsonPath("$.updated").value(sameInstant(DEFAULT_UPDATED)));
    }

    @Test
    @Transactional
    void getNonExistingTaximeterTimeRangeItem() throws Exception {
        // Get the taximeterTimeRangeItem
        restTaximeterTimeRangeItemMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTaximeterTimeRangeItem() throws Exception {
        // Initialize the database
        taximeterTimeRangeItemRepository.saveAndFlush(taximeterTimeRangeItem);

        int databaseSizeBeforeUpdate = taximeterTimeRangeItemRepository.findAll().size();

        // Update the taximeterTimeRangeItem
        TaximeterTimeRangeItem updatedTaximeterTimeRangeItem = taximeterTimeRangeItemRepository
            .findById(taximeterTimeRangeItem.getId())
            .get();
        // Disconnect from session so that the updates on updatedTaximeterTimeRangeItem are not directly saved in db
        em.detach(updatedTaximeterTimeRangeItem);
        updatedTaximeterTimeRangeItem.day(UPDATED_DAY).hours(UPDATED_HOURS).created(UPDATED_CREATED).updated(UPDATED_UPDATED);
        TaximeterTimeRangeItemDTO taximeterTimeRangeItemDTO = taximeterTimeRangeItemMapper.toDto(updatedTaximeterTimeRangeItem);

        restTaximeterTimeRangeItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, taximeterTimeRangeItemDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(taximeterTimeRangeItemDTO))
            )
            .andExpect(status().isOk());

        // Validate the TaximeterTimeRangeItem in the database
        List<TaximeterTimeRangeItem> taximeterTimeRangeItemList = taximeterTimeRangeItemRepository.findAll();
        assertThat(taximeterTimeRangeItemList).hasSize(databaseSizeBeforeUpdate);
        TaximeterTimeRangeItem testTaximeterTimeRangeItem = taximeterTimeRangeItemList.get(taximeterTimeRangeItemList.size() - 1);
        assertThat(testTaximeterTimeRangeItem.getDay()).isEqualTo(UPDATED_DAY);
        assertThat(testTaximeterTimeRangeItem.getHours()).isEqualTo(UPDATED_HOURS);
        assertThat(testTaximeterTimeRangeItem.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testTaximeterTimeRangeItem.getUpdated()).isEqualTo(UPDATED_UPDATED);
    }

    @Test
    @Transactional
    void putNonExistingTaximeterTimeRangeItem() throws Exception {
        int databaseSizeBeforeUpdate = taximeterTimeRangeItemRepository.findAll().size();
        taximeterTimeRangeItem.setId(count.incrementAndGet());

        // Create the TaximeterTimeRangeItem
        TaximeterTimeRangeItemDTO taximeterTimeRangeItemDTO = taximeterTimeRangeItemMapper.toDto(taximeterTimeRangeItem);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTaximeterTimeRangeItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, taximeterTimeRangeItemDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(taximeterTimeRangeItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TaximeterTimeRangeItem in the database
        List<TaximeterTimeRangeItem> taximeterTimeRangeItemList = taximeterTimeRangeItemRepository.findAll();
        assertThat(taximeterTimeRangeItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTaximeterTimeRangeItem() throws Exception {
        int databaseSizeBeforeUpdate = taximeterTimeRangeItemRepository.findAll().size();
        taximeterTimeRangeItem.setId(count.incrementAndGet());

        // Create the TaximeterTimeRangeItem
        TaximeterTimeRangeItemDTO taximeterTimeRangeItemDTO = taximeterTimeRangeItemMapper.toDto(taximeterTimeRangeItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaximeterTimeRangeItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(taximeterTimeRangeItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TaximeterTimeRangeItem in the database
        List<TaximeterTimeRangeItem> taximeterTimeRangeItemList = taximeterTimeRangeItemRepository.findAll();
        assertThat(taximeterTimeRangeItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTaximeterTimeRangeItem() throws Exception {
        int databaseSizeBeforeUpdate = taximeterTimeRangeItemRepository.findAll().size();
        taximeterTimeRangeItem.setId(count.incrementAndGet());

        // Create the TaximeterTimeRangeItem
        TaximeterTimeRangeItemDTO taximeterTimeRangeItemDTO = taximeterTimeRangeItemMapper.toDto(taximeterTimeRangeItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaximeterTimeRangeItemMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(taximeterTimeRangeItemDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TaximeterTimeRangeItem in the database
        List<TaximeterTimeRangeItem> taximeterTimeRangeItemList = taximeterTimeRangeItemRepository.findAll();
        assertThat(taximeterTimeRangeItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTaximeterTimeRangeItemWithPatch() throws Exception {
        // Initialize the database
        taximeterTimeRangeItemRepository.saveAndFlush(taximeterTimeRangeItem);

        int databaseSizeBeforeUpdate = taximeterTimeRangeItemRepository.findAll().size();

        // Update the taximeterTimeRangeItem using partial update
        TaximeterTimeRangeItem partialUpdatedTaximeterTimeRangeItem = new TaximeterTimeRangeItem();
        partialUpdatedTaximeterTimeRangeItem.setId(taximeterTimeRangeItem.getId());

        partialUpdatedTaximeterTimeRangeItem.day(UPDATED_DAY).created(UPDATED_CREATED).updated(UPDATED_UPDATED);

        restTaximeterTimeRangeItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTaximeterTimeRangeItem.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTaximeterTimeRangeItem))
            )
            .andExpect(status().isOk());

        // Validate the TaximeterTimeRangeItem in the database
        List<TaximeterTimeRangeItem> taximeterTimeRangeItemList = taximeterTimeRangeItemRepository.findAll();
        assertThat(taximeterTimeRangeItemList).hasSize(databaseSizeBeforeUpdate);
        TaximeterTimeRangeItem testTaximeterTimeRangeItem = taximeterTimeRangeItemList.get(taximeterTimeRangeItemList.size() - 1);
        assertThat(testTaximeterTimeRangeItem.getDay()).isEqualTo(UPDATED_DAY);
        assertThat(testTaximeterTimeRangeItem.getHours()).isEqualTo(DEFAULT_HOURS);
        assertThat(testTaximeterTimeRangeItem.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testTaximeterTimeRangeItem.getUpdated()).isEqualTo(UPDATED_UPDATED);
    }

    @Test
    @Transactional
    void fullUpdateTaximeterTimeRangeItemWithPatch() throws Exception {
        // Initialize the database
        taximeterTimeRangeItemRepository.saveAndFlush(taximeterTimeRangeItem);

        int databaseSizeBeforeUpdate = taximeterTimeRangeItemRepository.findAll().size();

        // Update the taximeterTimeRangeItem using partial update
        TaximeterTimeRangeItem partialUpdatedTaximeterTimeRangeItem = new TaximeterTimeRangeItem();
        partialUpdatedTaximeterTimeRangeItem.setId(taximeterTimeRangeItem.getId());

        partialUpdatedTaximeterTimeRangeItem.day(UPDATED_DAY).hours(UPDATED_HOURS).created(UPDATED_CREATED).updated(UPDATED_UPDATED);

        restTaximeterTimeRangeItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTaximeterTimeRangeItem.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTaximeterTimeRangeItem))
            )
            .andExpect(status().isOk());

        // Validate the TaximeterTimeRangeItem in the database
        List<TaximeterTimeRangeItem> taximeterTimeRangeItemList = taximeterTimeRangeItemRepository.findAll();
        assertThat(taximeterTimeRangeItemList).hasSize(databaseSizeBeforeUpdate);
        TaximeterTimeRangeItem testTaximeterTimeRangeItem = taximeterTimeRangeItemList.get(taximeterTimeRangeItemList.size() - 1);
        assertThat(testTaximeterTimeRangeItem.getDay()).isEqualTo(UPDATED_DAY);
        assertThat(testTaximeterTimeRangeItem.getHours()).isEqualTo(UPDATED_HOURS);
        assertThat(testTaximeterTimeRangeItem.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testTaximeterTimeRangeItem.getUpdated()).isEqualTo(UPDATED_UPDATED);
    }

    @Test
    @Transactional
    void patchNonExistingTaximeterTimeRangeItem() throws Exception {
        int databaseSizeBeforeUpdate = taximeterTimeRangeItemRepository.findAll().size();
        taximeterTimeRangeItem.setId(count.incrementAndGet());

        // Create the TaximeterTimeRangeItem
        TaximeterTimeRangeItemDTO taximeterTimeRangeItemDTO = taximeterTimeRangeItemMapper.toDto(taximeterTimeRangeItem);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTaximeterTimeRangeItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, taximeterTimeRangeItemDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(taximeterTimeRangeItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TaximeterTimeRangeItem in the database
        List<TaximeterTimeRangeItem> taximeterTimeRangeItemList = taximeterTimeRangeItemRepository.findAll();
        assertThat(taximeterTimeRangeItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTaximeterTimeRangeItem() throws Exception {
        int databaseSizeBeforeUpdate = taximeterTimeRangeItemRepository.findAll().size();
        taximeterTimeRangeItem.setId(count.incrementAndGet());

        // Create the TaximeterTimeRangeItem
        TaximeterTimeRangeItemDTO taximeterTimeRangeItemDTO = taximeterTimeRangeItemMapper.toDto(taximeterTimeRangeItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaximeterTimeRangeItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(taximeterTimeRangeItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TaximeterTimeRangeItem in the database
        List<TaximeterTimeRangeItem> taximeterTimeRangeItemList = taximeterTimeRangeItemRepository.findAll();
        assertThat(taximeterTimeRangeItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTaximeterTimeRangeItem() throws Exception {
        int databaseSizeBeforeUpdate = taximeterTimeRangeItemRepository.findAll().size();
        taximeterTimeRangeItem.setId(count.incrementAndGet());

        // Create the TaximeterTimeRangeItem
        TaximeterTimeRangeItemDTO taximeterTimeRangeItemDTO = taximeterTimeRangeItemMapper.toDto(taximeterTimeRangeItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaximeterTimeRangeItemMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(taximeterTimeRangeItemDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TaximeterTimeRangeItem in the database
        List<TaximeterTimeRangeItem> taximeterTimeRangeItemList = taximeterTimeRangeItemRepository.findAll();
        assertThat(taximeterTimeRangeItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTaximeterTimeRangeItem() throws Exception {
        // Initialize the database
        taximeterTimeRangeItemRepository.saveAndFlush(taximeterTimeRangeItem);

        int databaseSizeBeforeDelete = taximeterTimeRangeItemRepository.findAll().size();

        // Delete the taximeterTimeRangeItem
        restTaximeterTimeRangeItemMockMvc
            .perform(delete(ENTITY_API_URL_ID, taximeterTimeRangeItem.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TaximeterTimeRangeItem> taximeterTimeRangeItemList = taximeterTimeRangeItemRepository.findAll();
        assertThat(taximeterTimeRangeItemList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
