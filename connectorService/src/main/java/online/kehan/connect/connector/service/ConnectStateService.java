package online.kehan.connect.connector.service;

import online.kehan.connect.connector.domain.ConnectState;
import online.kehan.connect.connector.repository.ConnectStateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ConnectState}.
 */
@Service
@Transactional
public class ConnectStateService {

    private final Logger log = LoggerFactory.getLogger(ConnectStateService.class);

    private final ConnectStateRepository connectStateRepository;

    public ConnectStateService(ConnectStateRepository connectStateRepository) {
        this.connectStateRepository = connectStateRepository;
    }

    /**
     * Save a connectState.
     *
     * @param connectState the entity to save.
     * @return the persisted entity.
     */
    public ConnectState save(ConnectState connectState) {
        log.debug("Request to save ConnectState : {}", connectState);
        return connectStateRepository.save(connectState);
    }

    /**
     * Get all the connectStates.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ConnectState> findAll(Pageable pageable) {
        log.debug("Request to get all ConnectStates");
        return connectStateRepository.findAll(pageable);
    }


    /**
     * Get one connectState by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ConnectState> findOne(Long id) {
        log.debug("Request to get ConnectState : {}", id);
        return connectStateRepository.findById(id);
    }

    /**
     * Delete the connectState by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ConnectState : {}", id);

        connectStateRepository.deleteById(id);
    }
}
