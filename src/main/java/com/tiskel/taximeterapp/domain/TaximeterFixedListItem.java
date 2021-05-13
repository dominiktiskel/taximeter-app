package com.tiskel.taximeterapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A TaximeterFixedListItem.
 */
@Entity
@Table(name = "taximeter_fixed_list_item")
public class TaximeterFixedListItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "jhi_from", nullable = false)
    private String from;

    @NotNull
    @Column(name = "jhi_to", nullable = false)
    private String to;

    @NotNull
    @Column(name = "value", nullable = false)
    private Double value;

    @Column(name = "value_reverse")
    private Double valueReverse;

    @Column(name = "created")
    private ZonedDateTime created;

    @Column(name = "updated")
    private ZonedDateTime updated;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "items" }, allowSetters = true)
    private TaximeterFixedList list;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TaximeterFixedListItem id(Long id) {
        this.id = id;
        return this;
    }

    public String getFrom() {
        return this.from;
    }

    public TaximeterFixedListItem from(String from) {
        this.from = from;
        return this;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return this.to;
    }

    public TaximeterFixedListItem to(String to) {
        this.to = to;
        return this;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Double getValue() {
        return this.value;
    }

    public TaximeterFixedListItem value(Double value) {
        this.value = value;
        return this;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Double getValueReverse() {
        return this.valueReverse;
    }

    public TaximeterFixedListItem valueReverse(Double valueReverse) {
        this.valueReverse = valueReverse;
        return this;
    }

    public void setValueReverse(Double valueReverse) {
        this.valueReverse = valueReverse;
    }

    public ZonedDateTime getCreated() {
        return this.created;
    }

    public TaximeterFixedListItem created(ZonedDateTime created) {
        this.created = created;
        return this;
    }

    public void setCreated(ZonedDateTime created) {
        this.created = created;
    }

    public ZonedDateTime getUpdated() {
        return this.updated;
    }

    public TaximeterFixedListItem updated(ZonedDateTime updated) {
        this.updated = updated;
        return this;
    }

    public void setUpdated(ZonedDateTime updated) {
        this.updated = updated;
    }

    public TaximeterFixedList getList() {
        return this.list;
    }

    public TaximeterFixedListItem list(TaximeterFixedList taximeterFixedList) {
        this.setList(taximeterFixedList);
        return this;
    }

    public void setList(TaximeterFixedList taximeterFixedList) {
        this.list = taximeterFixedList;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TaximeterFixedListItem)) {
            return false;
        }
        return id != null && id.equals(((TaximeterFixedListItem) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TaximeterFixedListItem{" +
            "id=" + getId() +
            ", from='" + getFrom() + "'" +
            ", to='" + getTo() + "'" +
            ", value=" + getValue() +
            ", valueReverse=" + getValueReverse() +
            ", created='" + getCreated() + "'" +
            ", updated='" + getUpdated() + "'" +
            "}";
    }
}
