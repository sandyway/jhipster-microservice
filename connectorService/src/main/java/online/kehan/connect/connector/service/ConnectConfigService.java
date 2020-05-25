package online.kehan.connect.connector.service;

import online.kehan.connect.connector.domain.ConnectConfig;
import online.kehan.connect.connector.repository.ConnectConfigRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ConnectConfig}.
 */
@Service
@Transactional
public class ConnectConfigService {

    private final Logger log = LoggerFactory.getLogger(ConnectConfigService.class);

    private final ConnectConfigRepository connectConfigRepository;

    public ConnectConfigService(ConnectConfigRepository connectConfigRepository) {
        this.connectConfigRepository = connectConfigRepository;
    }

    /**
     * Save a connectConfig.
     *
     * @param connectConfig the entity to save.
     * @return the persisted entity.
     */
    public ConnectConfig save(ConnectConfig connectConfig) {
        log.debug("Request to save ConnectConfig : {}", connectConfig);
        return connectConfigRepository.save(connectConfig);
    }

    /**
     * Get all the connectConfigs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ConnectConfig> findAll(Pageable pageable) {
        log.debug("Request to get all ConnectConfigs");
        return connectConfigRepository.findAll(pageable);
    }


    /**
     * Get one connectConfig by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ConnectConfig> findOne(Long id) {
        log.debug("Request to get ConnectConfig : {}", id);
        return connectConfigRepository.findById(id);
    }

    /**
     * Delete the connectConfig by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ConnectConfig : {}", id);

        connectConfigRepository.deleteById(id);
    }
}
