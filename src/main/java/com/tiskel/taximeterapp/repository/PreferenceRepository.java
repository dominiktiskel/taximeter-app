package com.tiskel.taximeterapp.repository;

import com.tiskel.taximeterapp.domain.Preference;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Preference entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PreferenceRepository extends JpaRepository<Preference, Long> {}
