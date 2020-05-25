package online.kehan.connect.analytic.collection.service;

import online.kehan.connect.analytic.collection.domain.TemplateFlow;
import online.kehan.connect.analytic.collection.repository.TemplateFlowRepository;
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

    public TemplateFlowService(TemplateFlowRepository templateFlowRepository) {
        this.templateFlowRepository = templateFlowRepository;
    }

    /**
     * Save a templateFlow.
     *
     * @param templateFlow the entity to save.
     * @return the persisted entity.
     */
    public TemplateFlow save(TemplateFlow templateFlow) {
        log.debug("Request to save TemplateFlow : {}", templateFlow);
        return templateFlowRepository.save(templateFlow);
    }

    /**
     * Get all the templateFlows.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TemplateFlow> findAll(Pageable pageable) {
        log.debug("Request to get all TemplateFlows");
        return templateFlowRepository.findAll(pageable);
    }


    /**
     * Get one templateFlow by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TemplateFlow> findOne(Long id) {
        log.debug("Request to get TemplateFlow : {}", id);
        return templateFlowRepository.findById(id);
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
