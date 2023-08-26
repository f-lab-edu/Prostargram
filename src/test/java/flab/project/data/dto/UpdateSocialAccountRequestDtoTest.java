package flab.project.data.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UpdateSocialAccountRequestDtoTest {

    @DisplayName("convertEscapeCharacter 메서드는 Escape문자를 변환한다.")
    @Test
    void convertEscapeCharacter() {
        // given
        UpdateSocialAccountRequestDto updateSocialAccountRequestDto = new UpdateSocialAccountRequestDto(1L, "<<test>>");

        // when
        updateSocialAccountRequestDto.convertEscapeCharacter();

        // then
        assertThat(updateSocialAccountRequestDto.getSocialAccountUrl()).isEqualTo("&lt;&lt;test&gt;&gt;");
    }
}