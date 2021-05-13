package com.tiskel.taximeterapp.service;

import com.tiskel.taximeterapp.service.dto.TaximeterFixedListItemDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.tiskel.taximeterapp.domain.TaximeterFixedListItem}.
 */
public interface TaximeterFixedListItemService {
    /**
     * Save a taximeterFixedListItem.
     *
     * @param taximeterFixedListItemDTO the entity to save.
     * @return the persisted entity.
     */
    TaximeterFixedListItemDTO save(TaximeterFixedListItemDTO taximeterFixedListItemDTO);

    /**
     * Partially updates a taximeterFixedListItem.
     *
     * @param taximeterFixedListItemDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TaximeterFixedListItemDTO> partialUpdate(TaximeterFixedListItemDTO taximeterFixedListItemDTO);

    /**
     * Get all the taximeterFixedListItems.
     *
     * @return the list of entities.
     */
    List<TaximeterFixedListItemDTO> findAll();

    /**
     * Get the "id" taximeterFixedListItem.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TaximeterFixedListItemDTO> findOne(Long id);

    /**
     * Delete the "id" taximeterFixedListItem.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
