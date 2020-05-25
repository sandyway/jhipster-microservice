package online.kehan.connect.flow.service;

import online.kehan.connect.flow.domain.CampaignFlow;
import online.kehan.connect.flow.repository.CampaignFlowRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CampaignFlow}.
 */
@Service
@Transactional
public class CampaignFlowService {

    private final Logger log = LoggerFactory.getLogger(CampaignFlowService.class);

    private final CampaignFlowRepository campaignFlowRepository;

    public CampaignFlowService(CampaignFlowRepository campaignFlowRepository) {
        this.campaignFlowRepository = campaignFlowRepository;
    }

    /**
     * Save a campaignFlow.
     *
     * @param campaignFlow the entity to save.
     * @return the persisted entity.
     */
    public CampaignFlow save(CampaignFlow campaignFlow) {
        log.debug("Request to save CampaignFlow : {}", campaignFlow);
        return campaignFlowRepository.save(campaignFlow);
    }

    /**
     * Get all the campaignFlows.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CampaignFlow> findAll(Pageable pageable) {
        log.debug("Request to get all CampaignFlows");
        return campaignFlowRepository.findAll(pageable);
    }


    /**
     * Get one campaignFlow by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CampaignFlow> findOne(Long id) {
        log.debug("Request to get CampaignFlow : {}", id);
        return campaignFlowRepository.findById(id);
    }

    /**
     * Delete the campaignFlow by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CampaignFlow : {}", id);

        campaignFlowRepository.deleteById(id);
    }
}
