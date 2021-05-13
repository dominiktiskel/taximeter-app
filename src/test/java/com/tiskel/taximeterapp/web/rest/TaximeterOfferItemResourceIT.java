package com.tiskel.taximeterapp.web.rest;

import static com.tiskel.taximeterapp.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.tiskel.taximeterapp.IntegrationTest;
import com.tiskel.taximeterapp.domain.TaximeterOfferGroup;
import com.tiskel.taximeterapp.domain.TaximeterOfferItem;
import com.tiskel.taximeterapp.repository.TaximeterOfferItemRepository;
import com.tiskel.taximeterapp.service.TaximeterOfferItemService;
import com.tiskel.taximeterapp.service.dto.TaximeterOfferItemDTO;
import com.tiskel.taximeterapp.service.mapper.TaximeterOfferItemMapper;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link TaximeterOfferItemResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class TaximeterOfferItemResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Double DEFAULT_BILL_COMPANY_PAYS = 1D;
    private static final Double UPDATED_BILL_COMPANY_PAYS = 2D;

    private static final Double DEFAULT_CUSTOMER_PAYS = 1D;
    private static final Double UPDATED_CUSTOMER_PAYS = 2D;

    private static final Double DEFAULT_TAXI_GETS = 1D;
    private static final Double UPDATED_TAXI_GETS = 2D;

    private static final Double DEFAULT_TAXI_PAYS = 1D;
    private static final Double UPDATED_TAXI_PAYS = 2D;

    private static final ZonedDateTime DEFAULT_CREATED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_UPDATED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/taximeter-offer-items";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TaximeterOfferItemRepository taximeterOfferItemRepository;

    @Mock
    private TaximeterOfferItemRepository taximeterOfferItemRepositoryMock;

    @Autowired
    private TaximeterOfferItemMapper taximeterOfferItemMapper;

    @Mock
    private TaximeterOfferItemService taximeterOfferItemServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTaximeterOfferItemMockMvc;

    private TaximeterOfferItem taximeterOfferItem;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TaximeterOfferItem createEntity(EntityManager em) {
        TaximeterOfferItem taximeterOfferItem = new TaximeterOfferItem()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .billCompanyPays(DEFAULT_BILL_COMPANY_PAYS)
            .customerPays(DEFAULT_CUSTOMER_PAYS)
            .taxiGets(DEFAULT_TAXI_GETS)
            .taxiPays(DEFAULT_TAXI_PAYS)
            .created(DEFAULT_CREATED)
            .updated(DEFAULT_UPDATED);
        // Add required entity
        TaximeterOfferGroup taximeterOfferGroup;
        if (TestUtil.findAll(em, TaximeterOfferGroup.class).isEmpty()) {
            taximeterOfferGroup = TaximeterOfferGroupResourceIT.createEntity(em);
            em.persist(taximeterOfferGroup);
            em.flush();
        } else {
            taximeterOfferGroup = TestUtil.findAll(em, TaximeterOfferGroup.class).get(0);
        }
        taximeterOfferItem.setGroup(taximeterOfferGroup);
        return taximeterOfferItem;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TaximeterOfferItem createUpdatedEntity(EntityManager em) {
        TaximeterOfferItem taximeterOfferItem = new TaximeterOfferItem()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .billCompanyPays(UPDATED_BILL_COMPANY_PAYS)
            .customerPays(UPDATED_CUSTOMER_PAYS)
            .taxiGets(UPDATED_TAXI_GETS)
            .taxiPays(UPDATED_TAXI_PAYS)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        // Add required entity
        TaximeterOfferGroup taximeterOfferGroup;
        if (TestUtil.findAll(em, TaximeterOfferGroup.class).isEmpty()) {
            taximeterOfferGroup = TaximeterOfferGroupResourceIT.createUpdatedEntity(em);
            em.persist(taximeterOfferGroup);
            em.flush();
        } else {
            taximeterOfferGroup = TestUtil.findAll(em, TaximeterOfferGroup.class).get(0);
        }
        taximeterOfferItem.setGroup(taximeterOfferGroup);
        return taximeterOfferItem;
    }

    @BeforeEach
    public void initTest() {
        taximeterOfferItem = createEntity(em);
    }

    @Test
    @Transactional
    void createTaximeterOfferItem() throws Exception {
        int databaseSizeBeforeCreate = taximeterOfferItemRepository.findAll().size();
        // Create the TaximeterOfferItem
        TaximeterOfferItemDTO taximeterOfferItemDTO = taximeterOfferItemMapper.toDto(taximeterOfferItem);
        restTaximeterOfferItemMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(taximeterOfferItemDTO))
            )
            .andExpect(status().isCreated());

        // Validate the TaximeterOfferItem in the database
        List<TaximeterOfferItem> taximeterOfferItemList = taximeterOfferItemRepository.findAll();
        assertThat(taximeterOfferItemList).hasSize(databaseSizeBeforeCreate + 1);
        TaximeterOfferItem testTaximeterOfferItem = taximeterOfferItemList.get(taximeterOfferItemList.size() - 1);
        assertThat(testTaximeterOfferItem.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTaximeterOfferItem.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testTaximeterOfferItem.getBillCompanyPays()).isEqualTo(DEFAULT_BILL_COMPANY_PAYS);
        assertThat(testTaximeterOfferItem.getCustomerPays()).isEqualTo(DEFAULT_CUSTOMER_PAYS);
        assertThat(testTaximeterOfferItem.getTaxiGets()).isEqualTo(DEFAULT_TAXI_GETS);
        assertThat(testTaximeterOfferItem.getTaxiPays()).isEqualTo(DEFAULT_TAXI_PAYS);
        assertThat(testTaximeterOfferItem.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testTaximeterOfferItem.getUpdated()).isEqualTo(DEFAULT_UPDATED);
    }

    @Test
    @Transactional
    void createTaximeterOfferItemWithExistingId() throws Exception {
        // Create the TaximeterOfferItem with an existing ID
        taximeterOfferItem.setId(1L);
        TaximeterOfferItemDTO taximeterOfferItemDTO = taximeterOfferItemMapper.toDto(taximeterOfferItem);

        int databaseSizeBeforeCreate = taximeterOfferItemRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTaximeterOfferItemMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(taximeterOfferItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TaximeterOfferItem in the database
        List<TaximeterOfferItem> taximeterOfferItemList = taximeterOfferItemRepository.findAll();
        assertThat(taximeterOfferItemList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = taximeterOfferItemRepository.findAll().size();
        // set the field null
        taximeterOfferItem.setName(null);

        // Create the TaximeterOfferItem, which fails.
        TaximeterOfferItemDTO taximeterOfferItemDTO = taximeterOfferItemMapper.toDto(taximeterOfferItem);

        restTaximeterOfferItemMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(taximeterOfferItemDTO))
            )
            .andExpect(status().isBadRequest());

        List<TaximeterOfferItem> taximeterOfferItemList = taximeterOfferItemRepository.findAll();
        assertThat(taximeterOfferItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTaximeterOfferItems() throws Exception {
        // Initialize the database
        taximeterOfferItemRepository.saveAndFlush(taximeterOfferItem);

        // Get all the taximeterOfferItemList
        restTaximeterOfferItemMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(taximeterOfferItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].billCompanyPays").value(hasItem(DEFAULT_BILL_COMPANY_PAYS.doubleValue())))
            .andExpect(jsonPath("$.[*].customerPays").value(hasItem(DEFAULT_CUSTOMER_PAYS.doubleValue())))
            .andExpect(jsonPath("$.[*].taxiGets").value(hasItem(DEFAULT_TAXI_GETS.doubleValue())))
            .andExpect(jsonPath("$.[*].taxiPays").value(hasItem(DEFAULT_TAXI_PAYS.doubleValue())))
            .andExpect(jsonPath("$.[*].created").value(hasItem(sameInstant(DEFAULT_CREATED))))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(sameInstant(DEFAULT_UPDATED))));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllTaximeterOfferItemsWithEagerRelationshipsIsEnabled() throws Exception {
        when(taximeterOfferItemServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTaximeterOfferItemMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(taximeterOfferItemServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllTaximeterOfferItemsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(taximeterOfferItemServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTaximeterOfferItemMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(taximeterOfferItemServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getTaximeterOfferItem() throws Exception {
        // Initialize the database
        taximeterOfferItemRepository.saveAndFlush(taximeterOfferItem);

        // Get the taximeterOfferItem
        restTaximeterOfferItemMockMvc
            .perform(get(ENTITY_API_URL_ID, taximeterOfferItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(taximeterOfferItem.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.billCompanyPays").value(DEFAULT_BILL_COMPANY_PAYS.doubleValue()))
            .andExpect(jsonPath("$.customerPays").value(DEFAULT_CUSTOMER_PAYS.doubleValue()))
            .andExpect(jsonPath("$.taxiGets").value(DEFAULT_TAXI_GETS.doubleValue()))
            .andExpect(jsonPath("$.taxiPays").value(DEFAULT_TAXI_PAYS.doubleValue()))
            .andExpect(jsonPath("$.created").value(sameInstant(DEFAULT_CREATED)))
            .andExpect(jsonPath("$.updated").value(sameInstant(DEFAULT_UPDATED)));
    }

    @Test
    @Transactional
    void getNonExistingTaximeterOfferItem() throws Exception {
        // Get the taximeterOfferItem
        restTaximeterOfferItemMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTaximeterOfferItem() throws Exception {
        // Initialize the database
        taximeterOfferItemRepository.saveAndFlush(taximeterOfferItem);

        int databaseSizeBeforeUpdate = taximeterOfferItemRepository.findAll().size();

        // Update the taximeterOfferItem
        TaximeterOfferItem updatedTaximeterOfferItem = taximeterOfferItemRepository.findById(taximeterOfferItem.getId()).get();
        // Disconnect from session so that the updates on updatedTaximeterOfferItem are not directly saved in db
        em.detach(updatedTaximeterOfferItem);
        updatedTaximeterOfferItem
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .billCompanyPays(UPDATED_BILL_COMPANY_PAYS)
            .customerPays(UPDATED_CUSTOMER_PAYS)
            .taxiGets(UPDATED_TAXI_GETS)
            .taxiPays(UPDATED_TAXI_PAYS)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        TaximeterOfferItemDTO taximeterOfferItemDTO = taximeterOfferItemMapper.toDto(updatedTaximeterOfferItem);

        restTaximeterOfferItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, taximeterOfferItemDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(taximeterOfferItemDTO))
            )
            .andExpect(status().isOk());

        // Validate the TaximeterOfferItem in the database
        List<TaximeterOfferItem> taximeterOfferItemList = taximeterOfferItemRepository.findAll();
        assertThat(taximeterOfferItemList).hasSize(databaseSizeBeforeUpdate);
        TaximeterOfferItem testTaximeterOfferItem = taximeterOfferItemList.get(taximeterOfferItemList.size() - 1);
        assertThat(testTaximeterOfferItem.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTaximeterOfferItem.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTaximeterOfferItem.getBillCompanyPays()).isEqualTo(UPDATED_BILL_COMPANY_PAYS);
        assertThat(testTaximeterOfferItem.getCustomerPays()).isEqualTo(UPDATED_CUSTOMER_PAYS);
        assertThat(testTaximeterOfferItem.getTaxiGets()).isEqualTo(UPDATED_TAXI_GETS);
        assertThat(testTaximeterOfferItem.getTaxiPays()).isEqualTo(UPDATED_TAXI_PAYS);
        assertThat(testTaximeterOfferItem.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testTaximeterOfferItem.getUpdated()).isEqualTo(UPDATED_UPDATED);
    }

    @Test
    @Transactional
    void putNonExistingTaximeterOfferItem() throws Exception {
        int databaseSizeBeforeUpdate = taximeterOfferItemRepository.findAll().size();
        taximeterOfferItem.setId(count.incrementAndGet());

        // Create the TaximeterOfferItem
        TaximeterOfferItemDTO taximeterOfferItemDTO = taximeterOfferItemMapper.toDto(taximeterOfferItem);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTaximeterOfferItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, taximeterOfferItemDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(taximeterOfferItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TaximeterOfferItem in the database
        List<TaximeterOfferItem> taximeterOfferItemList = taximeterOfferItemRepository.findAll();
        assertThat(taximeterOfferItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTaximeterOfferItem() throws Exception {
        int databaseSizeBeforeUpdate = taximeterOfferItemRepository.findAll().size();
        taximeterOfferItem.setId(count.incrementAndGet());

        // Create the TaximeterOfferItem
        TaximeterOfferItemDTO taximeterOfferItemDTO = taximeterOfferItemMapper.toDto(taximeterOfferItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaximeterOfferItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(taximeterOfferItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TaximeterOfferItem in the database
        List<TaximeterOfferItem> taximeterOfferItemList = taximeterOfferItemRepository.findAll();
        assertThat(taximeterOfferItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTaximeterOfferItem() throws Exception {
        int databaseSizeBeforeUpdate = taximeterOfferItemRepository.findAll().size();
        taximeterOfferItem.setId(count.incrementAndGet());

        // Create the TaximeterOfferItem
        TaximeterOfferItemDTO taximeterOfferItemDTO = taximeterOfferItemMapper.toDto(taximeterOfferItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaximeterOfferItemMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(taximeterOfferItemDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TaximeterOfferItem in the database
        List<TaximeterOfferItem> taximeterOfferItemList = taximeterOfferItemRepository.findAll();
        assertThat(taximeterOfferItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTaximeterOfferItemWithPatch() throws Exception {
        // Initialize the database
        taximeterOfferItemRepository.saveAndFlush(taximeterOfferItem);

        int databaseSizeBeforeUpdate = taximeterOfferItemRepository.findAll().size();

        // Update the taximeterOfferItem using partial update
        TaximeterOfferItem partialUpdatedTaximeterOfferItem = new TaximeterOfferItem();
        partialUpdatedTaximeterOfferItem.setId(taximeterOfferItem.getId());

        partialUpdatedTaximeterOfferItem.customerPays(UPDATED_CUSTOMER_PAYS).taxiGets(UPDATED_TAXI_GETS).created(UPDATED_CREATED);

        restTaximeterOfferItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTaximeterOfferItem.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTaximeterOfferItem))
            )
            .andExpect(status().isOk());

        // Validate the TaximeterOfferItem in the database
        List<TaximeterOfferItem> taximeterOfferItemList = taximeterOfferItemRepository.findAll();
        assertThat(taximeterOfferItemList).hasSize(databaseSizeBeforeUpdate);
        TaximeterOfferItem testTaximeterOfferItem = taximeterOfferItemList.get(taximeterOfferItemList.size() - 1);
        assertThat(testTaximeterOfferItem.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTaximeterOfferItem.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testTaximeterOfferItem.getBillCompanyPays()).isEqualTo(DEFAULT_BILL_COMPANY_PAYS);
        assertThat(testTaximeterOfferItem.getCustomerPays()).isEqualTo(UPDATED_CUSTOMER_PAYS);
        assertThat(testTaximeterOfferItem.getTaxiGets()).isEqualTo(UPDATED_TAXI_GETS);
        assertThat(testTaximeterOfferItem.getTaxiPays()).isEqualTo(DEFAULT_TAXI_PAYS);
        assertThat(testTaximeterOfferItem.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testTaximeterOfferItem.getUpdated()).isEqualTo(DEFAULT_UPDATED);
    }

    @Test
    @Transactional
    void fullUpdateTaximeterOfferItemWithPatch() throws Exception {
        // Initialize the database
        taximeterOfferItemRepository.saveAndFlush(taximeterOfferItem);

        int databaseSizeBeforeUpdate = taximeterOfferItemRepository.findAll().size();

        // Update the taximeterOfferItem using partial update
        TaximeterOfferItem partialUpdatedTaximeterOfferItem = new TaximeterOfferItem();
        partialUpdatedTaximeterOfferItem.setId(taximeterOfferItem.getId());

        partialUpdatedTaximeterOfferItem
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .billCompanyPays(UPDATED_BILL_COMPANY_PAYS)
            .customerPays(UPDATED_CUSTOMER_PAYS)
            .taxiGets(UPDATED_TAXI_GETS)
            .taxiPays(UPDATED_TAXI_PAYS)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);

        restTaximeterOfferItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTaximeterOfferItem.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTaximeterOfferItem))
            )
            .andExpect(status().isOk());

        // Validate the TaximeterOfferItem in the database
        List<TaximeterOfferItem> taximeterOfferItemList = taximeterOfferItemRepository.findAll();
        assertThat(taximeterOfferItemList).hasSize(databaseSizeBeforeUpdate);
        TaximeterOfferItem testTaximeterOfferItem = taximeterOfferItemList.get(taximeterOfferItemList.size() - 1);
        assertThat(testTaximeterOfferItem.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTaximeterOfferItem.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTaximeterOfferItem.getBillCompanyPays()).isEqualTo(UPDATED_BILL_COMPANY_PAYS);
        assertThat(testTaximeterOfferItem.getCustomerPays()).isEqualTo(UPDATED_CUSTOMER_PAYS);
        assertThat(testTaximeterOfferItem.getTaxiGets()).isEqualTo(UPDATED_TAXI_GETS);
        assertThat(testTaximeterOfferItem.getTaxiPays()).isEqualTo(UPDATED_TAXI_PAYS);
        assertThat(testTaximeterOfferItem.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testTaximeterOfferItem.getUpdated()).isEqualTo(UPDATED_UPDATED);
    }

    @Test
    @Transactional
    void patchNonExistingTaximeterOfferItem() throws Exception {
        int databaseSizeBeforeUpdate = taximeterOfferItemRepository.findAll().size();
        taximeterOfferItem.setId(count.incrementAndGet());

        // Create the TaximeterOfferItem
        TaximeterOfferItemDTO taximeterOfferItemDTO = taximeterOfferItemMapper.toDto(taximeterOfferItem);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTaximeterOfferItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, taximeterOfferItemDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(taximeterOfferItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TaximeterOfferItem in the database
        List<TaximeterOfferItem> taximeterOfferItemList = taximeterOfferItemRepository.findAll();
        assertThat(taximeterOfferItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTaximeterOfferItem() throws Exception {
        int databaseSizeBeforeUpdate = taximeterOfferItemRepository.findAll().size();
        taximeterOfferItem.setId(count.incrementAndGet());

        // Create the TaximeterOfferItem
        TaximeterOfferItemDTO taximeterOfferItemDTO = taximeterOfferItemMapper.toDto(taximeterOfferItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaximeterOfferItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(taximeterOfferItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TaximeterOfferItem in the database
        List<TaximeterOfferItem> taximeterOfferItemList = taximeterOfferItemRepository.findAll();
        assertThat(taximeterOfferItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTaximeterOfferItem() throws Exception {
        int databaseSizeBeforeUpdate = taximeterOfferItemRepository.findAll().size();
        taximeterOfferItem.setId(count.incrementAndGet());

        // Create the TaximeterOfferItem
        TaximeterOfferItemDTO taximeterOfferItemDTO = taximeterOfferItemMapper.toDto(taximeterOfferItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaximeterOfferItemMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(taximeterOfferItemDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TaximeterOfferItem in the database
        List<TaximeterOfferItem> taximeterOfferItemList = taximeterOfferItemRepository.findAll();
        assertThat(taximeterOfferItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTaximeterOfferItem() throws Exception {
        // Initialize the database
        taximeterOfferItemRepository.saveAndFlush(taximeterOfferItem);

        int databaseSizeBeforeDelete = taximeterOfferItemRepository.findAll().size();

        // Delete the taximeterOfferItem
        restTaximeterOfferItemMockMvc
            .perform(delete(ENTITY_API_URL_ID, taximeterOfferItem.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TaximeterOfferItem> taximeterOfferItemList = taximeterOfferItemRepository.findAll();
        assertThat(taximeterOfferItemList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
