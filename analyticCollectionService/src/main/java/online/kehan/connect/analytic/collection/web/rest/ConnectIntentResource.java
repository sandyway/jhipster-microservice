package online.kehan.connect.analytic.collection.web.rest;

import online.kehan.connect.analytic.collection.domain.ConnectIntent;
import online.kehan.connect.analytic.collection.service.ConnectIntentService;
import online.kehan.connect.analytic.collection.web.rest.errors.BadRequestAlertException;

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
 * REST controller for managing {@link online.kehan.connect.analytic.collection.domain.ConnectIntent}.
 */
@RestController
@RequestMapping("/api")
public class ConnectIntentResource {

    private final Logger log = LoggerFactory.getLogger(ConnectIntentResource.class);

    private static final String ENTITY_NAME = "analyticCollectionServiceConnectIntent";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ConnectIntentService connectIntentService;

    public ConnectIntentResource(ConnectIntentService connectIntentService) {
        this.connectIntentService = connectIntentService;
    }

    /**
     * {@code POST  /connect-intents} : Create a new connectIntent.
     *
     * @param connectIntent the connectIntent to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new connectIntent, or with status {@code 400 (Bad Request)} if the connectIntent has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/connect-intents")
    public ResponseEntity<ConnectIntent> createConnectIntent(@Valid @RequestBody ConnectIntent connectIntent) throws URISyntaxException {
        log.debug("REST request to save ConnectIntent : {}", connectIntent);
        if (connectIntent.getId() != null) {
            throw new BadRequestAlertException("A new connectIntent cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ConnectIntent result = connectIntentService.save(connectIntent);
        return ResponseEntity.created(new URI("/api/connect-intents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /connect-intents} : Updates an existing connectIntent.
     *
     * @param connectIntent the connectIntent to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated connectIntent,
     * or with status {@code 400 (Bad Request)} if the connectIntent is not valid,
     * or with status {@code 500 (Internal Server Error)} if the connectIntent couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/connect-intents")
    public ResponseEntity<ConnectIntent> updateConnectIntent(@Valid @RequestBody ConnectIntent connectIntent) throws URISyntaxException {
        log.debug("REST request to update ConnectIntent : {}", connectIntent);
        if (connectIntent.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ConnectIntent result = connectIntentService.save(connectIntent);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, connectIntent.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /connect-intents} : get all the connectIntents.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of connectIntents in body.
     */
    @GetMapping("/connect-intents")
    public ResponseEntity<List<ConnectIntent>> getAllConnectIntents(Pageable pageable) {
        log.debug("REST request to get a page of ConnectIntents");
        Page<ConnectIntent> page = connectIntentService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /connect-intents/:id} : get the "id" connectIntent.
     *
     * @param id the id of the connectIntent to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the connectIntent, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/connect-intents/{id}")
    public ResponseEntity<ConnectIntent> getConnectIntent(@PathVariable Long id) {
        log.debug("REST request to get ConnectIntent : {}", id);
        Optional<ConnectIntent> connectIntent = connectIntentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(connectIntent);
    }

    /**
     * {@code DELETE  /connect-intents/:id} : delete the "id" connectIntent.
     *
     * @param id the id of the connectIntent to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/connect-intents/{id}")
    public ResponseEntity<Void> deleteConnectIntent(@PathVariable Long id) {
        log.debug("REST request to delete ConnectIntent : {}", id);

        connectIntentService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
