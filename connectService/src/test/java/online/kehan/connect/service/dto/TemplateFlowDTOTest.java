package online.kehan.connect.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import online.kehan.connect.web.rest.TestUtil;

public class TemplateFlowDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TemplateFlowDTO.class);
        TemplateFlowDTO templateFlowDTO1 = new TemplateFlowDTO();
        templateFlowDTO1.setId(1L);
        TemplateFlowDTO templateFlowDTO2 = new TemplateFlowDTO();
        assertThat(templateFlowDTO1).isNotEqualTo(templateFlowDTO2);
        templateFlowDTO2.setId(templateFlowDTO1.getId());
        assertThat(templateFlowDTO1).isEqualTo(templateFlowDTO2);
        templateFlowDTO2.setId(2L);
        assertThat(templateFlowDTO1).isNotEqualTo(templateFlowDTO2);
        templateFlowDTO1.setId(null);
        assertThat(templateFlowDTO1).isNotEqualTo(templateFlowDTO2);
    }
}
