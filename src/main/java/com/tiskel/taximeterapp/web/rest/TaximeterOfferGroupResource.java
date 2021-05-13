package com.tiskel.taximeterapp.web.rest;

import com.tiskel.taximeterapp.repository.TaximeterOfferGroupRepository;
import com.tiskel.taximeterapp.service.TaximeterOfferGroupService;
import com.tiskel.taximeterapp.service.dto.TaximeterOfferGroupDTO;
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
 * REST controller for managing {@link com.tiskel.taximeterapp.domain.TaximeterOfferGroup}.
 */
@RestController
@RequestMapping("/api")
public class TaximeterOfferGroupResource {

    private final Logger log = LoggerFactory.getLogger(TaximeterOfferGroupResource.class);

    private static final String ENTITY_NAME = "taximeterOfferGroup";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TaximeterOfferGroupService taximeterOfferGroupService;

    private final TaximeterOfferGroupRepository taximeterOfferGroupRepository;

    public TaximeterOfferGroupResource(
        TaximeterOfferGroupService taximeterOfferGroupService,
        TaximeterOfferGroupRepository taximeterOfferGroupRepository
    ) {
        this.taximeterOfferGroupService = taximeterOfferGroupService;
        this.taximeterOfferGroupRepository = taximeterOfferGroupRepository;
    }

    /**
     * {@code POST  /taximeter-offer-groups} : Create a new taximeterOfferGroup.
     *
     * @param taximeterOfferGroupDTO the taximeterOfferGroupDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new taximeterOfferGroupDTO, or with status {@code 400 (Bad Request)} if the taximeterOfferGroup has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/taximeter-offer-groups")
    public ResponseEntity<TaximeterOfferGroupDTO> createTaximeterOfferGroup(
        @Valid @RequestBody TaximeterOfferGroupDTO taximeterOfferGroupDTO
    ) throws URISyntaxException {
        log.debug("REST request to save TaximeterOfferGroup : {}", taximeterOfferGroupDTO);
        if (taximeterOfferGroupDTO.getId() != null) {
            throw new BadRequestAlertException("A new taximeterOfferGroup cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TaximeterOfferGroupDTO result = taximeterOfferGroupService.save(taximeterOfferGroupDTO);
        return ResponseEntity
            .created(new URI("/api/taximeter-offer-groups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /taximeter-offer-groups/:id} : Updates an existing taximeterOfferGroup.
     *
     * @param id the id of the taximeterOfferGroupDTO to save.
     * @param taximeterOfferGroupDTO the taximeterOfferGroupDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated taximeterOfferGroupDTO,
     * or with status {@code 400 (Bad Request)} if the taximeterOfferGroupDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the taximeterOfferGroupDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/taximeter-offer-groups/{id}")
    public ResponseEntity<TaximeterOfferGroupDTO> updateTaximeterOfferGroup(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TaximeterOfferGroupDTO taximeterOfferGroupDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TaximeterOfferGroup : {}, {}", id, taximeterOfferGroupDTO);
        if (taximeterOfferGroupDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, taximeterOfferGroupDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!taximeterOfferGroupRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TaximeterOfferGroupDTO result = taximeterOfferGroupService.save(taximeterOfferGroupDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, taximeterOfferGroupDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /taximeter-offer-groups/:id} : Partial updates given fields of an existing taximeterOfferGroup, field will ignore if it is null
     *
     * @param id the id of the taximeterOfferGroupDTO to save.
     * @param taximeterOfferGroupDTO the taximeterOfferGroupDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated taximeterOfferGroupDTO,
     * or with status {@code 400 (Bad Request)} if the taximeterOfferGroupDTO is not valid,
     * or with status {@code 404 (Not Found)} if the taximeterOfferGroupDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the taximeterOfferGroupDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/taximeter-offer-groups/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<TaximeterOfferGroupDTO> partialUpdateTaximeterOfferGroup(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TaximeterOfferGroupDTO taximeterOfferGroupDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TaximeterOfferGroup partially : {}, {}", id, taximeterOfferGroupDTO);
        if (taximeterOfferGroupDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, taximeterOfferGroupDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!taximeterOfferGroupRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TaximeterOfferGroupDTO> result = taximeterOfferGroupService.partialUpdate(taximeterOfferGroupDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, taximeterOfferGroupDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /taximeter-offer-groups} : get all the taximeterOfferGroups.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of taximeterOfferGroups in body.
     */
    @GetMapping("/taximeter-offer-groups")
    public List<TaximeterOfferGroupDTO> getAllTaximeterOfferGroups() {
        log.debug("REST request to get all TaximeterOfferGroups");
        return taximeterOfferGroupService.findAll();
    }

    /**
     * {@code GET  /taximeter-offer-groups/:id} : get the "id" taximeterOfferGroup.
     *
     * @param id the id of the taximeterOfferGroupDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the taximeterOfferGroupDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/taximeter-offer-groups/{id}")
    public ResponseEntity<TaximeterOfferGroupDTO> getTaximeterOfferGroup(@PathVariable Long id) {
        log.debug("REST request to get TaximeterOfferGroup : {}", id);
        Optional<TaximeterOfferGroupDTO> taximeterOfferGroupDTO = taximeterOfferGroupService.findOne(id);
        return ResponseUtil.wrapOrNotFound(taximeterOfferGroupDTO);
    }

    /**
     * {@code DELETE  /taximeter-offer-groups/:id} : delete the "id" taximeterOfferGroup.
     *
     * @param id the id of the taximeterOfferGroupDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/taximeter-offer-groups/{id}")
    public ResponseEntity<Void> deleteTaximeterOfferGroup(@PathVariable Long id) {
        log.debug("REST request to delete TaximeterOfferGroup : {}", id);
        taximeterOfferGroupService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
