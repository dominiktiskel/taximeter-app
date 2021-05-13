package com.tiskel.taximeterapp.service.dto;

import com.tiskel.taximeterapp.domain.enumeration.TaximeterFixedListType;
import com.tiskel.taximeterapp.domain.enumeration.TaximeterMultipickupMatchingRules;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.tiskel.taximeterapp.domain.TaximeterFixedList} entity.
 */
public class TaximeterFixedListDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private String description;

    @NotNull
    private TaximeterFixedListType type;

    private TaximeterMultipickupMatchingRules matchingRules;

    private ZonedDateTime created;

    private ZonedDateTime updated;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaximeterFixedListType getType() {
        return type;
    }

    public void setType(TaximeterFixedListType type) {
        this.type = type;
    }

    public TaximeterMultipickupMatchingRules getMatchingRules() {
        return matchingRules;
    }

    public void setMatchingRules(TaximeterMultipickupMatchingRules matchingRules) {
        this.matchingRules = matchingRules;
    }

    public ZonedDateTime getCreated() {
        return created;
    }

    public void setCreated(ZonedDateTime created) {
        this.created = created;
    }

    public ZonedDateTime getUpdated() {
        return updated;
    }

    public void setUpdated(ZonedDateTime updated) {
        this.updated = updated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TaximeterFixedListDTO)) {
            return false;
        }

        TaximeterFixedListDTO taximeterFixedListDTO = (TaximeterFixedListDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, taximeterFixedListDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TaximeterFixedListDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", type='" + getType() + "'" +
            ", matchingRules='" + getMatchingRules() + "'" +
            ", created='" + getCreated() + "'" +
            ", updated='" + getUpdated() + "'" +
            "}";
    }
}
