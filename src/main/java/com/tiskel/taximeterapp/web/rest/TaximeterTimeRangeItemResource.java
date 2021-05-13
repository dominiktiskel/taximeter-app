package com.tiskel.taximeterapp.web.rest;

import com.tiskel.taximeterapp.repository.TaximeterTimeRangeItemRepository;
import com.tiskel.taximeterapp.service.TaximeterTimeRangeItemService;
import com.tiskel.taximeterapp.service.dto.TaximeterTimeRangeItemDTO;
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
 * REST controller for managing {@link com.tiskel.taximeterapp.domain.TaximeterTimeRangeItem}.
 */
@RestController
@RequestMapping("/api")
public class TaximeterTimeRangeItemResource {

    private final Logger log = LoggerFactory.getLogger(TaximeterTimeRangeItemResource.class);

    private static final String ENTITY_NAME = "taximeterTimeRangeItem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TaximeterTimeRangeItemService taximeterTimeRangeItemService;

    private final TaximeterTimeRangeItemRepository taximeterTimeRangeItemRepository;

    public TaximeterTimeRangeItemResource(
        TaximeterTimeRangeItemService taximeterTimeRangeItemService,
        TaximeterTimeRangeItemRepository taximeterTimeRangeItemRepository
    ) {
        this.taximeterTimeRangeItemService = taximeterTimeRangeItemService;
        this.taximeterTimeRangeItemRepository = taximeterTimeRangeItemRepository;
    }

    /**
     * {@code POST  /taximeter-time-range-items} : Create a new taximeterTimeRangeItem.
     *
     * @param taximeterTimeRangeItemDTO the taximeterTimeRangeItemDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new taximeterTimeRangeItemDTO, or with status {@code 400 (Bad Request)} if the taximeterTimeRangeItem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/taximeter-time-range-items")
    public ResponseEntity<TaximeterTimeRangeItemDTO> createTaximeterTimeRangeItem(
        @Valid @RequestBody TaximeterTimeRangeItemDTO taximeterTimeRangeItemDTO
    ) throws URISyntaxException {
        log.debug("REST request to save TaximeterTimeRangeItem : {}", taximeterTimeRangeItemDTO);
        if (taximeterTimeRangeItemDTO.getId() != null) {
            throw new BadRequestAlertException("A new taximeterTimeRangeItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TaximeterTimeRangeItemDTO result = taximeterTimeRangeItemService.save(taximeterTimeRangeItemDTO);
        return ResponseEntity
            .created(new URI("/api/taximeter-time-range-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /taximeter-time-range-items/:id} : Updates an existing taximeterTimeRangeItem.
     *
     * @param id the id of the taximeterTimeRangeItemDTO to save.
     * @param taximeterTimeRangeItemDTO the taximeterTimeRangeItemDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated taximeterTimeRangeItemDTO,
     * or with status {@code 400 (Bad Request)} if the taximeterTimeRangeItemDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the taximeterTimeRangeItemDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/taximeter-time-range-items/{id}")
    public ResponseEntity<TaximeterTimeRangeItemDTO> updateTaximeterTimeRangeItem(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TaximeterTimeRangeItemDTO taximeterTimeRangeItemDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TaximeterTimeRangeItem : {}, {}", id, taximeterTimeRangeItemDTO);
        if (taximeterTimeRangeItemDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, taximeterTimeRangeItemDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!taximeterTimeRangeItemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TaximeterTimeRangeItemDTO result = taximeterTimeRangeItemService.save(taximeterTimeRangeItemDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, taximeterTimeRangeItemDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /taximeter-time-range-items/:id} : Partial updates given fields of an existing taximeterTimeRangeItem, field will ignore if it is null
     *
     * @param id the id of the taximeterTimeRangeItemDTO to save.
     * @param taximeterTimeRangeItemDTO the taximeterTimeRangeItemDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated taximeterTimeRangeItemDTO,
     * or with status {@code 400 (Bad Request)} if the taximeterTimeRangeItemDTO is not valid,
     * or with status {@code 404 (Not Found)} if the taximeterTimeRangeItemDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the taximeterTimeRangeItemDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/taximeter-time-range-items/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<TaximeterTimeRangeItemDTO> partialUpdateTaximeterTimeRangeItem(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TaximeterTimeRangeItemDTO taximeterTimeRangeItemDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TaximeterTimeRangeItem partially : {}, {}", id, taximeterTimeRangeItemDTO);
        if (taximeterTimeRangeItemDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, taximeterTimeRangeItemDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!taximeterTimeRangeItemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TaximeterTimeRangeItemDTO> result = taximeterTimeRangeItemService.partialUpdate(taximeterTimeRangeItemDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, taximeterTimeRangeItemDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /taximeter-time-range-items} : get all the taximeterTimeRangeItems.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of taximeterTimeRangeItems in body.
     */
    @GetMapping("/taximeter-time-range-items")
    public List<TaximeterTimeRangeItemDTO> getAllTaximeterTimeRangeItems() {
        log.debug("REST request to get all TaximeterTimeRangeItems");
        return taximeterTimeRangeItemService.findAll();
    }

    /**
     * {@code GET  /taximeter-time-range-items/:id} : get the "id" taximeterTimeRangeItem.
     *
     * @param id the id of the taximeterTimeRangeItemDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the taximeterTimeRangeItemDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/taximeter-time-range-items/{id}")
    public ResponseEntity<TaximeterTimeRangeItemDTO> getTaximeterTimeRangeItem(@PathVariable Long id) {
        log.debug("REST request to get TaximeterTimeRangeItem : {}", id);
        Optional<TaximeterTimeRangeItemDTO> taximeterTimeRangeItemDTO = taximeterTimeRangeItemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(taximeterTimeRangeItemDTO);
    }

    /**
     * {@code DELETE  /taximeter-time-range-items/:id} : delete the "id" taximeterTimeRangeItem.
     *
     * @param id the id of the taximeterTimeRangeItemDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/taximeter-time-range-items/{id}")
    public ResponseEntity<Void> deleteTaximeterTimeRangeItem(@PathVariable Long id) {
        log.debug("REST request to delete TaximeterTimeRangeItem : {}", id);
        taximeterTimeRangeItemService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
