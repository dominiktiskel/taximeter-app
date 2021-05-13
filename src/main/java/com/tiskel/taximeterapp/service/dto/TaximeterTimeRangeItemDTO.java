package com.tiskel.taximeterapp.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.tiskel.taximeterapp.domain.TaximeterTimeRangeItem} entity.
 */
public class TaximeterTimeRangeItemDTO implements Serializable {

    private Long id;

    @NotNull
    private String day;

    @NotNull
    private String hours;

    private ZonedDateTime created;

    private ZonedDateTime updated;

    private TaximeterTimeRangeDTO range;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
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

    public TaximeterTimeRangeDTO getRange() {
        return range;
    }

    public void setRange(TaximeterTimeRangeDTO range) {
        this.range = range;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TaximeterTimeRangeItemDTO)) {
            return false;
        }

        TaximeterTimeRangeItemDTO taximeterTimeRangeItemDTO = (TaximeterTimeRangeItemDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, taximeterTimeRangeItemDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TaximeterTimeRangeItemDTO{" +
            "id=" + getId() +
            ", day='" + getDay() + "'" +
            ", hours='" + getHours() + "'" +
            ", created='" + getCreated() + "'" +
            ", updated='" + getUpdated() + "'" +
            ", range=" + getRange() +
            "}";
    }
}
