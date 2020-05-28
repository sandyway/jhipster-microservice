package online.kehan.connect.service;

import online.kehan.connect.domain.ConnectIntent;
import online.kehan.connect.repository.ConnectIntentRepository;
import online.kehan.connect.service.dto.ConnectIntentDTO;
import online.kehan.connect.service.mapper.ConnectIntentMapper;
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

    private final ConnectIntentMapper connectIntentMapper;

    public ConnectIntentService(ConnectIntentRepository connectIntentRepository, ConnectIntentMapper connectIntentMapper) {
        this.connectIntentRepository = connectIntentRepository;
        this.connectIntentMapper = connectIntentMapper;
    }

    /**
     * Save a connectIntent.
     *
     * @param connectIntentDTO the entity to save.
     * @return the persisted entity.
     */
    public ConnectIntentDTO save(ConnectIntentDTO connectIntentDTO) {
        log.debug("Request to save ConnectIntent : {}", connectIntentDTO);
        ConnectIntent connectIntent = connectIntentMapper.toEntity(connectIntentDTO);
        connectIntent = connectIntentRepository.save(connectIntent);
        return connectIntentMapper.toDto(connectIntent);
    }

    /**
     * Get all the connectIntents.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ConnectIntentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ConnectIntents");
        return connectIntentRepository.findAll(pageable)
            .map(connectIntentMapper::toDto);
    }


    /**
     * Get one connectIntent by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ConnectIntentDTO> findOne(Long id) {
        log.debug("Request to get ConnectIntent : {}", id);
        return connectIntentRepository.findById(id)
            .map(connectIntentMapper::toDto);
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
