package com.tiskel.taximeterapp.service.impl;

import com.tiskel.taximeterapp.domain.TaximeterOfferGroup;
import com.tiskel.taximeterapp.repository.TaximeterOfferGroupRepository;
import com.tiskel.taximeterapp.service.TaximeterOfferGroupService;
import com.tiskel.taximeterapp.service.dto.TaximeterOfferGroupDTO;
import com.tiskel.taximeterapp.service.mapper.TaximeterOfferGroupMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TaximeterOfferGroup}.
 */
@Service
@Transactional
public class TaximeterOfferGroupServiceImpl implements TaximeterOfferGroupService {

    private final Logger log = LoggerFactory.getLogger(TaximeterOfferGroupServiceImpl.class);

    private final TaximeterOfferGroupRepository taximeterOfferGroupRepository;

    private final TaximeterOfferGroupMapper taximeterOfferGroupMapper;

    public TaximeterOfferGroupServiceImpl(
        TaximeterOfferGroupRepository taximeterOfferGroupRepository,
        TaximeterOfferGroupMapper taximeterOfferGroupMapper
    ) {
        this.taximeterOfferGroupRepository = taximeterOfferGroupRepository;
        this.taximeterOfferGroupMapper = taximeterOfferGroupMapper;
    }

    @Override
    public TaximeterOfferGroupDTO save(TaximeterOfferGroupDTO taximeterOfferGroupDTO) {
        log.debug("Request to save TaximeterOfferGroup : {}", taximeterOfferGroupDTO);
        TaximeterOfferGroup taximeterOfferGroup = taximeterOfferGroupMapper.toEntity(taximeterOfferGroupDTO);
        taximeterOfferGroup = taximeterOfferGroupRepository.save(taximeterOfferGroup);
        return taximeterOfferGroupMapper.toDto(taximeterOfferGroup);
    }

    @Override
    public Optional<TaximeterOfferGroupDTO> partialUpdate(TaximeterOfferGroupDTO taximeterOfferGroupDTO) {
        log.debug("Request to partially update TaximeterOfferGroup : {}", taximeterOfferGroupDTO);

        return taximeterOfferGroupRepository
            .findById(taximeterOfferGroupDTO.getId())
            .map(
                existingTaximeterOfferGroup -> {
                    taximeterOfferGroupMapper.partialUpdate(existingTaximeterOfferGroup, taximeterOfferGroupDTO);
                    return existingTaximeterOfferGroup;
                }
            )
            .map(taximeterOfferGroupRepository::save)
            .map(taximeterOfferGroupMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaximeterOfferGroupDTO> findAll() {
        log.debug("Request to get all TaximeterOfferGroups");
        return taximeterOfferGroupRepository
            .findAll()
            .stream()
            .map(taximeterOfferGroupMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TaximeterOfferGroupDTO> findOne(Long id) {
        log.debug("Request to get TaximeterOfferGroup : {}", id);
        return taximeterOfferGroupRepository.findById(id).map(taximeterOfferGroupMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TaximeterOfferGroup : {}", id);
        taximeterOfferGroupRepository.deleteById(id);
    }
}
