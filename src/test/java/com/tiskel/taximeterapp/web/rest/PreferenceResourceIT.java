package com.tiskel.taximeterapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.tiskel.taximeterapp.IntegrationTest;
import com.tiskel.taximeterapp.domain.Preference;
import com.tiskel.taximeterapp.repository.PreferenceRepository;
import com.tiskel.taximeterapp.service.dto.PreferenceDTO;
import com.tiskel.taximeterapp.service.mapper.PreferenceMapper;
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
 * Integration tests for the {@link PreferenceResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PreferenceResourceIT {

    private static final String ENTITY_API_URL = "/api/preferences";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PreferenceRepository preferenceRepository;

    @Autowired
    private PreferenceMapper preferenceMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPreferenceMockMvc;

    private Preference preference;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Preference createEntity(EntityManager em) {
        Preference preference = new Preference();
        return preference;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Preference createUpdatedEntity(EntityManager em) {
        Preference preference = new Preference();
        return preference;
    }

    @BeforeEach
    public void initTest() {
        preference = createEntity(em);
    }

    @Test
    @Transactional
    void createPreference() throws Exception {
        int databaseSizeBeforeCreate = preferenceRepository.findAll().size();
        // Create the Preference
        PreferenceDTO preferenceDTO = preferenceMapper.toDto(preference);
        restPreferenceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(preferenceDTO)))
            .andExpect(status().isCreated());

        // Validate the Preference in the database
        List<Preference> preferenceList = preferenceRepository.findAll();
        assertThat(preferenceList).hasSize(databaseSizeBeforeCreate + 1);
        Preference testPreference = preferenceList.get(preferenceList.size() - 1);
    }

    @Test
    @Transactional
    void createPreferenceWithExistingId() throws Exception {
        // Create the Preference with an existing ID
        preference.setId(1L);
        PreferenceDTO preferenceDTO = preferenceMapper.toDto(preference);

        int databaseSizeBeforeCreate = preferenceRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPreferenceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(preferenceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Preference in the database
        List<Preference> preferenceList = preferenceRepository.findAll();
        assertThat(preferenceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPreferences() throws Exception {
        // Initialize the database
        preferenceRepository.saveAndFlush(preference);

        // Get all the preferenceList
        restPreferenceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(preference.getId().intValue())));
    }

    @Test
    @Transactional
    void getPreference() throws Exception {
        // Initialize the database
        preferenceRepository.saveAndFlush(preference);

        // Get the preference
        restPreferenceMockMvc
            .perform(get(ENTITY_API_URL_ID, preference.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(preference.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingPreference() throws Exception {
        // Get the preference
        restPreferenceMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPreference() throws Exception {
        // Initialize the database
        preferenceRepository.saveAndFlush(preference);

        int databaseSizeBeforeUpdate = preferenceRepository.findAll().size();

        // Update the preference
        Preference updatedPreference = preferenceRepository.findById(preference.getId()).get();
        // Disconnect from session so that the updates on updatedPreference are not directly saved in db
        em.detach(updatedPreference);
        PreferenceDTO preferenceDTO = preferenceMapper.toDto(updatedPreference);

        restPreferenceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, preferenceDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(preferenceDTO))
            )
            .andExpect(status().isOk());

        // Validate the Preference in the database
        List<Preference> preferenceList = preferenceRepository.findAll();
        assertThat(preferenceList).hasSize(databaseSizeBeforeUpdate);
        Preference testPreference = preferenceList.get(preferenceList.size() - 1);
    }

    @Test
    @Transactional
    void putNonExistingPreference() throws Exception {
        int databaseSizeBeforeUpdate = preferenceRepository.findAll().size();
        preference.setId(count.incrementAndGet());

        // Create the Preference
        PreferenceDTO preferenceDTO = preferenceMapper.toDto(preference);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPreferenceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, preferenceDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(preferenceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Preference in the database
        List<Preference> preferenceList = preferenceRepository.findAll();
        assertThat(preferenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPreference() throws Exception {
        int databaseSizeBeforeUpdate = preferenceRepository.findAll().size();
        preference.setId(count.incrementAndGet());

        // Create the Preference
        PreferenceDTO preferenceDTO = preferenceMapper.toDto(preference);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPreferenceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(preferenceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Preference in the database
        List<Preference> preferenceList = preferenceRepository.findAll();
        assertThat(preferenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPreference() throws Exception {
        int databaseSizeBeforeUpdate = preferenceRepository.findAll().size();
        preference.setId(count.incrementAndGet());

        // Create the Preference
        PreferenceDTO preferenceDTO = preferenceMapper.toDto(preference);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPreferenceMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(preferenceDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Preference in the database
        List<Preference> preferenceList = preferenceRepository.findAll();
        assertThat(preferenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePreferenceWithPatch() throws Exception {
        // Initialize the database
        preferenceRepository.saveAndFlush(preference);

        int databaseSizeBeforeUpdate = preferenceRepository.findAll().size();

        // Update the preference using partial update
        Preference partialUpdatedPreference = new Preference();
        partialUpdatedPreference.setId(preference.getId());

        restPreferenceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPreference.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPreference))
            )
            .andExpect(status().isOk());

        // Validate the Preference in the database
        List<Preference> preferenceList = preferenceRepository.findAll();
        assertThat(preferenceList).hasSize(databaseSizeBeforeUpdate);
        Preference testPreference = preferenceList.get(preferenceList.size() - 1);
    }

    @Test
    @Transactional
    void fullUpdatePreferenceWithPatch() throws Exception {
        // Initialize the database
        preferenceRepository.saveAndFlush(preference);

        int databaseSizeBeforeUpdate = preferenceRepository.findAll().size();

        // Update the preference using partial update
        Preference partialUpdatedPreference = new Preference();
        partialUpdatedPreference.setId(preference.getId());

        restPreferenceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPreference.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPreference))
            )
            .andExpect(status().isOk());

        // Validate the Preference in the database
        List<Preference> preferenceList = preferenceRepository.findAll();
        assertThat(preferenceList).hasSize(databaseSizeBeforeUpdate);
        Preference testPreference = preferenceList.get(preferenceList.size() - 1);
    }

    @Test
    @Transactional
    void patchNonExistingPreference() throws Exception {
        int databaseSizeBeforeUpdate = preferenceRepository.findAll().size();
        preference.setId(count.incrementAndGet());

        // Create the Preference
        PreferenceDTO preferenceDTO = preferenceMapper.toDto(preference);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPreferenceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, preferenceDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(preferenceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Preference in the database
        List<Preference> preferenceList = preferenceRepository.findAll();
        assertThat(preferenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPreference() throws Exception {
        int databaseSizeBeforeUpdate = preferenceRepository.findAll().size();
        preference.setId(count.incrementAndGet());

        // Create the Preference
        PreferenceDTO preferenceDTO = preferenceMapper.toDto(preference);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPreferenceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(preferenceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Preference in the database
        List<Preference> preferenceList = preferenceRepository.findAll();
        assertThat(preferenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPreference() throws Exception {
        int databaseSizeBeforeUpdate = preferenceRepository.findAll().size();
        preference.setId(count.incrementAndGet());

        // Create the Preference
        PreferenceDTO preferenceDTO = preferenceMapper.toDto(preference);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPreferenceMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(preferenceDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Preference in the database
        List<Preference> preferenceList = preferenceRepository.findAll();
        assertThat(preferenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePreference() throws Exception {
        // Initialize the database
        preferenceRepository.saveAndFlush(preference);

        int databaseSizeBeforeDelete = preferenceRepository.findAll().size();

        // Delete the preference
        restPreferenceMockMvc
            .perform(delete(ENTITY_API_URL_ID, preference.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Preference> preferenceList = preferenceRepository.findAll();
        assertThat(preferenceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
