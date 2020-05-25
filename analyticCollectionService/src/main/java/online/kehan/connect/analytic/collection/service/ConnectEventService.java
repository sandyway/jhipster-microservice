package online.kehan.connect.analytic.collection.service;

import online.kehan.connect.analytic.collection.domain.ConnectEvent;
import online.kehan.connect.analytic.collection.repository.ConnectEventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ConnectEvent}.
 */
@Service
@Transactional
public class ConnectEventService {

    private final Logger log = LoggerFactory.getLogger(ConnectEventService.class);

    private final ConnectEventRepository connectEventRepository;

    public ConnectEventService(ConnectEventRepository connectEventRepository) {
        this.connectEventRepository = connectEventRepository;
    }

    /**
     * Save a connectEvent.
     *
     * @param connectEvent the entity to save.
     * @return the persisted entity.
     */
    public ConnectEvent save(ConnectEvent connectEvent) {
        log.debug("Request to save ConnectEvent : {}", connectEvent);
        return connectEventRepository.save(connectEvent);
    }

    /**
     * Get all the connectEvents.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ConnectEvent> findAll(Pageable pageable) {
        log.debug("Request to get all ConnectEvents");
        return connectEventRepository.findAll(pageable);
    }


    /**
     * Get one connectEvent by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ConnectEvent> findOne(Long id) {
        log.debug("Request to get ConnectEvent : {}", id);
        return connectEventRepository.findById(id);
    }

    /**
     * Delete the connectEvent by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ConnectEvent : {}", id);

        connectEventRepository.deleteById(id);
    }
}
