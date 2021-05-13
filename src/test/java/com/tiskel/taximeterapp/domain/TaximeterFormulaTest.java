package com.tiskel.taximeterapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.tiskel.taximeterapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TaximeterFormulaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TaximeterFormula.class);
        TaximeterFormula taximeterFormula1 = new TaximeterFormula();
        taximeterFormula1.setId(1L);
        TaximeterFormula taximeterFormula2 = new TaximeterFormula();
        taximeterFormula2.setId(taximeterFormula1.getId());
        assertThat(taximeterFormula1).isEqualTo(taximeterFormula2);
        taximeterFormula2.setId(2L);
        assertThat(taximeterFormula1).isNotEqualTo(taximeterFormula2);
        taximeterFormula1.setId(null);
        assertThat(taximeterFormula1).isNotEqualTo(taximeterFormula2);
    }
}
