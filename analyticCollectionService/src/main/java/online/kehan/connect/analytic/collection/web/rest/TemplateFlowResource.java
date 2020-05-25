package online.kehan.connect.analytic.collection.web.rest;

import online.kehan.connect.analytic.collection.domain.TemplateFlow;
import online.kehan.connect.analytic.collection.service.TemplateFlowService;
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
 * REST controller for managing {@link online.kehan.connect.analytic.collection.domain.TemplateFlow}.
 */
@RestController
@RequestMapping("/api")
public class TemplateFlowResource {

    private final Logger log = LoggerFactory.getLogger(TemplateFlowResource.class);

    private static final String ENTITY_NAME = "analyticCollectionServiceTemplateFlow";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TemplateFlowService templateFlowService;

    public TemplateFlowResource(TemplateFlowService templateFlowService) {
        this.templateFlowService = templateFlowService;
    }

    /**
     * {@code POST  /template-flows} : Create a new templateFlow.
     *
     * @param templateFlow the templateFlow to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new templateFlow, or with status {@code 400 (Bad Request)} if the templateFlow has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/template-flows")
    public ResponseEntity<TemplateFlow> createTemplateFlow(@Valid @RequestBody TemplateFlow templateFlow) throws URISyntaxException {
        log.debug("REST request to save TemplateFlow : {}", templateFlow);
        if (templateFlow.getId() != null) {
            throw new BadRequestAlertException("A new templateFlow cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TemplateFlow result = templateFlowService.save(templateFlow);
        return ResponseEntity.created(new URI("/api/template-flows/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /template-flows} : Updates an existing templateFlow.
     *
     * @param templateFlow the templateFlow to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated templateFlow,
     * or with status {@code 400 (Bad Request)} if the templateFlow is not valid,
     * or with status {@code 500 (Internal Server Error)} if the templateFlow couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/template-flows")
    public ResponseEntity<TemplateFlow> updateTemplateFlow(@Valid @RequestBody TemplateFlow templateFlow) throws URISyntaxException {
        log.debug("REST request to update TemplateFlow : {}", templateFlow);
        if (templateFlow.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TemplateFlow result = templateFlowService.save(templateFlow);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, templateFlow.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /template-flows} : get all the templateFlows.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of templateFlows in body.
     */
    @GetMapping("/template-flows")
    public ResponseEntity<List<TemplateFlow>> getAllTemplateFlows(Pageable pageable) {
        log.debug("REST request to get a page of TemplateFlows");
        Page<TemplateFlow> page = templateFlowService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /template-flows/:id} : get the "id" templateFlow.
     *
     * @param id the id of the templateFlow to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the templateFlow, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/template-flows/{id}")
    public ResponseEntity<TemplateFlow> getTemplateFlow(@PathVariable Long id) {
        log.debug("REST request to get TemplateFlow : {}", id);
        Optional<TemplateFlow> templateFlow = templateFlowService.findOne(id);
        return ResponseUtil.wrapOrNotFound(templateFlow);
    }

    /**
     * {@code DELETE  /template-flows/:id} : delete the "id" templateFlow.
     *
     * @param id the id of the templateFlow to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/template-flows/{id}")
    public ResponseEntity<Void> deleteTemplateFlow(@PathVariable Long id) {
        log.debug("REST request to delete TemplateFlow : {}", id);

        templateFlowService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
