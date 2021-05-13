package com.tiskel.taximeterapp.service;

import com.tiskel.taximeterapp.service.dto.PreferenceDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.tiskel.taximeterapp.domain.Preference}.
 */
public interface PreferenceService {
    /**
     * Save a preference.
     *
     * @param preferenceDTO the entity to save.
     * @return the persisted entity.
     */
    PreferenceDTO save(PreferenceDTO preferenceDTO);

    /**
     * Partially updates a preference.
     *
     * @param preferenceDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PreferenceDTO> partialUpdate(PreferenceDTO preferenceDTO);

    /**
     * Get all the preferences.
     *
     * @return the list of entities.
     */
    List<PreferenceDTO> findAll();

    /**
     * Get the "id" preference.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PreferenceDTO> findOne(Long id);

    /**
     * Delete the "id" preference.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
