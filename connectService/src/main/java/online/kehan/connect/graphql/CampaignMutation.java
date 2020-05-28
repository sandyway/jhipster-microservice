package online.kehan.connect.graphql;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import lombok.AllArgsConstructor;
import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import online.kehan.connect.domain.Campaign;
import online.kehan.connect.domain.enumeration.CampaignType;
import online.kehan.connect.service.CampaignService;
import online.kehan.connect.service.dto.CampaignDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.time.ZonedDateTime;

@Component
//@AllArgsConstructor
public class CampaignMutation implements GraphQLMutationResolver {
    @Autowired
    private CampaignService campaignService;

    public CampaignDTO createCampaign(final String campaignDetails, final CampaignType campaignType,
                                   final String createdBy) {
        CampaignDTO campaign = new CampaignDTO();
        campaign.setCampaignDetails(campaignDetails);
        campaign.setCampaignType(campaignType);
        campaign.setCreatedBy(createdBy);
//        campaign.setStartDate(ZonedDateTime.now());
        return campaignService.save(campaign);
    }
}
