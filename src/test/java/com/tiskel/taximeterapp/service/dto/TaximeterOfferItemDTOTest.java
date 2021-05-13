package com.tiskel.taximeterapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.tiskel.taximeterapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TaximeterOfferItemDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TaximeterOfferItemDTO.class);
        TaximeterOfferItemDTO taximeterOfferItemDTO1 = new TaximeterOfferItemDTO();
        taximeterOfferItemDTO1.setId(1L);
        TaximeterOfferItemDTO taximeterOfferItemDTO2 = new TaximeterOfferItemDTO();
        assertThat(taximeterOfferItemDTO1).isNotEqualTo(taximeterOfferItemDTO2);
        taximeterOfferItemDTO2.setId(taximeterOfferItemDTO1.getId());
        assertThat(taximeterOfferItemDTO1).isEqualTo(taximeterOfferItemDTO2);
        taximeterOfferItemDTO2.setId(2L);
        assertThat(taximeterOfferItemDTO1).isNotEqualTo(taximeterOfferItemDTO2);
        taximeterOfferItemDTO1.setId(null);
        assertThat(taximeterOfferItemDTO1).isNotEqualTo(taximeterOfferItemDTO2);
    }
}
