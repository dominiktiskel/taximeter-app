package com.tiskel.taximeterapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A TaximeterOfferItem.
 */
@Entity
@Table(name = "taximeter_offer_item")
public class TaximeterOfferItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "bill_company_pays")
    private Double billCompanyPays;

    @Column(name = "customer_pays")
    private Double customerPays;

    @Column(name = "taxi_gets")
    private Double taxiGets;

    @Column(name = "taxi_pays")
    private Double taxiPays;

    @Column(name = "created")
    private ZonedDateTime created;

    @Column(name = "updated")
    private ZonedDateTime updated;

    @ManyToOne
    @JsonIgnoreProperties(value = { "items" }, allowSetters = true)
    private TaximeterFixedList fixedList1;

    @ManyToOne
    @JsonIgnoreProperties(value = { "items" }, allowSetters = true)
    private TaximeterFixedList fixedList2;

    @ManyToOne
    private TaximeterFormula formula;

    @ManyToOne
    @JsonIgnoreProperties(value = { "items" }, allowSetters = true)
    private TaximeterTimeRange timeRange;

    @ManyToMany
    @JoinTable(
        name = "rel_taximeter_offer_item__preference",
        joinColumns = @JoinColumn(name = "taximeter_offer_item_id"),
        inverseJoinColumns = @JoinColumn(name = "preference_id")
    )
    @JsonIgnoreProperties(value = { "taximeterOfferItems" }, allowSetters = true)
    private Set<Preference> preferences = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "items", "offer" }, allowSetters = true)
    private TaximeterOfferGroup group;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TaximeterOfferItem id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public TaximeterOfferItem name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public TaximeterOfferItem description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getBillCompanyPays() {
        return this.billCompanyPays;
    }

    public TaximeterOfferItem billCompanyPays(Double billCompanyPays) {
        this.billCompanyPays = billCompanyPays;
        return this;
    }

    public void setBillCompanyPays(Double billCompanyPays) {
        this.billCompanyPays = billCompanyPays;
    }

    public Double getCustomerPays() {
        return this.customerPays;
    }

    public TaximeterOfferItem customerPays(Double customerPays) {
        this.customerPays = customerPays;
        return this;
    }

    public void setCustomerPays(Double customerPays) {
        this.customerPays = customerPays;
    }

    public Double getTaxiGets() {
        return this.taxiGets;
    }

    public TaximeterOfferItem taxiGets(Double taxiGets) {
        this.taxiGets = taxiGets;
        return this;
    }

    public void setTaxiGets(Double taxiGets) {
        this.taxiGets = taxiGets;
    }

    public Double getTaxiPays() {
        return this.taxiPays;
    }

    public TaximeterOfferItem taxiPays(Double taxiPays) {
        this.taxiPays = taxiPays;
        return this;
    }

    public void setTaxiPays(Double taxiPays) {
        this.taxiPays = taxiPays;
    }

    public ZonedDateTime getCreated() {
        return this.created;
    }

    public TaximeterOfferItem created(ZonedDateTime created) {
        this.created = created;
        return this;
    }

    public void setCreated(ZonedDateTime created) {
        this.created = created;
    }

    public ZonedDateTime getUpdated() {
        return this.updated;
    }

    public TaximeterOfferItem updated(ZonedDateTime updated) {
        this.updated = updated;
        return this;
    }

    public void setUpdated(ZonedDateTime updated) {
        this.updated = updated;
    }

    public TaximeterFixedList getFixedList1() {
        return this.fixedList1;
    }

    public TaximeterOfferItem fixedList1(TaximeterFixedList taximeterFixedList) {
        this.setFixedList1(taximeterFixedList);
        return this;
    }

    public void setFixedList1(TaximeterFixedList taximeterFixedList) {
        this.fixedList1 = taximeterFixedList;
    }

    public TaximeterFixedList getFixedList2() {
        return this.fixedList2;
    }

    public TaximeterOfferItem fixedList2(TaximeterFixedList taximeterFixedList) {
        this.setFixedList2(taximeterFixedList);
        return this;
    }

    public void setFixedList2(TaximeterFixedList taximeterFixedList) {
        this.fixedList2 = taximeterFixedList;
    }

    public TaximeterFormula getFormula() {
        return this.formula;
    }

    public TaximeterOfferItem formula(TaximeterFormula taximeterFormula) {
        this.setFormula(taximeterFormula);
        return this;
    }

    public void setFormula(TaximeterFormula taximeterFormula) {
        this.formula = taximeterFormula;
    }

    public TaximeterTimeRange getTimeRange() {
        return this.timeRange;
    }

    public TaximeterOfferItem timeRange(TaximeterTimeRange taximeterTimeRange) {
        this.setTimeRange(taximeterTimeRange);
        return this;
    }

    public void setTimeRange(TaximeterTimeRange taximeterTimeRange) {
        this.timeRange = taximeterTimeRange;
    }

    public Set<Preference> getPreferences() {
        return this.preferences;
    }

    public TaximeterOfferItem preferences(Set<Preference> preferences) {
        this.setPreferences(preferences);
        return this;
    }

    public TaximeterOfferItem addPreference(Preference preference) {
        this.preferences.add(preference);
        preference.getTaximeterOfferItems().add(this);
        return this;
    }

    public TaximeterOfferItem removePreference(Preference preference) {
        this.preferences.remove(preference);
        preference.getTaximeterOfferItems().remove(this);
        return this;
    }

    public void setPreferences(Set<Preference> preferences) {
        this.preferences = preferences;
    }

    public TaximeterOfferGroup getGroup() {
        return this.group;
    }

    public TaximeterOfferItem group(TaximeterOfferGroup taximeterOfferGroup) {
        this.setGroup(taximeterOfferGroup);
        return this;
    }

    public void setGroup(TaximeterOfferGroup taximeterOfferGroup) {
        this.group = taximeterOfferGroup;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TaximeterOfferItem)) {
            return false;
        }
        return id != null && id.equals(((TaximeterOfferItem) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TaximeterOfferItem{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", billCompanyPays=" + getBillCompanyPays() +
            ", customerPays=" + getCustomerPays() +
            ", taxiGets=" + getTaxiGets() +
            ", taxiPays=" + getTaxiPays() +
            ", created='" + getCreated() + "'" +
            ", updated='" + getUpdated() + "'" +
            "}";
    }
}
