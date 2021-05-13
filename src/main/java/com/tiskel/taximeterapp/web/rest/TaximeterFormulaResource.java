package com.tiskel.taximeterapp.web.rest;

import com.tiskel.taximeterapp.repository.TaximeterFormulaRepository;
import com.tiskel.taximeterapp.service.TaximeterFormulaService;
import com.tiskel.taximeterapp.service.dto.TaximeterFormulaDTO;
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
 * REST controller for managing {@link com.tiskel.taximeterapp.domain.TaximeterFormula}.
 */
@RestController
@RequestMapping("/api")
public class TaximeterFormulaResource {

    private final Logger log = LoggerFactory.getLogger(TaximeterFormulaResource.class);

    private static final String ENTITY_NAME = "taximeterFormula";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TaximeterFormulaService taximeterFormulaService;

    private final TaximeterFormulaRepository taximeterFormulaRepository;

    public TaximeterFormulaResource(
        TaximeterFormulaService taximeterFormulaService,
        TaximeterFormulaRepository taximeterFormulaRepository
    ) {
        this.taximeterFormulaService = taximeterFormulaService;
        this.taximeterFormulaRepository = taximeterFormulaRepository;
    }

    /**
     * {@code POST  /taximeter-formulas} : Create a new taximeterFormula.
     *
     * @param taximeterFormulaDTO the taximeterFormulaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new taximeterFormulaDTO, or with status {@code 400 (Bad Request)} if the taximeterFormula has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/taximeter-formulas")
    public ResponseEntity<TaximeterFormulaDTO> createTaximeterFormula(@Valid @RequestBody TaximeterFormulaDTO taximeterFormulaDTO)
        throws URISyntaxException {
        log.debug("REST request to save TaximeterFormula : {}", taximeterFormulaDTO);
        if (taximeterFormulaDTO.getId() != null) {
            throw new BadRequestAlertException("A new taximeterFormula cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TaximeterFormulaDTO result = taximeterFormulaService.save(taximeterFormulaDTO);
        return ResponseEntity
            .created(new URI("/api/taximeter-formulas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /taximeter-formulas/:id} : Updates an existing taximeterFormula.
     *
     * @param id the id of the taximeterFormulaDTO to save.
     * @param taximeterFormulaDTO the taximeterFormulaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated taximeterFormulaDTO,
     * or with status {@code 400 (Bad Request)} if the taximeterFormulaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the taximeterFormulaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/taximeter-formulas/{id}")
    public ResponseEntity<TaximeterFormulaDTO> updateTaximeterFormula(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TaximeterFormulaDTO taximeterFormulaDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TaximeterFormula : {}, {}", id, taximeterFormulaDTO);
        if (taximeterFormulaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, taximeterFormulaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!taximeterFormulaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TaximeterFormulaDTO result = taximeterFormulaService.save(taximeterFormulaDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, taximeterFormulaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /taximeter-formulas/:id} : Partial updates given fields of an existing taximeterFormula, field will ignore if it is null
     *
     * @param id the id of the taximeterFormulaDTO to save.
     * @param taximeterFormulaDTO the taximeterFormulaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated taximeterFormulaDTO,
     * or with status {@code 400 (Bad Request)} if the taximeterFormulaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the taximeterFormulaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the taximeterFormulaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/taximeter-formulas/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<TaximeterFormulaDTO> partialUpdateTaximeterFormula(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TaximeterFormulaDTO taximeterFormulaDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TaximeterFormula partially : {}, {}", id, taximeterFormulaDTO);
        if (taximeterFormulaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, taximeterFormulaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!taximeterFormulaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TaximeterFormulaDTO> result = taximeterFormulaService.partialUpdate(taximeterFormulaDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, taximeterFormulaDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /taximeter-formulas} : get all the taximeterFormulas.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of taximeterFormulas in body.
     */
    @GetMapping("/taximeter-formulas")
    public List<TaximeterFormulaDTO> getAllTaximeterFormulas() {
        log.debug("REST request to get all TaximeterFormulas");
        return taximeterFormulaService.findAll();
    }

    /**
     * {@code GET  /taximeter-formulas/:id} : get the "id" taximeterFormula.
     *
     * @param id the id of the taximeterFormulaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the taximeterFormulaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/taximeter-formulas/{id}")
    public ResponseEntity<TaximeterFormulaDTO> getTaximeterFormula(@PathVariable Long id) {
        log.debug("REST request to get TaximeterFormula : {}", id);
        Optional<TaximeterFormulaDTO> taximeterFormulaDTO = taximeterFormulaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(taximeterFormulaDTO);
    }

    /**
     * {@code DELETE  /taximeter-formulas/:id} : delete the "id" taximeterFormula.
     *
     * @param id the id of the taximeterFormulaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/taximeter-formulas/{id}")
    public ResponseEntity<Void> deleteTaximeterFormula(@PathVariable Long id) {
        log.debug("REST request to delete TaximeterFormula : {}", id);
        taximeterFormulaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
