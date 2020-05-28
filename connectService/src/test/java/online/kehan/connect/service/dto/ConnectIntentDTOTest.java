package online.kehan.connect.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import online.kehan.connect.web.rest.TestUtil;

public class ConnectIntentDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConnectIntentDTO.class);
        ConnectIntentDTO connectIntentDTO1 = new ConnectIntentDTO();
        connectIntentDTO1.setId(1L);
        ConnectIntentDTO connectIntentDTO2 = new ConnectIntentDTO();
        assertThat(connectIntentDTO1).isNotEqualTo(connectIntentDTO2);
        connectIntentDTO2.setId(connectIntentDTO1.getId());
        assertThat(connectIntentDTO1).isEqualTo(connectIntentDTO2);
        connectIntentDTO2.setId(2L);
        assertThat(connectIntentDTO1).isNotEqualTo(connectIntentDTO2);
        connectIntentDTO1.setId(null);
        assertThat(connectIntentDTO1).isNotEqualTo(connectIntentDTO2);
    }
}
