package online.kehan.connect.service;

import online.kehan.connect.domain.CampaignFlow;
import online.kehan.connect.repository.CampaignFlowRepository;
import online.kehan.connect.service.dto.CampaignFlowDTO;
import online.kehan.connect.service.mapper.CampaignFlowMapper;
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

    private final CampaignFlowMapper campaignFlowMapper;

    public CampaignFlowService(CampaignFlowRepository campaignFlowRepository, CampaignFlowMapper campaignFlowMapper) {
        this.campaignFlowRepository = campaignFlowRepository;
        this.campaignFlowMapper = campaignFlowMapper;
    }

    /**
     * Save a campaignFlow.
     *
     * @param campaignFlowDTO the entity to save.
     * @return the persisted entity.
     */
    public CampaignFlowDTO save(CampaignFlowDTO campaignFlowDTO) {
        log.debug("Request to save CampaignFlow : {}", campaignFlowDTO);
        CampaignFlow campaignFlow = campaignFlowMapper.toEntity(campaignFlowDTO);
        campaignFlow = campaignFlowRepository.save(campaignFlow);
        return campaignFlowMapper.toDto(campaignFlow);
    }

    /**
     * Get all the campaignFlows.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CampaignFlowDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CampaignFlows");
        return campaignFlowRepository.findAll(pageable)
            .map(campaignFlowMapper::toDto);
    }


    /**
     * Get one campaignFlow by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CampaignFlowDTO> findOne(Long id) {
        log.debug("Request to get CampaignFlow : {}", id);
        return campaignFlowRepository.findById(id)
            .map(campaignFlowMapper::toDto);
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
