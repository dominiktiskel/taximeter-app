package com.tiskel.taximeterapp.service.mapper;

import com.tiskel.taximeterapp.domain.*;
import com.tiskel.taximeterapp.service.dto.TaximeterFixedListDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TaximeterFixedList} and its DTO {@link TaximeterFixedListDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TaximeterFixedListMapper extends EntityMapper<TaximeterFixedListDTO, TaximeterFixedList> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TaximeterFixedListDTO toDtoId(TaximeterFixedList taximeterFixedList);
}
