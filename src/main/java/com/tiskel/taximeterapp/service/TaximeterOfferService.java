package com.tiskel.taximeterapp.service;

import com.tiskel.taximeterapp.service.dto.TaximeterOfferDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.tiskel.taximeterapp.domain.TaximeterOffer}.
 */
public interface TaximeterOfferService {
    /**
     * Save a taximeterOffer.
     *
     * @param taximeterOfferDTO the entity to save.
     * @return the persisted entity.
     */
    TaximeterOfferDTO save(TaximeterOfferDTO taximeterOfferDTO);

    /**
     * Partially updates a taximeterOffer.
     *
     * @param taximeterOfferDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TaximeterOfferDTO> partialUpdate(TaximeterOfferDTO taximeterOfferDTO);

    /**
     * Get all the taximeterOffers.
     *
     * @return the list of entities.
     */
    List<TaximeterOfferDTO> findAll();

    /**
     * Get the "id" taximeterOffer.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TaximeterOfferDTO> findOne(Long id);

    /**
     * Delete the "id" taximeterOffer.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
