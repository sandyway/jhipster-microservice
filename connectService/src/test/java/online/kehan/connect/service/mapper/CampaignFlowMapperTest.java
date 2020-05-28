package online.kehan.connect.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CampaignFlowMapperTest {

    private CampaignFlowMapper campaignFlowMapper;

    @BeforeEach
    public void setUp() {
        campaignFlowMapper = new CampaignFlowMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(campaignFlowMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(campaignFlowMapper.fromId(null)).isNull();
    }
}
