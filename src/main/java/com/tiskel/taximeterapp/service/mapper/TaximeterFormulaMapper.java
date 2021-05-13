package com.tiskel.taximeterapp.service.mapper;

import com.tiskel.taximeterapp.domain.*;
import com.tiskel.taximeterapp.service.dto.TaximeterFormulaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TaximeterFormula} and its DTO {@link TaximeterFormulaDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TaximeterFormulaMapper extends EntityMapper<TaximeterFormulaDTO, TaximeterFormula> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TaximeterFormulaDTO toDtoId(TaximeterFormula taximeterFormula);
}
