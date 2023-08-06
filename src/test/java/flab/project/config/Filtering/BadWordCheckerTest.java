package flab.project.config.Filtering;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BadWordCheckerTest {

    @DisplayName("BadWord로 지정된 단어가 있으면 true가 반환된다.")
    @Test
    public void hasBadWord() {
        //given
        SubStringExtractor extractor = new SubStringExtractor();
        BadWordChecker badWordChecker = new BadWordChecker(extractor);

        List<String> receivedStrings = List.of(
            "바보 입니다."
        );

        //when
        boolean hasBadWord = badWordChecker.hasBadWord(receivedStrings);

        //then
        Assertions.assertTrue(hasBadWord);
    }

    @DisplayName("띄어쓰기 없이 BadWord가 입력 되었더라도 true가 반환된다.")
    @Test
    public void hasBadWordWithoutSpacing() {
        //given
        SubStringExtractor extractor = new SubStringExtractor();
        BadWordChecker badWordChecker = new BadWordChecker(extractor);

        List<String> receivedStrings = List.of(
            "바보입니다."
        );

        //when
        boolean hasBadWord = badWordChecker.hasBadWord(receivedStrings);

        //then
        Assertions.assertTrue(hasBadWord);
    }

    @DisplayName("BadWord로 지정된 단어가 없으면 false가 반환된다.")
    @Test
    public void hasNotBadWord() {
        //given
        SubStringExtractor extractor = new SubStringExtractor();
        BadWordChecker badWordChecker = new BadWordChecker(extractor);

        List<String> receivedStrings = List.of(
            "천재 입니다."
        );

        //when
        boolean hasBadWord = badWordChecker.hasBadWord(receivedStrings);

        //then
        Assertions.assertFalse(hasBadWord);
    }

}