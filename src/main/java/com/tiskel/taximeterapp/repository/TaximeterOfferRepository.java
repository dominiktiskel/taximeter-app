package com.tiskel.taximeterapp.repository;

import com.tiskel.taximeterapp.domain.TaximeterOffer;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the TaximeterOffer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TaximeterOfferRepository extends JpaRepository<TaximeterOffer, Long> {}
