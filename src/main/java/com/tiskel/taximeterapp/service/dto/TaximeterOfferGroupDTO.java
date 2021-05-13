package com.tiskel.taximeterapp.service.dto;

import com.tiskel.taximeterapp.domain.enumeration.TaximeterChargeByType;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.tiskel.taximeterapp.domain.TaximeterOfferGroup} entity.
 */
public class TaximeterOfferGroupDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private String description;

    private String invoiceAs;

    @NotNull
    private TaximeterChargeByType chargeBy;

    private ZonedDateTime created;

    private ZonedDateTime updated;

    private TaximeterOfferDTO offer;

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

    public String getInvoiceAs() {
        return invoiceAs;
    }

    public void setInvoiceAs(String invoiceAs) {
        this.invoiceAs = invoiceAs;
    }

    public TaximeterChargeByType getChargeBy() {
        return chargeBy;
    }

    public void setChargeBy(TaximeterChargeByType chargeBy) {
        this.chargeBy = chargeBy;
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

    public TaximeterOfferDTO getOffer() {
        return offer;
    }

    public void setOffer(TaximeterOfferDTO offer) {
        this.offer = offer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TaximeterOfferGroupDTO)) {
            return false;
        }

        TaximeterOfferGroupDTO taximeterOfferGroupDTO = (TaximeterOfferGroupDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, taximeterOfferGroupDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TaximeterOfferGroupDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", invoiceAs='" + getInvoiceAs() + "'" +
            ", chargeBy='" + getChargeBy() + "'" +
            ", created='" + getCreated() + "'" +
            ", updated='" + getUpdated() + "'" +
            ", offer=" + getOffer() +
            "}";
    }
}
