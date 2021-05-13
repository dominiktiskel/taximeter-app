package com.tiskel.taximeterapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TaximeterOfferGroupMapperTest {

    private TaximeterOfferGroupMapper taximeterOfferGroupMapper;

    @BeforeEach
    public void setUp() {
        taximeterOfferGroupMapper = new TaximeterOfferGroupMapperImpl();
    }
}
