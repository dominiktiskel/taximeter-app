package com.tiskel.taximeterapp.service;

import com.tiskel.taximeterapp.service.dto.TaximeterOfferGroupDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.tiskel.taximeterapp.domain.TaximeterOfferGroup}.
 */
public interface TaximeterOfferGroupService {
    /**
     * Save a taximeterOfferGroup.
     *
     * @param taximeterOfferGroupDTO the entity to save.
     * @return the persisted entity.
     */
    TaximeterOfferGroupDTO save(TaximeterOfferGroupDTO taximeterOfferGroupDTO);

    /**
     * Partially updates a taximeterOfferGroup.
     *
     * @param taximeterOfferGroupDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TaximeterOfferGroupDTO> partialUpdate(TaximeterOfferGroupDTO taximeterOfferGroupDTO);

    /**
     * Get all the taximeterOfferGroups.
     *
     * @return the list of entities.
     */
    List<TaximeterOfferGroupDTO> findAll();

    /**
     * Get the "id" taximeterOfferGroup.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TaximeterOfferGroupDTO> findOne(Long id);

    /**
     * Delete the "id" taximeterOfferGroup.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
