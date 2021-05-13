package com.tiskel.taximeterapp.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.tiskel.taximeterapp.domain.TaximeterFixedListItem} entity.
 */
public class TaximeterFixedListItemDTO implements Serializable {

    private Long id;

    @NotNull
    private String from;

    @NotNull
    private String to;

    @NotNull
    private Double value;

    private Double valueReverse;

    private ZonedDateTime created;

    private ZonedDateTime updated;

    private TaximeterFixedListDTO list;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Double getValueReverse() {
        return valueReverse;
    }

    public void setValueReverse(Double valueReverse) {
        this.valueReverse = valueReverse;
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

    public TaximeterFixedListDTO getList() {
        return list;
    }

    public void setList(TaximeterFixedListDTO list) {
        this.list = list;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TaximeterFixedListItemDTO)) {
            return false;
        }

        TaximeterFixedListItemDTO taximeterFixedListItemDTO = (TaximeterFixedListItemDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, taximeterFixedListItemDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TaximeterFixedListItemDTO{" +
            "id=" + getId() +
            ", from='" + getFrom() + "'" +
            ", to='" + getTo() + "'" +
            ", value=" + getValue() +
            ", valueReverse=" + getValueReverse() +
            ", created='" + getCreated() + "'" +
            ", updated='" + getUpdated() + "'" +
            ", list=" + getList() +
            "}";
    }
}
