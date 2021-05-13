package com.tiskel.taximeterapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.tiskel.taximeterapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TaximeterOfferDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TaximeterOfferDTO.class);
        TaximeterOfferDTO taximeterOfferDTO1 = new TaximeterOfferDTO();
        taximeterOfferDTO1.setId(1L);
        TaximeterOfferDTO taximeterOfferDTO2 = new TaximeterOfferDTO();
        assertThat(taximeterOfferDTO1).isNotEqualTo(taximeterOfferDTO2);
        taximeterOfferDTO2.setId(taximeterOfferDTO1.getId());
        assertThat(taximeterOfferDTO1).isEqualTo(taximeterOfferDTO2);
        taximeterOfferDTO2.setId(2L);
        assertThat(taximeterOfferDTO1).isNotEqualTo(taximeterOfferDTO2);
        taximeterOfferDTO1.setId(null);
        assertThat(taximeterOfferDTO1).isNotEqualTo(taximeterOfferDTO2);
    }
}
