package com.tiskel.taximeterapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.tiskel.taximeterapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TaximeterFixedListTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TaximeterFixedList.class);
        TaximeterFixedList taximeterFixedList1 = new TaximeterFixedList();
        taximeterFixedList1.setId(1L);
        TaximeterFixedList taximeterFixedList2 = new TaximeterFixedList();
        taximeterFixedList2.setId(taximeterFixedList1.getId());
        assertThat(taximeterFixedList1).isEqualTo(taximeterFixedList2);
        taximeterFixedList2.setId(2L);
        assertThat(taximeterFixedList1).isNotEqualTo(taximeterFixedList2);
        taximeterFixedList1.setId(null);
        assertThat(taximeterFixedList1).isNotEqualTo(taximeterFixedList2);
    }
}
