package flab.project.data.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class AddInterestTest {

    @DisplayName("관심사 이름은 Sharp과 함께 나타나게 한다.")
    @Test
    void getInterestNameWithSharp(){
        AddInterest addInterest = new AddInterest(1L, "test");

        String interestNameWithSharp = addInterest.getInterestNameWithSharp();

        assertThat(interestNameWithSharp).isEqualTo("#test");
    }

    @DisplayName("AddInterest의 String으로 된 필드를 뽑아낼 수 있다.")
    @Test
    void getStringFields(){
        AddInterest addInterest = new AddInterest(1L, "test");

        List<String> stringFields = addInterest.getStringFields();

        assertThat(stringFields).hasSize(1)
                .containsExactly("test");
    }

    @DisplayName("addInterst.convertEscapeCharacter 메서드는 문자열 안에 escape 문자들을 변경해준다.")
    @Test
    void convertEscapeCharacter(){
        AddInterest addInterest = new AddInterest(1L, "<<test>>");

        addInterest.convertEscapeCharacter();

        assertThat(addInterest.getInterestNameWithSharp()).isEqualTo("#&lt;&lt;test&gt;&gt;");
    }
}