package com.tiskel.taximeterapp.web.rest;

import com.tiskel.taximeterapp.repository.TaximeterFormulaRowRepository;
import com.tiskel.taximeterapp.service.TaximeterFormulaRowService;
import com.tiskel.taximeterapp.service.dto.TaximeterFormulaRowDTO;
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
 * REST controller for managing {@link com.tiskel.taximeterapp.domain.TaximeterFormulaRow}.
 */
@RestController
@RequestMapping("/api")
public class TaximeterFormulaRowResource {

    private final Logger log = LoggerFactory.getLogger(TaximeterFormulaRowResource.class);

    private static final String ENTITY_NAME = "taximeterFormulaRow";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TaximeterFormulaRowService taximeterFormulaRowService;

    private final TaximeterFormulaRowRepository taximeterFormulaRowRepository;

    public TaximeterFormulaRowResource(
        TaximeterFormulaRowService taximeterFormulaRowService,
        TaximeterFormulaRowRepository taximeterFormulaRowRepository
    ) {
        this.taximeterFormulaRowService = taximeterFormulaRowService;
        this.taximeterFormulaRowRepository = taximeterFormulaRowRepository;
    }

    /**
     * {@code POST  /taximeter-formula-rows} : Create a new taximeterFormulaRow.
     *
     * @param taximeterFormulaRowDTO the taximeterFormulaRowDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new taximeterFormulaRowDTO, or with status {@code 400 (Bad Request)} if the taximeterFormulaRow has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/taximeter-formula-rows")
    public ResponseEntity<TaximeterFormulaRowDTO> createTaximeterFormulaRow(
        @Valid @RequestBody TaximeterFormulaRowDTO taximeterFormulaRowDTO
    ) throws URISyntaxException {
        log.debug("REST request to save TaximeterFormulaRow : {}", taximeterFormulaRowDTO);
        if (taximeterFormulaRowDTO.getId() != null) {
            throw new BadRequestAlertException("A new taximeterFormulaRow cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TaximeterFormulaRowDTO result = taximeterFormulaRowService.save(taximeterFormulaRowDTO);
        return ResponseEntity
            .created(new URI("/api/taximeter-formula-rows/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /taximeter-formula-rows/:id} : Updates an existing taximeterFormulaRow.
     *
     * @param id the id of the taximeterFormulaRowDTO to save.
     * @param taximeterFormulaRowDTO the taximeterFormulaRowDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated taximeterFormulaRowDTO,
     * or with status {@code 400 (Bad Request)} if the taximeterFormulaRowDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the taximeterFormulaRowDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/taximeter-formula-rows/{id}")
    public ResponseEntity<TaximeterFormulaRowDTO> updateTaximeterFormulaRow(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TaximeterFormulaRowDTO taximeterFormulaRowDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TaximeterFormulaRow : {}, {}", id, taximeterFormulaRowDTO);
        if (taximeterFormulaRowDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, taximeterFormulaRowDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!taximeterFormulaRowRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TaximeterFormulaRowDTO result = taximeterFormulaRowService.save(taximeterFormulaRowDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, taximeterFormulaRowDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /taximeter-formula-rows/:id} : Partial updates given fields of an existing taximeterFormulaRow, field will ignore if it is null
     *
     * @param id the id of the taximeterFormulaRowDTO to save.
     * @param taximeterFormulaRowDTO the taximeterFormulaRowDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated taximeterFormulaRowDTO,
     * or with status {@code 400 (Bad Request)} if the taximeterFormulaRowDTO is not valid,
     * or with status {@code 404 (Not Found)} if the taximeterFormulaRowDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the taximeterFormulaRowDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/taximeter-formula-rows/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<TaximeterFormulaRowDTO> partialUpdateTaximeterFormulaRow(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TaximeterFormulaRowDTO taximeterFormulaRowDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TaximeterFormulaRow partially : {}, {}", id, taximeterFormulaRowDTO);
        if (taximeterFormulaRowDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, taximeterFormulaRowDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!taximeterFormulaRowRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TaximeterFormulaRowDTO> result = taximeterFormulaRowService.partialUpdate(taximeterFormulaRowDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, taximeterFormulaRowDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /taximeter-formula-rows} : get all the taximeterFormulaRows.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of taximeterFormulaRows in body.
     */
    @GetMapping("/taximeter-formula-rows")
    public List<TaximeterFormulaRowDTO> getAllTaximeterFormulaRows() {
        log.debug("REST request to get all TaximeterFormulaRows");
        return taximeterFormulaRowService.findAll();
    }

    /**
     * {@code GET  /taximeter-formula-rows/:id} : get the "id" taximeterFormulaRow.
     *
     * @param id the id of the taximeterFormulaRowDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the taximeterFormulaRowDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/taximeter-formula-rows/{id}")
    public ResponseEntity<TaximeterFormulaRowDTO> getTaximeterFormulaRow(@PathVariable Long id) {
        log.debug("REST request to get TaximeterFormulaRow : {}", id);
        Optional<TaximeterFormulaRowDTO> taximeterFormulaRowDTO = taximeterFormulaRowService.findOne(id);
        return ResponseUtil.wrapOrNotFound(taximeterFormulaRowDTO);
    }

    /**
     * {@code DELETE  /taximeter-formula-rows/:id} : delete the "id" taximeterFormulaRow.
     *
     * @param id the id of the taximeterFormulaRowDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/taximeter-formula-rows/{id}")
    public ResponseEntity<Void> deleteTaximeterFormulaRow(@PathVariable Long id) {
        log.debug("REST request to delete TaximeterFormulaRow : {}", id);
        taximeterFormulaRowService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
