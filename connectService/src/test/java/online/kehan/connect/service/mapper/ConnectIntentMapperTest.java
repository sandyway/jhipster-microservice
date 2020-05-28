package online.kehan.connect.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ConnectIntentMapperTest {

    private ConnectIntentMapper connectIntentMapper;

    @BeforeEach
    public void setUp() {
        connectIntentMapper = new ConnectIntentMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(connectIntentMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(connectIntentMapper.fromId(null)).isNull();
    }
}
