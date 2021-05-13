package com.tiskel.taximeterapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A TaximeterTimeRange.
 */
@Entity
@Table(name = "taximeter_time_range")
public class TaximeterTimeRange implements Serializable {

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

    @Column(name = "created")
    private ZonedDateTime created;

    @Column(name = "updated")
    private ZonedDateTime updated;

    @OneToMany(mappedBy = "range")
    @JsonIgnoreProperties(value = { "range" }, allowSetters = true)
    private Set<TaximeterTimeRangeItem> items = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TaximeterTimeRange id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public TaximeterTimeRange name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public TaximeterTimeRange description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ZonedDateTime getCreated() {
        return this.created;
    }

    public TaximeterTimeRange created(ZonedDateTime created) {
        this.created = created;
        return this;
    }

    public void setCreated(ZonedDateTime created) {
        this.created = created;
    }

    public ZonedDateTime getUpdated() {
        return this.updated;
    }

    public TaximeterTimeRange updated(ZonedDateTime updated) {
        this.updated = updated;
        return this;
    }

    public void setUpdated(ZonedDateTime updated) {
        this.updated = updated;
    }

    public Set<TaximeterTimeRangeItem> getItems() {
        return this.items;
    }

    public TaximeterTimeRange items(Set<TaximeterTimeRangeItem> taximeterTimeRangeItems) {
        this.setItems(taximeterTimeRangeItems);
        return this;
    }

    public TaximeterTimeRange addItem(TaximeterTimeRangeItem taximeterTimeRangeItem) {
        this.items.add(taximeterTimeRangeItem);
        taximeterTimeRangeItem.setRange(this);
        return this;
    }

    public TaximeterTimeRange removeItem(TaximeterTimeRangeItem taximeterTimeRangeItem) {
        this.items.remove(taximeterTimeRangeItem);
        taximeterTimeRangeItem.setRange(null);
        return this;
    }

    public void setItems(Set<TaximeterTimeRangeItem> taximeterTimeRangeItems) {
        if (this.items != null) {
            this.items.forEach(i -> i.setRange(null));
        }
        if (taximeterTimeRangeItems != null) {
            taximeterTimeRangeItems.forEach(i -> i.setRange(this));
        }
        this.items = taximeterTimeRangeItems;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TaximeterTimeRange)) {
            return false;
        }
        return id != null && id.equals(((TaximeterTimeRange) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TaximeterTimeRange{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", created='" + getCreated() + "'" +
            ", updated='" + getUpdated() + "'" +
            "}";
    }
}
