package com.tiskel.taximeterapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.tiskel.taximeterapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TaximeterOfferTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TaximeterOffer.class);
        TaximeterOffer taximeterOffer1 = new TaximeterOffer();
        taximeterOffer1.setId(1L);
        TaximeterOffer taximeterOffer2 = new TaximeterOffer();
        taximeterOffer2.setId(taximeterOffer1.getId());
        assertThat(taximeterOffer1).isEqualTo(taximeterOffer2);
        taximeterOffer2.setId(2L);
        assertThat(taximeterOffer1).isNotEqualTo(taximeterOffer2);
        taximeterOffer1.setId(null);
        assertThat(taximeterOffer1).isNotEqualTo(taximeterOffer2);
    }
}
