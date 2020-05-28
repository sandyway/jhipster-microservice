package online.kehan.connect.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import online.kehan.connect.web.rest.TestUtil;

public class ConnectConfigDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConnectConfigDTO.class);
        ConnectConfigDTO connectConfigDTO1 = new ConnectConfigDTO();
        connectConfigDTO1.setId(1L);
        ConnectConfigDTO connectConfigDTO2 = new ConnectConfigDTO();
        assertThat(connectConfigDTO1).isNotEqualTo(connectConfigDTO2);
        connectConfigDTO2.setId(connectConfigDTO1.getId());
        assertThat(connectConfigDTO1).isEqualTo(connectConfigDTO2);
        connectConfigDTO2.setId(2L);
        assertThat(connectConfigDTO1).isNotEqualTo(connectConfigDTO2);
        connectConfigDTO1.setId(null);
        assertThat(connectConfigDTO1).isNotEqualTo(connectConfigDTO2);
    }
}
