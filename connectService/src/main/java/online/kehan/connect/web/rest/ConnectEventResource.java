package online.kehan.connect.web.rest;

import online.kehan.connect.service.ConnectEventService;
import online.kehan.connect.web.rest.errors.BadRequestAlertException;
import online.kehan.connect.service.dto.ConnectEventDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link online.kehan.connect.domain.ConnectEvent}.
 */
@RestController
@RequestMapping("/api")
public class ConnectEventResource {

    private final Logger log = LoggerFactory.getLogger(ConnectEventResource.class);

    private static final String ENTITY_NAME = "connectServiceConnectEvent";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ConnectEventService connectEventService;

    public ConnectEventResource(ConnectEventService connectEventService) {
        this.connectEventService = connectEventService;
    }

    /**
     * {@code POST  /connect-events} : Create a new connectEvent.
     *
     * @param connectEventDTO the connectEventDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new connectEventDTO, or with status {@code 400 (Bad Request)} if the connectEvent has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/connect-events")
    public ResponseEntity<ConnectEventDTO> createConnectEvent(@Valid @RequestBody ConnectEventDTO connectEventDTO) throws URISyntaxException {
        log.debug("REST request to save ConnectEvent : {}", connectEventDTO);
        if (connectEventDTO.getId() != null) {
            throw new BadRequestAlertException("A new connectEvent cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ConnectEventDTO result = connectEventService.save(connectEventDTO);
        return ResponseEntity.created(new URI("/api/connect-events/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /connect-events} : Updates an existing connectEvent.
     *
     * @param connectEventDTO the connectEventDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated connectEventDTO,
     * or with status {@code 400 (Bad Request)} if the connectEventDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the connectEventDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/connect-events")
    public ResponseEntity<ConnectEventDTO> updateConnectEvent(@Valid @RequestBody ConnectEventDTO connectEventDTO) throws URISyntaxException {
        log.debug("REST request to update ConnectEvent : {}", connectEventDTO);
        if (connectEventDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ConnectEventDTO result = connectEventService.save(connectEventDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, connectEventDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /connect-events} : get all the connectEvents.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of connectEvents in body.
     */
    @GetMapping("/connect-events")
    public ResponseEntity<List<ConnectEventDTO>> getAllConnectEvents(Pageable pageable) {
        log.debug("REST request to get a page of ConnectEvents");
        Page<ConnectEventDTO> page = connectEventService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /connect-events/:id} : get the "id" connectEvent.
     *
     * @param id the id of the connectEventDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the connectEventDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/connect-events/{id}")
    public ResponseEntity<ConnectEventDTO> getConnectEvent(@PathVariable Long id) {
        log.debug("REST request to get ConnectEvent : {}", id);
        Optional<ConnectEventDTO> connectEventDTO = connectEventService.findOne(id);
        return ResponseUtil.wrapOrNotFound(connectEventDTO);
    }

    /**
     * {@code DELETE  /connect-events/:id} : delete the "id" connectEvent.
     *
     * @param id the id of the connectEventDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/connect-events/{id}")
    public ResponseEntity<Void> deleteConnectEvent(@PathVariable Long id) {
        log.debug("REST request to delete ConnectEvent : {}", id);

        connectEventService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
