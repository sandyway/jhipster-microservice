package online.kehan.connect.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import online.kehan.connect.web.rest.TestUtil;

public class ConnectIntentTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConnectIntent.class);
        ConnectIntent connectIntent1 = new ConnectIntent();
        connectIntent1.setId(1L);
        ConnectIntent connectIntent2 = new ConnectIntent();
        connectIntent2.setId(connectIntent1.getId());
        assertThat(connectIntent1).isEqualTo(connectIntent2);
        connectIntent2.setId(2L);
        assertThat(connectIntent1).isNotEqualTo(connectIntent2);
        connectIntent1.setId(null);
        assertThat(connectIntent1).isNotEqualTo(connectIntent2);
    }
}
