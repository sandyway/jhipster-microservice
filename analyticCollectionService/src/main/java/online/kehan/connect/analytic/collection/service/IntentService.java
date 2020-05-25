package online.kehan.connect.analytic.collection.service;

import online.kehan.connect.analytic.collection.domain.Intent;
import online.kehan.connect.analytic.collection.repository.IntentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Intent}.
 */
@Service
@Transactional
public class IntentService {

    private final Logger log = LoggerFactory.getLogger(IntentService.class);

    private final IntentRepository intentRepository;

    public IntentService(IntentRepository intentRepository) {
        this.intentRepository = intentRepository;
    }

    /**
     * Save a intent.
     *
     * @param intent the entity to save.
     * @return the persisted entity.
     */
    public Intent save(Intent intent) {
        log.debug("Request to save Intent : {}", intent);
        return intentRepository.save(intent);
    }

    /**
     * Get all the intents.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Intent> findAll(Pageable pageable) {
        log.debug("Request to get all Intents");
        return intentRepository.findAll(pageable);
    }


    /**
     * Get one intent by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Intent> findOne(Long id) {
        log.debug("Request to get Intent : {}", id);
        return intentRepository.findById(id);
    }

    /**
     * Delete the intent by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Intent : {}", id);

        intentRepository.deleteById(id);
    }
}
