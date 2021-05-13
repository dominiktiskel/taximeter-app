package com.tiskel.taximeterapp.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.tiskel.taximeterapp.domain.TaximeterOffer} entity.
 */
public class TaximeterOfferDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private String description;

    private ZonedDateTime validFrom;

    private ZonedDateTime validTo;

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

    public ZonedDateTime getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(ZonedDateTime validFrom) {
        this.validFrom = validFrom;
    }

    public ZonedDateTime getValidTo() {
        return validTo;
    }

    public void setValidTo(ZonedDateTime validTo) {
        this.validTo = validTo;
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
        if (!(o instanceof TaximeterOfferDTO)) {
            return false;
        }

        TaximeterOfferDTO taximeterOfferDTO = (TaximeterOfferDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, taximeterOfferDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TaximeterOfferDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", validFrom='" + getValidFrom() + "'" +
            ", validTo='" + getValidTo() + "'" +
            ", created='" + getCreated() + "'" +
            ", updated='" + getUpdated() + "'" +
            "}";
    }
}
