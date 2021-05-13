package com.tiskel.taximeterapp.web.rest;

import com.tiskel.taximeterapp.repository.TaximeterTimeRangeRepository;
import com.tiskel.taximeterapp.service.TaximeterTimeRangeService;
import com.tiskel.taximeterapp.service.dto.TaximeterTimeRangeDTO;
import com.tiskel.taximeterapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.tiskel.taximeterapp.domain.TaximeterTimeRange}.
 */
@RestController
@RequestMapping("/api")
public class TaximeterTimeRangeResource {

    private final Logger log = LoggerFactory.getLogger(TaximeterTimeRangeResource.class);

    private static final String ENTITY_NAME = "taximeterTimeRange";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TaximeterTimeRangeService taximeterTimeRangeService;

    private final TaximeterTimeRangeRepository taximeterTimeRangeRepository;

    public TaximeterTimeRangeResource(
        TaximeterTimeRangeService taximeterTimeRangeService,
        TaximeterTimeRangeRepository taximeterTimeRangeRepository
    ) {
        this.taximeterTimeRangeService = taximeterTimeRangeService;
        this.taximeterTimeRangeRepository = taximeterTimeRangeRepository;
    }

    /**
     * {@code POST  /taximeter-time-ranges} : Create a new taximeterTimeRange.
     *
     * @param taximeterTimeRangeDTO the taximeterTimeRangeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new taximeterTimeRangeDTO, or with status {@code 400 (Bad Request)} if the taximeterTimeRange has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/taximeter-time-ranges")
    public ResponseEntity<TaximeterTimeRangeDTO> createTaximeterTimeRange(@Valid @RequestBody TaximeterTimeRangeDTO taximeterTimeRangeDTO)
        throws URISyntaxException {
        log.debug("REST request to save TaximeterTimeRange : {}", taximeterTimeRangeDTO);
        if (taximeterTimeRangeDTO.getId() != null) {
            throw new BadRequestAlertException("A new taximeterTimeRange cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TaximeterTimeRangeDTO result = taximeterTimeRangeService.save(taximeterTimeRangeDTO);
        return ResponseEntity
            .created(new URI("/api/taximeter-time-ranges/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /taximeter-time-ranges/:id} : Updates an existing taximeterTimeRange.
     *
     * @param id the id of the taximeterTimeRangeDTO to save.
     * @param taximeterTimeRangeDTO the taximeterTimeRangeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated taximeterTimeRangeDTO,
     * or with status {@code 400 (Bad Request)} if the taximeterTimeRangeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the taximeterTimeRangeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/taximeter-time-ranges/{id}")
    public ResponseEntity<TaximeterTimeRangeDTO> updateTaximeterTimeRange(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TaximeterTimeRangeDTO taximeterTimeRangeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TaximeterTimeRange : {}, {}", id, taximeterTimeRangeDTO);
        if (taximeterTimeRangeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, taximeterTimeRangeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!taximeterTimeRangeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TaximeterTimeRangeDTO result = taximeterTimeRangeService.save(taximeterTimeRangeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, taximeterTimeRangeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /taximeter-time-ranges/:id} : Partial updates given fields of an existing taximeterTimeRange, field will ignore if it is null
     *
     * @param id the id of the taximeterTimeRangeDTO to save.
     * @param taximeterTimeRangeDTO the taximeterTimeRangeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated taximeterTimeRangeDTO,
     * or with status {@code 400 (Bad Request)} if the taximeterTimeRangeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the taximeterTimeRangeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the taximeterTimeRangeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/taximeter-time-ranges/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<TaximeterTimeRangeDTO> partialUpdateTaximeterTimeRange(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TaximeterTimeRangeDTO taximeterTimeRangeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TaximeterTimeRange partially : {}, {}", id, taximeterTimeRangeDTO);
        if (taximeterTimeRangeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, taximeterTimeRangeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!taximeterTimeRangeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TaximeterTimeRangeDTO> result = taximeterTimeRangeService.partialUpdate(taximeterTimeRangeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, taximeterTimeRangeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /taximeter-time-ranges} : get all the taximeterTimeRanges.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of taximeterTimeRanges in body.
     */
    @GetMapping("/taximeter-time-ranges")
    public List<TaximeterTimeRangeDTO> getAllTaximeterTimeRanges() {
        log.debug("REST request to get all TaximeterTimeRanges");
        return taximeterTimeRangeService.findAll();
    }

    /**
     * {@code GET  /taximeter-time-ranges/:id} : get the "id" taximeterTimeRange.
     *
     * @param id the id of the taximeterTimeRangeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the taximeterTimeRangeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/taximeter-time-ranges/{id}")
    public ResponseEntity<TaximeterTimeRangeDTO> getTaximeterTimeRange(@PathVariable Long id) {
        log.debug("REST request to get TaximeterTimeRange : {}", id);
        Optional<TaximeterTimeRangeDTO> taximeterTimeRangeDTO = taximeterTimeRangeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(taximeterTimeRangeDTO);
    }

    /**
     * {@code DELETE  /taximeter-time-ranges/:id} : delete the "id" taximeterTimeRange.
     *
     * @param id the id of the taximeterTimeRangeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/taximeter-time-ranges/{id}")
    public ResponseEntity<Void> deleteTaximeterTimeRange(@PathVariable Long id) {
        log.debug("REST request to delete TaximeterTimeRange : {}", id);
        taximeterTimeRangeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
