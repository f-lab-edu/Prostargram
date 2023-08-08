package flab.project.service;

import flab.project.config.exception.InvalidUserInputException;
import flab.project.mapper.LikeMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
public class LikeServiceTest {

    @InjectMocks
    LikeService likeService;

    @Mock
    LikeMapper likeMapper;

    @DisplayName("게시물에 좋아요를 추가할 때, postId 또는 userId의 값이 잘못된 경우(= 음수) InvalidUserInputException을 던진다.")
    @Test
    void invalidParameterInAddPostLike() {
        // given
        // postId is negative
        long invalidPostId = -1L;
        long userId = 1L;

        // when then
        assertThatThrownBy(() -> likeService.addPostLike(invalidPostId, userId)).isInstanceOf(InvalidUserInputException.class);

        // given
        // userId is negative
        long postId = 1L;
        long invalidUserId = -1L;

        // when then
        assertThatThrownBy(() -> likeService.addPostLike(postId, invalidUserId)).isInstanceOf(InvalidUserInputException.class);

        // given
        // both postId and userId are negative
        long anotherInvalidPostId = -2L;
        long anotherInvalidUserId = -2L;

        // when then
        assertThatThrownBy(() -> likeService.addPostLike(anotherInvalidPostId, anotherInvalidUserId)).isInstanceOf(InvalidUserInputException.class);
    }

    @DisplayName("게시물에 좋아요를 추가할 때, postId와 userId의 값이 올바르면 정상적으로 처리된다.")
    @Test
    void validParamterInAddPostLike() {
        // given
        long postId = 1L;
        long userId = 2L;

        // when then
        assertThatCode(() -> likeService.addPostLike(postId, userId)).doesNotThrowAnyException();
    }
}
