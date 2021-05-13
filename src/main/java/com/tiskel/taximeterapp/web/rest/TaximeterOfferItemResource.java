package com.tiskel.taximeterapp.web.rest;

import com.tiskel.taximeterapp.repository.TaximeterOfferItemRepository;
import com.tiskel.taximeterapp.service.TaximeterOfferItemService;
import com.tiskel.taximeterapp.service.dto.TaximeterOfferItemDTO;
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
 * REST controller for managing {@link com.tiskel.taximeterapp.domain.TaximeterOfferItem}.
 */
@RestController
@RequestMapping("/api")
public class TaximeterOfferItemResource {

    private final Logger log = LoggerFactory.getLogger(TaximeterOfferItemResource.class);

    private static final String ENTITY_NAME = "taximeterOfferItem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TaximeterOfferItemService taximeterOfferItemService;

    private final TaximeterOfferItemRepository taximeterOfferItemRepository;

    public TaximeterOfferItemResource(
        TaximeterOfferItemService taximeterOfferItemService,
        TaximeterOfferItemRepository taximeterOfferItemRepository
    ) {
        this.taximeterOfferItemService = taximeterOfferItemService;
        this.taximeterOfferItemRepository = taximeterOfferItemRepository;
    }

    /**
     * {@code POST  /taximeter-offer-items} : Create a new taximeterOfferItem.
     *
     * @param taximeterOfferItemDTO the taximeterOfferItemDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new taximeterOfferItemDTO, or with status {@code 400 (Bad Request)} if the taximeterOfferItem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/taximeter-offer-items")
    public ResponseEntity<TaximeterOfferItemDTO> createTaximeterOfferItem(@Valid @RequestBody TaximeterOfferItemDTO taximeterOfferItemDTO)
        throws URISyntaxException {
        log.debug("REST request to save TaximeterOfferItem : {}", taximeterOfferItemDTO);
        if (taximeterOfferItemDTO.getId() != null) {
            throw new BadRequestAlertException("A new taximeterOfferItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TaximeterOfferItemDTO result = taximeterOfferItemService.save(taximeterOfferItemDTO);
        return ResponseEntity
            .created(new URI("/api/taximeter-offer-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /taximeter-offer-items/:id} : Updates an existing taximeterOfferItem.
     *
     * @param id the id of the taximeterOfferItemDTO to save.
     * @param taximeterOfferItemDTO the taximeterOfferItemDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated taximeterOfferItemDTO,
     * or with status {@code 400 (Bad Request)} if the taximeterOfferItemDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the taximeterOfferItemDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/taximeter-offer-items/{id}")
    public ResponseEntity<TaximeterOfferItemDTO> updateTaximeterOfferItem(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TaximeterOfferItemDTO taximeterOfferItemDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TaximeterOfferItem : {}, {}", id, taximeterOfferItemDTO);
        if (taximeterOfferItemDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, taximeterOfferItemDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!taximeterOfferItemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TaximeterOfferItemDTO result = taximeterOfferItemService.save(taximeterOfferItemDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, taximeterOfferItemDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /taximeter-offer-items/:id} : Partial updates given fields of an existing taximeterOfferItem, field will ignore if it is null
     *
     * @param id the id of the taximeterOfferItemDTO to save.
     * @param taximeterOfferItemDTO the taximeterOfferItemDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated taximeterOfferItemDTO,
     * or with status {@code 400 (Bad Request)} if the taximeterOfferItemDTO is not valid,
     * or with status {@code 404 (Not Found)} if the taximeterOfferItemDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the taximeterOfferItemDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/taximeter-offer-items/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<TaximeterOfferItemDTO> partialUpdateTaximeterOfferItem(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TaximeterOfferItemDTO taximeterOfferItemDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TaximeterOfferItem partially : {}, {}", id, taximeterOfferItemDTO);
        if (taximeterOfferItemDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, taximeterOfferItemDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!taximeterOfferItemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TaximeterOfferItemDTO> result = taximeterOfferItemService.partialUpdate(taximeterOfferItemDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, taximeterOfferItemDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /taximeter-offer-items} : get all the taximeterOfferItems.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of taximeterOfferItems in body.
     */
    @GetMapping("/taximeter-offer-items")
    public List<TaximeterOfferItemDTO> getAllTaximeterOfferItems(
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get all TaximeterOfferItems");
        return taximeterOfferItemService.findAll();
    }

    /**
     * {@code GET  /taximeter-offer-items/:id} : get the "id" taximeterOfferItem.
     *
     * @param id the id of the taximeterOfferItemDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the taximeterOfferItemDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/taximeter-offer-items/{id}")
    public ResponseEntity<TaximeterOfferItemDTO> getTaximeterOfferItem(@PathVariable Long id) {
        log.debug("REST request to get TaximeterOfferItem : {}", id);
        Optional<TaximeterOfferItemDTO> taximeterOfferItemDTO = taximeterOfferItemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(taximeterOfferItemDTO);
    }

    /**
     * {@code DELETE  /taximeter-offer-items/:id} : delete the "id" taximeterOfferItem.
     *
     * @param id the id of the taximeterOfferItemDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/taximeter-offer-items/{id}")
    public ResponseEntity<Void> deleteTaximeterOfferItem(@PathVariable Long id) {
        log.debug("REST request to delete TaximeterOfferItem : {}", id);
        taximeterOfferItemService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
