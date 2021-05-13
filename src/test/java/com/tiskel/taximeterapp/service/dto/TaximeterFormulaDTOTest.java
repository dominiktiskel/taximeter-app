package com.tiskel.taximeterapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.tiskel.taximeterapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TaximeterFormulaDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TaximeterFormulaDTO.class);
        TaximeterFormulaDTO taximeterFormulaDTO1 = new TaximeterFormulaDTO();
        taximeterFormulaDTO1.setId(1L);
        TaximeterFormulaDTO taximeterFormulaDTO2 = new TaximeterFormulaDTO();
        assertThat(taximeterFormulaDTO1).isNotEqualTo(taximeterFormulaDTO2);
        taximeterFormulaDTO2.setId(taximeterFormulaDTO1.getId());
        assertThat(taximeterFormulaDTO1).isEqualTo(taximeterFormulaDTO2);
        taximeterFormulaDTO2.setId(2L);
        assertThat(taximeterFormulaDTO1).isNotEqualTo(taximeterFormulaDTO2);
        taximeterFormulaDTO1.setId(null);
        assertThat(taximeterFormulaDTO1).isNotEqualTo(taximeterFormulaDTO2);
    }
}
