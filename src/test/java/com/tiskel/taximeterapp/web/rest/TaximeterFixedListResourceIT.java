package com.tiskel.taximeterapp.web.rest;

import static com.tiskel.taximeterapp.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.tiskel.taximeterapp.IntegrationTest;
import com.tiskel.taximeterapp.domain.TaximeterFixedList;
import com.tiskel.taximeterapp.domain.enumeration.TaximeterFixedListType;
import com.tiskel.taximeterapp.domain.enumeration.TaximeterMultipickupMatchingRules;
import com.tiskel.taximeterapp.repository.TaximeterFixedListRepository;
import com.tiskel.taximeterapp.service.dto.TaximeterFixedListDTO;
import com.tiskel.taximeterapp.service.mapper.TaximeterFixedListMapper;
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
 * Integration tests for the {@link TaximeterFixedListResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TaximeterFixedListResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final TaximeterFixedListType DEFAULT_TYPE = TaximeterFixedListType.POST_CODE;
    private static final TaximeterFixedListType UPDATED_TYPE = TaximeterFixedListType.ZONE;

    private static final TaximeterMultipickupMatchingRules DEFAULT_MATCHING_RULES = TaximeterMultipickupMatchingRules.NOT_ALLOWED;
    private static final TaximeterMultipickupMatchingRules UPDATED_MATCHING_RULES = TaximeterMultipickupMatchingRules.STRINCT;

    private static final ZonedDateTime DEFAULT_CREATED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_UPDATED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/taximeter-fixed-lists";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TaximeterFixedListRepository taximeterFixedListRepository;

    @Autowired
    private TaximeterFixedListMapper taximeterFixedListMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTaximeterFixedListMockMvc;

    private TaximeterFixedList taximeterFixedList;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TaximeterFixedList createEntity(EntityManager em) {
        TaximeterFixedList taximeterFixedList = new TaximeterFixedList()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .type(DEFAULT_TYPE)
            .matchingRules(DEFAULT_MATCHING_RULES)
            .created(DEFAULT_CREATED)
            .updated(DEFAULT_UPDATED);
        return taximeterFixedList;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TaximeterFixedList createUpdatedEntity(EntityManager em) {
        TaximeterFixedList taximeterFixedList = new TaximeterFixedList()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .type(UPDATED_TYPE)
            .matchingRules(UPDATED_MATCHING_RULES)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        return taximeterFixedList;
    }

    @BeforeEach
    public void initTest() {
        taximeterFixedList = createEntity(em);
    }

    @Test
    @Transactional
    void createTaximeterFixedList() throws Exception {
        int databaseSizeBeforeCreate = taximeterFixedListRepository.findAll().size();
        // Create the TaximeterFixedList
        TaximeterFixedListDTO taximeterFixedListDTO = taximeterFixedListMapper.toDto(taximeterFixedList);
        restTaximeterFixedListMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(taximeterFixedListDTO))
            )
            .andExpect(status().isCreated());

        // Validate the TaximeterFixedList in the database
        List<TaximeterFixedList> taximeterFixedListList = taximeterFixedListRepository.findAll();
        assertThat(taximeterFixedListList).hasSize(databaseSizeBeforeCreate + 1);
        TaximeterFixedList testTaximeterFixedList = taximeterFixedListList.get(taximeterFixedListList.size() - 1);
        assertThat(testTaximeterFixedList.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTaximeterFixedList.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testTaximeterFixedList.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testTaximeterFixedList.getMatchingRules()).isEqualTo(DEFAULT_MATCHING_RULES);
        assertThat(testTaximeterFixedList.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testTaximeterFixedList.getUpdated()).isEqualTo(DEFAULT_UPDATED);
    }

    @Test
    @Transactional
    void createTaximeterFixedListWithExistingId() throws Exception {
        // Create the TaximeterFixedList with an existing ID
        taximeterFixedList.setId(1L);
        TaximeterFixedListDTO taximeterFixedListDTO = taximeterFixedListMapper.toDto(taximeterFixedList);

        int databaseSizeBeforeCreate = taximeterFixedListRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTaximeterFixedListMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(taximeterFixedListDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TaximeterFixedList in the database
        List<TaximeterFixedList> taximeterFixedListList = taximeterFixedListRepository.findAll();
        assertThat(taximeterFixedListList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = taximeterFixedListRepository.findAll().size();
        // set the field null
        taximeterFixedList.setName(null);

        // Create the TaximeterFixedList, which fails.
        TaximeterFixedListDTO taximeterFixedListDTO = taximeterFixedListMapper.toDto(taximeterFixedList);

        restTaximeterFixedListMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(taximeterFixedListDTO))
            )
            .andExpect(status().isBadRequest());

        List<TaximeterFixedList> taximeterFixedListList = taximeterFixedListRepository.findAll();
        assertThat(taximeterFixedListList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = taximeterFixedListRepository.findAll().size();
        // set the field null
        taximeterFixedList.setType(null);

        // Create the TaximeterFixedList, which fails.
        TaximeterFixedListDTO taximeterFixedListDTO = taximeterFixedListMapper.toDto(taximeterFixedList);

        restTaximeterFixedListMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(taximeterFixedListDTO))
            )
            .andExpect(status().isBadRequest());

        List<TaximeterFixedList> taximeterFixedListList = taximeterFixedListRepository.findAll();
        assertThat(taximeterFixedListList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTaximeterFixedLists() throws Exception {
        // Initialize the database
        taximeterFixedListRepository.saveAndFlush(taximeterFixedList);

        // Get all the taximeterFixedListList
        restTaximeterFixedListMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(taximeterFixedList.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].matchingRules").value(hasItem(DEFAULT_MATCHING_RULES.toString())))
            .andExpect(jsonPath("$.[*].created").value(hasItem(sameInstant(DEFAULT_CREATED))))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(sameInstant(DEFAULT_UPDATED))));
    }

    @Test
    @Transactional
    void getTaximeterFixedList() throws Exception {
        // Initialize the database
        taximeterFixedListRepository.saveAndFlush(taximeterFixedList);

        // Get the taximeterFixedList
        restTaximeterFixedListMockMvc
            .perform(get(ENTITY_API_URL_ID, taximeterFixedList.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(taximeterFixedList.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.matchingRules").value(DEFAULT_MATCHING_RULES.toString()))
            .andExpect(jsonPath("$.created").value(sameInstant(DEFAULT_CREATED)))
            .andExpect(jsonPath("$.updated").value(sameInstant(DEFAULT_UPDATED)));
    }

    @Test
    @Transactional
    void getNonExistingTaximeterFixedList() throws Exception {
        // Get the taximeterFixedList
        restTaximeterFixedListMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTaximeterFixedList() throws Exception {
        // Initialize the database
        taximeterFixedListRepository.saveAndFlush(taximeterFixedList);

        int databaseSizeBeforeUpdate = taximeterFixedListRepository.findAll().size();

        // Update the taximeterFixedList
        TaximeterFixedList updatedTaximeterFixedList = taximeterFixedListRepository.findById(taximeterFixedList.getId()).get();
        // Disconnect from session so that the updates on updatedTaximeterFixedList are not directly saved in db
        em.detach(updatedTaximeterFixedList);
        updatedTaximeterFixedList
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .type(UPDATED_TYPE)
            .matchingRules(UPDATED_MATCHING_RULES)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        TaximeterFixedListDTO taximeterFixedListDTO = taximeterFixedListMapper.toDto(updatedTaximeterFixedList);

        restTaximeterFixedListMockMvc
            .perform(
                put(ENTITY_API_URL_ID, taximeterFixedListDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(taximeterFixedListDTO))
            )
            .andExpect(status().isOk());

        // Validate the TaximeterFixedList in the database
        List<TaximeterFixedList> taximeterFixedListList = taximeterFixedListRepository.findAll();
        assertThat(taximeterFixedListList).hasSize(databaseSizeBeforeUpdate);
        TaximeterFixedList testTaximeterFixedList = taximeterFixedListList.get(taximeterFixedListList.size() - 1);
        assertThat(testTaximeterFixedList.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTaximeterFixedList.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTaximeterFixedList.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testTaximeterFixedList.getMatchingRules()).isEqualTo(UPDATED_MATCHING_RULES);
        assertThat(testTaximeterFixedList.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testTaximeterFixedList.getUpdated()).isEqualTo(UPDATED_UPDATED);
    }

    @Test
    @Transactional
    void putNonExistingTaximeterFixedList() throws Exception {
        int databaseSizeBeforeUpdate = taximeterFixedListRepository.findAll().size();
        taximeterFixedList.setId(count.incrementAndGet());

        // Create the TaximeterFixedList
        TaximeterFixedListDTO taximeterFixedListDTO = taximeterFixedListMapper.toDto(taximeterFixedList);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTaximeterFixedListMockMvc
            .perform(
                put(ENTITY_API_URL_ID, taximeterFixedListDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(taximeterFixedListDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TaximeterFixedList in the database
        List<TaximeterFixedList> taximeterFixedListList = taximeterFixedListRepository.findAll();
        assertThat(taximeterFixedListList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTaximeterFixedList() throws Exception {
        int databaseSizeBeforeUpdate = taximeterFixedListRepository.findAll().size();
        taximeterFixedList.setId(count.incrementAndGet());

        // Create the TaximeterFixedList
        TaximeterFixedListDTO taximeterFixedListDTO = taximeterFixedListMapper.toDto(taximeterFixedList);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaximeterFixedListMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(taximeterFixedListDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TaximeterFixedList in the database
        List<TaximeterFixedList> taximeterFixedListList = taximeterFixedListRepository.findAll();
        assertThat(taximeterFixedListList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTaximeterFixedList() throws Exception {
        int databaseSizeBeforeUpdate = taximeterFixedListRepository.findAll().size();
        taximeterFixedList.setId(count.incrementAndGet());

        // Create the TaximeterFixedList
        TaximeterFixedListDTO taximeterFixedListDTO = taximeterFixedListMapper.toDto(taximeterFixedList);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaximeterFixedListMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(taximeterFixedListDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TaximeterFixedList in the database
        List<TaximeterFixedList> taximeterFixedListList = taximeterFixedListRepository.findAll();
        assertThat(taximeterFixedListList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTaximeterFixedListWithPatch() throws Exception {
        // Initialize the database
        taximeterFixedListRepository.saveAndFlush(taximeterFixedList);

        int databaseSizeBeforeUpdate = taximeterFixedListRepository.findAll().size();

        // Update the taximeterFixedList using partial update
        TaximeterFixedList partialUpdatedTaximeterFixedList = new TaximeterFixedList();
        partialUpdatedTaximeterFixedList.setId(taximeterFixedList.getId());

        partialUpdatedTaximeterFixedList
            .description(UPDATED_DESCRIPTION)
            .type(UPDATED_TYPE)
            .matchingRules(UPDATED_MATCHING_RULES)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);

        restTaximeterFixedListMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTaximeterFixedList.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTaximeterFixedList))
            )
            .andExpect(status().isOk());

        // Validate the TaximeterFixedList in the database
        List<TaximeterFixedList> taximeterFixedListList = taximeterFixedListRepository.findAll();
        assertThat(taximeterFixedListList).hasSize(databaseSizeBeforeUpdate);
        TaximeterFixedList testTaximeterFixedList = taximeterFixedListList.get(taximeterFixedListList.size() - 1);
        assertThat(testTaximeterFixedList.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTaximeterFixedList.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTaximeterFixedList.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testTaximeterFixedList.getMatchingRules()).isEqualTo(UPDATED_MATCHING_RULES);
        assertThat(testTaximeterFixedList.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testTaximeterFixedList.getUpdated()).isEqualTo(UPDATED_UPDATED);
    }

    @Test
    @Transactional
    void fullUpdateTaximeterFixedListWithPatch() throws Exception {
        // Initialize the database
        taximeterFixedListRepository.saveAndFlush(taximeterFixedList);

        int databaseSizeBeforeUpdate = taximeterFixedListRepository.findAll().size();

        // Update the taximeterFixedList using partial update
        TaximeterFixedList partialUpdatedTaximeterFixedList = new TaximeterFixedList();
        partialUpdatedTaximeterFixedList.setId(taximeterFixedList.getId());

        partialUpdatedTaximeterFixedList
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .type(UPDATED_TYPE)
            .matchingRules(UPDATED_MATCHING_RULES)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);

        restTaximeterFixedListMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTaximeterFixedList.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTaximeterFixedList))
            )
            .andExpect(status().isOk());

        // Validate the TaximeterFixedList in the database
        List<TaximeterFixedList> taximeterFixedListList = taximeterFixedListRepository.findAll();
        assertThat(taximeterFixedListList).hasSize(databaseSizeBeforeUpdate);
        TaximeterFixedList testTaximeterFixedList = taximeterFixedListList.get(taximeterFixedListList.size() - 1);
        assertThat(testTaximeterFixedList.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTaximeterFixedList.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTaximeterFixedList.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testTaximeterFixedList.getMatchingRules()).isEqualTo(UPDATED_MATCHING_RULES);
        assertThat(testTaximeterFixedList.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testTaximeterFixedList.getUpdated()).isEqualTo(UPDATED_UPDATED);
    }

    @Test
    @Transactional
    void patchNonExistingTaximeterFixedList() throws Exception {
        int databaseSizeBeforeUpdate = taximeterFixedListRepository.findAll().size();
        taximeterFixedList.setId(count.incrementAndGet());

        // Create the TaximeterFixedList
        TaximeterFixedListDTO taximeterFixedListDTO = taximeterFixedListMapper.toDto(taximeterFixedList);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTaximeterFixedListMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, taximeterFixedListDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(taximeterFixedListDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TaximeterFixedList in the database
        List<TaximeterFixedList> taximeterFixedListList = taximeterFixedListRepository.findAll();
        assertThat(taximeterFixedListList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTaximeterFixedList() throws Exception {
        int databaseSizeBeforeUpdate = taximeterFixedListRepository.findAll().size();
        taximeterFixedList.setId(count.incrementAndGet());

        // Create the TaximeterFixedList
        TaximeterFixedListDTO taximeterFixedListDTO = taximeterFixedListMapper.toDto(taximeterFixedList);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaximeterFixedListMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(taximeterFixedListDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TaximeterFixedList in the database
        List<TaximeterFixedList> taximeterFixedListList = taximeterFixedListRepository.findAll();
        assertThat(taximeterFixedListList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTaximeterFixedList() throws Exception {
        int databaseSizeBeforeUpdate = taximeterFixedListRepository.findAll().size();
        taximeterFixedList.setId(count.incrementAndGet());

        // Create the TaximeterFixedList
        TaximeterFixedListDTO taximeterFixedListDTO = taximeterFixedListMapper.toDto(taximeterFixedList);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaximeterFixedListMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(taximeterFixedListDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TaximeterFixedList in the database
        List<TaximeterFixedList> taximeterFixedListList = taximeterFixedListRepository.findAll();
        assertThat(taximeterFixedListList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTaximeterFixedList() throws Exception {
        // Initialize the database
        taximeterFixedListRepository.saveAndFlush(taximeterFixedList);

        int databaseSizeBeforeDelete = taximeterFixedListRepository.findAll().size();

        // Delete the taximeterFixedList
        restTaximeterFixedListMockMvc
            .perform(delete(ENTITY_API_URL_ID, taximeterFixedList.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TaximeterFixedList> taximeterFixedListList = taximeterFixedListRepository.findAll();
        assertThat(taximeterFixedListList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
