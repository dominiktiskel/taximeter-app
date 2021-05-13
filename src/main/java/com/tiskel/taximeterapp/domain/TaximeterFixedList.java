package com.tiskel.taximeterapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tiskel.taximeterapp.domain.enumeration.TaximeterFixedListType;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A TaximeterFixedList.
 */
@Entity
@Table(name = "taximeter_fixed_list")
public class TaximeterFixedList implements Serializable {

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

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private TaximeterFixedListType type;

    @Column(name = "created")
    private ZonedDateTime created;

    @Column(name = "updated")
    private ZonedDateTime updated;

    @OneToMany(mappedBy = "list")
    @JsonIgnoreProperties(value = { "list" }, allowSetters = true)
    private Set<TaximeterFixedListItem> items = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TaximeterFixedList id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public TaximeterFixedList name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public TaximeterFixedList description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaximeterFixedListType getType() {
        return this.type;
    }

    public TaximeterFixedList type(TaximeterFixedListType type) {
        this.type = type;
        return this;
    }

    public void setType(TaximeterFixedListType type) {
        this.type = type;
    }

    public ZonedDateTime getCreated() {
        return this.created;
    }

    public TaximeterFixedList created(ZonedDateTime created) {
        this.created = created;
        return this;
    }

    public void setCreated(ZonedDateTime created) {
        this.created = created;
    }

    public ZonedDateTime getUpdated() {
        return this.updated;
    }

    public TaximeterFixedList updated(ZonedDateTime updated) {
        this.updated = updated;
        return this;
    }

    public void setUpdated(ZonedDateTime updated) {
        this.updated = updated;
    }

    public Set<TaximeterFixedListItem> getItems() {
        return this.items;
    }

    public TaximeterFixedList items(Set<TaximeterFixedListItem> taximeterFixedListItems) {
        this.setItems(taximeterFixedListItems);
        return this;
    }

    public TaximeterFixedList addItem(TaximeterFixedListItem taximeterFixedListItem) {
        this.items.add(taximeterFixedListItem);
        taximeterFixedListItem.setList(this);
        return this;
    }

    public TaximeterFixedList removeItem(TaximeterFixedListItem taximeterFixedListItem) {
        this.items.remove(taximeterFixedListItem);
        taximeterFixedListItem.setList(null);
        return this;
    }

    public void setItems(Set<TaximeterFixedListItem> taximeterFixedListItems) {
        if (this.items != null) {
            this.items.forEach(i -> i.setList(null));
        }
        if (taximeterFixedListItems != null) {
            taximeterFixedListItems.forEach(i -> i.setList(this));
        }
        this.items = taximeterFixedListItems;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TaximeterFixedList)) {
            return false;
        }
        return id != null && id.equals(((TaximeterFixedList) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TaximeterFixedList{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", type='" + getType() + "'" +
            ", created='" + getCreated() + "'" +
            ", updated='" + getUpdated() + "'" +
            "}";
    }
}
