package com.tiskel.taximeterapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TaximeterFormulaRowMapperTest {

    private TaximeterFormulaRowMapper taximeterFormulaRowMapper;

    @BeforeEach
    public void setUp() {
        taximeterFormulaRowMapper = new TaximeterFormulaRowMapperImpl();
    }
}
