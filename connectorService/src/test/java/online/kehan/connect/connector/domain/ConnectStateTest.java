package online.kehan.connect.connector.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import online.kehan.connect.connector.web.rest.TestUtil;

public class ConnectStateTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConnectState.class);
        ConnectState connectState1 = new ConnectState();
        connectState1.setId(1L);
        ConnectState connectState2 = new ConnectState();
        connectState2.setId(connectState1.getId());
        assertThat(connectState1).isEqualTo(connectState2);
        connectState2.setId(2L);
        assertThat(connectState1).isNotEqualTo(connectState2);
        connectState1.setId(null);
        assertThat(connectState1).isNotEqualTo(connectState2);
    }
}
