package com.tiskel.taximeterapp.service.mapper;

import com.tiskel.taximeterapp.domain.*;
import com.tiskel.taximeterapp.service.dto.TaximeterOfferGroupDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TaximeterOfferGroup} and its DTO {@link TaximeterOfferGroupDTO}.
 */
@Mapper(componentModel = "spring", uses = { TaximeterOfferMapper.class })
public interface TaximeterOfferGroupMapper extends EntityMapper<TaximeterOfferGroupDTO, TaximeterOfferGroup> {
    @Mapping(target = "offer", source = "offer", qualifiedByName = "id")
    TaximeterOfferGroupDTO toDto(TaximeterOfferGroup s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TaximeterOfferGroupDTO toDtoId(TaximeterOfferGroup taximeterOfferGroup);
}
