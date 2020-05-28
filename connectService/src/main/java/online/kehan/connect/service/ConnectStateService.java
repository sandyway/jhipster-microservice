package online.kehan.connect.service;

import online.kehan.connect.domain.ConnectState;
import online.kehan.connect.repository.ConnectStateRepository;
import online.kehan.connect.service.dto.ConnectStateDTO;
import online.kehan.connect.service.mapper.ConnectStateMapper;
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

    private final ConnectStateMapper connectStateMapper;

    public ConnectStateService(ConnectStateRepository connectStateRepository, ConnectStateMapper connectStateMapper) {
        this.connectStateRepository = connectStateRepository;
        this.connectStateMapper = connectStateMapper;
    }

    /**
     * Save a connectState.
     *
     * @param connectStateDTO the entity to save.
     * @return the persisted entity.
     */
    public ConnectStateDTO save(ConnectStateDTO connectStateDTO) {
        log.debug("Request to save ConnectState : {}", connectStateDTO);
        ConnectState connectState = connectStateMapper.toEntity(connectStateDTO);
        connectState = connectStateRepository.save(connectState);
        return connectStateMapper.toDto(connectState);
    }

    /**
     * Get all the connectStates.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ConnectStateDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ConnectStates");
        return connectStateRepository.findAll(pageable)
            .map(connectStateMapper::toDto);
    }


    /**
     * Get one connectState by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ConnectStateDTO> findOne(Long id) {
        log.debug("Request to get ConnectState : {}", id);
        return connectStateRepository.findById(id)
            .map(connectStateMapper::toDto);
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
