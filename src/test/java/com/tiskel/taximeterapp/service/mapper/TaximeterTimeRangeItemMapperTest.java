package com.tiskel.taximeterapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TaximeterTimeRangeItemMapperTest {

    private TaximeterTimeRangeItemMapper taximeterTimeRangeItemMapper;

    @BeforeEach
    public void setUp() {
        taximeterTimeRangeItemMapper = new TaximeterTimeRangeItemMapperImpl();
    }
}
