package com.tiskel.taximeterapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tiskel.taximeterapp.domain.enumeration.TaximeterChargeByType;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A TaximeterOfferGroup.
 */
@Entity
@Table(name = "taximeter_offer_group")
public class TaximeterOfferGroup implements Serializable {

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

    @Column(name = "invoice_as")
    private String invoiceAs;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "charge_by", nullable = false)
    private TaximeterChargeByType chargeBy;

    @Column(name = "created")
    private ZonedDateTime created;

    @Column(name = "updated")
    private ZonedDateTime updated;

    @OneToMany(mappedBy = "group")
    @JsonIgnoreProperties(value = { "fixedList1", "fixedList2", "formula", "timeRange", "preferences", "group" }, allowSetters = true)
    private Set<TaximeterOfferItem> items = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "groups" }, allowSetters = true)
    private TaximeterOffer offer;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TaximeterOfferGroup id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public TaximeterOfferGroup name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public TaximeterOfferGroup description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInvoiceAs() {
        return this.invoiceAs;
    }

    public TaximeterOfferGroup invoiceAs(String invoiceAs) {
        this.invoiceAs = invoiceAs;
        return this;
    }

    public void setInvoiceAs(String invoiceAs) {
        this.invoiceAs = invoiceAs;
    }

    public TaximeterChargeByType getChargeBy() {
        return this.chargeBy;
    }

    public TaximeterOfferGroup chargeBy(TaximeterChargeByType chargeBy) {
        this.chargeBy = chargeBy;
        return this;
    }

    public void setChargeBy(TaximeterChargeByType chargeBy) {
        this.chargeBy = chargeBy;
    }

    public ZonedDateTime getCreated() {
        return this.created;
    }

    public TaximeterOfferGroup created(ZonedDateTime created) {
        this.created = created;
        return this;
    }

    public void setCreated(ZonedDateTime created) {
        this.created = created;
    }

    public ZonedDateTime getUpdated() {
        return this.updated;
    }

    public TaximeterOfferGroup updated(ZonedDateTime updated) {
        this.updated = updated;
        return this;
    }

    public void setUpdated(ZonedDateTime updated) {
        this.updated = updated;
    }

    public Set<TaximeterOfferItem> getItems() {
        return this.items;
    }

    public TaximeterOfferGroup items(Set<TaximeterOfferItem> taximeterOfferItems) {
        this.setItems(taximeterOfferItems);
        return this;
    }

    public TaximeterOfferGroup addItem(TaximeterOfferItem taximeterOfferItem) {
        this.items.add(taximeterOfferItem);
        taximeterOfferItem.setGroup(this);
        return this;
    }

    public TaximeterOfferGroup removeItem(TaximeterOfferItem taximeterOfferItem) {
        this.items.remove(taximeterOfferItem);
        taximeterOfferItem.setGroup(null);
        return this;
    }

    public void setItems(Set<TaximeterOfferItem> taximeterOfferItems) {
        if (this.items != null) {
            this.items.forEach(i -> i.setGroup(null));
        }
        if (taximeterOfferItems != null) {
            taximeterOfferItems.forEach(i -> i.setGroup(this));
        }
        this.items = taximeterOfferItems;
    }

    public TaximeterOffer getOffer() {
        return this.offer;
    }

    public TaximeterOfferGroup offer(TaximeterOffer taximeterOffer) {
        this.setOffer(taximeterOffer);
        return this;
    }

    public void setOffer(TaximeterOffer taximeterOffer) {
        this.offer = taximeterOffer;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TaximeterOfferGroup)) {
            return false;
        }
        return id != null && id.equals(((TaximeterOfferGroup) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TaximeterOfferGroup{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", invoiceAs='" + getInvoiceAs() + "'" +
            ", chargeBy='" + getChargeBy() + "'" +
            ", created='" + getCreated() + "'" +
            ", updated='" + getUpdated() + "'" +
            "}";
    }
}
