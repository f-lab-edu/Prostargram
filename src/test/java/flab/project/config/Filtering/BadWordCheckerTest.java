package flab.project.config.Filtering;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import flab.project.mapper.BadwordMapper;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BadWordCheckerTest {

    @Mock
    private BadwordMapper badwordMapper;

    @InjectMocks
    private BadWordChecker badWordChecker;

//    @DisplayName("BadWord로 지정된 단어가 있으면 true가 반환된다.")
//    @Test
//    public void hasBadWord() {
//        //given
//
////        when(badwordMapper.getAll()).thenReturn(Set.of("바보", "멍청이"));
//
//
//        List<String> receivedStrings = List.of(
//            "바보 입니다."
//        );
//
//        //when
//        boolean hasBadWord = badWordChecker.hasBadWord(receivedStrings);
//
//        //then
//        Assertions.assertTrue(hasBadWord);
//    }
//
//    @DisplayName("BadWord로 지정된 단어가 없으면 false가 반환된다.")
//    @Test
//    public void hasNotBadWord() {
//        //given
//        SubStringExtractor extractor = new SubStringExtractor();
//        BadWordChecker badWordChecker = new BadWordChecker(extractor);
//
//        List<String> receivedStrings = List.of(
//            "천재 입니다."
//        );
//
//        //when
//        boolean hasBadWord = badWordChecker.hasBadWord(receivedStrings);
//
//        //then
//        Assertions.assertFalse(hasBadWord);
//    }

}