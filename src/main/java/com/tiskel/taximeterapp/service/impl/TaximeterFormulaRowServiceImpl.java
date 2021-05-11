package com.tiskel.taximeterapp.service.impl;

import com.tiskel.taximeterapp.domain.TaximeterFormulaRow;
import com.tiskel.taximeterapp.repository.TaximeterFormulaRowRepository;
import com.tiskel.taximeterapp.service.TaximeterFormulaRowService;
import com.tiskel.taximeterapp.service.dto.TaximeterFormulaRowDTO;
import com.tiskel.taximeterapp.service.mapper.TaximeterFormulaRowMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TaximeterFormulaRow}.
 */
@Service
@Transactional
public class TaximeterFormulaRowServiceImpl implements TaximeterFormulaRowService {

    private final Logger log = LoggerFactory.getLogger(TaximeterFormulaRowServiceImpl.class);

    private final TaximeterFormulaRowRepository taximeterFormulaRowRepository;

    private final TaximeterFormulaRowMapper taximeterFormulaRowMapper;

    public TaximeterFormulaRowServiceImpl(
        TaximeterFormulaRowRepository taximeterFormulaRowRepository,
        TaximeterFormulaRowMapper taximeterFormulaRowMapper
    ) {
        this.taximeterFormulaRowRepository = taximeterFormulaRowRepository;
        this.taximeterFormulaRowMapper = taximeterFormulaRowMapper;
    }

    @Override
    public TaximeterFormulaRowDTO save(TaximeterFormulaRowDTO taximeterFormulaRowDTO) {
        log.debug("Request to save TaximeterFormulaRow : {}", taximeterFormulaRowDTO);
        TaximeterFormulaRow taximeterFormulaRow = taximeterFormulaRowMapper.toEntity(taximeterFormulaRowDTO);
        taximeterFormulaRow = taximeterFormulaRowRepository.save(taximeterFormulaRow);
        return taximeterFormulaRowMapper.toDto(taximeterFormulaRow);
    }

    @Override
    public Optional<TaximeterFormulaRowDTO> partialUpdate(TaximeterFormulaRowDTO taximeterFormulaRowDTO) {
        log.debug("Request to partially update TaximeterFormulaRow : {}", taximeterFormulaRowDTO);

        return taximeterFormulaRowRepository
            .findById(taximeterFormulaRowDTO.getId())
            .map(
                existingTaximeterFormulaRow -> {
                    taximeterFormulaRowMapper.partialUpdate(existingTaximeterFormulaRow, taximeterFormulaRowDTO);
                    return existingTaximeterFormulaRow;
                }
            )
            .map(taximeterFormulaRowRepository::save)
            .map(taximeterFormulaRowMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaximeterFormulaRowDTO> findAll() {
        log.debug("Request to get all TaximeterFormulaRows");
        return taximeterFormulaRowRepository
            .findAll()
            .stream()
            .map(taximeterFormulaRowMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TaximeterFormulaRowDTO> findOne(Long id) {
        log.debug("Request to get TaximeterFormulaRow : {}", id);
        return taximeterFormulaRowRepository.findById(id).map(taximeterFormulaRowMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TaximeterFormulaRow : {}", id);
        taximeterFormulaRowRepository.deleteById(id);
    }
}
