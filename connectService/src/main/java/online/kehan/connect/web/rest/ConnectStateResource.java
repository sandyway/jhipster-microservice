package online.kehan.connect.web.rest;

import online.kehan.connect.service.ConnectStateService;
import online.kehan.connect.web.rest.errors.BadRequestAlertException;
import online.kehan.connect.service.dto.ConnectStateDTO;

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
 * REST controller for managing {@link online.kehan.connect.domain.ConnectState}.
 */
@RestController
@RequestMapping("/api")
public class ConnectStateResource {

    private final Logger log = LoggerFactory.getLogger(ConnectStateResource.class);

    private static final String ENTITY_NAME = "connectServiceConnectState";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ConnectStateService connectStateService;

    public ConnectStateResource(ConnectStateService connectStateService) {
        this.connectStateService = connectStateService;
    }

    /**
     * {@code POST  /connect-states} : Create a new connectState.
     *
     * @param connectStateDTO the connectStateDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new connectStateDTO, or with status {@code 400 (Bad Request)} if the connectState has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/connect-states")
    public ResponseEntity<ConnectStateDTO> createConnectState(@Valid @RequestBody ConnectStateDTO connectStateDTO) throws URISyntaxException {
        log.debug("REST request to save ConnectState : {}", connectStateDTO);
        if (connectStateDTO.getId() != null) {
            throw new BadRequestAlertException("A new connectState cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ConnectStateDTO result = connectStateService.save(connectStateDTO);
        return ResponseEntity.created(new URI("/api/connect-states/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /connect-states} : Updates an existing connectState.
     *
     * @param connectStateDTO the connectStateDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated connectStateDTO,
     * or with status {@code 400 (Bad Request)} if the connectStateDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the connectStateDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/connect-states")
    public ResponseEntity<ConnectStateDTO> updateConnectState(@Valid @RequestBody ConnectStateDTO connectStateDTO) throws URISyntaxException {
        log.debug("REST request to update ConnectState : {}", connectStateDTO);
        if (connectStateDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ConnectStateDTO result = connectStateService.save(connectStateDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, connectStateDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /connect-states} : get all the connectStates.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of connectStates in body.
     */
    @GetMapping("/connect-states")
    public ResponseEntity<List<ConnectStateDTO>> getAllConnectStates(Pageable pageable) {
        log.debug("REST request to get a page of ConnectStates");
        Page<ConnectStateDTO> page = connectStateService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /connect-states/:id} : get the "id" connectState.
     *
     * @param id the id of the connectStateDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the connectStateDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/connect-states/{id}")
    public ResponseEntity<ConnectStateDTO> getConnectState(@PathVariable Long id) {
        log.debug("REST request to get ConnectState : {}", id);
        Optional<ConnectStateDTO> connectStateDTO = connectStateService.findOne(id);
        return ResponseUtil.wrapOrNotFound(connectStateDTO);
    }

    /**
     * {@code DELETE  /connect-states/:id} : delete the "id" connectState.
     *
     * @param id the id of the connectStateDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/connect-states/{id}")
    public ResponseEntity<Void> deleteConnectState(@PathVariable Long id) {
        log.debug("REST request to delete ConnectState : {}", id);

        connectStateService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
