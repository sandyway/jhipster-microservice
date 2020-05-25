package online.kehan.connect.analytic.collection.web.rest;

import online.kehan.connect.analytic.collection.domain.CampaignFlow;
import online.kehan.connect.analytic.collection.service.CampaignFlowService;
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
 * REST controller for managing {@link online.kehan.connect.analytic.collection.domain.CampaignFlow}.
 */
@RestController
@RequestMapping("/api")
public class CampaignFlowResource {

    private final Logger log = LoggerFactory.getLogger(CampaignFlowResource.class);

    private static final String ENTITY_NAME = "analyticCollectionServiceCampaignFlow";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CampaignFlowService campaignFlowService;

    public CampaignFlowResource(CampaignFlowService campaignFlowService) {
        this.campaignFlowService = campaignFlowService;
    }

    /**
     * {@code POST  /campaign-flows} : Create a new campaignFlow.
     *
     * @param campaignFlow the campaignFlow to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new campaignFlow, or with status {@code 400 (Bad Request)} if the campaignFlow has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/campaign-flows")
    public ResponseEntity<CampaignFlow> createCampaignFlow(@Valid @RequestBody CampaignFlow campaignFlow) throws URISyntaxException {
        log.debug("REST request to save CampaignFlow : {}", campaignFlow);
        if (campaignFlow.getId() != null) {
            throw new BadRequestAlertException("A new campaignFlow cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CampaignFlow result = campaignFlowService.save(campaignFlow);
        return ResponseEntity.created(new URI("/api/campaign-flows/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /campaign-flows} : Updates an existing campaignFlow.
     *
     * @param campaignFlow the campaignFlow to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated campaignFlow,
     * or with status {@code 400 (Bad Request)} if the campaignFlow is not valid,
     * or with status {@code 500 (Internal Server Error)} if the campaignFlow couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/campaign-flows")
    public ResponseEntity<CampaignFlow> updateCampaignFlow(@Valid @RequestBody CampaignFlow campaignFlow) throws URISyntaxException {
        log.debug("REST request to update CampaignFlow : {}", campaignFlow);
        if (campaignFlow.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CampaignFlow result = campaignFlowService.save(campaignFlow);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, campaignFlow.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /campaign-flows} : get all the campaignFlows.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of campaignFlows in body.
     */
    @GetMapping("/campaign-flows")
    public ResponseEntity<List<CampaignFlow>> getAllCampaignFlows(Pageable pageable) {
        log.debug("REST request to get a page of CampaignFlows");
        Page<CampaignFlow> page = campaignFlowService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /campaign-flows/:id} : get the "id" campaignFlow.
     *
     * @param id the id of the campaignFlow to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the campaignFlow, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/campaign-flows/{id}")
    public ResponseEntity<CampaignFlow> getCampaignFlow(@PathVariable Long id) {
        log.debug("REST request to get CampaignFlow : {}", id);
        Optional<CampaignFlow> campaignFlow = campaignFlowService.findOne(id);
        return ResponseUtil.wrapOrNotFound(campaignFlow);
    }

    /**
     * {@code DELETE  /campaign-flows/:id} : delete the "id" campaignFlow.
     *
     * @param id the id of the campaignFlow to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/campaign-flows/{id}")
    public ResponseEntity<Void> deleteCampaignFlow(@PathVariable Long id) {
        log.debug("REST request to delete CampaignFlow : {}", id);

        campaignFlowService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
