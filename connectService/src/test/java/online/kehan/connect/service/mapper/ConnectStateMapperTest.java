package online.kehan.connect.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ConnectStateMapperTest {

    private ConnectStateMapper connectStateMapper;

    @BeforeEach
    public void setUp() {
        connectStateMapper = new ConnectStateMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(connectStateMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(connectStateMapper.fromId(null)).isNull();
    }
}
