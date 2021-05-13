package com.tiskel.taximeterapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.tiskel.taximeterapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TaximeterTimeRangeItemDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TaximeterTimeRangeItemDTO.class);
        TaximeterTimeRangeItemDTO taximeterTimeRangeItemDTO1 = new TaximeterTimeRangeItemDTO();
        taximeterTimeRangeItemDTO1.setId(1L);
        TaximeterTimeRangeItemDTO taximeterTimeRangeItemDTO2 = new TaximeterTimeRangeItemDTO();
        assertThat(taximeterTimeRangeItemDTO1).isNotEqualTo(taximeterTimeRangeItemDTO2);
        taximeterTimeRangeItemDTO2.setId(taximeterTimeRangeItemDTO1.getId());
        assertThat(taximeterTimeRangeItemDTO1).isEqualTo(taximeterTimeRangeItemDTO2);
        taximeterTimeRangeItemDTO2.setId(2L);
        assertThat(taximeterTimeRangeItemDTO1).isNotEqualTo(taximeterTimeRangeItemDTO2);
        taximeterTimeRangeItemDTO1.setId(null);
        assertThat(taximeterTimeRangeItemDTO1).isNotEqualTo(taximeterTimeRangeItemDTO2);
    }
}
