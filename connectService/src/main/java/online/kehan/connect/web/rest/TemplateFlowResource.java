package online.kehan.connect.web.rest;

import online.kehan.connect.service.TemplateFlowService;
import online.kehan.connect.web.rest.errors.BadRequestAlertException;
import online.kehan.connect.service.dto.TemplateFlowDTO;

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
 * REST controller for managing {@link online.kehan.connect.domain.TemplateFlow}.
 */
@RestController
@RequestMapping("/api")
public class TemplateFlowResource {

    private final Logger log = LoggerFactory.getLogger(TemplateFlowResource.class);

    private static final String ENTITY_NAME = "connectServiceTemplateFlow";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TemplateFlowService templateFlowService;

    public TemplateFlowResource(TemplateFlowService templateFlowService) {
        this.templateFlowService = templateFlowService;
    }

    /**
     * {@code POST  /template-flows} : Create a new templateFlow.
     *
     * @param templateFlowDTO the templateFlowDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new templateFlowDTO, or with status {@code 400 (Bad Request)} if the templateFlow has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/template-flows")
    public ResponseEntity<TemplateFlowDTO> createTemplateFlow(@Valid @RequestBody TemplateFlowDTO templateFlowDTO) throws URISyntaxException {
        log.debug("REST request to save TemplateFlow : {}", templateFlowDTO);
        if (templateFlowDTO.getId() != null) {
            throw new BadRequestAlertException("A new templateFlow cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TemplateFlowDTO result = templateFlowService.save(templateFlowDTO);
        return ResponseEntity.created(new URI("/api/template-flows/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /template-flows} : Updates an existing templateFlow.
     *
     * @param templateFlowDTO the templateFlowDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated templateFlowDTO,
     * or with status {@code 400 (Bad Request)} if the templateFlowDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the templateFlowDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/template-flows")
    public ResponseEntity<TemplateFlowDTO> updateTemplateFlow(@Valid @RequestBody TemplateFlowDTO templateFlowDTO) throws URISyntaxException {
        log.debug("REST request to update TemplateFlow : {}", templateFlowDTO);
        if (templateFlowDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TemplateFlowDTO result = templateFlowService.save(templateFlowDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, templateFlowDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /template-flows} : get all the templateFlows.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of templateFlows in body.
     */
    @GetMapping("/template-flows")
    public ResponseEntity<List<TemplateFlowDTO>> getAllTemplateFlows(Pageable pageable) {
        log.debug("REST request to get a page of TemplateFlows");
        Page<TemplateFlowDTO> page = templateFlowService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /template-flows/:id} : get the "id" templateFlow.
     *
     * @param id the id of the templateFlowDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the templateFlowDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/template-flows/{id}")
    public ResponseEntity<TemplateFlowDTO> getTemplateFlow(@PathVariable Long id) {
        log.debug("REST request to get TemplateFlow : {}", id);
        Optional<TemplateFlowDTO> templateFlowDTO = templateFlowService.findOne(id);
        return ResponseUtil.wrapOrNotFound(templateFlowDTO);
    }

    /**
     * {@code DELETE  /template-flows/:id} : delete the "id" templateFlow.
     *
     * @param id the id of the templateFlowDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/template-flows/{id}")
    public ResponseEntity<Void> deleteTemplateFlow(@PathVariable Long id) {
        log.debug("REST request to delete TemplateFlow : {}", id);

        templateFlowService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
