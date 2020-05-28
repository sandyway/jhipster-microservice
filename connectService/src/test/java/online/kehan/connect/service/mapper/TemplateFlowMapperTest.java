package online.kehan.connect.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TemplateFlowMapperTest {

    private TemplateFlowMapper templateFlowMapper;

    @BeforeEach
    public void setUp() {
        templateFlowMapper = new TemplateFlowMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(templateFlowMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(templateFlowMapper.fromId(null)).isNull();
    }
}
