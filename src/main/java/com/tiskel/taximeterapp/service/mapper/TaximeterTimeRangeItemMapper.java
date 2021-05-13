package com.tiskel.taximeterapp.service.mapper;

import com.tiskel.taximeterapp.domain.*;
import com.tiskel.taximeterapp.service.dto.TaximeterTimeRangeItemDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TaximeterTimeRangeItem} and its DTO {@link TaximeterTimeRangeItemDTO}.
 */
@Mapper(componentModel = "spring", uses = { TaximeterTimeRangeMapper.class })
public interface TaximeterTimeRangeItemMapper extends EntityMapper<TaximeterTimeRangeItemDTO, TaximeterTimeRangeItem> {
    @Mapping(target = "range", source = "range", qualifiedByName = "id")
    TaximeterTimeRangeItemDTO toDto(TaximeterTimeRangeItem s);
}
