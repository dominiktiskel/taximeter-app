package com.tiskel.taximeterapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.tiskel.taximeterapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TaximeterFixedListDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TaximeterFixedListDTO.class);
        TaximeterFixedListDTO taximeterFixedListDTO1 = new TaximeterFixedListDTO();
        taximeterFixedListDTO1.setId(1L);
        TaximeterFixedListDTO taximeterFixedListDTO2 = new TaximeterFixedListDTO();
        assertThat(taximeterFixedListDTO1).isNotEqualTo(taximeterFixedListDTO2);
        taximeterFixedListDTO2.setId(taximeterFixedListDTO1.getId());
        assertThat(taximeterFixedListDTO1).isEqualTo(taximeterFixedListDTO2);
        taximeterFixedListDTO2.setId(2L);
        assertThat(taximeterFixedListDTO1).isNotEqualTo(taximeterFixedListDTO2);
        taximeterFixedListDTO1.setId(null);
        assertThat(taximeterFixedListDTO1).isNotEqualTo(taximeterFixedListDTO2);
    }
}
