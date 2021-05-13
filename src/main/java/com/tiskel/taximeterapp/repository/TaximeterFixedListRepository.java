package com.tiskel.taximeterapp.repository;

import com.tiskel.taximeterapp.domain.TaximeterFixedList;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the TaximeterFixedList entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TaximeterFixedListRepository extends JpaRepository<TaximeterFixedList, Long> {}
