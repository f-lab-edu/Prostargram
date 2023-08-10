package flab.project.service;

import flab.project.config.exception.InvalidUserInputException;
import flab.project.mapper.LikeMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class LikeServiceTest {

    @InjectMocks
    LikeService likeService;

    @Mock
    LikeMapper likeMapper;

    @DisplayName("게시물에 좋아요를 추가할 때, 올바른 postId 및 userId가 들어온다면 두 값에 맞춰 알맞은 Mapper class가 호출이 된다.")
    @Test
    void validParamterInAddPostLike() {
        long postId = 1L;
        long userId = 2L;

        likeService.addPostLike(postId, userId);

        verify(likeMapper).addPostLike(postId, userId);
    }

    @DisplayName("게시물에 좋아요를 추가할 때, postId 또는 userId의 값이 잘못된 경우(= 음수) InvalidUserInputException을 던진다.")
    @Test
    void invalidParameterInAddPostLike() {
        long invalidPostId = -1L;
        long userId = 1L;

        assertThatThrownBy(() -> likeService.addPostLike(invalidPostId, userId)).isInstanceOf(InvalidUserInputException.class);

        long postId = 1L;
        long invalidUserId = -1L;

        assertThatThrownBy(() -> likeService.addPostLike(postId, invalidUserId)).isInstanceOf(InvalidUserInputException.class);

        long anotherInvalidPostId = -2L;
        long anotherInvalidUserId = -2L;

        assertThatThrownBy(() -> likeService.addPostLike(anotherInvalidPostId, anotherInvalidUserId)).isInstanceOf(InvalidUserInputException.class);
    }
}
