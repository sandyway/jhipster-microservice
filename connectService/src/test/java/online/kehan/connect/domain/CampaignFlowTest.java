package online.kehan.connect.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import online.kehan.connect.web.rest.TestUtil;

public class CampaignFlowTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CampaignFlow.class);
        CampaignFlow campaignFlow1 = new CampaignFlow();
        campaignFlow1.setId(1L);
        CampaignFlow campaignFlow2 = new CampaignFlow();
        campaignFlow2.setId(campaignFlow1.getId());
        assertThat(campaignFlow1).isEqualTo(campaignFlow2);
        campaignFlow2.setId(2L);
        assertThat(campaignFlow1).isNotEqualTo(campaignFlow2);
        campaignFlow1.setId(null);
        assertThat(campaignFlow1).isNotEqualTo(campaignFlow2);
    }
}
