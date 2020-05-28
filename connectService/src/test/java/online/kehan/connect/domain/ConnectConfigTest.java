package online.kehan.connect.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import online.kehan.connect.web.rest.TestUtil;

public class ConnectConfigTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConnectConfig.class);
        ConnectConfig connectConfig1 = new ConnectConfig();
        connectConfig1.setId(1L);
        ConnectConfig connectConfig2 = new ConnectConfig();
        connectConfig2.setId(connectConfig1.getId());
        assertThat(connectConfig1).isEqualTo(connectConfig2);
        connectConfig2.setId(2L);
        assertThat(connectConfig1).isNotEqualTo(connectConfig2);
        connectConfig1.setId(null);
        assertThat(connectConfig1).isNotEqualTo(connectConfig2);
    }
}
