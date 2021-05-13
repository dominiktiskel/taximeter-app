package com.tiskel.taximeterapp.repository;

import com.tiskel.taximeterapp.domain.TaximeterOfferGroup;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the TaximeterOfferGroup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TaximeterOfferGroupRepository extends JpaRepository<TaximeterOfferGroup, Long> {}
