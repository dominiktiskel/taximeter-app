package com.tiskel.taximeterapp.service.impl;

import com.tiskel.taximeterapp.domain.TaximeterFormula;
import com.tiskel.taximeterapp.repository.TaximeterFormulaRepository;
import com.tiskel.taximeterapp.service.TaximeterFormulaService;
import com.tiskel.taximeterapp.service.dto.TaximeterFormulaDTO;
import com.tiskel.taximeterapp.service.mapper.TaximeterFormulaMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TaximeterFormula}.
 */
@Service
@Transactional
public class TaximeterFormulaServiceImpl implements TaximeterFormulaService {

    private final Logger log = LoggerFactory.getLogger(TaximeterFormulaServiceImpl.class);

    private final TaximeterFormulaRepository taximeterFormulaRepository;

    private final TaximeterFormulaMapper taximeterFormulaMapper;

    public TaximeterFormulaServiceImpl(
        TaximeterFormulaRepository taximeterFormulaRepository,
        TaximeterFormulaMapper taximeterFormulaMapper
    ) {
        this.taximeterFormulaRepository = taximeterFormulaRepository;
        this.taximeterFormulaMapper = taximeterFormulaMapper;
    }

    @Override
    public TaximeterFormulaDTO save(TaximeterFormulaDTO taximeterFormulaDTO) {
        log.debug("Request to save TaximeterFormula : {}", taximeterFormulaDTO);
        TaximeterFormula taximeterFormula = taximeterFormulaMapper.toEntity(taximeterFormulaDTO);
        taximeterFormula = taximeterFormulaRepository.save(taximeterFormula);
        return taximeterFormulaMapper.toDto(taximeterFormula);
    }

    @Override
    public Optional<TaximeterFormulaDTO> partialUpdate(TaximeterFormulaDTO taximeterFormulaDTO) {
        log.debug("Request to partially update TaximeterFormula : {}", taximeterFormulaDTO);

        return taximeterFormulaRepository
            .findById(taximeterFormulaDTO.getId())
            .map(
                existingTaximeterFormula -> {
                    taximeterFormulaMapper.partialUpdate(existingTaximeterFormula, taximeterFormulaDTO);
                    return existingTaximeterFormula;
                }
            )
            .map(taximeterFormulaRepository::save)
            .map(taximeterFormulaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaximeterFormulaDTO> findAll() {
        log.debug("Request to get all TaximeterFormulas");
        return taximeterFormulaRepository
            .findAll()
            .stream()
            .map(taximeterFormulaMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TaximeterFormulaDTO> findOne(Long id) {
        log.debug("Request to get TaximeterFormula : {}", id);
        return taximeterFormulaRepository.findById(id).map(taximeterFormulaMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TaximeterFormula : {}", id);
        taximeterFormulaRepository.deleteById(id);
    }
}
