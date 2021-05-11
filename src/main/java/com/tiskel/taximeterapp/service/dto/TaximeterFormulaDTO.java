package com.tiskel.taximeterapp.service.dto;

import com.tiskel.taximeterapp.domain.enumeration.TaximeterFormulaType;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.tiskel.taximeterapp.domain.TaximeterFormula} entity.
 */
public class TaximeterFormulaDTO implements Serializable {

    private Long id;

    @NotNull
    private TaximeterFormulaType type;

    @NotNull
    private String name;

    @NotNull
    private Boolean active;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TaximeterFormulaType getType() {
        return type;
    }

    public void setType(TaximeterFormulaType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
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
            ", type='" + getType() + "'" +
            ", name='" + getName() + "'" +
            ", active='" + getActive() + "'" +
            "}";
    }
}
