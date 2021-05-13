package com.tiskel.taximeterapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.tiskel.taximeterapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TaximeterTimeRangeItemTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TaximeterTimeRangeItem.class);
        TaximeterTimeRangeItem taximeterTimeRangeItem1 = new TaximeterTimeRangeItem();
        taximeterTimeRangeItem1.setId(1L);
        TaximeterTimeRangeItem taximeterTimeRangeItem2 = new TaximeterTimeRangeItem();
        taximeterTimeRangeItem2.setId(taximeterTimeRangeItem1.getId());
        assertThat(taximeterTimeRangeItem1).isEqualTo(taximeterTimeRangeItem2);
        taximeterTimeRangeItem2.setId(2L);
        assertThat(taximeterTimeRangeItem1).isNotEqualTo(taximeterTimeRangeItem2);
        taximeterTimeRangeItem1.setId(null);
        assertThat(taximeterTimeRangeItem1).isNotEqualTo(taximeterTimeRangeItem2);
    }
}
