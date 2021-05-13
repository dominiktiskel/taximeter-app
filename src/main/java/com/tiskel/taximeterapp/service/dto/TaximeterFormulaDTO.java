package com.tiskel.taximeterapp.service.dto;

import com.tiskel.taximeterapp.domain.enumeration.TaximeterChargeByType;
import com.tiskel.taximeterapp.domain.enumeration.TaximeterFormulaType;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.tiskel.taximeterapp.domain.TaximeterFormula} entity.
 */
public class TaximeterFormulaDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private String description;

    @NotNull
    private TaximeterFormulaType type;

    @NotNull
    private TaximeterChargeByType chargeBy;

    @NotNull
    private String jsonData;

    private ZonedDateTime created;

    private ZonedDateTime updated;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaximeterFormulaType getType() {
        return type;
    }

    public void setType(TaximeterFormulaType type) {
        this.type = type;
    }

    public TaximeterChargeByType getChargeBy() {
        return chargeBy;
    }

    public void setChargeBy(TaximeterChargeByType chargeBy) {
        this.chargeBy = chargeBy;
    }

    public String getJsonData() {
        return jsonData;
    }

    public void setJsonData(String jsonData) {
        this.jsonData = jsonData;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TaximeterFormulaDTO)) {
            return false;
        }

        TaximeterFormulaDTO taximeterFormulaDTO = (TaximeterFormulaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, taximeterFormulaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TaximeterFormulaDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", type='" + getType() + "'" +
            ", chargeBy='" + getChargeBy() + "'" +
            ", jsonData='" + getJsonData() + "'" +
            ", created='" + getCreated() + "'" +
            ", updated='" + getUpdated() + "'" +
            "}";
    }
}
