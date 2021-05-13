package com.tiskel.taximeterapp.service.mapper;

import com.tiskel.taximeterapp.domain.*;
import com.tiskel.taximeterapp.service.dto.TaximeterOfferItemDTO;
import java.util.Set;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TaximeterOfferItem} and its DTO {@link TaximeterOfferItemDTO}.
 */
@Mapper(
    componentModel = "spring",
    uses = {
        TaximeterFixedListMapper.class,
        TaximeterFormulaMapper.class,
        TaximeterTimeRangeMapper.class,
        PreferenceMapper.class,
        TaximeterOfferGroupMapper.class,
    }
)
public interface TaximeterOfferItemMapper extends EntityMapper<TaximeterOfferItemDTO, TaximeterOfferItem> {
    @Mapping(target = "fixedList1", source = "fixedList1", qualifiedByName = "id")
    @Mapping(target = "fixedList2", source = "fixedList2", qualifiedByName = "id")
    @Mapping(target = "formula", source = "formula", qualifiedByName = "id")
    @Mapping(target = "timeRange", source = "timeRange", qualifiedByName = "id")
    @Mapping(target = "preferences", source = "preferences", qualifiedByName = "idSet")
    @Mapping(target = "group", source = "group", qualifiedByName = "id")
    TaximeterOfferItemDTO toDto(TaximeterOfferItem s);

    @Mapping(target = "removePreference", ignore = true)
    TaximeterOfferItem toEntity(TaximeterOfferItemDTO taximeterOfferItemDTO);
}
