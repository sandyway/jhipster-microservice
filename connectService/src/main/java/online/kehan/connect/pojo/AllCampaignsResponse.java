package online.kehan.connect.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import online.kehan.connect.service.dto.CampaignDTO;

import java.util.List;

@AllArgsConstructor
@Data
public class AllCampaignsResponse {
    private List<CampaignDTO> campaigns;
    private int totalCount;
}
