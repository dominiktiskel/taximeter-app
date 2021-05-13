package com.tiskel.taximeterapp.web.rest;

import com.tiskel.taximeterapp.repository.TaximeterOfferRepository;
import com.tiskel.taximeterapp.service.TaximeterOfferService;
import com.tiskel.taximeterapp.service.dto.TaximeterOfferDTO;
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
 * REST controller for managing {@link com.tiskel.taximeterapp.domain.TaximeterOffer}.
 */
@RestController
@RequestMapping("/api")
public class TaximeterOfferResource {

    private final Logger log = LoggerFactory.getLogger(TaximeterOfferResource.class);

    private static final String ENTITY_NAME = "taximeterOffer";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TaximeterOfferService taximeterOfferService;

    private final TaximeterOfferRepository taximeterOfferRepository;

    public TaximeterOfferResource(TaximeterOfferService taximeterOfferService, TaximeterOfferRepository taximeterOfferRepository) {
        this.taximeterOfferService = taximeterOfferService;
        this.taximeterOfferRepository = taximeterOfferRepository;
    }

    /**
     * {@code POST  /taximeter-offers} : Create a new taximeterOffer.
     *
     * @param taximeterOfferDTO the taximeterOfferDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new taximeterOfferDTO, or with status {@code 400 (Bad Request)} if the taximeterOffer has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/taximeter-offers")
    public ResponseEntity<TaximeterOfferDTO> createTaximeterOffer(@Valid @RequestBody TaximeterOfferDTO taximeterOfferDTO)
        throws URISyntaxException {
        log.debug("REST request to save TaximeterOffer : {}", taximeterOfferDTO);
        if (taximeterOfferDTO.getId() != null) {
            throw new BadRequestAlertException("A new taximeterOffer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TaximeterOfferDTO result = taximeterOfferService.save(taximeterOfferDTO);
        return ResponseEntity
            .created(new URI("/api/taximeter-offers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /taximeter-offers/:id} : Updates an existing taximeterOffer.
     *
     * @param id the id of the taximeterOfferDTO to save.
     * @param taximeterOfferDTO the taximeterOfferDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated taximeterOfferDTO,
     * or with status {@code 400 (Bad Request)} if the taximeterOfferDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the taximeterOfferDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/taximeter-offers/{id}")
    public ResponseEntity<TaximeterOfferDTO> updateTaximeterOffer(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TaximeterOfferDTO taximeterOfferDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TaximeterOffer : {}, {}", id, taximeterOfferDTO);
        if (taximeterOfferDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, taximeterOfferDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!taximeterOfferRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TaximeterOfferDTO result = taximeterOfferService.save(taximeterOfferDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, taximeterOfferDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /taximeter-offers/:id} : Partial updates given fields of an existing taximeterOffer, field will ignore if it is null
     *
     * @param id the id of the taximeterOfferDTO to save.
     * @param taximeterOfferDTO the taximeterOfferDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated taximeterOfferDTO,
     * or with status {@code 400 (Bad Request)} if the taximeterOfferDTO is not valid,
     * or with status {@code 404 (Not Found)} if the taximeterOfferDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the taximeterOfferDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/taximeter-offers/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<TaximeterOfferDTO> partialUpdateTaximeterOffer(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TaximeterOfferDTO taximeterOfferDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TaximeterOffer partially : {}, {}", id, taximeterOfferDTO);
        if (taximeterOfferDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, taximeterOfferDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!taximeterOfferRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TaximeterOfferDTO> result = taximeterOfferService.partialUpdate(taximeterOfferDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, taximeterOfferDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /taximeter-offers} : get all the taximeterOffers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of taximeterOffers in body.
     */
    @GetMapping("/taximeter-offers")
    public List<TaximeterOfferDTO> getAllTaximeterOffers() {
        log.debug("REST request to get all TaximeterOffers");
        return taximeterOfferService.findAll();
    }

    /**
     * {@code GET  /taximeter-offers/:id} : get the "id" taximeterOffer.
     *
     * @param id the id of the taximeterOfferDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the taximeterOfferDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/taximeter-offers/{id}")
    public ResponseEntity<TaximeterOfferDTO> getTaximeterOffer(@PathVariable Long id) {
        log.debug("REST request to get TaximeterOffer : {}", id);
        Optional<TaximeterOfferDTO> taximeterOfferDTO = taximeterOfferService.findOne(id);
        return ResponseUtil.wrapOrNotFound(taximeterOfferDTO);
    }

    /**
     * {@code DELETE  /taximeter-offers/:id} : delete the "id" taximeterOffer.
     *
     * @param id the id of the taximeterOfferDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/taximeter-offers/{id}")
    public ResponseEntity<Void> deleteTaximeterOffer(@PathVariable Long id) {
        log.debug("REST request to delete TaximeterOffer : {}", id);
        taximeterOfferService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
