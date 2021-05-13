package com.tiskel.taximeterapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.tiskel.taximeterapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TaximeterFixedListItemDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TaximeterFixedListItemDTO.class);
        TaximeterFixedListItemDTO taximeterFixedListItemDTO1 = new TaximeterFixedListItemDTO();
        taximeterFixedListItemDTO1.setId(1L);
        TaximeterFixedListItemDTO taximeterFixedListItemDTO2 = new TaximeterFixedListItemDTO();
        assertThat(taximeterFixedListItemDTO1).isNotEqualTo(taximeterFixedListItemDTO2);
        taximeterFixedListItemDTO2.setId(taximeterFixedListItemDTO1.getId());
        assertThat(taximeterFixedListItemDTO1).isEqualTo(taximeterFixedListItemDTO2);
        taximeterFixedListItemDTO2.setId(2L);
        assertThat(taximeterFixedListItemDTO1).isNotEqualTo(taximeterFixedListItemDTO2);
        taximeterFixedListItemDTO1.setId(null);
        assertThat(taximeterFixedListItemDTO1).isNotEqualTo(taximeterFixedListItemDTO2);
    }
}
