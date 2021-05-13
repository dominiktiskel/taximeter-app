package com.tiskel.taximeterapp.service.mapper;

import com.tiskel.taximeterapp.domain.*;
import com.tiskel.taximeterapp.service.dto.TaximeterOfferDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TaximeterOffer} and its DTO {@link TaximeterOfferDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TaximeterOfferMapper extends EntityMapper<TaximeterOfferDTO, TaximeterOffer> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TaximeterOfferDTO toDtoId(TaximeterOffer taximeterOffer);
}
