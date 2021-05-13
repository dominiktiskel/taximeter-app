package com.tiskel.taximeterapp.domain;

import com.tiskel.taximeterapp.domain.enumeration.TaximeterChargeByType;
import com.tiskel.taximeterapp.domain.enumeration.TaximeterFormulaType;
import java.io.Serializable;
import java.time.ZonedDateTime;
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
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private TaximeterFormulaType type;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "charge_by", nullable = false)
    private TaximeterChargeByType chargeBy;

    @NotNull
    @Column(name = "json_data", nullable = false)
    private String jsonData;

    @Column(name = "created")
    private ZonedDateTime created;

    @Column(name = "updated")
    private ZonedDateTime updated;

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

    public String getDescription() {
        return this.description;
    }

    public TaximeterFormula description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public TaximeterChargeByType getChargeBy() {
        return this.chargeBy;
    }

    public TaximeterFormula chargeBy(TaximeterChargeByType chargeBy) {
        this.chargeBy = chargeBy;
        return this;
    }

    public void setChargeBy(TaximeterChargeByType chargeBy) {
        this.chargeBy = chargeBy;
    }

    public String getJsonData() {
        return this.jsonData;
    }

    public TaximeterFormula jsonData(String jsonData) {
        this.jsonData = jsonData;
        return this;
    }

    public void setJsonData(String jsonData) {
        this.jsonData = jsonData;
    }

    public ZonedDateTime getCreated() {
        return this.created;
    }

    public TaximeterFormula created(ZonedDateTime created) {
        this.created = created;
        return this;
    }

    public void setCreated(ZonedDateTime created) {
        this.created = created;
    }

    public ZonedDateTime getUpdated() {
        return this.updated;
    }

    public TaximeterFormula updated(ZonedDateTime updated) {
        this.updated = updated;
        return this;
    }

    public void setUpdated(ZonedDateTime updated) {
        this.updated = updated;
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
