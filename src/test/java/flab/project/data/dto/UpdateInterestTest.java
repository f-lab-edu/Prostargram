package flab.project.data.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class UpdateInterestTest {

    @DisplayName("관심사 이름은 Sharp과 함께 나타나게 한다.")
    @Test
    void getInterestNameWithSharp(){
        UpdateInterest updateInterest = new UpdateInterest(1L, "test");

        String interestNameWithSharp = updateInterest.getInterestNameWithSharp();

        assertThat(interestNameWithSharp).isEqualTo("#test");
    }

    @DisplayName("AddInterest의 String으로 된 필드를 뽑아낼 수 있다.")
    @Test
    void getStringFields(){
        UpdateInterest updateInterest = new UpdateInterest(1L, "test");

        List<String> stringFields = updateInterest.getStringFields();

        assertThat(stringFields).hasSize(1)
                .containsExactly("test");
    }

    @DisplayName("addInterst.convertEscapeCharacter 메서드는 문자열 안에 escape 문자들을 변경해준다.")
    @Test
    void convertEscapeCharacter(){
        UpdateInterest updateInterest = new UpdateInterest(1L, "<<test>>");

        updateInterest.convertEscapeCharacter();

        assertThat(updateInterest.getInterestNameWithSharp()).isEqualTo("#&lt;&lt;test&gt;&gt;");
    }
}