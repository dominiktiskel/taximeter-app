package com.tiskel.taximeterapp.repository;

import com.tiskel.taximeterapp.domain.TaximeterFormula;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the TaximeterFormula entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TaximeterFormulaRepository extends JpaRepository<TaximeterFormula, Long> {}
