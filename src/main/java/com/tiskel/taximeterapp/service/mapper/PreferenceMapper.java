package com.tiskel.taximeterapp.service.mapper;

import com.tiskel.taximeterapp.domain.*;
import com.tiskel.taximeterapp.service.dto.PreferenceDTO;
import java.util.Set;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Preference} and its DTO {@link PreferenceDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PreferenceMapper extends EntityMapper<PreferenceDTO, Preference> {
    @Named("idSet")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    Set<PreferenceDTO> toDtoIdSet(Set<Preference> preference);
}
