package com.tiskel.taximeterapp.web.rest;

import com.tiskel.taximeterapp.repository.PreferenceRepository;
import com.tiskel.taximeterapp.service.PreferenceService;
import com.tiskel.taximeterapp.service.dto.PreferenceDTO;
import com.tiskel.taximeterapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.tiskel.taximeterapp.domain.Preference}.
 */
@RestController
@RequestMapping("/api")
public class PreferenceResource {

    private final Logger log = LoggerFactory.getLogger(PreferenceResource.class);

    private static final String ENTITY_NAME = "preference";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PreferenceService preferenceService;

    private final PreferenceRepository preferenceRepository;

    public PreferenceResource(PreferenceService preferenceService, PreferenceRepository preferenceRepository) {
        this.preferenceService = preferenceService;
        this.preferenceRepository = preferenceRepository;
    }

    /**
     * {@code POST  /preferences} : Create a new preference.
     *
     * @param preferenceDTO the preferenceDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new preferenceDTO, or with status {@code 400 (Bad Request)} if the preference has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/preferences")
    public ResponseEntity<PreferenceDTO> createPreference(@RequestBody PreferenceDTO preferenceDTO) throws URISyntaxException {
        log.debug("REST request to save Preference : {}", preferenceDTO);
        if (preferenceDTO.getId() != null) {
            throw new BadRequestAlertException("A new preference cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PreferenceDTO result = preferenceService.save(preferenceDTO);
        return ResponseEntity
            .created(new URI("/api/preferences/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /preferences/:id} : Updates an existing preference.
     *
     * @param id the id of the preferenceDTO to save.
     * @param preferenceDTO the preferenceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated preferenceDTO,
     * or with status {@code 400 (Bad Request)} if the preferenceDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the preferenceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/preferences/{id}")
    public ResponseEntity<PreferenceDTO> updatePreference(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PreferenceDTO preferenceDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Preference : {}, {}", id, preferenceDTO);
        if (preferenceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, preferenceDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!preferenceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PreferenceDTO result = preferenceService.save(preferenceDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, preferenceDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /preferences/:id} : Partial updates given fields of an existing preference, field will ignore if it is null
     *
     * @param id the id of the preferenceDTO to save.
     * @param preferenceDTO the preferenceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated preferenceDTO,
     * or with status {@code 400 (Bad Request)} if the preferenceDTO is not valid,
     * or with status {@code 404 (Not Found)} if the preferenceDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the preferenceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/preferences/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<PreferenceDTO> partialUpdatePreference(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PreferenceDTO preferenceDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Preference partially : {}, {}", id, preferenceDTO);
        if (preferenceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, preferenceDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!preferenceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PreferenceDTO> result = preferenceService.partialUpdate(preferenceDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, preferenceDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /preferences} : get all the preferences.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of preferences in body.
     */
    @GetMapping("/preferences")
    public List<PreferenceDTO> getAllPreferences() {
        log.debug("REST request to get all Preferences");
        return preferenceService.findAll();
    }

    /**
     * {@code GET  /preferences/:id} : get the "id" preference.
     *
     * @param id the id of the preferenceDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the preferenceDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/preferences/{id}")
    public ResponseEntity<PreferenceDTO> getPreference(@PathVariable Long id) {
        log.debug("REST request to get Preference : {}", id);
        Optional<PreferenceDTO> preferenceDTO = preferenceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(preferenceDTO);
    }

    /**
     * {@code DELETE  /preferences/:id} : delete the "id" preference.
     *
     * @param id the id of the preferenceDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/preferences/{id}")
    public ResponseEntity<Void> deletePreference(@PathVariable Long id) {
        log.debug("REST request to delete Preference : {}", id);
        preferenceService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
