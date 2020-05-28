package online.kehan.connect.graphql;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import lombok.AllArgsConstructor;
import lombok.Data;
import online.kehan.connect.domain.Campaign;
import online.kehan.connect.pojo.AllCampaignsResponse;
import online.kehan.connect.security.SecurityUtils;
import online.kehan.connect.service.CampaignService;
import online.kehan.connect.service.dto.CampaignDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static online.kehan.connect.security.SecurityUtils.getCurrentUserLogin;

@Component
//@AllArgsConstructor
public class CampaignQuery implements GraphQLQueryResolver {

    @Autowired
    private CampaignService campaignService;

    public List<CampaignDTO> getCampaign(final int count) {
        Optional<String> curUserLogin = SecurityUtils.getCurrentUserLogin();
        PageRequest pageable = PageRequest.of(0, count);
        return campaignService.findAll(pageable).getContent();
    }
    public Optional<CampaignDTO> getCampaignByID(final Long id) {
        return this.campaignService.findOne(id);
    }

//    public AllCampaignsResponse allCampaigns(int limit, int page, String filter, String orderBy) {
//        PageRequest pageable = PageRequest.of(page, limit);
//        List<CampaignDTO> campaignDTOS = campaignService.findAll(pageable).getContent();
//        return new AllCampaignsResponse(campaignDTOS, 1);
//    }
}
