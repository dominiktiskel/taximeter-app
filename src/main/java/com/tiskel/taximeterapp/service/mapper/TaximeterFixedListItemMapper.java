package com.tiskel.taximeterapp.service.mapper;

import com.tiskel.taximeterapp.domain.*;
import com.tiskel.taximeterapp.service.dto.TaximeterFixedListItemDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TaximeterFixedListItem} and its DTO {@link TaximeterFixedListItemDTO}.
 */
@Mapper(componentModel = "spring", uses = { TaximeterFixedListMapper.class })
public interface TaximeterFixedListItemMapper extends EntityMapper<TaximeterFixedListItemDTO, TaximeterFixedListItem> {
    @Mapping(target = "list", source = "list", qualifiedByName = "id")
    TaximeterFixedListItemDTO toDto(TaximeterFixedListItem s);
}
