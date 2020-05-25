package online.kehan.connect.connector.web.rest;

import online.kehan.connect.connector.domain.ConnectState;
import online.kehan.connect.connector.service.ConnectStateService;
import online.kehan.connect.connector.web.rest.errors.BadRequestAlertException;

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
 * REST controller for managing {@link online.kehan.connect.connector.domain.ConnectState}.
 */
@RestController
@RequestMapping("/api")
public class ConnectStateResource {

    private final Logger log = LoggerFactory.getLogger(ConnectStateResource.class);

    private static final String ENTITY_NAME = "connectorServiceConnectState";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ConnectStateService connectStateService;

    public ConnectStateResource(ConnectStateService connectStateService) {
        this.connectStateService = connectStateService;
    }

    /**
     * {@code POST  /connect-states} : Create a new connectState.
     *
     * @param connectState the connectState to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new connectState, or with status {@code 400 (Bad Request)} if the connectState has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/connect-states")
    public ResponseEntity<ConnectState> createConnectState(@Valid @RequestBody ConnectState connectState) throws URISyntaxException {
        log.debug("REST request to save ConnectState : {}", connectState);
        if (connectState.getId() != null) {
            throw new BadRequestAlertException("A new connectState cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ConnectState result = connectStateService.save(connectState);
        return ResponseEntity.created(new URI("/api/connect-states/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /connect-states} : Updates an existing connectState.
     *
     * @param connectState the connectState to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated connectState,
     * or with status {@code 400 (Bad Request)} if the connectState is not valid,
     * or with status {@code 500 (Internal Server Error)} if the connectState couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/connect-states")
    public ResponseEntity<ConnectState> updateConnectState(@Valid @RequestBody ConnectState connectState) throws URISyntaxException {
        log.debug("REST request to update ConnectState : {}", connectState);
        if (connectState.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ConnectState result = connectStateService.save(connectState);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, connectState.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /connect-states} : get all the connectStates.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of connectStates in body.
     */
    @GetMapping("/connect-states")
    public ResponseEntity<List<ConnectState>> getAllConnectStates(Pageable pageable) {
        log.debug("REST request to get a page of ConnectStates");
        Page<ConnectState> page = connectStateService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /connect-states/:id} : get the "id" connectState.
     *
     * @param id the id of the connectState to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the connectState, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/connect-states/{id}")
    public ResponseEntity<ConnectState> getConnectState(@PathVariable Long id) {
        log.debug("REST request to get ConnectState : {}", id);
        Optional<ConnectState> connectState = connectStateService.findOne(id);
        return ResponseUtil.wrapOrNotFound(connectState);
    }

    /**
     * {@code DELETE  /connect-states/:id} : delete the "id" connectState.
     *
     * @param id the id of the connectState to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/connect-states/{id}")
    public ResponseEntity<Void> deleteConnectState(@PathVariable Long id) {
        log.debug("REST request to delete ConnectState : {}", id);

        connectStateService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
