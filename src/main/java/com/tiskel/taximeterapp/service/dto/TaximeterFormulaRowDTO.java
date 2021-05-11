package com.tiskel.taximeterapp.service.dto;

import com.tiskel.taximeterapp.domain.enumeration.TaximeterFormulaRowType;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.tiskel.taximeterapp.domain.TaximeterFormulaRow} entity.
 */
public class TaximeterFormulaRowDTO implements Serializable {

    private Long id;

    @NotNull
    private TaximeterFormulaRowType type;

    @NotNull
    private Double value;

    @NotNull
    private Double step;

    private Double granulation;

    private TaximeterFormulaDTO formula;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TaximeterFormulaRowType getType() {
        return type;
    }

    public void setType(TaximeterFormulaRowType type) {
        this.type = type;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Double getStep() {
        return step;
    }

    public void setStep(Double step) {
        this.step = step;
    }

    public Double getGranulation() {
        return granulation;
    }

    public void setGranulation(Double granulation) {
        this.granulation = granulation;
    }

    public TaximeterFormulaDTO getFormula() {
        return formula;
    }

    public void setFormula(TaximeterFormulaDTO formula) {
        this.formula = formula;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TaximeterFormulaRowDTO)) {
            return false;
        }

        TaximeterFormulaRowDTO taximeterFormulaRowDTO = (TaximeterFormulaRowDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, taximeterFormulaRowDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TaximeterFormulaRowDTO{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", value=" + getValue() +
            ", step=" + getStep() +
            ", granulation=" + getGranulation() +
            ", formula=" + getFormula() +
            "}";
    }
}
