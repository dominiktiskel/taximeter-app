package com.tiskel.taximeterapp.service;

import com.tiskel.taximeterapp.service.dto.TaximeterFormulaRowDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.tiskel.taximeterapp.domain.TaximeterFormulaRow}.
 */
public interface TaximeterFormulaRowService {
    /**
     * Save a taximeterFormulaRow.
     *
     * @param taximeterFormulaRowDTO the entity to save.
     * @return the persisted entity.
     */
    TaximeterFormulaRowDTO save(TaximeterFormulaRowDTO taximeterFormulaRowDTO);

    /**
     * Partially updates a taximeterFormulaRow.
     *
     * @param taximeterFormulaRowDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TaximeterFormulaRowDTO> partialUpdate(TaximeterFormulaRowDTO taximeterFormulaRowDTO);

    /**
     * Get all the taximeterFormulaRows.
     *
     * @return the list of entities.
     */
    List<TaximeterFormulaRowDTO> findAll();

    /**
     * Get the "id" taximeterFormulaRow.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TaximeterFormulaRowDTO> findOne(Long id);

    /**
     * Delete the "id" taximeterFormulaRow.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
