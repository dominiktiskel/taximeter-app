package com.tiskel.taximeterapp.service.impl;

import com.tiskel.taximeterapp.domain.TaximeterTimeRangeItem;
import com.tiskel.taximeterapp.repository.TaximeterTimeRangeItemRepository;
import com.tiskel.taximeterapp.service.TaximeterTimeRangeItemService;
import com.tiskel.taximeterapp.service.dto.TaximeterTimeRangeItemDTO;
import com.tiskel.taximeterapp.service.mapper.TaximeterTimeRangeItemMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TaximeterTimeRangeItem}.
 */
@Service
@Transactional
public class TaximeterTimeRangeItemServiceImpl implements TaximeterTimeRangeItemService {

    private final Logger log = LoggerFactory.getLogger(TaximeterTimeRangeItemServiceImpl.class);

    private final TaximeterTimeRangeItemRepository taximeterTimeRangeItemRepository;

    private final TaximeterTimeRangeItemMapper taximeterTimeRangeItemMapper;

    public TaximeterTimeRangeItemServiceImpl(
        TaximeterTimeRangeItemRepository taximeterTimeRangeItemRepository,
        TaximeterTimeRangeItemMapper taximeterTimeRangeItemMapper
    ) {
        this.taximeterTimeRangeItemRepository = taximeterTimeRangeItemRepository;
        this.taximeterTimeRangeItemMapper = taximeterTimeRangeItemMapper;
    }

    @Override
    public TaximeterTimeRangeItemDTO save(TaximeterTimeRangeItemDTO taximeterTimeRangeItemDTO) {
        log.debug("Request to save TaximeterTimeRangeItem : {}", taximeterTimeRangeItemDTO);
        TaximeterTimeRangeItem taximeterTimeRangeItem = taximeterTimeRangeItemMapper.toEntity(taximeterTimeRangeItemDTO);
        taximeterTimeRangeItem = taximeterTimeRangeItemRepository.save(taximeterTimeRangeItem);
        return taximeterTimeRangeItemMapper.toDto(taximeterTimeRangeItem);
    }

    @Override
    public Optional<TaximeterTimeRangeItemDTO> partialUpdate(TaximeterTimeRangeItemDTO taximeterTimeRangeItemDTO) {
        log.debug("Request to partially update TaximeterTimeRangeItem : {}", taximeterTimeRangeItemDTO);

        return taximeterTimeRangeItemRepository
            .findById(taximeterTimeRangeItemDTO.getId())
            .map(
                existingTaximeterTimeRangeItem -> {
                    taximeterTimeRangeItemMapper.partialUpdate(existingTaximeterTimeRangeItem, taximeterTimeRangeItemDTO);
                    return existingTaximeterTimeRangeItem;
                }
            )
            .map(taximeterTimeRangeItemRepository::save)
            .map(taximeterTimeRangeItemMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaximeterTimeRangeItemDTO> findAll() {
        log.debug("Request to get all TaximeterTimeRangeItems");
        return taximeterTimeRangeItemRepository
            .findAll()
            .stream()
            .map(taximeterTimeRangeItemMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TaximeterTimeRangeItemDTO> findOne(Long id) {
        log.debug("Request to get TaximeterTimeRangeItem : {}", id);
        return taximeterTimeRangeItemRepository.findById(id).map(taximeterTimeRangeItemMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TaximeterTimeRangeItem : {}", id);
        taximeterTimeRangeItemRepository.deleteById(id);
    }
}
