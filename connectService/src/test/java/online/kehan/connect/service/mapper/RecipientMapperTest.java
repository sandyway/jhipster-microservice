package online.kehan.connect.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class RecipientMapperTest {

    private RecipientMapper recipientMapper;

    @BeforeEach
    public void setUp() {
        recipientMapper = new RecipientMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(recipientMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(recipientMapper.fromId(null)).isNull();
    }
}
