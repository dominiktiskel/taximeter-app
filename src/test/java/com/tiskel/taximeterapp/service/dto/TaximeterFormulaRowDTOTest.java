package com.tiskel.taximeterapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.tiskel.taximeterapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TaximeterFormulaRowDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TaximeterFormulaRowDTO.class);
        TaximeterFormulaRowDTO taximeterFormulaRowDTO1 = new TaximeterFormulaRowDTO();
        taximeterFormulaRowDTO1.setId(1L);
        TaximeterFormulaRowDTO taximeterFormulaRowDTO2 = new TaximeterFormulaRowDTO();
        assertThat(taximeterFormulaRowDTO1).isNotEqualTo(taximeterFormulaRowDTO2);
        taximeterFormulaRowDTO2.setId(taximeterFormulaRowDTO1.getId());
        assertThat(taximeterFormulaRowDTO1).isEqualTo(taximeterFormulaRowDTO2);
        taximeterFormulaRowDTO2.setId(2L);
        assertThat(taximeterFormulaRowDTO1).isNotEqualTo(taximeterFormulaRowDTO2);
        taximeterFormulaRowDTO1.setId(null);
        assertThat(taximeterFormulaRowDTO1).isNotEqualTo(taximeterFormulaRowDTO2);
    }
}
