package com.tiskel.taximeterapp.service;

import com.tiskel.taximeterapp.service.dto.TaximeterTimeRangeDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.tiskel.taximeterapp.domain.TaximeterTimeRange}.
 */
public interface TaximeterTimeRangeService {
    /**
     * Save a taximeterTimeRange.
     *
     * @param taximeterTimeRangeDTO the entity to save.
     * @return the persisted entity.
     */
    TaximeterTimeRangeDTO save(TaximeterTimeRangeDTO taximeterTimeRangeDTO);

    /**
     * Partially updates a taximeterTimeRange.
     *
     * @param taximeterTimeRangeDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TaximeterTimeRangeDTO> partialUpdate(TaximeterTimeRangeDTO taximeterTimeRangeDTO);

    /**
     * Get all the taximeterTimeRanges.
     *
     * @return the list of entities.
     */
    List<TaximeterTimeRangeDTO> findAll();

    /**
     * Get the "id" taximeterTimeRange.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TaximeterTimeRangeDTO> findOne(Long id);

    /**
     * Delete the "id" taximeterTimeRange.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
