package online.kehan.connect.service;

import online.kehan.connect.domain.ConnectEvent;
import online.kehan.connect.repository.ConnectEventRepository;
import online.kehan.connect.service.dto.ConnectEventDTO;
import online.kehan.connect.service.mapper.ConnectEventMapper;
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

    private final ConnectEventMapper connectEventMapper;

    public ConnectEventService(ConnectEventRepository connectEventRepository, ConnectEventMapper connectEventMapper) {
        this.connectEventRepository = connectEventRepository;
        this.connectEventMapper = connectEventMapper;
    }

    /**
     * Save a connectEvent.
     *
     * @param connectEventDTO the entity to save.
     * @return the persisted entity.
     */
    public ConnectEventDTO save(ConnectEventDTO connectEventDTO) {
        log.debug("Request to save ConnectEvent : {}", connectEventDTO);
        ConnectEvent connectEvent = connectEventMapper.toEntity(connectEventDTO);
        connectEvent = connectEventRepository.save(connectEvent);
        return connectEventMapper.toDto(connectEvent);
    }

    /**
     * Get all the connectEvents.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ConnectEventDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ConnectEvents");
        return connectEventRepository.findAll(pageable)
            .map(connectEventMapper::toDto);
    }


    /**
     * Get one connectEvent by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ConnectEventDTO> findOne(Long id) {
        log.debug("Request to get ConnectEvent : {}", id);
        return connectEventRepository.findById(id)
            .map(connectEventMapper::toDto);
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
