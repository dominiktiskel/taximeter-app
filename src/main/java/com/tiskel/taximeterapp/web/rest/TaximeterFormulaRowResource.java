package com.tiskel.taximeterapp.web.rest;

import com.tiskel.taximeterapp.domain.TaximeterFormulaRow;
import com.tiskel.taximeterapp.repository.TaximeterFormulaRowRepository;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.tiskel.taximeterapp.domain.TaximeterFormulaRow}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TaximeterFormulaRowResource {

    private final Logger log = LoggerFactory.getLogger(TaximeterFormulaRowResource.class);

    private static final String ENTITY_NAME = "taximeterFormulaRow";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TaximeterFormulaRowRepository taximeterFormulaRowRepository;

    public TaximeterFormulaRowResource(TaximeterFormulaRowRepository taximeterFormulaRowRepository) {
        this.taximeterFormulaRowRepository = taximeterFormulaRowRepository;
    }

    /**
     * {@code POST  /taximeter-formula-rows} : Create a new taximeterFormulaRow.
     *
     * @param taximeterFormulaRow the taximeterFormulaRow to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new taximeterFormulaRow, or with status {@code 400 (Bad Request)} if the taximeterFormulaRow has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/taximeter-formula-rows")
    public ResponseEntity<TaximeterFormulaRow> createTaximeterFormulaRow(@Valid @RequestBody TaximeterFormulaRow taximeterFormulaRow)
        throws URISyntaxException {
        log.debug("REST request to save TaximeterFormulaRow : {}", taximeterFormulaRow);
        if (taximeterFormulaRow.getId() != null) {
            throw new BadRequestAlertException("A new taximeterFormulaRow cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TaximeterFormulaRow result = taximeterFormulaRowRepository.save(taximeterFormulaRow);
        return ResponseEntity
            .created(new URI("/api/taximeter-formula-rows/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /taximeter-formula-rows/:id} : Updates an existing taximeterFormulaRow.
     *
     * @param id the id of the taximeterFormulaRow to save.
     * @param taximeterFormulaRow the taximeterFormulaRow to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated taximeterFormulaRow,
     * or with status {@code 400 (Bad Request)} if the taximeterFormulaRow is not valid,
     * or with status {@code 500 (Internal Server Error)} if the taximeterFormulaRow couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/taximeter-formula-rows/{id}")
    public ResponseEntity<TaximeterFormulaRow> updateTaximeterFormulaRow(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TaximeterFormulaRow taximeterFormulaRow
    ) throws URISyntaxException {
        log.debug("REST request to update TaximeterFormulaRow : {}, {}", id, taximeterFormulaRow);
        if (taximeterFormulaRow.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, taximeterFormulaRow.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!taximeterFormulaRowRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TaximeterFormulaRow result = taximeterFormulaRowRepository.save(taximeterFormulaRow);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, taximeterFormulaRow.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /taximeter-formula-rows/:id} : Partial updates given fields of an existing taximeterFormulaRow, field will ignore if it is null
     *
     * @param id the id of the taximeterFormulaRow to save.
     * @param taximeterFormulaRow the taximeterFormulaRow to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated taximeterFormulaRow,
     * or with status {@code 400 (Bad Request)} if the taximeterFormulaRow is not valid,
     * or with status {@code 404 (Not Found)} if the taximeterFormulaRow is not found,
     * or with status {@code 500 (Internal Server Error)} if the taximeterFormulaRow couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/taximeter-formula-rows/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<TaximeterFormulaRow> partialUpdateTaximeterFormulaRow(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TaximeterFormulaRow taximeterFormulaRow
    ) throws URISyntaxException {
        log.debug("REST request to partial update TaximeterFormulaRow partially : {}, {}", id, taximeterFormulaRow);
        if (taximeterFormulaRow.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, taximeterFormulaRow.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!taximeterFormulaRowRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TaximeterFormulaRow> result = taximeterFormulaRowRepository
            .findById(taximeterFormulaRow.getId())
            .map(
                existingTaximeterFormulaRow -> {
                    if (taximeterFormulaRow.getType() != null) {
                        existingTaximeterFormulaRow.setType(taximeterFormulaRow.getType());
                    }
                    if (taximeterFormulaRow.getValue() != null) {
                        existingTaximeterFormulaRow.setValue(taximeterFormulaRow.getValue());
                    }
                    if (taximeterFormulaRow.getStep() != null) {
                        existingTaximeterFormulaRow.setStep(taximeterFormulaRow.getStep());
                    }
                    if (taximeterFormulaRow.getGranulation() != null) {
                        existingTaximeterFormulaRow.setGranulation(taximeterFormulaRow.getGranulation());
                    }

                    return existingTaximeterFormulaRow;
                }
            )
            .map(taximeterFormulaRowRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, taximeterFormulaRow.getId().toString())
        );
    }

    /**
     * {@code GET  /taximeter-formula-rows} : get all the taximeterFormulaRows.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of taximeterFormulaRows in body.
     */
    @GetMapping("/taximeter-formula-rows")
    public List<TaximeterFormulaRow> getAllTaximeterFormulaRows() {
        log.debug("REST request to get all TaximeterFormulaRows");
        return taximeterFormulaRowRepository.findAll();
    }

    /**
     * {@code GET  /taximeter-formula-rows/:id} : get the "id" taximeterFormulaRow.
     *
     * @param id the id of the taximeterFormulaRow to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the taximeterFormulaRow, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/taximeter-formula-rows/{id}")
    public ResponseEntity<TaximeterFormulaRow> getTaximeterFormulaRow(@PathVariable Long id) {
        log.debug("REST request to get TaximeterFormulaRow : {}", id);
        Optional<TaximeterFormulaRow> taximeterFormulaRow = taximeterFormulaRowRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(taximeterFormulaRow);
    }

    /**
     * {@code DELETE  /taximeter-formula-rows/:id} : delete the "id" taximeterFormulaRow.
     *
     * @param id the id of the taximeterFormulaRow to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/taximeter-formula-rows/{id}")
    public ResponseEntity<Void> deleteTaximeterFormulaRow(@PathVariable Long id) {
        log.debug("REST request to delete TaximeterFormulaRow : {}", id);
        taximeterFormulaRowRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
