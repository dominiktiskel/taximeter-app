package com.tiskel.taximeterapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A TaximeterTimeRangeItem.
 */
@Entity
@Table(name = "taximeter_time_range_item")
public class TaximeterTimeRangeItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "day", nullable = false)
    private String day;

    @NotNull
    @Column(name = "hours", nullable = false)
    private String hours;

    @Column(name = "created")
    private ZonedDateTime created;

    @Column(name = "updated")
    private ZonedDateTime updated;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "items" }, allowSetters = true)
    private TaximeterTimeRange range;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TaximeterTimeRangeItem id(Long id) {
        this.id = id;
        return this;
    }

    public String getDay() {
        return this.day;
    }

    public TaximeterTimeRangeItem day(String day) {
        this.day = day;
        return this;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getHours() {
        return this.hours;
    }

    public TaximeterTimeRangeItem hours(String hours) {
        this.hours = hours;
        return this;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public ZonedDateTime getCreated() {
        return this.created;
    }

    public TaximeterTimeRangeItem created(ZonedDateTime created) {
        this.created = created;
        return this;
    }

    public void setCreated(ZonedDateTime created) {
        this.created = created;
    }

    public ZonedDateTime getUpdated() {
        return this.updated;
    }

    public TaximeterTimeRangeItem updated(ZonedDateTime updated) {
        this.updated = updated;
        return this;
    }

    public void setUpdated(ZonedDateTime updated) {
        this.updated = updated;
    }

    public TaximeterTimeRange getRange() {
        return this.range;
    }

    public TaximeterTimeRangeItem range(TaximeterTimeRange taximeterTimeRange) {
        this.setRange(taximeterTimeRange);
        return this;
    }

    public void setRange(TaximeterTimeRange taximeterTimeRange) {
        this.range = taximeterTimeRange;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TaximeterTimeRangeItem)) {
            return false;
        }
        return id != null && id.equals(((TaximeterTimeRangeItem) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TaximeterTimeRangeItem{" +
            "id=" + getId() +
            ", day='" + getDay() + "'" +
            ", hours='" + getHours() + "'" +
            ", created='" + getCreated() + "'" +
            ", updated='" + getUpdated() + "'" +
            "}";
    }
}
