package com.tiskel.taximeterapp.repository;

import com.tiskel.taximeterapp.domain.TaximeterFormulaRow;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the TaximeterFormulaRow entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TaximeterFormulaRowRepository extends JpaRepository<TaximeterFormulaRow, Long> {}
