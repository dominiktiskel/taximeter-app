package com.tiskel.taximeterapp.service.impl;

import com.tiskel.taximeterapp.domain.TaximeterFixedList;
import com.tiskel.taximeterapp.repository.TaximeterFixedListRepository;
import com.tiskel.taximeterapp.service.TaximeterFixedListService;
import com.tiskel.taximeterapp.service.dto.TaximeterFixedListDTO;
import com.tiskel.taximeterapp.service.mapper.TaximeterFixedListMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TaximeterFixedList}.
 */
@Service
@Transactional
public class TaximeterFixedListServiceImpl implements TaximeterFixedListService {

    private final Logger log = LoggerFactory.getLogger(TaximeterFixedListServiceImpl.class);

    private final TaximeterFixedListRepository taximeterFixedListRepository;

    private final TaximeterFixedListMapper taximeterFixedListMapper;

    public TaximeterFixedListServiceImpl(
        TaximeterFixedListRepository taximeterFixedListRepository,
        TaximeterFixedListMapper taximeterFixedListMapper
    ) {
        this.taximeterFixedListRepository = taximeterFixedListRepository;
        this.taximeterFixedListMapper = taximeterFixedListMapper;
    }

    @Override
    public TaximeterFixedListDTO save(TaximeterFixedListDTO taximeterFixedListDTO) {
        log.debug("Request to save TaximeterFixedList : {}", taximeterFixedListDTO);
        TaximeterFixedList taximeterFixedList = taximeterFixedListMapper.toEntity(taximeterFixedListDTO);
        taximeterFixedList = taximeterFixedListRepository.save(taximeterFixedList);
        return taximeterFixedListMapper.toDto(taximeterFixedList);
    }

    @Override
    public Optional<TaximeterFixedListDTO> partialUpdate(TaximeterFixedListDTO taximeterFixedListDTO) {
        log.debug("Request to partially update TaximeterFixedList : {}", taximeterFixedListDTO);

        return taximeterFixedListRepository
            .findById(taximeterFixedListDTO.getId())
            .map(
                existingTaximeterFixedList -> {
                    taximeterFixedListMapper.partialUpdate(existingTaximeterFixedList, taximeterFixedListDTO);
                    return existingTaximeterFixedList;
                }
            )
            .map(taximeterFixedListRepository::save)
            .map(taximeterFixedListMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaximeterFixedListDTO> findAll() {
        log.debug("Request to get all TaximeterFixedLists");
        return taximeterFixedListRepository
            .findAll()
            .stream()
            .map(taximeterFixedListMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TaximeterFixedListDTO> findOne(Long id) {
        log.debug("Request to get TaximeterFixedList : {}", id);
        return taximeterFixedListRepository.findById(id).map(taximeterFixedListMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TaximeterFixedList : {}", id);
        taximeterFixedListRepository.deleteById(id);
    }
}
