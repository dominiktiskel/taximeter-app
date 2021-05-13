package com.tiskel.taximeterapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A Preference.
 */
@Entity
@Table(name = "preference")
public class Preference implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @ManyToMany(mappedBy = "preferences")
    @JsonIgnoreProperties(value = { "fixedList1", "fixedList2", "formula", "timeRange", "preferences", "group" }, allowSetters = true)
    private Set<TaximeterOfferItem> taximeterOfferItems = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Preference id(Long id) {
        this.id = id;
        return this;
    }

    public Set<TaximeterOfferItem> getTaximeterOfferItems() {
        return this.taximeterOfferItems;
    }

    public Preference taximeterOfferItems(Set<TaximeterOfferItem> taximeterOfferItems) {
        this.setTaximeterOfferItems(taximeterOfferItems);
        return this;
    }

    public Preference addTaximeterOfferItem(TaximeterOfferItem taximeterOfferItem) {
        this.taximeterOfferItems.add(taximeterOfferItem);
        taximeterOfferItem.getPreferences().add(this);
        return this;
    }

    public Preference removeTaximeterOfferItem(TaximeterOfferItem taximeterOfferItem) {
        this.taximeterOfferItems.remove(taximeterOfferItem);
        taximeterOfferItem.getPreferences().remove(this);
        return this;
    }

    public void setTaximeterOfferItems(Set<TaximeterOfferItem> taximeterOfferItems) {
        if (this.taximeterOfferItems != null) {
            this.taximeterOfferItems.forEach(i -> i.removePreference(this));
        }
        if (taximeterOfferItems != null) {
            taximeterOfferItems.forEach(i -> i.addPreference(this));
        }
        this.taximeterOfferItems = taximeterOfferItems;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Preference)) {
            return false;
        }
        return id != null && id.equals(((Preference) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Preference{" +
            "id=" + getId() +
            "}";
    }
}
