package com.tiskel.taximeterapp.service;

import com.tiskel.taximeterapp.service.dto.TaximeterOfferItemDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.tiskel.taximeterapp.domain.TaximeterOfferItem}.
 */
public interface TaximeterOfferItemService {
    /**
     * Save a taximeterOfferItem.
     *
     * @param taximeterOfferItemDTO the entity to save.
     * @return the persisted entity.
     */
    TaximeterOfferItemDTO save(TaximeterOfferItemDTO taximeterOfferItemDTO);

    /**
     * Partially updates a taximeterOfferItem.
     *
     * @param taximeterOfferItemDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TaximeterOfferItemDTO> partialUpdate(TaximeterOfferItemDTO taximeterOfferItemDTO);

    /**
     * Get all the taximeterOfferItems.
     *
     * @return the list of entities.
     */
    List<TaximeterOfferItemDTO> findAll();

    /**
     * Get all the taximeterOfferItems with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TaximeterOfferItemDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" taximeterOfferItem.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TaximeterOfferItemDTO> findOne(Long id);

    /**
     * Delete the "id" taximeterOfferItem.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
