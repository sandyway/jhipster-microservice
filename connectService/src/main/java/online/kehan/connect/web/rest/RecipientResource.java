package online.kehan.connect.web.rest;

import online.kehan.connect.service.RecipientService;
import online.kehan.connect.web.rest.errors.BadRequestAlertException;
import online.kehan.connect.service.dto.RecipientDTO;

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
 * REST controller for managing {@link online.kehan.connect.domain.Recipient}.
 */
@RestController
@RequestMapping("/api")
public class RecipientResource {

    private final Logger log = LoggerFactory.getLogger(RecipientResource.class);

    private static final String ENTITY_NAME = "connectServiceRecipient";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RecipientService recipientService;

    public RecipientResource(RecipientService recipientService) {
        this.recipientService = recipientService;
    }

    /**
     * {@code POST  /recipients} : Create a new recipient.
     *
     * @param recipientDTO the recipientDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new recipientDTO, or with status {@code 400 (Bad Request)} if the recipient has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/recipients")
    public ResponseEntity<RecipientDTO> createRecipient(@Valid @RequestBody RecipientDTO recipientDTO) throws URISyntaxException {
        log.debug("REST request to save Recipient : {}", recipientDTO);
        if (recipientDTO.getId() != null) {
            throw new BadRequestAlertException("A new recipient cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RecipientDTO result = recipientService.save(recipientDTO);
        return ResponseEntity.created(new URI("/api/recipients/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /recipients} : Updates an existing recipient.
     *
     * @param recipientDTO the recipientDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated recipientDTO,
     * or with status {@code 400 (Bad Request)} if the recipientDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the recipientDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/recipients")
    public ResponseEntity<RecipientDTO> updateRecipient(@Valid @RequestBody RecipientDTO recipientDTO) throws URISyntaxException {
        log.debug("REST request to update Recipient : {}", recipientDTO);
        if (recipientDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RecipientDTO result = recipientService.save(recipientDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, recipientDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /recipients} : get all the recipients.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of recipients in body.
     */
    @GetMapping("/recipients")
    public ResponseEntity<List<RecipientDTO>> getAllRecipients(Pageable pageable) {
        log.debug("REST request to get a page of Recipients");
        Page<RecipientDTO> page = recipientService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /recipients/:id} : get the "id" recipient.
     *
     * @param id the id of the recipientDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the recipientDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/recipients/{id}")
    public ResponseEntity<RecipientDTO> getRecipient(@PathVariable Long id) {
        log.debug("REST request to get Recipient : {}", id);
        Optional<RecipientDTO> recipientDTO = recipientService.findOne(id);
        return ResponseUtil.wrapOrNotFound(recipientDTO);
    }

    /**
     * {@code DELETE  /recipients/:id} : delete the "id" recipient.
     *
     * @param id the id of the recipientDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/recipients/{id}")
    public ResponseEntity<Void> deleteRecipient(@PathVariable Long id) {
        log.debug("REST request to delete Recipient : {}", id);

        recipientService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
