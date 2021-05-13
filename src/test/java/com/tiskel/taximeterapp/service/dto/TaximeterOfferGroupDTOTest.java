package com.tiskel.taximeterapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.tiskel.taximeterapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TaximeterOfferGroupDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TaximeterOfferGroupDTO.class);
        TaximeterOfferGroupDTO taximeterOfferGroupDTO1 = new TaximeterOfferGroupDTO();
        taximeterOfferGroupDTO1.setId(1L);
        TaximeterOfferGroupDTO taximeterOfferGroupDTO2 = new TaximeterOfferGroupDTO();
        assertThat(taximeterOfferGroupDTO1).isNotEqualTo(taximeterOfferGroupDTO2);
        taximeterOfferGroupDTO2.setId(taximeterOfferGroupDTO1.getId());
        assertThat(taximeterOfferGroupDTO1).isEqualTo(taximeterOfferGroupDTO2);
        taximeterOfferGroupDTO2.setId(2L);
        assertThat(taximeterOfferGroupDTO1).isNotEqualTo(taximeterOfferGroupDTO2);
        taximeterOfferGroupDTO1.setId(null);
        assertThat(taximeterOfferGroupDTO1).isNotEqualTo(taximeterOfferGroupDTO2);
    }
}
