package online.kehan.connect.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ConnectConfigMapperTest {

    private ConnectConfigMapper connectConfigMapper;

    @BeforeEach
    public void setUp() {
        connectConfigMapper = new ConnectConfigMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(connectConfigMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(connectConfigMapper.fromId(null)).isNull();
    }
}
