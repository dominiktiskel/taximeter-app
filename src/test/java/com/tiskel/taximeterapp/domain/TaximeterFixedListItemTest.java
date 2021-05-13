package com.tiskel.taximeterapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.tiskel.taximeterapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TaximeterFixedListItemTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TaximeterFixedListItem.class);
        TaximeterFixedListItem taximeterFixedListItem1 = new TaximeterFixedListItem();
        taximeterFixedListItem1.setId(1L);
        TaximeterFixedListItem taximeterFixedListItem2 = new TaximeterFixedListItem();
        taximeterFixedListItem2.setId(taximeterFixedListItem1.getId());
        assertThat(taximeterFixedListItem1).isEqualTo(taximeterFixedListItem2);
        taximeterFixedListItem2.setId(2L);
        assertThat(taximeterFixedListItem1).isNotEqualTo(taximeterFixedListItem2);
        taximeterFixedListItem1.setId(null);
        assertThat(taximeterFixedListItem1).isNotEqualTo(taximeterFixedListItem2);
    }
}
