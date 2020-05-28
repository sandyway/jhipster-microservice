package online.kehan.connect.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ConnectEventMapperTest {

    private ConnectEventMapper connectEventMapper;

    @BeforeEach
    public void setUp() {
        connectEventMapper = new ConnectEventMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(connectEventMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(connectEventMapper.fromId(null)).isNull();
    }
}
