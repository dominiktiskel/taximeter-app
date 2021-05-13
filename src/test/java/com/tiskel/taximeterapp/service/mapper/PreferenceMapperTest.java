package com.tiskel.taximeterapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PreferenceMapperTest {

    private PreferenceMapper preferenceMapper;

    @BeforeEach
    public void setUp() {
        preferenceMapper = new PreferenceMapperImpl();
    }
}
