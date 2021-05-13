package com.tiskel.taximeterapp.service.impl;

import com.tiskel.taximeterapp.domain.Preference;
import com.tiskel.taximeterapp.repository.PreferenceRepository;
import com.tiskel.taximeterapp.service.PreferenceService;
import com.tiskel.taximeterapp.service.dto.PreferenceDTO;
import com.tiskel.taximeterapp.service.mapper.PreferenceMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Preference}.
 */
@Service
@Transactional
public class PreferenceServiceImpl implements PreferenceService {

    private final Logger log = LoggerFactory.getLogger(PreferenceServiceImpl.class);

    private final PreferenceRepository preferenceRepository;

    private final PreferenceMapper preferenceMapper;

    public PreferenceServiceImpl(PreferenceRepository preferenceRepository, PreferenceMapper preferenceMapper) {
        this.preferenceRepository = preferenceRepository;
        this.preferenceMapper = preferenceMapper;
    }

    @Override
    public PreferenceDTO save(PreferenceDTO preferenceDTO) {
        log.debug("Request to save Preference : {}", preferenceDTO);
        Preference preference = preferenceMapper.toEntity(preferenceDTO);
        preference = preferenceRepository.save(preference);
        return preferenceMapper.toDto(preference);
    }

    @Override
    public Optional<PreferenceDTO> partialUpdate(PreferenceDTO preferenceDTO) {
        log.debug("Request to partially update Preference : {}", preferenceDTO);

        return preferenceRepository
            .findById(preferenceDTO.getId())
            .map(
                existingPreference -> {
                    preferenceMapper.partialUpdate(existingPreference, preferenceDTO);
                    return existingPreference;
                }
            )
            .map(preferenceRepository::save)
            .map(preferenceMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PreferenceDTO> findAll() {
        log.debug("Request to get all Preferences");
        return preferenceRepository.findAll().stream().map(preferenceMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PreferenceDTO> findOne(Long id) {
        log.debug("Request to get Preference : {}", id);
        return preferenceRepository.findById(id).map(preferenceMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Preference : {}", id);
        preferenceRepository.deleteById(id);
    }
}
