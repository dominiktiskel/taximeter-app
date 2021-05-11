package com.tiskel.taximeterapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tiskel.taximeterapp.domain.enumeration.TaximeterFormulaType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A TaximeterFormula.
 */
@Entity
@Table(name = "taximeter_formula")
public class TaximeterFormula implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private TaximeterFormulaType type;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "active", nullable = false)
    private Boolean active;

    @OneToMany(mappedBy = "row")
    @JsonIgnoreProperties(value = { "row" }, allowSetters = true)
    private Set<TaximeterFormulaRow> taximeterFormulaRows = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TaximeterFormula id(Long id) {
        this.id = id;
        return this;
    }

    public TaximeterFormulaType getType() {
        return this.type;
    }

    public TaximeterFormula type(TaximeterFormulaType type) {
        this.type = type;
        return this;
    }

    public void setType(TaximeterFormulaType type) {
        this.type = type;
    }

    public String getName() {
        return this.name;
    }

    public TaximeterFormula name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getActive() {
        return this.active;
    }

    public TaximeterFormula active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Set<TaximeterFormulaRow> getTaximeterFormulaRows() {
        return this.taximeterFormulaRows;
    }

    public TaximeterFormula taximeterFormulaRows(Set<TaximeterFormulaRow> taximeterFormulaRows) {
        this.setTaximeterFormulaRows(taximeterFormulaRows);
        return this;
    }

    public TaximeterFormula addTaximeterFormulaRow(TaximeterFormulaRow taximeterFormulaRow) {
        this.taximeterFormulaRows.add(taximeterFormulaRow);
        taximeterFormulaRow.setRow(this);
        return this;
    }

    public TaximeterFormula removeTaximeterFormulaRow(TaximeterFormulaRow taximeterFormulaRow) {
        this.taximeterFormulaRows.remove(taximeterFormulaRow);
        taximeterFormulaRow.setRow(null);
        return this;
    }

    public void setTaximeterFormulaRows(Set<TaximeterFormulaRow> taximeterFormulaRows) {
        if (this.taximeterFormulaRows != null) {
            this.taximeterFormulaRows.forEach(i -> i.setRow(null));
        }
        if (taximeterFormulaRows != null) {
            taximeterFormulaRows.forEach(i -> i.setRow(this));
        }
        this.taximeterFormulaRows = taximeterFormulaRows;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TaximeterFormula)) {
            return false;
        }
        return id != null && id.equals(((TaximeterFormula) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TaximeterFormula{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", name='" + getName() + "'" +
            ", active='" + getActive() + "'" +
            "}";
    }
}
