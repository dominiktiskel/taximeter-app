package com.tiskel.taximeterapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A TaximeterOffer.
 */
@Entity
@Table(name = "taximeter_offer")
public class TaximeterOffer implements Serializable {

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

    @Column(name = "valid_from")
    private ZonedDateTime validFrom;

    @Column(name = "valid_to")
    private ZonedDateTime validTo;

    @Column(name = "created")
    private ZonedDateTime created;

    @Column(name = "updated")
    private ZonedDateTime updated;

    @OneToMany(mappedBy = "offer")
    @JsonIgnoreProperties(value = { "items", "offer" }, allowSetters = true)
    private Set<TaximeterOfferGroup> groups = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TaximeterOffer id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public TaximeterOffer name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public TaximeterOffer description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ZonedDateTime getValidFrom() {
        return this.validFrom;
    }

    public TaximeterOffer validFrom(ZonedDateTime validFrom) {
        this.validFrom = validFrom;
        return this;
    }

    public void setValidFrom(ZonedDateTime validFrom) {
        this.validFrom = validFrom;
    }

    public ZonedDateTime getValidTo() {
        return this.validTo;
    }

    public TaximeterOffer validTo(ZonedDateTime validTo) {
        this.validTo = validTo;
        return this;
    }

    public void setValidTo(ZonedDateTime validTo) {
        this.validTo = validTo;
    }

    public ZonedDateTime getCreated() {
        return this.created;
    }

    public TaximeterOffer created(ZonedDateTime created) {
        this.created = created;
        return this;
    }

    public void setCreated(ZonedDateTime created) {
        this.created = created;
    }

    public ZonedDateTime getUpdated() {
        return this.updated;
    }

    public TaximeterOffer updated(ZonedDateTime updated) {
        this.updated = updated;
        return this;
    }

    public void setUpdated(ZonedDateTime updated) {
        this.updated = updated;
    }

    public Set<TaximeterOfferGroup> getGroups() {
        return this.groups;
    }

    public TaximeterOffer groups(Set<TaximeterOfferGroup> taximeterOfferGroups) {
        this.setGroups(taximeterOfferGroups);
        return this;
    }

    public TaximeterOffer addGroup(TaximeterOfferGroup taximeterOfferGroup) {
        this.groups.add(taximeterOfferGroup);
        taximeterOfferGroup.setOffer(this);
        return this;
    }

    public TaximeterOffer removeGroup(TaximeterOfferGroup taximeterOfferGroup) {
        this.groups.remove(taximeterOfferGroup);
        taximeterOfferGroup.setOffer(null);
        return this;
    }

    public void setGroups(Set<TaximeterOfferGroup> taximeterOfferGroups) {
        if (this.groups != null) {
            this.groups.forEach(i -> i.setOffer(null));
        }
        if (taximeterOfferGroups != null) {
            taximeterOfferGroups.forEach(i -> i.setOffer(this));
        }
        this.groups = taximeterOfferGroups;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TaximeterOffer)) {
            return false;
        }
        return id != null && id.equals(((TaximeterOffer) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TaximeterOffer{" +
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
