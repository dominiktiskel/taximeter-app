package com.tiskel.taximeterapp.service;

import com.tiskel.taximeterapp.service.dto.TaximeterFormulaDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.tiskel.taximeterapp.domain.TaximeterFormula}.
 */
public interface TaximeterFormulaService {
    /**
     * Save a taximeterFormula.
     *
     * @param taximeterFormulaDTO the entity to save.
     * @return the persisted entity.
     */
    TaximeterFormulaDTO save(TaximeterFormulaDTO taximeterFormulaDTO);

    /**
     * Partially updates a taximeterFormula.
     *
     * @param taximeterFormulaDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TaximeterFormulaDTO> partialUpdate(TaximeterFormulaDTO taximeterFormulaDTO);

    /**
     * Get all the taximeterFormulas.
     *
     * @return the list of entities.
     */
    List<TaximeterFormulaDTO> findAll();

    /**
     * Get the "id" taximeterFormula.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TaximeterFormulaDTO> findOne(Long id);

    /**
     * Delete the "id" taximeterFormula.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
