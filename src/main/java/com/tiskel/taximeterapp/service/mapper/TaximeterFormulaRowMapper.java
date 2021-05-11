package com.tiskel.taximeterapp.service.mapper;

import com.tiskel.taximeterapp.domain.*;
import com.tiskel.taximeterapp.service.dto.TaximeterFormulaRowDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TaximeterFormulaRow} and its DTO {@link TaximeterFormulaRowDTO}.
 */
@Mapper(componentModel = "spring", uses = { TaximeterFormulaMapper.class })
public interface TaximeterFormulaRowMapper extends EntityMapper<TaximeterFormulaRowDTO, TaximeterFormulaRow> {
    @Mapping(target = "formula", source = "formula", qualifiedByName = "id")
    TaximeterFormulaRowDTO toDto(TaximeterFormulaRow s);
}
