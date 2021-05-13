package com.tiskel.taximeterapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TaximeterFormulaMapperTest {

    private TaximeterFormulaMapper taximeterFormulaMapper;

    @BeforeEach
    public void setUp() {
        taximeterFormulaMapper = new TaximeterFormulaMapperImpl();
    }
}
