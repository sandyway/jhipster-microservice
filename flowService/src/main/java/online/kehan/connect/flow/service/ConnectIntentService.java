package online.kehan.connect.flow.service;

import online.kehan.connect.flow.domain.ConnectIntent;
import online.kehan.connect.flow.repository.ConnectIntentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ConnectIntent}.
 */
@Service
@Transactional
public class ConnectIntentService {

    private final Logger log = LoggerFactory.getLogger(ConnectIntentService.class);

    private final ConnectIntentRepository connectIntentRepository;

    public ConnectIntentService(ConnectIntentRepository connectIntentRepository) {
        this.connectIntentRepository = connectIntentRepository;
    }

    /**
     * Save a connectIntent.
     *
     * @param connectIntent the entity to save.
     * @return the persisted entity.
     */
    public ConnectIntent save(ConnectIntent connectIntent) {
        log.debug("Request to save ConnectIntent : {}", connectIntent);
        return connectIntentRepository.save(connectIntent);
    }

    /**
     * Get all the connectIntents.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ConnectIntent> findAll(Pageable pageable) {
        log.debug("Request to get all ConnectIntents");
        return connectIntentRepository.findAll(pageable);
    }


    /**
     * Get one connectIntent by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ConnectIntent> findOne(Long id) {
        log.debug("Request to get ConnectIntent : {}", id);
        return connectIntentRepository.findById(id);
    }

    /**
     * Delete the connectIntent by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ConnectIntent : {}", id);

        connectIntentRepository.deleteById(id);
    }
}
