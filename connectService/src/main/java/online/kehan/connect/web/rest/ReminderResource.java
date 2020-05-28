package online.kehan.connect.web.rest;

import online.kehan.connect.service.ReminderService;
import online.kehan.connect.web.rest.errors.BadRequestAlertException;
import online.kehan.connect.service.dto.ReminderDTO;

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
 * REST controller for managing {@link online.kehan.connect.domain.Reminder}.
 */
@RestController
@RequestMapping("/api")
public class ReminderResource {

    private final Logger log = LoggerFactory.getLogger(ReminderResource.class);

    private static final String ENTITY_NAME = "connectServiceReminder";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ReminderService reminderService;

    public ReminderResource(ReminderService reminderService) {
        this.reminderService = reminderService;
    }

    /**
     * {@code POST  /reminders} : Create a new reminder.
     *
     * @param reminderDTO the reminderDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new reminderDTO, or with status {@code 400 (Bad Request)} if the reminder has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/reminders")
    public ResponseEntity<ReminderDTO> createReminder(@Valid @RequestBody ReminderDTO reminderDTO) throws URISyntaxException {
        log.debug("REST request to save Reminder : {}", reminderDTO);
        if (reminderDTO.getId() != null) {
            throw new BadRequestAlertException("A new reminder cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ReminderDTO result = reminderService.save(reminderDTO);
        return ResponseEntity.created(new URI("/api/reminders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /reminders} : Updates an existing reminder.
     *
     * @param reminderDTO the reminderDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated reminderDTO,
     * or with status {@code 400 (Bad Request)} if the reminderDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the reminderDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/reminders")
    public ResponseEntity<ReminderDTO> updateReminder(@Valid @RequestBody ReminderDTO reminderDTO) throws URISyntaxException {
        log.debug("REST request to update Reminder : {}", reminderDTO);
        if (reminderDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ReminderDTO result = reminderService.save(reminderDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, reminderDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /reminders} : get all the reminders.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of reminders in body.
     */
    @GetMapping("/reminders")
    public ResponseEntity<List<ReminderDTO>> getAllReminders(Pageable pageable) {
        log.debug("REST request to get a page of Reminders");
        Page<ReminderDTO> page = reminderService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /reminders/:id} : get the "id" reminder.
     *
     * @param id the id of the reminderDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the reminderDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/reminders/{id}")
    public ResponseEntity<ReminderDTO> getReminder(@PathVariable Long id) {
        log.debug("REST request to get Reminder : {}", id);
        Optional<ReminderDTO> reminderDTO = reminderService.findOne(id);
        return ResponseUtil.wrapOrNotFound(reminderDTO);
    }

    /**
     * {@code DELETE  /reminders/:id} : delete the "id" reminder.
     *
     * @param id the id of the reminderDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/reminders/{id}")
    public ResponseEntity<Void> deleteReminder(@PathVariable Long id) {
        log.debug("REST request to delete Reminder : {}", id);

        reminderService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
