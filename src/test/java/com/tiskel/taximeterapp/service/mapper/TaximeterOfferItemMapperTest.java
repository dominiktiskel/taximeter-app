package com.tiskel.taximeterapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TaximeterOfferItemMapperTest {

    private TaximeterOfferItemMapper taximeterOfferItemMapper;

    @BeforeEach
    public void setUp() {
        taximeterOfferItemMapper = new TaximeterOfferItemMapperImpl();
    }
}
