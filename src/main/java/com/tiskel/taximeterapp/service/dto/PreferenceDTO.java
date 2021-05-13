package com.tiskel.taximeterapp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.tiskel.taximeterapp.domain.Preference} entity.
 */
public class PreferenceDTO implements Serializable {

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PreferenceDTO)) {
            return false;
        }

        PreferenceDTO preferenceDTO = (PreferenceDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, preferenceDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PreferenceDTO{" +
            "id=" + getId() +
            "}";
    }
}
