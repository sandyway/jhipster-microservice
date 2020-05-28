package online.kehan.connect.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import online.kehan.connect.web.rest.TestUtil;

public class RecipientDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RecipientDTO.class);
        RecipientDTO recipientDTO1 = new RecipientDTO();
        recipientDTO1.setId(1L);
        RecipientDTO recipientDTO2 = new RecipientDTO();
        assertThat(recipientDTO1).isNotEqualTo(recipientDTO2);
        recipientDTO2.setId(recipientDTO1.getId());
        assertThat(recipientDTO1).isEqualTo(recipientDTO2);
        recipientDTO2.setId(2L);
        assertThat(recipientDTO1).isNotEqualTo(recipientDTO2);
        recipientDTO1.setId(null);
        assertThat(recipientDTO1).isNotEqualTo(recipientDTO2);
    }
}
