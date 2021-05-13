package com.tiskel.taximeterapp.web.rest;

import com.tiskel.taximeterapp.repository.TaximeterFixedListRepository;
import com.tiskel.taximeterapp.service.TaximeterFixedListService;
import com.tiskel.taximeterapp.service.dto.TaximeterFixedListDTO;
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
 * REST controller for managing {@link com.tiskel.taximeterapp.domain.TaximeterFixedList}.
 */
@RestController
@RequestMapping("/api")
public class TaximeterFixedListResource {

    private final Logger log = LoggerFactory.getLogger(TaximeterFixedListResource.class);

    private static final String ENTITY_NAME = "taximeterFixedList";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TaximeterFixedListService taximeterFixedListService;

    private final TaximeterFixedListRepository taximeterFixedListRepository;

    public TaximeterFixedListResource(
        TaximeterFixedListService taximeterFixedListService,
        TaximeterFixedListRepository taximeterFixedListRepository
    ) {
        this.taximeterFixedListService = taximeterFixedListService;
        this.taximeterFixedListRepository = taximeterFixedListRepository;
    }

    /**
     * {@code POST  /taximeter-fixed-lists} : Create a new taximeterFixedList.
     *
     * @param taximeterFixedListDTO the taximeterFixedListDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new taximeterFixedListDTO, or with status {@code 400 (Bad Request)} if the taximeterFixedList has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/taximeter-fixed-lists")
    public ResponseEntity<TaximeterFixedListDTO> createTaximeterFixedList(@Valid @RequestBody TaximeterFixedListDTO taximeterFixedListDTO)
        throws URISyntaxException {
        log.debug("REST request to save TaximeterFixedList : {}", taximeterFixedListDTO);
        if (taximeterFixedListDTO.getId() != null) {
            throw new BadRequestAlertException("A new taximeterFixedList cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TaximeterFixedListDTO result = taximeterFixedListService.save(taximeterFixedListDTO);
        return ResponseEntity
            .created(new URI("/api/taximeter-fixed-lists/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /taximeter-fixed-lists/:id} : Updates an existing taximeterFixedList.
     *
     * @param id the id of the taximeterFixedListDTO to save.
     * @param taximeterFixedListDTO the taximeterFixedListDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated taximeterFixedListDTO,
     * or with status {@code 400 (Bad Request)} if the taximeterFixedListDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the taximeterFixedListDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/taximeter-fixed-lists/{id}")
    public ResponseEntity<TaximeterFixedListDTO> updateTaximeterFixedList(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TaximeterFixedListDTO taximeterFixedListDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TaximeterFixedList : {}, {}", id, taximeterFixedListDTO);
        if (taximeterFixedListDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, taximeterFixedListDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!taximeterFixedListRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TaximeterFixedListDTO result = taximeterFixedListService.save(taximeterFixedListDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, taximeterFixedListDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /taximeter-fixed-lists/:id} : Partial updates given fields of an existing taximeterFixedList, field will ignore if it is null
     *
     * @param id the id of the taximeterFixedListDTO to save.
     * @param taximeterFixedListDTO the taximeterFixedListDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated taximeterFixedListDTO,
     * or with status {@code 400 (Bad Request)} if the taximeterFixedListDTO is not valid,
     * or with status {@code 404 (Not Found)} if the taximeterFixedListDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the taximeterFixedListDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/taximeter-fixed-lists/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<TaximeterFixedListDTO> partialUpdateTaximeterFixedList(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TaximeterFixedListDTO taximeterFixedListDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TaximeterFixedList partially : {}, {}", id, taximeterFixedListDTO);
        if (taximeterFixedListDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, taximeterFixedListDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!taximeterFixedListRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TaximeterFixedListDTO> result = taximeterFixedListService.partialUpdate(taximeterFixedListDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, taximeterFixedListDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /taximeter-fixed-lists} : get all the taximeterFixedLists.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of taximeterFixedLists in body.
     */
    @GetMapping("/taximeter-fixed-lists")
    public List<TaximeterFixedListDTO> getAllTaximeterFixedLists() {
        log.debug("REST request to get all TaximeterFixedLists");
        return taximeterFixedListService.findAll();
    }

    /**
     * {@code GET  /taximeter-fixed-lists/:id} : get the "id" taximeterFixedList.
     *
     * @param id the id of the taximeterFixedListDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the taximeterFixedListDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/taximeter-fixed-lists/{id}")
    public ResponseEntity<TaximeterFixedListDTO> getTaximeterFixedList(@PathVariable Long id) {
        log.debug("REST request to get TaximeterFixedList : {}", id);
        Optional<TaximeterFixedListDTO> taximeterFixedListDTO = taximeterFixedListService.findOne(id);
        return ResponseUtil.wrapOrNotFound(taximeterFixedListDTO);
    }

    /**
     * {@code DELETE  /taximeter-fixed-lists/:id} : delete the "id" taximeterFixedList.
     *
     * @param id the id of the taximeterFixedListDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/taximeter-fixed-lists/{id}")
    public ResponseEntity<Void> deleteTaximeterFixedList(@PathVariable Long id) {
        log.debug("REST request to delete TaximeterFixedList : {}", id);
        taximeterFixedListService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
