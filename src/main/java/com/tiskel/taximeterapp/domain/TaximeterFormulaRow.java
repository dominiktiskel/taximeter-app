package com.tiskel.taximeterapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tiskel.taximeterapp.domain.enumeration.TaximeterFormulaRowType;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A TaximeterFormulaRow.
 */
@Entity
@Table(name = "taximeter_formula_row")
public class TaximeterFormulaRow implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private TaximeterFormulaRowType type;

    @NotNull
    @Column(name = "value", nullable = false)
    private Double value;

    @NotNull
    @Column(name = "step", nullable = false)
    private Double step;

    @Column(name = "granulation")
    private Double granulation;

    @ManyToOne
    @JsonIgnoreProperties(value = { "taximeterFormulaRows" }, allowSetters = true)
    private TaximeterFormula row;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TaximeterFormulaRow id(Long id) {
        this.id = id;
        return this;
    }

    public TaximeterFormulaRowType getType() {
        return this.type;
    }

    public TaximeterFormulaRow type(TaximeterFormulaRowType type) {
        this.type = type;
        return this;
    }

    public void setType(TaximeterFormulaRowType type) {
        this.type = type;
    }

    public Double getValue() {
        return this.value;
    }

    public TaximeterFormulaRow value(Double value) {
        this.value = value;
        return this;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Double getStep() {
        return this.step;
    }

    public TaximeterFormulaRow step(Double step) {
        this.step = step;
        return this;
    }

    public void setStep(Double step) {
        this.step = step;
    }

    public Double getGranulation() {
        return this.granulation;
    }

    public TaximeterFormulaRow granulation(Double granulation) {
        this.granulation = granulation;
        return this;
    }

    public void setGranulation(Double granulation) {
        this.granulation = granulation;
    }

    public TaximeterFormula getRow() {
        return this.row;
    }

    public TaximeterFormulaRow row(TaximeterFormula taximeterFormula) {
        this.setRow(taximeterFormula);
        return this;
    }

    public void setRow(TaximeterFormula taximeterFormula) {
        this.row = taximeterFormula;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TaximeterFormulaRow)) {
            return false;
        }
        return id != null && id.equals(((TaximeterFormulaRow) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TaximeterFormulaRow{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", value=" + getValue() +
            ", step=" + getStep() +
            ", granulation=" + getGranulation() +
            "}";
    }
}
