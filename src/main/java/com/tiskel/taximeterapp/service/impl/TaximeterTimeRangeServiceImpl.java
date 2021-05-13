package com.tiskel.taximeterapp.service.impl;

import com.tiskel.taximeterapp.domain.TaximeterTimeRange;
import com.tiskel.taximeterapp.repository.TaximeterTimeRangeRepository;
import com.tiskel.taximeterapp.service.TaximeterTimeRangeService;
import com.tiskel.taximeterapp.service.dto.TaximeterTimeRangeDTO;
import com.tiskel.taximeterapp.service.mapper.TaximeterTimeRangeMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TaximeterTimeRange}.
 */
@Service
@Transactional
public class TaximeterTimeRangeServiceImpl implements TaximeterTimeRangeService {

    private final Logger log = LoggerFactory.getLogger(TaximeterTimeRangeServiceImpl.class);

    private final TaximeterTimeRangeRepository taximeterTimeRangeRepository;

    private final TaximeterTimeRangeMapper taximeterTimeRangeMapper;

    public TaximeterTimeRangeServiceImpl(
        TaximeterTimeRangeRepository taximeterTimeRangeRepository,
        TaximeterTimeRangeMapper taximeterTimeRangeMapper
    ) {
        this.taximeterTimeRangeRepository = taximeterTimeRangeRepository;
        this.taximeterTimeRangeMapper = taximeterTimeRangeMapper;
    }

    @Override
    public TaximeterTimeRangeDTO save(TaximeterTimeRangeDTO taximeterTimeRangeDTO) {
        log.debug("Request to save TaximeterTimeRange : {}", taximeterTimeRangeDTO);
        TaximeterTimeRange taximeterTimeRange = taximeterTimeRangeMapper.toEntity(taximeterTimeRangeDTO);
        taximeterTimeRange = taximeterTimeRangeRepository.save(taximeterTimeRange);
        return taximeterTimeRangeMapper.toDto(taximeterTimeRange);
    }

    @Override
    public Optional<TaximeterTimeRangeDTO> partialUpdate(TaximeterTimeRangeDTO taximeterTimeRangeDTO) {
        log.debug("Request to partially update TaximeterTimeRange : {}", taximeterTimeRangeDTO);

        return taximeterTimeRangeRepository
            .findById(taximeterTimeRangeDTO.getId())
            .map(
                existingTaximeterTimeRange -> {
                    taximeterTimeRangeMapper.partialUpdate(existingTaximeterTimeRange, taximeterTimeRangeDTO);
                    return existingTaximeterTimeRange;
                }
            )
            .map(taximeterTimeRangeRepository::save)
            .map(taximeterTimeRangeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaximeterTimeRangeDTO> findAll() {
        log.debug("Request to get all TaximeterTimeRanges");
        return taximeterTimeRangeRepository
            .findAll()
            .stream()
            .map(taximeterTimeRangeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TaximeterTimeRangeDTO> findOne(Long id) {
        log.debug("Request to get TaximeterTimeRange : {}", id);
        return taximeterTimeRangeRepository.findById(id).map(taximeterTimeRangeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TaximeterTimeRange : {}", id);
        taximeterTimeRangeRepository.deleteById(id);
    }
}
