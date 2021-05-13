package com.tiskel.taximeterapp.service.mapper;

import com.tiskel.taximeterapp.domain.*;
import com.tiskel.taximeterapp.service.dto.TaximeterTimeRangeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TaximeterTimeRange} and its DTO {@link TaximeterTimeRangeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TaximeterTimeRangeMapper extends EntityMapper<TaximeterTimeRangeDTO, TaximeterTimeRange> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TaximeterTimeRangeDTO toDtoId(TaximeterTimeRange taximeterTimeRange);
}
