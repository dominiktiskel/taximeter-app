package com.tiskel.taximeterapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TaximeterTimeRangeMapperTest {

    private TaximeterTimeRangeMapper taximeterTimeRangeMapper;

    @BeforeEach
    public void setUp() {
        taximeterTimeRangeMapper = new TaximeterTimeRangeMapperImpl();
    }
}
