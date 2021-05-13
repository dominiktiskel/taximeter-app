package com.tiskel.taximeterapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.tiskel.taximeterapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TaximeterTimeRangeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TaximeterTimeRangeDTO.class);
        TaximeterTimeRangeDTO taximeterTimeRangeDTO1 = new TaximeterTimeRangeDTO();
        taximeterTimeRangeDTO1.setId(1L);
        TaximeterTimeRangeDTO taximeterTimeRangeDTO2 = new TaximeterTimeRangeDTO();
        assertThat(taximeterTimeRangeDTO1).isNotEqualTo(taximeterTimeRangeDTO2);
        taximeterTimeRangeDTO2.setId(taximeterTimeRangeDTO1.getId());
        assertThat(taximeterTimeRangeDTO1).isEqualTo(taximeterTimeRangeDTO2);
        taximeterTimeRangeDTO2.setId(2L);
        assertThat(taximeterTimeRangeDTO1).isNotEqualTo(taximeterTimeRangeDTO2);
        taximeterTimeRangeDTO1.setId(null);
        assertThat(taximeterTimeRangeDTO1).isNotEqualTo(taximeterTimeRangeDTO2);
    }
}
