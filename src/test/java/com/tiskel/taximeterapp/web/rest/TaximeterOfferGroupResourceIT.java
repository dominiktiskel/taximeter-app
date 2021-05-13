package com.tiskel.taximeterapp.web.rest;

import static com.tiskel.taximeterapp.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.tiskel.taximeterapp.IntegrationTest;
import com.tiskel.taximeterapp.domain.TaximeterOffer;
import com.tiskel.taximeterapp.domain.TaximeterOfferGroup;
import com.tiskel.taximeterapp.domain.enumeration.TaximeterChargeByType;
import com.tiskel.taximeterapp.repository.TaximeterOfferGroupRepository;
import com.tiskel.taximeterapp.service.dto.TaximeterOfferGroupDTO;
import com.tiskel.taximeterapp.service.mapper.TaximeterOfferGroupMapper;
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
 * Integration tests for the {@link TaximeterOfferGroupResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TaximeterOfferGroupResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_INVOICE_AS = "AAAAAAAAAA";
    private static final String UPDATED_INVOICE_AS = "BBBBBBBBBB";

    private static final TaximeterChargeByType DEFAULT_CHARGE_BY = TaximeterChargeByType.DISTANCE;
    private static final TaximeterChargeByType UPDATED_CHARGE_BY = TaximeterChargeByType.TIME;

    private static final ZonedDateTime DEFAULT_CREATED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_UPDATED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/taximeter-offer-groups";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TaximeterOfferGroupRepository taximeterOfferGroupRepository;

    @Autowired
    private TaximeterOfferGroupMapper taximeterOfferGroupMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTaximeterOfferGroupMockMvc;

    private TaximeterOfferGroup taximeterOfferGroup;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TaximeterOfferGroup createEntity(EntityManager em) {
        TaximeterOfferGroup taximeterOfferGroup = new TaximeterOfferGroup()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .invoiceAs(DEFAULT_INVOICE_AS)
            .chargeBy(DEFAULT_CHARGE_BY)
            .created(DEFAULT_CREATED)
            .updated(DEFAULT_UPDATED);
        // Add required entity
        TaximeterOffer taximeterOffer;
        if (TestUtil.findAll(em, TaximeterOffer.class).isEmpty()) {
            taximeterOffer = TaximeterOfferResourceIT.createEntity(em);
            em.persist(taximeterOffer);
            em.flush();
        } else {
            taximeterOffer = TestUtil.findAll(em, TaximeterOffer.class).get(0);
        }
        taximeterOfferGroup.setOffer(taximeterOffer);
        return taximeterOfferGroup;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TaximeterOfferGroup createUpdatedEntity(EntityManager em) {
        TaximeterOfferGroup taximeterOfferGroup = new TaximeterOfferGroup()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .invoiceAs(UPDATED_INVOICE_AS)
            .chargeBy(UPDATED_CHARGE_BY)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        // Add required entity
        TaximeterOffer taximeterOffer;
        if (TestUtil.findAll(em, TaximeterOffer.class).isEmpty()) {
            taximeterOffer = TaximeterOfferResourceIT.createUpdatedEntity(em);
            em.persist(taximeterOffer);
            em.flush();
        } else {
            taximeterOffer = TestUtil.findAll(em, TaximeterOffer.class).get(0);
        }
        taximeterOfferGroup.setOffer(taximeterOffer);
        return taximeterOfferGroup;
    }

    @BeforeEach
    public void initTest() {
        taximeterOfferGroup = createEntity(em);
    }

    @Test
    @Transactional
    void createTaximeterOfferGroup() throws Exception {
        int databaseSizeBeforeCreate = taximeterOfferGroupRepository.findAll().size();
        // Create the TaximeterOfferGroup
        TaximeterOfferGroupDTO taximeterOfferGroupDTO = taximeterOfferGroupMapper.toDto(taximeterOfferGroup);
        restTaximeterOfferGroupMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(taximeterOfferGroupDTO))
            )
            .andExpect(status().isCreated());

        // Validate the TaximeterOfferGroup in the database
        List<TaximeterOfferGroup> taximeterOfferGroupList = taximeterOfferGroupRepository.findAll();
        assertThat(taximeterOfferGroupList).hasSize(databaseSizeBeforeCreate + 1);
        TaximeterOfferGroup testTaximeterOfferGroup = taximeterOfferGroupList.get(taximeterOfferGroupList.size() - 1);
        assertThat(testTaximeterOfferGroup.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTaximeterOfferGroup.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testTaximeterOfferGroup.getInvoiceAs()).isEqualTo(DEFAULT_INVOICE_AS);
        assertThat(testTaximeterOfferGroup.getChargeBy()).isEqualTo(DEFAULT_CHARGE_BY);
        assertThat(testTaximeterOfferGroup.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testTaximeterOfferGroup.getUpdated()).isEqualTo(DEFAULT_UPDATED);
    }

    @Test
    @Transactional
    void createTaximeterOfferGroupWithExistingId() throws Exception {
        // Create the TaximeterOfferGroup with an existing ID
        taximeterOfferGroup.setId(1L);
        TaximeterOfferGroupDTO taximeterOfferGroupDTO = taximeterOfferGroupMapper.toDto(taximeterOfferGroup);

        int databaseSizeBeforeCreate = taximeterOfferGroupRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTaximeterOfferGroupMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(taximeterOfferGroupDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TaximeterOfferGroup in the database
        List<TaximeterOfferGroup> taximeterOfferGroupList = taximeterOfferGroupRepository.findAll();
        assertThat(taximeterOfferGroupList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = taximeterOfferGroupRepository.findAll().size();
        // set the field null
        taximeterOfferGroup.setName(null);

        // Create the TaximeterOfferGroup, which fails.
        TaximeterOfferGroupDTO taximeterOfferGroupDTO = taximeterOfferGroupMapper.toDto(taximeterOfferGroup);

        restTaximeterOfferGroupMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(taximeterOfferGroupDTO))
            )
            .andExpect(status().isBadRequest());

        List<TaximeterOfferGroup> taximeterOfferGroupList = taximeterOfferGroupRepository.findAll();
        assertThat(taximeterOfferGroupList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkChargeByIsRequired() throws Exception {
        int databaseSizeBeforeTest = taximeterOfferGroupRepository.findAll().size();
        // set the field null
        taximeterOfferGroup.setChargeBy(null);

        // Create the TaximeterOfferGroup, which fails.
        TaximeterOfferGroupDTO taximeterOfferGroupDTO = taximeterOfferGroupMapper.toDto(taximeterOfferGroup);

        restTaximeterOfferGroupMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(taximeterOfferGroupDTO))
            )
            .andExpect(status().isBadRequest());

        List<TaximeterOfferGroup> taximeterOfferGroupList = taximeterOfferGroupRepository.findAll();
        assertThat(taximeterOfferGroupList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTaximeterOfferGroups() throws Exception {
        // Initialize the database
        taximeterOfferGroupRepository.saveAndFlush(taximeterOfferGroup);

        // Get all the taximeterOfferGroupList
        restTaximeterOfferGroupMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(taximeterOfferGroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].invoiceAs").value(hasItem(DEFAULT_INVOICE_AS)))
            .andExpect(jsonPath("$.[*].chargeBy").value(hasItem(DEFAULT_CHARGE_BY.toString())))
            .andExpect(jsonPath("$.[*].created").value(hasItem(sameInstant(DEFAULT_CREATED))))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(sameInstant(DEFAULT_UPDATED))));
    }

    @Test
    @Transactional
    void getTaximeterOfferGroup() throws Exception {
        // Initialize the database
        taximeterOfferGroupRepository.saveAndFlush(taximeterOfferGroup);

        // Get the taximeterOfferGroup
        restTaximeterOfferGroupMockMvc
            .perform(get(ENTITY_API_URL_ID, taximeterOfferGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(taximeterOfferGroup.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.invoiceAs").value(DEFAULT_INVOICE_AS))
            .andExpect(jsonPath("$.chargeBy").value(DEFAULT_CHARGE_BY.toString()))
            .andExpect(jsonPath("$.created").value(sameInstant(DEFAULT_CREATED)))
            .andExpect(jsonPath("$.updated").value(sameInstant(DEFAULT_UPDATED)));
    }

    @Test
    @Transactional
    void getNonExistingTaximeterOfferGroup() throws Exception {
        // Get the taximeterOfferGroup
        restTaximeterOfferGroupMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTaximeterOfferGroup() throws Exception {
        // Initialize the database
        taximeterOfferGroupRepository.saveAndFlush(taximeterOfferGroup);

        int databaseSizeBeforeUpdate = taximeterOfferGroupRepository.findAll().size();

        // Update the taximeterOfferGroup
        TaximeterOfferGroup updatedTaximeterOfferGroup = taximeterOfferGroupRepository.findById(taximeterOfferGroup.getId()).get();
        // Disconnect from session so that the updates on updatedTaximeterOfferGroup are not directly saved in db
        em.detach(updatedTaximeterOfferGroup);
        updatedTaximeterOfferGroup
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .invoiceAs(UPDATED_INVOICE_AS)
            .chargeBy(UPDATED_CHARGE_BY)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);
        TaximeterOfferGroupDTO taximeterOfferGroupDTO = taximeterOfferGroupMapper.toDto(updatedTaximeterOfferGroup);

        restTaximeterOfferGroupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, taximeterOfferGroupDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(taximeterOfferGroupDTO))
            )
            .andExpect(status().isOk());

        // Validate the TaximeterOfferGroup in the database
        List<TaximeterOfferGroup> taximeterOfferGroupList = taximeterOfferGroupRepository.findAll();
        assertThat(taximeterOfferGroupList).hasSize(databaseSizeBeforeUpdate);
        TaximeterOfferGroup testTaximeterOfferGroup = taximeterOfferGroupList.get(taximeterOfferGroupList.size() - 1);
        assertThat(testTaximeterOfferGroup.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTaximeterOfferGroup.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTaximeterOfferGroup.getInvoiceAs()).isEqualTo(UPDATED_INVOICE_AS);
        assertThat(testTaximeterOfferGroup.getChargeBy()).isEqualTo(UPDATED_CHARGE_BY);
        assertThat(testTaximeterOfferGroup.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testTaximeterOfferGroup.getUpdated()).isEqualTo(UPDATED_UPDATED);
    }

    @Test
    @Transactional
    void putNonExistingTaximeterOfferGroup() throws Exception {
        int databaseSizeBeforeUpdate = taximeterOfferGroupRepository.findAll().size();
        taximeterOfferGroup.setId(count.incrementAndGet());

        // Create the TaximeterOfferGroup
        TaximeterOfferGroupDTO taximeterOfferGroupDTO = taximeterOfferGroupMapper.toDto(taximeterOfferGroup);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTaximeterOfferGroupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, taximeterOfferGroupDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(taximeterOfferGroupDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TaximeterOfferGroup in the database
        List<TaximeterOfferGroup> taximeterOfferGroupList = taximeterOfferGroupRepository.findAll();
        assertThat(taximeterOfferGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTaximeterOfferGroup() throws Exception {
        int databaseSizeBeforeUpdate = taximeterOfferGroupRepository.findAll().size();
        taximeterOfferGroup.setId(count.incrementAndGet());

        // Create the TaximeterOfferGroup
        TaximeterOfferGroupDTO taximeterOfferGroupDTO = taximeterOfferGroupMapper.toDto(taximeterOfferGroup);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaximeterOfferGroupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(taximeterOfferGroupDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TaximeterOfferGroup in the database
        List<TaximeterOfferGroup> taximeterOfferGroupList = taximeterOfferGroupRepository.findAll();
        assertThat(taximeterOfferGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTaximeterOfferGroup() throws Exception {
        int databaseSizeBeforeUpdate = taximeterOfferGroupRepository.findAll().size();
        taximeterOfferGroup.setId(count.incrementAndGet());

        // Create the TaximeterOfferGroup
        TaximeterOfferGroupDTO taximeterOfferGroupDTO = taximeterOfferGroupMapper.toDto(taximeterOfferGroup);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaximeterOfferGroupMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(taximeterOfferGroupDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TaximeterOfferGroup in the database
        List<TaximeterOfferGroup> taximeterOfferGroupList = taximeterOfferGroupRepository.findAll();
        assertThat(taximeterOfferGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTaximeterOfferGroupWithPatch() throws Exception {
        // Initialize the database
        taximeterOfferGroupRepository.saveAndFlush(taximeterOfferGroup);

        int databaseSizeBeforeUpdate = taximeterOfferGroupRepository.findAll().size();

        // Update the taximeterOfferGroup using partial update
        TaximeterOfferGroup partialUpdatedTaximeterOfferGroup = new TaximeterOfferGroup();
        partialUpdatedTaximeterOfferGroup.setId(taximeterOfferGroup.getId());

        partialUpdatedTaximeterOfferGroup
            .name(UPDATED_NAME)
            .invoiceAs(UPDATED_INVOICE_AS)
            .chargeBy(UPDATED_CHARGE_BY)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);

        restTaximeterOfferGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTaximeterOfferGroup.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTaximeterOfferGroup))
            )
            .andExpect(status().isOk());

        // Validate the TaximeterOfferGroup in the database
        List<TaximeterOfferGroup> taximeterOfferGroupList = taximeterOfferGroupRepository.findAll();
        assertThat(taximeterOfferGroupList).hasSize(databaseSizeBeforeUpdate);
        TaximeterOfferGroup testTaximeterOfferGroup = taximeterOfferGroupList.get(taximeterOfferGroupList.size() - 1);
        assertThat(testTaximeterOfferGroup.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTaximeterOfferGroup.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testTaximeterOfferGroup.getInvoiceAs()).isEqualTo(UPDATED_INVOICE_AS);
        assertThat(testTaximeterOfferGroup.getChargeBy()).isEqualTo(UPDATED_CHARGE_BY);
        assertThat(testTaximeterOfferGroup.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testTaximeterOfferGroup.getUpdated()).isEqualTo(UPDATED_UPDATED);
    }

    @Test
    @Transactional
    void fullUpdateTaximeterOfferGroupWithPatch() throws Exception {
        // Initialize the database
        taximeterOfferGroupRepository.saveAndFlush(taximeterOfferGroup);

        int databaseSizeBeforeUpdate = taximeterOfferGroupRepository.findAll().size();

        // Update the taximeterOfferGroup using partial update
        TaximeterOfferGroup partialUpdatedTaximeterOfferGroup = new TaximeterOfferGroup();
        partialUpdatedTaximeterOfferGroup.setId(taximeterOfferGroup.getId());

        partialUpdatedTaximeterOfferGroup
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .invoiceAs(UPDATED_INVOICE_AS)
            .chargeBy(UPDATED_CHARGE_BY)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED);

        restTaximeterOfferGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTaximeterOfferGroup.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTaximeterOfferGroup))
            )
            .andExpect(status().isOk());

        // Validate the TaximeterOfferGroup in the database
        List<TaximeterOfferGroup> taximeterOfferGroupList = taximeterOfferGroupRepository.findAll();
        assertThat(taximeterOfferGroupList).hasSize(databaseSizeBeforeUpdate);
        TaximeterOfferGroup testTaximeterOfferGroup = taximeterOfferGroupList.get(taximeterOfferGroupList.size() - 1);
        assertThat(testTaximeterOfferGroup.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTaximeterOfferGroup.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTaximeterOfferGroup.getInvoiceAs()).isEqualTo(UPDATED_INVOICE_AS);
        assertThat(testTaximeterOfferGroup.getChargeBy()).isEqualTo(UPDATED_CHARGE_BY);
        assertThat(testTaximeterOfferGroup.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testTaximeterOfferGroup.getUpdated()).isEqualTo(UPDATED_UPDATED);
    }

    @Test
    @Transactional
    void patchNonExistingTaximeterOfferGroup() throws Exception {
        int databaseSizeBeforeUpdate = taximeterOfferGroupRepository.findAll().size();
        taximeterOfferGroup.setId(count.incrementAndGet());

        // Create the TaximeterOfferGroup
        TaximeterOfferGroupDTO taximeterOfferGroupDTO = taximeterOfferGroupMapper.toDto(taximeterOfferGroup);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTaximeterOfferGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, taximeterOfferGroupDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(taximeterOfferGroupDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TaximeterOfferGroup in the database
        List<TaximeterOfferGroup> taximeterOfferGroupList = taximeterOfferGroupRepository.findAll();
        assertThat(taximeterOfferGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTaximeterOfferGroup() throws Exception {
        int databaseSizeBeforeUpdate = taximeterOfferGroupRepository.findAll().size();
        taximeterOfferGroup.setId(count.incrementAndGet());

        // Create the TaximeterOfferGroup
        TaximeterOfferGroupDTO taximeterOfferGroupDTO = taximeterOfferGroupMapper.toDto(taximeterOfferGroup);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaximeterOfferGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(taximeterOfferGroupDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TaximeterOfferGroup in the database
        List<TaximeterOfferGroup> taximeterOfferGroupList = taximeterOfferGroupRepository.findAll();
        assertThat(taximeterOfferGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTaximeterOfferGroup() throws Exception {
        int databaseSizeBeforeUpdate = taximeterOfferGroupRepository.findAll().size();
        taximeterOfferGroup.setId(count.incrementAndGet());

        // Create the TaximeterOfferGroup
        TaximeterOfferGroupDTO taximeterOfferGroupDTO = taximeterOfferGroupMapper.toDto(taximeterOfferGroup);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaximeterOfferGroupMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(taximeterOfferGroupDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TaximeterOfferGroup in the database
        List<TaximeterOfferGroup> taximeterOfferGroupList = taximeterOfferGroupRepository.findAll();
        assertThat(taximeterOfferGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTaximeterOfferGroup() throws Exception {
        // Initialize the database
        taximeterOfferGroupRepository.saveAndFlush(taximeterOfferGroup);

        int databaseSizeBeforeDelete = taximeterOfferGroupRepository.findAll().size();

        // Delete the taximeterOfferGroup
        restTaximeterOfferGroupMockMvc
            .perform(delete(ENTITY_API_URL_ID, taximeterOfferGroup.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TaximeterOfferGroup> taximeterOfferGroupList = taximeterOfferGroupRepository.findAll();
        assertThat(taximeterOfferGroupList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
