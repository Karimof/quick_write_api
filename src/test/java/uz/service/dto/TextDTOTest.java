package uz.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import uz.web.rest.TestUtil;

class TextDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TextDTO.class);
        TextDTO textDTO1 = new TextDTO();
        textDTO1.setId(1L);
        TextDTO textDTO2 = new TextDTO();
        assertThat(textDTO1).isNotEqualTo(textDTO2);
        textDTO2.setId(textDTO1.getId());
        assertThat(textDTO1).isEqualTo(textDTO2);
        textDTO2.setId(2L);
        assertThat(textDTO1).isNotEqualTo(textDTO2);
        textDTO1.setId(null);
        assertThat(textDTO1).isNotEqualTo(textDTO2);
    }
}
