package com.tiskel.taximeterapp.service.impl;

import com.tiskel.taximeterapp.domain.TaximeterFixedListItem;
import com.tiskel.taximeterapp.repository.TaximeterFixedListItemRepository;
import com.tiskel.taximeterapp.service.TaximeterFixedListItemService;
import com.tiskel.taximeterapp.service.dto.TaximeterFixedListItemDTO;
import com.tiskel.taximeterapp.service.mapper.TaximeterFixedListItemMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TaximeterFixedListItem}.
 */
@Service
@Transactional
public class TaximeterFixedListItemServiceImpl implements TaximeterFixedListItemService {

    private final Logger log = LoggerFactory.getLogger(TaximeterFixedListItemServiceImpl.class);

    private final TaximeterFixedListItemRepository taximeterFixedListItemRepository;

    private final TaximeterFixedListItemMapper taximeterFixedListItemMapper;

    public TaximeterFixedListItemServiceImpl(
        TaximeterFixedListItemRepository taximeterFixedListItemRepository,
        TaximeterFixedListItemMapper taximeterFixedListItemMapper
    ) {
        this.taximeterFixedListItemRepository = taximeterFixedListItemRepository;
        this.taximeterFixedListItemMapper = taximeterFixedListItemMapper;
    }

    @Override
    public TaximeterFixedListItemDTO save(TaximeterFixedListItemDTO taximeterFixedListItemDTO) {
        log.debug("Request to save TaximeterFixedListItem : {}", taximeterFixedListItemDTO);
        TaximeterFixedListItem taximeterFixedListItem = taximeterFixedListItemMapper.toEntity(taximeterFixedListItemDTO);
        taximeterFixedListItem = taximeterFixedListItemRepository.save(taximeterFixedListItem);
        return taximeterFixedListItemMapper.toDto(taximeterFixedListItem);
    }

    @Override
    public Optional<TaximeterFixedListItemDTO> partialUpdate(TaximeterFixedListItemDTO taximeterFixedListItemDTO) {
        log.debug("Request to partially update TaximeterFixedListItem : {}", taximeterFixedListItemDTO);

        return taximeterFixedListItemRepository
            .findById(taximeterFixedListItemDTO.getId())
            .map(
                existingTaximeterFixedListItem -> {
                    taximeterFixedListItemMapper.partialUpdate(existingTaximeterFixedListItem, taximeterFixedListItemDTO);
                    return existingTaximeterFixedListItem;
                }
            )
            .map(taximeterFixedListItemRepository::save)
            .map(taximeterFixedListItemMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaximeterFixedListItemDTO> findAll() {
        log.debug("Request to get all TaximeterFixedListItems");
        return taximeterFixedListItemRepository
            .findAll()
            .stream()
            .map(taximeterFixedListItemMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TaximeterFixedListItemDTO> findOne(Long id) {
        log.debug("Request to get TaximeterFixedListItem : {}", id);
        return taximeterFixedListItemRepository.findById(id).map(taximeterFixedListItemMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TaximeterFixedListItem : {}", id);
        taximeterFixedListItemRepository.deleteById(id);
    }
}
