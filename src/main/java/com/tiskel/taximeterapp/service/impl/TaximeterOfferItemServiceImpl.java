package com.tiskel.taximeterapp.service.impl;

import com.tiskel.taximeterapp.domain.TaximeterOfferItem;
import com.tiskel.taximeterapp.repository.TaximeterOfferItemRepository;
import com.tiskel.taximeterapp.service.TaximeterOfferItemService;
import com.tiskel.taximeterapp.service.dto.TaximeterOfferItemDTO;
import com.tiskel.taximeterapp.service.mapper.TaximeterOfferItemMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TaximeterOfferItem}.
 */
@Service
@Transactional
public class TaximeterOfferItemServiceImpl implements TaximeterOfferItemService {

    private final Logger log = LoggerFactory.getLogger(TaximeterOfferItemServiceImpl.class);

    private final TaximeterOfferItemRepository taximeterOfferItemRepository;

    private final TaximeterOfferItemMapper taximeterOfferItemMapper;

    public TaximeterOfferItemServiceImpl(
        TaximeterOfferItemRepository taximeterOfferItemRepository,
        TaximeterOfferItemMapper taximeterOfferItemMapper
    ) {
        this.taximeterOfferItemRepository = taximeterOfferItemRepository;
        this.taximeterOfferItemMapper = taximeterOfferItemMapper;
    }

    @Override
    public TaximeterOfferItemDTO save(TaximeterOfferItemDTO taximeterOfferItemDTO) {
        log.debug("Request to save TaximeterOfferItem : {}", taximeterOfferItemDTO);
        TaximeterOfferItem taximeterOfferItem = taximeterOfferItemMapper.toEntity(taximeterOfferItemDTO);
        taximeterOfferItem = taximeterOfferItemRepository.save(taximeterOfferItem);
        return taximeterOfferItemMapper.toDto(taximeterOfferItem);
    }

    @Override
    public Optional<TaximeterOfferItemDTO> partialUpdate(TaximeterOfferItemDTO taximeterOfferItemDTO) {
        log.debug("Request to partially update TaximeterOfferItem : {}", taximeterOfferItemDTO);

        return taximeterOfferItemRepository
            .findById(taximeterOfferItemDTO.getId())
            .map(
                existingTaximeterOfferItem -> {
                    taximeterOfferItemMapper.partialUpdate(existingTaximeterOfferItem, taximeterOfferItemDTO);
                    return existingTaximeterOfferItem;
                }
            )
            .map(taximeterOfferItemRepository::save)
            .map(taximeterOfferItemMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaximeterOfferItemDTO> findAll() {
        log.debug("Request to get all TaximeterOfferItems");
        return taximeterOfferItemRepository
            .findAllWithEagerRelationships()
            .stream()
            .map(taximeterOfferItemMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    public Page<TaximeterOfferItemDTO> findAllWithEagerRelationships(Pageable pageable) {
        return taximeterOfferItemRepository.findAllWithEagerRelationships(pageable).map(taximeterOfferItemMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TaximeterOfferItemDTO> findOne(Long id) {
        log.debug("Request to get TaximeterOfferItem : {}", id);
        return taximeterOfferItemRepository.findOneWithEagerRelationships(id).map(taximeterOfferItemMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TaximeterOfferItem : {}", id);
        taximeterOfferItemRepository.deleteById(id);
    }
}
