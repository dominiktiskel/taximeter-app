package com.tiskel.taximeterapp.service.impl;

import com.tiskel.taximeterapp.domain.TaximeterOffer;
import com.tiskel.taximeterapp.repository.TaximeterOfferRepository;
import com.tiskel.taximeterapp.service.TaximeterOfferService;
import com.tiskel.taximeterapp.service.dto.TaximeterOfferDTO;
import com.tiskel.taximeterapp.service.mapper.TaximeterOfferMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TaximeterOffer}.
 */
@Service
@Transactional
public class TaximeterOfferServiceImpl implements TaximeterOfferService {

    private final Logger log = LoggerFactory.getLogger(TaximeterOfferServiceImpl.class);

    private final TaximeterOfferRepository taximeterOfferRepository;

    private final TaximeterOfferMapper taximeterOfferMapper;

    public TaximeterOfferServiceImpl(TaximeterOfferRepository taximeterOfferRepository, TaximeterOfferMapper taximeterOfferMapper) {
        this.taximeterOfferRepository = taximeterOfferRepository;
        this.taximeterOfferMapper = taximeterOfferMapper;
    }

    @Override
    public TaximeterOfferDTO save(TaximeterOfferDTO taximeterOfferDTO) {
        log.debug("Request to save TaximeterOffer : {}", taximeterOfferDTO);
        TaximeterOffer taximeterOffer = taximeterOfferMapper.toEntity(taximeterOfferDTO);
        taximeterOffer = taximeterOfferRepository.save(taximeterOffer);
        return taximeterOfferMapper.toDto(taximeterOffer);
    }

    @Override
    public Optional<TaximeterOfferDTO> partialUpdate(TaximeterOfferDTO taximeterOfferDTO) {
        log.debug("Request to partially update TaximeterOffer : {}", taximeterOfferDTO);

        return taximeterOfferRepository
            .findById(taximeterOfferDTO.getId())
            .map(
                existingTaximeterOffer -> {
                    taximeterOfferMapper.partialUpdate(existingTaximeterOffer, taximeterOfferDTO);
                    return existingTaximeterOffer;
                }
            )
            .map(taximeterOfferRepository::save)
            .map(taximeterOfferMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaximeterOfferDTO> findAll() {
        log.debug("Request to get all TaximeterOffers");
        return taximeterOfferRepository
            .findAll()
            .stream()
            .map(taximeterOfferMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TaximeterOfferDTO> findOne(Long id) {
        log.debug("Request to get TaximeterOffer : {}", id);
        return taximeterOfferRepository.findById(id).map(taximeterOfferMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TaximeterOffer : {}", id);
        taximeterOfferRepository.deleteById(id);
    }
}
