package com.tiskel.taximeterapp.web.rest;

import com.tiskel.taximeterapp.repository.TaximeterFixedListItemRepository;
import com.tiskel.taximeterapp.service.TaximeterFixedListItemService;
import com.tiskel.taximeterapp.service.dto.TaximeterFixedListItemDTO;
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
 * REST controller for managing {@link com.tiskel.taximeterapp.domain.TaximeterFixedListItem}.
 */
@RestController
@RequestMapping("/api")
public class TaximeterFixedListItemResource {

    private final Logger log = LoggerFactory.getLogger(TaximeterFixedListItemResource.class);

    private static final String ENTITY_NAME = "taximeterFixedListItem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TaximeterFixedListItemService taximeterFixedListItemService;

    private final TaximeterFixedListItemRepository taximeterFixedListItemRepository;

    public TaximeterFixedListItemResource(
        TaximeterFixedListItemService taximeterFixedListItemService,
        TaximeterFixedListItemRepository taximeterFixedListItemRepository
    ) {
        this.taximeterFixedListItemService = taximeterFixedListItemService;
        this.taximeterFixedListItemRepository = taximeterFixedListItemRepository;
    }

    /**
     * {@code POST  /taximeter-fixed-list-items} : Create a new taximeterFixedListItem.
     *
     * @param taximeterFixedListItemDTO the taximeterFixedListItemDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new taximeterFixedListItemDTO, or with status {@code 400 (Bad Request)} if the taximeterFixedListItem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/taximeter-fixed-list-items")
    public ResponseEntity<TaximeterFixedListItemDTO> createTaximeterFixedListItem(
        @Valid @RequestBody TaximeterFixedListItemDTO taximeterFixedListItemDTO
    ) throws URISyntaxException {
        log.debug("REST request to save TaximeterFixedListItem : {}", taximeterFixedListItemDTO);
        if (taximeterFixedListItemDTO.getId() != null) {
            throw new BadRequestAlertException("A new taximeterFixedListItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TaximeterFixedListItemDTO result = taximeterFixedListItemService.save(taximeterFixedListItemDTO);
        return ResponseEntity
            .created(new URI("/api/taximeter-fixed-list-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /taximeter-fixed-list-items/:id} : Updates an existing taximeterFixedListItem.
     *
     * @param id the id of the taximeterFixedListItemDTO to save.
     * @param taximeterFixedListItemDTO the taximeterFixedListItemDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated taximeterFixedListItemDTO,
     * or with status {@code 400 (Bad Request)} if the taximeterFixedListItemDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the taximeterFixedListItemDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/taximeter-fixed-list-items/{id}")
    public ResponseEntity<TaximeterFixedListItemDTO> updateTaximeterFixedListItem(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TaximeterFixedListItemDTO taximeterFixedListItemDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TaximeterFixedListItem : {}, {}", id, taximeterFixedListItemDTO);
        if (taximeterFixedListItemDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, taximeterFixedListItemDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!taximeterFixedListItemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TaximeterFixedListItemDTO result = taximeterFixedListItemService.save(taximeterFixedListItemDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, taximeterFixedListItemDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /taximeter-fixed-list-items/:id} : Partial updates given fields of an existing taximeterFixedListItem, field will ignore if it is null
     *
     * @param id the id of the taximeterFixedListItemDTO to save.
     * @param taximeterFixedListItemDTO the taximeterFixedListItemDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated taximeterFixedListItemDTO,
     * or with status {@code 400 (Bad Request)} if the taximeterFixedListItemDTO is not valid,
     * or with status {@code 404 (Not Found)} if the taximeterFixedListItemDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the taximeterFixedListItemDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/taximeter-fixed-list-items/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<TaximeterFixedListItemDTO> partialUpdateTaximeterFixedListItem(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TaximeterFixedListItemDTO taximeterFixedListItemDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TaximeterFixedListItem partially : {}, {}", id, taximeterFixedListItemDTO);
        if (taximeterFixedListItemDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, taximeterFixedListItemDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!taximeterFixedListItemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TaximeterFixedListItemDTO> result = taximeterFixedListItemService.partialUpdate(taximeterFixedListItemDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, taximeterFixedListItemDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /taximeter-fixed-list-items} : get all the taximeterFixedListItems.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of taximeterFixedListItems in body.
     */
    @GetMapping("/taximeter-fixed-list-items")
    public List<TaximeterFixedListItemDTO> getAllTaximeterFixedListItems() {
        log.debug("REST request to get all TaximeterFixedListItems");
        return taximeterFixedListItemService.findAll();
    }

    /**
     * {@code GET  /taximeter-fixed-list-items/:id} : get the "id" taximeterFixedListItem.
     *
     * @param id the id of the taximeterFixedListItemDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the taximeterFixedListItemDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/taximeter-fixed-list-items/{id}")
    public ResponseEntity<TaximeterFixedListItemDTO> getTaximeterFixedListItem(@PathVariable Long id) {
        log.debug("REST request to get TaximeterFixedListItem : {}", id);
        Optional<TaximeterFixedListItemDTO> taximeterFixedListItemDTO = taximeterFixedListItemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(taximeterFixedListItemDTO);
    }

    /**
     * {@code DELETE  /taximeter-fixed-list-items/:id} : delete the "id" taximeterFixedListItem.
     *
     * @param id the id of the taximeterFixedListItemDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/taximeter-fixed-list-items/{id}")
    public ResponseEntity<Void> deleteTaximeterFixedListItem(@PathVariable Long id) {
        log.debug("REST request to delete TaximeterFixedListItem : {}", id);
        taximeterFixedListItemService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
