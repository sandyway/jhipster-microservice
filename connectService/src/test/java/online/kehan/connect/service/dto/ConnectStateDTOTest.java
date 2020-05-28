package online.kehan.connect.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import online.kehan.connect.web.rest.TestUtil;

public class ConnectStateDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConnectStateDTO.class);
        ConnectStateDTO connectStateDTO1 = new ConnectStateDTO();
        connectStateDTO1.setId(1L);
        ConnectStateDTO connectStateDTO2 = new ConnectStateDTO();
        assertThat(connectStateDTO1).isNotEqualTo(connectStateDTO2);
        connectStateDTO2.setId(connectStateDTO1.getId());
        assertThat(connectStateDTO1).isEqualTo(connectStateDTO2);
        connectStateDTO2.setId(2L);
        assertThat(connectStateDTO1).isNotEqualTo(connectStateDTO2);
        connectStateDTO1.setId(null);
        assertThat(connectStateDTO1).isNotEqualTo(connectStateDTO2);
    }
}
