package online.kehan.connect.service.mapper;


import online.kehan.connect.domain.*;
import online.kehan.connect.service.dto.CampaignFlowDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CampaignFlow} and its DTO {@link CampaignFlowDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CampaignFlowMapper extends EntityMapper<CampaignFlowDTO, CampaignFlow> {



    default CampaignFlow fromId(Long id) {
        if (id == null) {
            return null;
        }
        CampaignFlow campaignFlow = new CampaignFlow();
        campaignFlow.setId(id);
        return campaignFlow;
    }
}
