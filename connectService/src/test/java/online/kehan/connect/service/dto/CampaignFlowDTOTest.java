package online.kehan.connect.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import online.kehan.connect.web.rest.TestUtil;

public class CampaignFlowDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CampaignFlowDTO.class);
        CampaignFlowDTO campaignFlowDTO1 = new CampaignFlowDTO();
        campaignFlowDTO1.setId(1L);
        CampaignFlowDTO campaignFlowDTO2 = new CampaignFlowDTO();
        assertThat(campaignFlowDTO1).isNotEqualTo(campaignFlowDTO2);
        campaignFlowDTO2.setId(campaignFlowDTO1.getId());
        assertThat(campaignFlowDTO1).isEqualTo(campaignFlowDTO2);
        campaignFlowDTO2.setId(2L);
        assertThat(campaignFlowDTO1).isNotEqualTo(campaignFlowDTO2);
        campaignFlowDTO1.setId(null);
        assertThat(campaignFlowDTO1).isNotEqualTo(campaignFlowDTO2);
    }
}
