package com.tiskel.taximeterapp.service;

import com.tiskel.taximeterapp.service.dto.TaximeterTimeRangeItemDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.tiskel.taximeterapp.domain.TaximeterTimeRangeItem}.
 */
public interface TaximeterTimeRangeItemService {
    /**
     * Save a taximeterTimeRangeItem.
     *
     * @param taximeterTimeRangeItemDTO the entity to save.
     * @return the persisted entity.
     */
    TaximeterTimeRangeItemDTO save(TaximeterTimeRangeItemDTO taximeterTimeRangeItemDTO);

    /**
     * Partially updates a taximeterTimeRangeItem.
     *
     * @param taximeterTimeRangeItemDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TaximeterTimeRangeItemDTO> partialUpdate(TaximeterTimeRangeItemDTO taximeterTimeRangeItemDTO);

    /**
     * Get all the taximeterTimeRangeItems.
     *
     * @return the list of entities.
     */
    List<TaximeterTimeRangeItemDTO> findAll();

    /**
     * Get the "id" taximeterTimeRangeItem.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TaximeterTimeRangeItemDTO> findOne(Long id);

    /**
     * Delete the "id" taximeterTimeRangeItem.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
