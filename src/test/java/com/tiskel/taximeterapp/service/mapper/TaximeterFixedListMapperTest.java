package com.tiskel.taximeterapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TaximeterFixedListMapperTest {

    private TaximeterFixedListMapper taximeterFixedListMapper;

    @BeforeEach
    public void setUp() {
        taximeterFixedListMapper = new TaximeterFixedListMapperImpl();
    }
}
