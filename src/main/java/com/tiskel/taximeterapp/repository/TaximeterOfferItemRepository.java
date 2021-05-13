package com.tiskel.taximeterapp.repository;

import com.tiskel.taximeterapp.domain.TaximeterOfferItem;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the TaximeterOfferItem entity.
 */
@Repository
public interface TaximeterOfferItemRepository extends JpaRepository<TaximeterOfferItem, Long> {
    @Query(
        value = "select distinct taximeterOfferItem from TaximeterOfferItem taximeterOfferItem left join fetch taximeterOfferItem.preferences",
        countQuery = "select count(distinct taximeterOfferItem) from TaximeterOfferItem taximeterOfferItem"
    )
    Page<TaximeterOfferItem> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct taximeterOfferItem from TaximeterOfferItem taximeterOfferItem left join fetch taximeterOfferItem.preferences")
    List<TaximeterOfferItem> findAllWithEagerRelationships();

    @Query(
        "select taximeterOfferItem from TaximeterOfferItem taximeterOfferItem left join fetch taximeterOfferItem.preferences where taximeterOfferItem.id =:id"
    )
    Optional<TaximeterOfferItem> findOneWithEagerRelationships(@Param("id") Long id);
}
