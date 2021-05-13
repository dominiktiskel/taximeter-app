package com.tiskel.taximeterapp.repository;

import com.tiskel.taximeterapp.domain.TaximeterTimeRangeItem;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the TaximeterTimeRangeItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TaximeterTimeRangeItemRepository extends JpaRepository<TaximeterTimeRangeItem, Long> {}
