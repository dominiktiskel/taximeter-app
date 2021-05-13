package com.tiskel.taximeterapp.web.rest;

import static com.tiskel.taximeterapp.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.tiskel.taximeterapp.IntegrationTest;
import com.tiskel.taximeterapp.domain.TaximeterFixedList;
import com.tiskel.taximeterapp.domain.TaximeterFixedListItem;
import com.tiskel.taximeterapp.repository.TaximeterFixedListItemRepository;
import com.tiskel.taximeterapp.service.dto.TaximeterFixedListItemDTO;
import com.tiskel.taximeterapp.service.mapper.TaximeterFixedListItemMapper;
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
 * Integration tests for the {@link TaximeterFixedListItemResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TaximeterFixedListItemResourceIT {

    private static final String DEFAULT_FROM = "AAAAAAAAAA";
    private static final String UPDATED_FROM = "BBBBBBBBBB";

    private static final String DEFAULT_TO = "AAAAAAAAAA";
    private static final String UPDATED_TO = "BBBBBBBBBB";

    private static final Double DEFAULT_VALUE = 1D;
    private static final Double UPDATED_VALUE = 2D;

    private static final Double DEFAULT_VALUE_REVERSE = 1D;
    private static final Double UPDATED_VALUE_REVERSE = 2D;

    private static final ZonedDateTime DEFAULT_CREATED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_UPDATED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/taximeter-fixed-list-items";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TaximeterFixedListItemRepository taximeterFixedListItemRepository;

    @Autowired
    private TaximeterFixedListItemMapper taximeterFixedListItemMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTaximeterFixedListItemMockMvc;

    private TaximeterFixedListItem taximeterFixedListItem;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TaximeterFixedListItem createEntity(EntityManager em) {
        TaximeterFixedListItem taximeterFixedListItem = new TaximeterFixedListItem()
            .from(DEFAULT_FROM)
            .to(DEFAULT_TO)
            .value(DEFAULT_VALUE)
            .valueReverse(DEFAULT_VALUE_REVERSE)
            .created(DEFAULT_CREATED)
            .updated(DEFAULT_UPDATED);
        // Add required entity
        TaximeterFixedList taximeterFixedList;
        if (TestUtil.findAll(em, TaximeterFixedList.class).isEmpty()) {
            taximeterFixedList = TaximeterFixedListResourceIT.createEntity(em);
            em.persist(taximeterFixedList);
            em.flush();
        } else {
            taximeterFixedList = TestUtil.findAll(em, TaximeterFixedList.class).get(0);
        }
        taximeterFixedListItem.setList(taximeterFixedList);
        return taximeterFixedListItem;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TaximeterFixedListItem createUpdatedEntity(EntityManager em) {
        TaximeterFixedListItem taximeterFixedListItem = new TaximeterFixedListItem()
            .from(UPDATED_FROM)
            .to(UPDATED_TO)
            .value(UPDATED_VALUE)
            .valueReverse(UPDATED_VALUE_REVERSE)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        // Add required entity
        TaximeterFixedList taximeterFixedList;
        if (TestUtil.findAll(em, TaximeterFixedList.class).isEmpty()) {
            taximeterFixedList = TaximeterFixedListResourceIT.createUpdatedEntity(em);
            em.persist(taximeterFixedList);
            em.flush();
        } else {
            taximeterFixedList = TestUtil.findAll(em, TaximeterFixedList.class).get(0);
        }
        taximeterFixedListItem.setList(taximeterFixedList);
        return taximeterFixedListItem;
    }

    @BeforeEach
    public void initTest() {
        taximeterFixedListItem = createEntity(em);
    }

    @Test
    @Transactional
    void createTaximeterFixedListItem() throws Exception {
        int databaseSizeBeforeCreate = taximeterFixedListItemRepository.findAll().size();
        // Create the TaximeterFixedListItem
        TaximeterFixedListItemDTO taximeterFixedListItemDTO = taximeterFixedListItemMapper.toDto(taximeterFixedListItem);
        restTaximeterFixedListItemMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(taximeterFixedListItemDTO))
            )
            .andExpect(status().isCreated());

        // Validate the TaximeterFixedListItem in the database
        List<TaximeterFixedListItem> taximeterFixedListItemList = taximeterFixedListItemRepository.findAll();
        assertThat(taximeterFixedListItemList).hasSize(databaseSizeBeforeCreate + 1);
        TaximeterFixedListItem testTaximeterFixedListItem = taximeterFixedListItemList.get(taximeterFixedListItemList.size() - 1);
        assertThat(testTaximeterFixedListItem.getFrom()).isEqualTo(DEFAULT_FROM);
        assertThat(testTaximeterFixedListItem.getTo()).isEqualTo(DEFAULT_TO);
        assertThat(testTaximeterFixedListItem.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testTaximeterFixedListItem.getValueReverse()).isEqualTo(DEFAULT_VALUE_REVERSE);
        assertThat(testTaximeterFixedListItem.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testTaximeterFixedListItem.getUpdated()).isEqualTo(DEFAULT_UPDATED);
    }

    @Test
    @Transactional
    void createTaximeterFixedListItemWithExistingId() throws Exception {
        // Create the TaximeterFixedListItem with an existing ID
        taximeterFixedListItem.setId(1L);
        TaximeterFixedListItemDTO taximeterFixedListItemDTO = taximeterFixedListItemMapper.toDto(taximeterFixedListItem);

        int databaseSizeBeforeCreate = taximeterFixedListItemRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTaximeterFixedListItemMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(taximeterFixedListItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TaximeterFixedListItem in the database
        List<TaximeterFixedListItem> taximeterFixedListItemList = taximeterFixedListItemRepository.findAll();
        assertThat(taximeterFixedListItemList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkFromIsRequired() throws Exception {
        int databaseSizeBeforeTest = taximeterFixedListItemRepository.findAll().size();
        // set the field null
        taximeterFixedListItem.setFrom(null);

        // Create the TaximeterFixedListItem, which fails.
        TaximeterFixedListItemDTO taximeterFixedListItemDTO = taximeterFixedListItemMapper.toDto(taximeterFixedListItem);

        restTaximeterFixedListItemMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(taximeterFixedListItemDTO))
            )
            .andExpect(status().isBadRequest());

        List<TaximeterFixedListItem> taximeterFixedListItemList = taximeterFixedListItemRepository.findAll();
        assertThat(taximeterFixedListItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkToIsRequired() throws Exception {
        int databaseSizeBeforeTest = taximeterFixedListItemRepository.findAll().size();
        // set the field null
        taximeterFixedListItem.setTo(null);

        // Create the TaximeterFixedListItem, which fails.
        TaximeterFixedListItemDTO taximeterFixedListItemDTO = taximeterFixedListItemMapper.toDto(taximeterFixedListItem);

        restTaximeterFixedListItemMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(taximeterFixedListItemDTO))
            )
            .andExpect(status().isBadRequest());

        List<TaximeterFixedListItem> taximeterFixedListItemList = taximeterFixedListItemRepository.findAll();
        assertThat(taximeterFixedListItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = taximeterFixedListItemRepository.findAll().size();
        // set the field null
        taximeterFixedListItem.setValue(null);

        // Create the TaximeterFixedListItem, which fails.
        TaximeterFixedListItemDTO taximeterFixedListItemDTO = taximeterFixedListItemMapper.toDto(taximeterFixedListItem);

        restTaximeterFixedListItemMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(taximeterFixedListItemDTO))
            )
            .andExpect(status().isBadRequest());

        List<TaximeterFixedListItem> taximeterFixedListItemList = taximeterFixedListItemRepository.findAll();
        assertThat(taximeterFixedListItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTaximeterFixedListItems() throws Exception {
        // Initialize the database
        taximeterFixedListItemRepository.saveAndFlush(taximeterFixedListItem);

        // Get all the taximeterFixedListItemList
        restTaximeterFixedListItemMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(taximeterFixedListItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].from").value(hasItem(DEFAULT_FROM)))
            .andExpect(jsonPath("$.[*].to").value(hasItem(DEFAULT_TO)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].valueReverse").value(hasItem(DEFAULT_VALUE_REVERSE.doubleValue())))
            .andExpect(jsonPath("$.[*].created").value(hasItem(sameInstant(DEFAULT_CREATED))))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(sameInstant(DEFAULT_UPDATED))));
    }

    @Test
    @Transactional
    void getTaximeterFixedListItem() throws Exception {
        // Initialize the database
        taximeterFixedListItemRepository.saveAndFlush(taximeterFixedListItem);

        // Get the taximeterFixedListItem
        restTaximeterFixedListItemMockMvc
            .perform(get(ENTITY_API_URL_ID, taximeterFixedListItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(taximeterFixedListItem.getId().intValue()))
            .andExpect(jsonPath("$.from").value(DEFAULT_FROM))
            .andExpect(jsonPath("$.to").value(DEFAULT_TO))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.doubleValue()))
            .andExpect(jsonPath("$.valueReverse").value(DEFAULT_VALUE_REVERSE.doubleValue()))
            .andExpect(jsonPath("$.created").value(sameInstant(DEFAULT_CREATED)))
            .andExpect(jsonPath("$.updated").value(sameInstant(DEFAULT_UPDATED)));
    }

    @Test
    @Transactional
    void getNonExistingTaximeterFixedListItem() throws Exception {
        // Get the taximeterFixedListItem
        restTaximeterFixedListItemMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTaximeterFixedListItem() throws Exception {
        // Initialize the database
        taximeterFixedListItemRepository.saveAndFlush(taximeterFixedListItem);

        int databaseSizeBeforeUpdate = taximeterFixedListItemRepository.findAll().size();

        // Update the taximeterFixedListItem
        TaximeterFixedListItem updatedTaximeterFixedListItem = taximeterFixedListItemRepository
            .findById(taximeterFixedListItem.getId())
            .get();
        // Disconnect from session so that the updates on updatedTaximeterFixedListItem are not directly saved in db
        em.detach(updatedTaximeterFixedListItem);
        updatedTaximeterFixedListItem
            .from(UPDATED_FROM)
            .to(UPDATED_TO)
            .value(UPDATED_VALUE)
            .valueReverse(UPDATED_VALUE_REVERSE)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        TaximeterFixedListItemDTO taximeterFixedListItemDTO = taximeterFixedListItemMapper.toDto(updatedTaximeterFixedListItem);

        restTaximeterFixedListItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, taximeterFixedListItemDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(taximeterFixedListItemDTO))
            )
            .andExpect(status().isOk());

        // Validate the TaximeterFixedListItem in the database
        List<TaximeterFixedListItem> taximeterFixedListItemList = taximeterFixedListItemRepository.findAll();
        assertThat(taximeterFixedListItemList).hasSize(databaseSizeBeforeUpdate);
        TaximeterFixedListItem testTaximeterFixedListItem = taximeterFixedListItemList.get(taximeterFixedListItemList.size() - 1);
        assertThat(testTaximeterFixedListItem.getFrom()).isEqualTo(UPDATED_FROM);
        assertThat(testTaximeterFixedListItem.getTo()).isEqualTo(UPDATED_TO);
        assertThat(testTaximeterFixedListItem.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testTaximeterFixedListItem.getValueReverse()).isEqualTo(UPDATED_VALUE_REVERSE);
        assertThat(testTaximeterFixedListItem.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testTaximeterFixedListItem.getUpdated()).isEqualTo(UPDATED_UPDATED);
    }

    @Test
    @Transactional
    void putNonExistingTaximeterFixedListItem() throws Exception {
        int databaseSizeBeforeUpdate = taximeterFixedListItemRepository.findAll().size();
        taximeterFixedListItem.setId(count.incrementAndGet());

        // Create the TaximeterFixedListItem
        TaximeterFixedListItemDTO taximeterFixedListItemDTO = taximeterFixedListItemMapper.toDto(taximeterFixedListItem);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTaximeterFixedListItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, taximeterFixedListItemDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(taximeterFixedListItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TaximeterFixedListItem in the database
        List<TaximeterFixedListItem> taximeterFixedListItemList = taximeterFixedListItemRepository.findAll();
        assertThat(taximeterFixedListItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTaximeterFixedListItem() throws Exception {
        int databaseSizeBeforeUpdate = taximeterFixedListItemRepository.findAll().size();
        taximeterFixedListItem.setId(count.incrementAndGet());

        // Create the TaximeterFixedListItem
        TaximeterFixedListItemDTO taximeterFixedListItemDTO = taximeterFixedListItemMapper.toDto(taximeterFixedListItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaximeterFixedListItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(taximeterFixedListItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TaximeterFixedListItem in the database
        List<TaximeterFixedListItem> taximeterFixedListItemList = taximeterFixedListItemRepository.findAll();
        assertThat(taximeterFixedListItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTaximeterFixedListItem() throws Exception {
        int databaseSizeBeforeUpdate = taximeterFixedListItemRepository.findAll().size();
        taximeterFixedListItem.setId(count.incrementAndGet());

        // Create the TaximeterFixedListItem
        TaximeterFixedListItemDTO taximeterFixedListItemDTO = taximeterFixedListItemMapper.toDto(taximeterFixedListItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaximeterFixedListItemMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(taximeterFixedListItemDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TaximeterFixedListItem in the database
        List<TaximeterFixedListItem> taximeterFixedListItemList = taximeterFixedListItemRepository.findAll();
        assertThat(taximeterFixedListItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTaximeterFixedListItemWithPatch() throws Exception {
        // Initialize the database
        taximeterFixedListItemRepository.saveAndFlush(taximeterFixedListItem);

        int databaseSizeBeforeUpdate = taximeterFixedListItemRepository.findAll().size();

        // Update the taximeterFixedListItem using partial update
        TaximeterFixedListItem partialUpdatedTaximeterFixedListItem = new TaximeterFixedListItem();
        partialUpdatedTaximeterFixedListItem.setId(taximeterFixedListItem.getId());

        partialUpdatedTaximeterFixedListItem.updated(UPDATED_UPDATED);

        restTaximeterFixedListItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTaximeterFixedListItem.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTaximeterFixedListItem))
            )
            .andExpect(status().isOk());

        // Validate the TaximeterFixedListItem in the database
        List<TaximeterFixedListItem> taximeterFixedListItemList = taximeterFixedListItemRepository.findAll();
        assertThat(taximeterFixedListItemList).hasSize(databaseSizeBeforeUpdate);
        TaximeterFixedListItem testTaximeterFixedListItem = taximeterFixedListItemList.get(taximeterFixedListItemList.size() - 1);
        assertThat(testTaximeterFixedListItem.getFrom()).isEqualTo(DEFAULT_FROM);
        assertThat(testTaximeterFixedListItem.getTo()).isEqualTo(DEFAULT_TO);
        assertThat(testTaximeterFixedListItem.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testTaximeterFixedListItem.getValueReverse()).isEqualTo(DEFAULT_VALUE_REVERSE);
        assertThat(testTaximeterFixedListItem.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testTaximeterFixedListItem.getUpdated()).isEqualTo(UPDATED_UPDATED);
    }

    @Test
    @Transactional
    void fullUpdateTaximeterFixedListItemWithPatch() throws Exception {
        // Initialize the database
        taximeterFixedListItemRepository.saveAndFlush(taximeterFixedListItem);

        int databaseSizeBeforeUpdate = taximeterFixedListItemRepository.findAll().size();

        // Update the taximeterFixedListItem using partial update
        TaximeterFixedListItem partialUpdatedTaximeterFixedListItem = new TaximeterFixedListItem();
        partialUpdatedTaximeterFixedListItem.setId(taximeterFixedListItem.getId());

        partialUpdatedTaximeterFixedListItem
            .from(UPDATED_FROM)
            .to(UPDATED_TO)
            .value(UPDATED_VALUE)
            .valueReverse(UPDATED_VALUE_REVERSE)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);

        restTaximeterFixedListItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTaximeterFixedListItem.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTaximeterFixedListItem))
            )
            .andExpect(status().isOk());

        // Validate the TaximeterFixedListItem in the database
        List<TaximeterFixedListItem> taximeterFixedListItemList = taximeterFixedListItemRepository.findAll();
        assertThat(taximeterFixedListItemList).hasSize(databaseSizeBeforeUpdate);
        TaximeterFixedListItem testTaximeterFixedListItem = taximeterFixedListItemList.get(taximeterFixedListItemList.size() - 1);
        assertThat(testTaximeterFixedListItem.getFrom()).isEqualTo(UPDATED_FROM);
        assertThat(testTaximeterFixedListItem.getTo()).isEqualTo(UPDATED_TO);
        assertThat(testTaximeterFixedListItem.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testTaximeterFixedListItem.getValueReverse()).isEqualTo(UPDATED_VALUE_REVERSE);
        assertThat(testTaximeterFixedListItem.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testTaximeterFixedListItem.getUpdated()).isEqualTo(UPDATED_UPDATED);
    }

    @Test
    @Transactional
    void patchNonExistingTaximeterFixedListItem() throws Exception {
        int databaseSizeBeforeUpdate = taximeterFixedListItemRepository.findAll().size();
        taximeterFixedListItem.setId(count.incrementAndGet());

        // Create the TaximeterFixedListItem
        TaximeterFixedListItemDTO taximeterFixedListItemDTO = taximeterFixedListItemMapper.toDto(taximeterFixedListItem);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTaximeterFixedListItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, taximeterFixedListItemDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(taximeterFixedListItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TaximeterFixedListItem in the database
        List<TaximeterFixedListItem> taximeterFixedListItemList = taximeterFixedListItemRepository.findAll();
        assertThat(taximeterFixedListItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTaximeterFixedListItem() throws Exception {
        int databaseSizeBeforeUpdate = taximeterFixedListItemRepository.findAll().size();
        taximeterFixedListItem.setId(count.incrementAndGet());

        // Create the TaximeterFixedListItem
        TaximeterFixedListItemDTO taximeterFixedListItemDTO = taximeterFixedListItemMapper.toDto(taximeterFixedListItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaximeterFixedListItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(taximeterFixedListItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TaximeterFixedListItem in the database
        List<TaximeterFixedListItem> taximeterFixedListItemList = taximeterFixedListItemRepository.findAll();
        assertThat(taximeterFixedListItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTaximeterFixedListItem() throws Exception {
        int databaseSizeBeforeUpdate = taximeterFixedListItemRepository.findAll().size();
        taximeterFixedListItem.setId(count.incrementAndGet());

        // Create the TaximeterFixedListItem
        TaximeterFixedListItemDTO taximeterFixedListItemDTO = taximeterFixedListItemMapper.toDto(taximeterFixedListItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaximeterFixedListItemMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(taximeterFixedListItemDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TaximeterFixedListItem in the database
        List<TaximeterFixedListItem> taximeterFixedListItemList = taximeterFixedListItemRepository.findAll();
        assertThat(taximeterFixedListItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTaximeterFixedListItem() throws Exception {
        // Initialize the database
        taximeterFixedListItemRepository.saveAndFlush(taximeterFixedListItem);

        int databaseSizeBeforeDelete = taximeterFixedListItemRepository.findAll().size();

        // Delete the taximeterFixedListItem
        restTaximeterFixedListItemMockMvc
            .perform(delete(ENTITY_API_URL_ID, taximeterFixedListItem.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TaximeterFixedListItem> taximeterFixedListItemList = taximeterFixedListItemRepository.findAll();
        assertThat(taximeterFixedListItemList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
