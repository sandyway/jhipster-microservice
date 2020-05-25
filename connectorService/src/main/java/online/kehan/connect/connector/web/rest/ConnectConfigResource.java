package online.kehan.connect.connector.web.rest;

import online.kehan.connect.connector.domain.ConnectConfig;
import online.kehan.connect.connector.service.ConnectConfigService;
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
 * REST controller for managing {@link online.kehan.connect.connector.domain.ConnectConfig}.
 */
@RestController
@RequestMapping("/api")
public class ConnectConfigResource {

    private final Logger log = LoggerFactory.getLogger(ConnectConfigResource.class);

    private static final String ENTITY_NAME = "connectorServiceConnectConfig";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ConnectConfigService connectConfigService;

    public ConnectConfigResource(ConnectConfigService connectConfigService) {
        this.connectConfigService = connectConfigService;
    }

    /**
     * {@code POST  /connect-configs} : Create a new connectConfig.
     *
     * @param connectConfig the connectConfig to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new connectConfig, or with status {@code 400 (Bad Request)} if the connectConfig has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/connect-configs")
    public ResponseEntity<ConnectConfig> createConnectConfig(@Valid @RequestBody ConnectConfig connectConfig) throws URISyntaxException {
        log.debug("REST request to save ConnectConfig : {}", connectConfig);
        if (connectConfig.getId() != null) {
            throw new BadRequestAlertException("A new connectConfig cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ConnectConfig result = connectConfigService.save(connectConfig);
        return ResponseEntity.created(new URI("/api/connect-configs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /connect-configs} : Updates an existing connectConfig.
     *
     * @param connectConfig the connectConfig to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated connectConfig,
     * or with status {@code 400 (Bad Request)} if the connectConfig is not valid,
     * or with status {@code 500 (Internal Server Error)} if the connectConfig couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/connect-configs")
    public ResponseEntity<ConnectConfig> updateConnectConfig(@Valid @RequestBody ConnectConfig connectConfig) throws URISyntaxException {
        log.debug("REST request to update ConnectConfig : {}", connectConfig);
        if (connectConfig.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ConnectConfig result = connectConfigService.save(connectConfig);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, connectConfig.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /connect-configs} : get all the connectConfigs.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of connectConfigs in body.
     */
    @GetMapping("/connect-configs")
    public ResponseEntity<List<ConnectConfig>> getAllConnectConfigs(Pageable pageable) {
        log.debug("REST request to get a page of ConnectConfigs");
        Page<ConnectConfig> page = connectConfigService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /connect-configs/:id} : get the "id" connectConfig.
     *
     * @param id the id of the connectConfig to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the connectConfig, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/connect-configs/{id}")
    public ResponseEntity<ConnectConfig> getConnectConfig(@PathVariable Long id) {
        log.debug("REST request to get ConnectConfig : {}", id);
        Optional<ConnectConfig> connectConfig = connectConfigService.findOne(id);
        return ResponseUtil.wrapOrNotFound(connectConfig);
    }

    /**
     * {@code DELETE  /connect-configs/:id} : delete the "id" connectConfig.
     *
     * @param id the id of the connectConfig to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/connect-configs/{id}")
    public ResponseEntity<Void> deleteConnectConfig(@PathVariable Long id) {
        log.debug("REST request to delete ConnectConfig : {}", id);

        connectConfigService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
