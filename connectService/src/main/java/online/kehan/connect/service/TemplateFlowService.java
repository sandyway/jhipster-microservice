package online.kehan.connect.service;

import online.kehan.connect.domain.TemplateFlow;
import online.kehan.connect.repository.TemplateFlowRepository;
import online.kehan.connect.service.dto.TemplateFlowDTO;
import online.kehan.connect.service.mapper.TemplateFlowMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TemplateFlow}.
 */
@Service
@Transactional
public class TemplateFlowService {

    private final Logger log = LoggerFactory.getLogger(TemplateFlowService.class);

    private final TemplateFlowRepository templateFlowRepository;

    private final TemplateFlowMapper templateFlowMapper;

    public TemplateFlowService(TemplateFlowRepository templateFlowRepository, TemplateFlowMapper templateFlowMapper) {
        this.templateFlowRepository = templateFlowRepository;
        this.templateFlowMapper = templateFlowMapper;
    }

    /**
     * Save a templateFlow.
     *
     * @param templateFlowDTO the entity to save.
     * @return the persisted entity.
     */
    public TemplateFlowDTO save(TemplateFlowDTO templateFlowDTO) {
        log.debug("Request to save TemplateFlow : {}", templateFlowDTO);
        TemplateFlow templateFlow = templateFlowMapper.toEntity(templateFlowDTO);
        templateFlow = templateFlowRepository.save(templateFlow);
        return templateFlowMapper.toDto(templateFlow);
    }

    /**
     * Get all the templateFlows.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TemplateFlowDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TemplateFlows");
        return templateFlowRepository.findAll(pageable)
            .map(templateFlowMapper::toDto);
    }


    /**
     * Get one templateFlow by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TemplateFlowDTO> findOne(Long id) {
        log.debug("Request to get TemplateFlow : {}", id);
        return templateFlowRepository.findById(id)
            .map(templateFlowMapper::toDto);
    }

    /**
     * Delete the templateFlow by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TemplateFlow : {}", id);

        templateFlowRepository.deleteById(id);
    }
}
