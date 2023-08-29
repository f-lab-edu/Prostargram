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
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
public class LikeServiceTest {

    @InjectMocks
    LikeService likeService;

    @Mock
    LikeMapper likeMapper;

    @DisplayName("게시물에 좋아요를 할 수 있다.")
    @Test
    void validParamterInAddPostLike() {
        // given
        long postId = 1L;
        long userId = 2L;

        // when
        likeService.addPostLike(postId, userId);

        // then
        then(likeMapper).should().addPostLike(postId, userId);
    }

    @DisplayName("존재하지 않는 게시물 또는 존재하지 않는 유저가 좋아요를 추가할 때, 게시물에 좋아요가 반영되지 않는다.")
    @Test
    void invalidParameterInAddPostLike() {
        // given
        long negativePostId = -1L;
        long userId = 1L;

        // when & then
        assertThatThrownBy(() -> likeService.addPostLike(negativePostId, userId)).isInstanceOf(InvalidUserInputException.class);

        // given
        long zeroPostId = 0;

        // when & then
        assertThatThrownBy(() -> likeService.addPostLike(zeroPostId, userId)).isInstanceOf(InvalidUserInputException.class);

        // given
        long postId = 1L;
        long negativeUserId = -2L;

        // when & then
        assertThatThrownBy(() -> likeService.addPostLike(postId, negativeUserId)).isInstanceOf(InvalidUserInputException.class);

        // given
        long zeroUserId = 0L;

        // when & then
        assertThatThrownBy(() -> likeService.addPostLike(postId, zeroUserId)).isInstanceOf(InvalidUserInputException.class);
    }
}
