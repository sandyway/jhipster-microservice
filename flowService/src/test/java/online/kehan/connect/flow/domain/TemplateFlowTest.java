package online.kehan.connect.flow.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import online.kehan.connect.flow.web.rest.TestUtil;

public class TemplateFlowTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TemplateFlow.class);
        TemplateFlow templateFlow1 = new TemplateFlow();
        templateFlow1.setId(1L);
        TemplateFlow templateFlow2 = new TemplateFlow();
        templateFlow2.setId(templateFlow1.getId());
        assertThat(templateFlow1).isEqualTo(templateFlow2);
        templateFlow2.setId(2L);
        assertThat(templateFlow1).isNotEqualTo(templateFlow2);
        templateFlow1.setId(null);
        assertThat(templateFlow1).isNotEqualTo(templateFlow2);
    }
}
