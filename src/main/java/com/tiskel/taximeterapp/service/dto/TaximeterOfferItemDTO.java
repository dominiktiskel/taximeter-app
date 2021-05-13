package com.tiskel.taximeterapp.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.tiskel.taximeterapp.domain.TaximeterOfferItem} entity.
 */
public class TaximeterOfferItemDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private String description;

    private Double billCompanyPays;

    private Double customerPays;

    private Double taxiGets;

    private Double taxiPays;

    private ZonedDateTime created;

    private ZonedDateTime updated;

    private TaximeterFixedListDTO fixedList1;

    private TaximeterFixedListDTO fixedList2;

    private TaximeterFormulaDTO formula;

    private TaximeterTimeRangeDTO timeRange;

    private Set<PreferenceDTO> preferences = new HashSet<>();

    private TaximeterOfferGroupDTO group;

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

    public Double getBillCompanyPays() {
        return billCompanyPays;
    }

    public void setBillCompanyPays(Double billCompanyPays) {
        this.billCompanyPays = billCompanyPays;
    }

    public Double getCustomerPays() {
        return customerPays;
    }

    public void setCustomerPays(Double customerPays) {
        this.customerPays = customerPays;
    }

    public Double getTaxiGets() {
        return taxiGets;
    }

    public void setTaxiGets(Double taxiGets) {
        this.taxiGets = taxiGets;
    }

    public Double getTaxiPays() {
        return taxiPays;
    }

    public void setTaxiPays(Double taxiPays) {
        this.taxiPays = taxiPays;
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

    public TaximeterFixedListDTO getFixedList1() {
        return fixedList1;
    }

    public void setFixedList1(TaximeterFixedListDTO fixedList1) {
        this.fixedList1 = fixedList1;
    }

    public TaximeterFixedListDTO getFixedList2() {
        return fixedList2;
    }

    public void setFixedList2(TaximeterFixedListDTO fixedList2) {
        this.fixedList2 = fixedList2;
    }

    public TaximeterFormulaDTO getFormula() {
        return formula;
    }

    public void setFormula(TaximeterFormulaDTO formula) {
        this.formula = formula;
    }

    public TaximeterTimeRangeDTO getTimeRange() {
        return timeRange;
    }

    public void setTimeRange(TaximeterTimeRangeDTO timeRange) {
        this.timeRange = timeRange;
    }

    public Set<PreferenceDTO> getPreferences() {
        return preferences;
    }

    public void setPreferences(Set<PreferenceDTO> preferences) {
        this.preferences = preferences;
    }

    public TaximeterOfferGroupDTO getGroup() {
        return group;
    }

    public void setGroup(TaximeterOfferGroupDTO group) {
        this.group = group;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TaximeterOfferItemDTO)) {
            return false;
        }

        TaximeterOfferItemDTO taximeterOfferItemDTO = (TaximeterOfferItemDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, taximeterOfferItemDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TaximeterOfferItemDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", billCompanyPays=" + getBillCompanyPays() +
            ", customerPays=" + getCustomerPays() +
            ", taxiGets=" + getTaxiGets() +
            ", taxiPays=" + getTaxiPays() +
            ", created='" + getCreated() + "'" +
            ", updated='" + getUpdated() + "'" +
            ", fixedList1=" + getFixedList1() +
            ", fixedList2=" + getFixedList2() +
            ", formula=" + getFormula() +
            ", timeRange=" + getTimeRange() +
            ", preferences=" + getPreferences() +
            ", group=" + getGroup() +
            "}";
    }
}
