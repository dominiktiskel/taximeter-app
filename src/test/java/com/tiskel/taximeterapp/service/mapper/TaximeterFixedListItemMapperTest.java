package com.tiskel.taximeterapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TaximeterFixedListItemMapperTest {

    private TaximeterFixedListItemMapper taximeterFixedListItemMapper;

    @BeforeEach
    public void setUp() {
        taximeterFixedListItemMapper = new TaximeterFixedListItemMapperImpl();
    }
}
