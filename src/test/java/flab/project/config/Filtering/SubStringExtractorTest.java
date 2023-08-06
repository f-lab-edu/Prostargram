package flab.project.config.Filtering;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

class SubStringExtractorTest {

    @DisplayName("모든 경우의 수의 SubString을 추출해서 반환한다.")
    @Test
    public void test() {
        //given
        SubStringExtractor subStringExtractor = new SubStringExtractor();
        List<String> words = List.of("abc");

        //when
        List<String> subStrings = subStringExtractor.extractSubString(words);

        //then
        assertThat(subStrings)
            .hasSize(6)
            .containsExactlyInAnyOrder("abc", "ab", "bc", "a", "b", "c");

    }
}