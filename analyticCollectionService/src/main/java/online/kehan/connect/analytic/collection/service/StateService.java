package online.kehan.connect.analytic.collection.service;

import online.kehan.connect.analytic.collection.domain.State;
import online.kehan.connect.analytic.collection.repository.StateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link State}.
 */
@Service
@Transactional
public class StateService {

    private final Logger log = LoggerFactory.getLogger(StateService.class);

    private final StateRepository stateRepository;

    public StateService(StateRepository stateRepository) {
        this.stateRepository = stateRepository;
    }

    /**
     * Save a state.
     *
     * @param state the entity to save.
     * @return the persisted entity.
     */
    public State save(State state) {
        log.debug("Request to save State : {}", state);
        return stateRepository.save(state);
    }

    /**
     * Get all the states.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<State> findAll(Pageable pageable) {
        log.debug("Request to get all States");
        return stateRepository.findAll(pageable);
    }


    /**
     * Get one state by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<State> findOne(Long id) {
        log.debug("Request to get State : {}", id);
        return stateRepository.findById(id);
    }

    /**
     * Delete the state by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete State : {}", id);

        stateRepository.deleteById(id);
    }
}
