package com.tiskel.taximeterapp.repository;

import com.tiskel.taximeterapp.domain.TaximeterTimeRange;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the TaximeterTimeRange entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TaximeterTimeRangeRepository extends JpaRepository<TaximeterTimeRange, Long> {}
