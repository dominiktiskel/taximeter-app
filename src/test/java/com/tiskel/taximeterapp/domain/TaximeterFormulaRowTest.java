package com.tiskel.taximeterapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.tiskel.taximeterapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TaximeterFormulaRowTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TaximeterFormulaRow.class);
        TaximeterFormulaRow taximeterFormulaRow1 = new TaximeterFormulaRow();
        taximeterFormulaRow1.setId(1L);
        TaximeterFormulaRow taximeterFormulaRow2 = new TaximeterFormulaRow();
        taximeterFormulaRow2.setId(taximeterFormulaRow1.getId());
        assertThat(taximeterFormulaRow1).isEqualTo(taximeterFormulaRow2);
        taximeterFormulaRow2.setId(2L);
        assertThat(taximeterFormulaRow1).isNotEqualTo(taximeterFormulaRow2);
        taximeterFormulaRow1.setId(null);
        assertThat(taximeterFormulaRow1).isNotEqualTo(taximeterFormulaRow2);
    }
}
