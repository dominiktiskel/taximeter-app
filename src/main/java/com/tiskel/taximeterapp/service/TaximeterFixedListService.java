package com.tiskel.taximeterapp.service;

import com.tiskel.taximeterapp.service.dto.TaximeterFixedListDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.tiskel.taximeterapp.domain.TaximeterFixedList}.
 */
public interface TaximeterFixedListService {
    /**
     * Save a taximeterFixedList.
     *
     * @param taximeterFixedListDTO the entity to save.
     * @return the persisted entity.
     */
    TaximeterFixedListDTO save(TaximeterFixedListDTO taximeterFixedListDTO);

    /**
     * Partially updates a taximeterFixedList.
     *
     * @param taximeterFixedListDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TaximeterFixedListDTO> partialUpdate(TaximeterFixedListDTO taximeterFixedListDTO);

    /**
     * Get all the taximeterFixedLists.
     *
     * @return the list of entities.
     */
    List<TaximeterFixedListDTO> findAll();

    /**
     * Get the "id" taximeterFixedList.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TaximeterFixedListDTO> findOne(Long id);

    /**
     * Delete the "id" taximeterFixedList.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
