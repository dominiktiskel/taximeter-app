package com.tiskel.taximeterapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.tiskel.taximeterapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TaximeterOfferGroupTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TaximeterOfferGroup.class);
        TaximeterOfferGroup taximeterOfferGroup1 = new TaximeterOfferGroup();
        taximeterOfferGroup1.setId(1L);
        TaximeterOfferGroup taximeterOfferGroup2 = new TaximeterOfferGroup();
        taximeterOfferGroup2.setId(taximeterOfferGroup1.getId());
        assertThat(taximeterOfferGroup1).isEqualTo(taximeterOfferGroup2);
        taximeterOfferGroup2.setId(2L);
        assertThat(taximeterOfferGroup1).isNotEqualTo(taximeterOfferGroup2);
        taximeterOfferGroup1.setId(null);
        assertThat(taximeterOfferGroup1).isNotEqualTo(taximeterOfferGroup2);
    }
}
