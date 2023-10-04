package flab.project.service;

import flab.project.config.exception.InvalidUserInputException;
import flab.project.mapper.PostOptionMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Set;

import static flab.project.common.Constraints.FIXED_SIZE_OF_DEBATE_POST_OPTIONS;
import static flab.project.common.Constraints.MAX_LENGTH_DEBATE_POST_OPTION_CONTENT;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class DebatePostOptionServiceTest {

    private static final long validPostId = 1L;
    private static final Set<String> validPostOptions = Set.of("선택지1", "선택지2");

    @InjectMocks
    DebatePostOptionService debatePostOptionService;
    @Mock
    PostOptionMapper postOptionMapper;

    @DisplayName("게시물 선택지를 추가한다.")
    @Test
    void savePostOptions() {
        // when
        debatePostOptionService.savePostOptions(validPostId, validPostOptions);

        // then
        then(postOptionMapper).should().saveAll(validPostId, validPostOptions);
    }

    @DisplayName("게시물 보기를 추가할 때, postId가 음수면 INVALID_USER_INPUT을 반환한다.")
    @Test
    void savePostOptions_negativePostId() {
        // given
        long negativePostId = -1L;

        // when & then
        assertThatCode(() -> debatePostOptionService.savePostOptions(negativePostId, validPostOptions))
                .isExactlyInstanceOf(InvalidUserInputException.class);
    }

    @DisplayName("게시물 보기를 추가할 때, postId가 0이면 INVALID_USER_INPUT을 반환한다.")
    @Test
    void savePostOptions_zeroPostId() {
        // given
        long zeroPostId = 0L;

        // when & then
        assertThatCode(() -> debatePostOptionService.savePostOptions(zeroPostId, validPostOptions))
                .isExactlyInstanceOf(InvalidUserInputException.class);
    }

    @DisplayName("게시물 보기를 추가할 때, postOptions중 비어있는 postOption이 있으면 INVALID_USER_INPUT을 반환한다.")
    @Test
    void savePostOptions_emptyPostOption() {
        // given
        String emptyPostOption = "";
        Set<String> invalidPostOptions = Set.of(emptyPostOption, "선택지");

        // when & then
        assertThatCode(() -> debatePostOptionService.savePostOptions(validPostId, invalidPostOptions))
                .isExactlyInstanceOf(InvalidUserInputException.class);
    }

    @DisplayName("게시물 보기를 추가할 때, postOptions중 공백으로만 이루어진 postOption이 있으면 INVALID_USER_INPUT을 반환한다.")
    @Test
    void savePostOptions_OnlyBlankPostOption() {
        // given
        String onlyBlankPostOption = "   ";
        Set<String> invalidPostOptions = Set.of(onlyBlankPostOption, "선택지");

        // when & then
        assertThatCode(() -> debatePostOptionService.savePostOptions(validPostId, invalidPostOptions))
                .isExactlyInstanceOf(InvalidUserInputException.class);
    }

    @DisplayName("게시물 보기를 추가할 때, postOptions중 최대 길이를 초과한 postOption이 있으면 INVALID_USER_INPUT을 반환한다.")
    @Test
    void savePostOptions_exceedMaxLengthOfPostOption() {
        // given
        String postOptionExceededMaxLength = RandomStringUtils.randomAlphabetic(MAX_LENGTH_DEBATE_POST_OPTION_CONTENT + 1);
        Set<String> invalidPostOptions = Set.of(postOptionExceededMaxLength, "선택지");

        // when & then
        assertThatCode(() -> debatePostOptionService.savePostOptions(validPostId, invalidPostOptions))
                .isExactlyInstanceOf(InvalidUserInputException.class);
    }

    @DisplayName("게시물 보기를 추가할 때, postOptions가 2가 아니라면 INVALID_USER_INPUT을 반환한다.")
    @Test
    void savePostOptions_notFixedSizeOfPostOptions() {
        // given
        Set<String> invalidPostOptions = new HashSet<>();

        for (int i = 0; i < FIXED_SIZE_OF_DEBATE_POST_OPTIONS + 1; i++) {
            invalidPostOptions.add("#test" + i);
        }

        // when & then
        assertThatCode(() -> debatePostOptionService.savePostOptions(validPostId, invalidPostOptions))
                .isExactlyInstanceOf(InvalidUserInputException.class);
    }
}