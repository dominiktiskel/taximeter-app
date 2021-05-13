package com.tiskel.taximeterapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.tiskel.taximeterapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TaximeterTimeRangeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TaximeterTimeRange.class);
        TaximeterTimeRange taximeterTimeRange1 = new TaximeterTimeRange();
        taximeterTimeRange1.setId(1L);
        TaximeterTimeRange taximeterTimeRange2 = new TaximeterTimeRange();
        taximeterTimeRange2.setId(taximeterTimeRange1.getId());
        assertThat(taximeterTimeRange1).isEqualTo(taximeterTimeRange2);
        taximeterTimeRange2.setId(2L);
        assertThat(taximeterTimeRange1).isNotEqualTo(taximeterTimeRange2);
        taximeterTimeRange1.setId(null);
        assertThat(taximeterTimeRange1).isNotEqualTo(taximeterTimeRange2);
    }
}
