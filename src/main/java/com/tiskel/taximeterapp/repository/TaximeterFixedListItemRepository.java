package com.tiskel.taximeterapp.repository;

import com.tiskel.taximeterapp.domain.TaximeterFixedListItem;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the TaximeterFixedListItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TaximeterFixedListItemRepository extends JpaRepository<TaximeterFixedListItem, Long> {}
