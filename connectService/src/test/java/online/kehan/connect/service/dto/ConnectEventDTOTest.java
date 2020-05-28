package online.kehan.connect.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import online.kehan.connect.web.rest.TestUtil;

public class ConnectEventDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConnectEventDTO.class);
        ConnectEventDTO connectEventDTO1 = new ConnectEventDTO();
        connectEventDTO1.setId(1L);
        ConnectEventDTO connectEventDTO2 = new ConnectEventDTO();
        assertThat(connectEventDTO1).isNotEqualTo(connectEventDTO2);
        connectEventDTO2.setId(connectEventDTO1.getId());
        assertThat(connectEventDTO1).isEqualTo(connectEventDTO2);
        connectEventDTO2.setId(2L);
        assertThat(connectEventDTO1).isNotEqualTo(connectEventDTO2);
        connectEventDTO1.setId(null);
        assertThat(connectEventDTO1).isNotEqualTo(connectEventDTO2);
    }
}
