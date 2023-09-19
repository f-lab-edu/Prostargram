package flab.project.service;

import flab.project.config.exception.DeletedPostException;
import flab.project.config.exception.InvalidUserInputException;
import flab.project.data.dto.model.BasicPost;
import flab.project.data.dto.model.DebatePost;
import flab.project.data.dto.model.PollPost;
import flab.project.mapper.PostMapper;
import flab.project.mapper.UserMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static flab.project.data.enums.PostType.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @InjectMocks
    PostService postService;
    @Mock
    PostMapper postMapper;
    @Mock
    UserMapper userMapper;

    @DisplayName("일반 게시물 상세 보기를 할 수 있다.")
    @Test
    void getBasicPostDetail() {
        // given
        long postId = 1L;
        long userId = 1L;

        given(postMapper.getBasicPostDetail(postId, userId))
                .willReturn(new BasicPost());

        // when & then
        postService.getPostDetail(postId, userId, BASIC);

        then(userMapper).should().getBasicUser(userId);
        then(postMapper).should().getBasicPostDetail(postId, userId);
    }

    @DisplayName("토론 게시물 상세 보기를 할 수 있다.")
    @Test
    void getDebatePostDetail() {
        // given
        long postId = 1L;
        long userId = 1L;

        given(postMapper.getDebatePostDetail(postId, userId))
                .willReturn(new DebatePost());

        // when & then
        postService.getPostDetail(postId, userId, DEBATE);

        then(userMapper).should().getBasicUser(userId);
        then(postMapper).should().getDebatePostDetail(postId, userId);
    }

    @DisplayName("통계 게시물 상세 보기를 할 수 있다.")
    @Test
    void getPollPostDetail() {
        // given
        long postId = 1L;
        long userId = 1L;

        given(postMapper.getPollPostDetail(postId, userId))
                .willReturn(new PollPost());

        // when & then
        postService.getPostDetail(postId, userId, POLL);

        then(userMapper).should().getBasicUser(userId);
        then(postMapper).should().getPollPostDetail(postId, userId);
    }

    @DisplayName("일반 게시물이 존재하지 않는 게시물이면 DeletedPostException을 던진다.")
    @Test
    void getBasicPostDetail_deletedPost() {
        // given
        long postId = 1L;
        long userId = 1L;

        given(postMapper.getBasicPostDetail(postId, userId))
                .willReturn(null);

        // when & then
        assertThatThrownBy(() -> postService.getPostDetail(postId, userId, BASIC))
                .isInstanceOf(DeletedPostException.class);
    }

    @DisplayName("토론 게시물이 존재하지 않는 게시물이면 DeletedPostException을 던진다.")
    @Test
    void getDebatePostDetail_deletedPost() {
        // given
        long postId = 1L;
        long userId = 1L;

        given(postMapper.getDebatePostDetail(postId, userId))
                .willReturn(null);

        // when & then
        assertThatThrownBy(() -> postService.getPostDetail(postId, userId, DEBATE))
                .isInstanceOf(DeletedPostException.class);
    }

    @DisplayName("통계 게시물이 존재하지 않는 게시물이면 DeletedPostException을 던진다.")
    @Test
    void getPollPostDetail_deletedPost() {
        // given
        long postId = 1L;
        long userId = 1L;

        given(postMapper.getPollPostDetail(postId, userId))
                .willReturn(null);

        // when & then
        assertThatThrownBy(() -> postService.getPostDetail(postId, userId, POLL))
                .isInstanceOf(DeletedPostException.class);
    }

    @DisplayName("게시물 상세 보기를 할 때, postId는 양수여야 한다.")
    @Test
    void getPostDetail_invalidPostId() {
        // given
        long zeroPostId = 0L;
        long negativePostId = -1L;

        long userId = 1L;

        // when & then
        assertThatThrownBy(() -> postService.getPostDetail(zeroPostId, userId, BASIC))
                .isInstanceOf(InvalidUserInputException.class);

        assertThatThrownBy(() -> postService.getPostDetail(negativePostId, userId, BASIC))
                .isInstanceOf(InvalidUserInputException.class);
    }

    @DisplayName("게시물 상세 보기를 할 때, userId는 양수여야 한다.")
    @Test
    void getPostDetail_invalidUserId() {
        // given
        long postId = 1L;

        long zeroUserId = 0L;
        long negativeUserId = -1L;

        // when & then
        assertThatThrownBy(() -> postService.getPostDetail(postId, zeroUserId, BASIC))
                .isInstanceOf(InvalidUserInputException.class);

        assertThatThrownBy(() -> postService.getPostDetail(postId, negativeUserId, BASIC))
                .isInstanceOf(InvalidUserInputException.class);
    }
}