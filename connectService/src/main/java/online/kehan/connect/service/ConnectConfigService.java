package online.kehan.connect.service;

import online.kehan.connect.domain.ConnectConfig;
import online.kehan.connect.repository.ConnectConfigRepository;
import online.kehan.connect.service.dto.ConnectConfigDTO;
import online.kehan.connect.service.mapper.ConnectConfigMapper;
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

    private final ConnectConfigMapper connectConfigMapper;

    public ConnectConfigService(ConnectConfigRepository connectConfigRepository, ConnectConfigMapper connectConfigMapper) {
        this.connectConfigRepository = connectConfigRepository;
        this.connectConfigMapper = connectConfigMapper;
    }

    /**
     * Save a connectConfig.
     *
     * @param connectConfigDTO the entity to save.
     * @return the persisted entity.
     */
    public ConnectConfigDTO save(ConnectConfigDTO connectConfigDTO) {
        log.debug("Request to save ConnectConfig : {}", connectConfigDTO);
        ConnectConfig connectConfig = connectConfigMapper.toEntity(connectConfigDTO);
        connectConfig = connectConfigRepository.save(connectConfig);
        return connectConfigMapper.toDto(connectConfig);
    }

    /**
     * Get all the connectConfigs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ConnectConfigDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ConnectConfigs");
        return connectConfigRepository.findAll(pageable)
            .map(connectConfigMapper::toDto);
    }


    /**
     * Get one connectConfig by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ConnectConfigDTO> findOne(Long id) {
        log.debug("Request to get ConnectConfig : {}", id);
        return connectConfigRepository.findById(id)
            .map(connectConfigMapper::toDto);
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
