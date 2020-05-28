package online.kehan.connect.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import online.kehan.connect.web.rest.TestUtil;

public class ConnectEventTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConnectEvent.class);
        ConnectEvent connectEvent1 = new ConnectEvent();
        connectEvent1.setId(1L);
        ConnectEvent connectEvent2 = new ConnectEvent();
        connectEvent2.setId(connectEvent1.getId());
        assertThat(connectEvent1).isEqualTo(connectEvent2);
        connectEvent2.setId(2L);
        assertThat(connectEvent1).isNotEqualTo(connectEvent2);
        connectEvent1.setId(null);
        assertThat(connectEvent1).isNotEqualTo(connectEvent2);
    }
}
