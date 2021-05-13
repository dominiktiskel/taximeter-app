package com.tiskel.taximeterapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TaximeterOfferMapperTest {

    private TaximeterOfferMapper taximeterOfferMapper;

    @BeforeEach
    public void setUp() {
        taximeterOfferMapper = new TaximeterOfferMapperImpl();
    }
}
