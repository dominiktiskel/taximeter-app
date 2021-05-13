package com.tiskel.taximeterapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.tiskel.taximeterapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TaximeterOfferItemTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TaximeterOfferItem.class);
        TaximeterOfferItem taximeterOfferItem1 = new TaximeterOfferItem();
        taximeterOfferItem1.setId(1L);
        TaximeterOfferItem taximeterOfferItem2 = new TaximeterOfferItem();
        taximeterOfferItem2.setId(taximeterOfferItem1.getId());
        assertThat(taximeterOfferItem1).isEqualTo(taximeterOfferItem2);
        taximeterOfferItem2.setId(2L);
        assertThat(taximeterOfferItem1).isNotEqualTo(taximeterOfferItem2);
        taximeterOfferItem1.setId(null);
        assertThat(taximeterOfferItem1).isNotEqualTo(taximeterOfferItem2);
    }
}
