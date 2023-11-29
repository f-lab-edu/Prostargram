package flab.project.service;

import flab.project.config.exception.InvalidUserInputException;
import flab.project.data.dto.model.AddDebatePostRequest;
import flab.project.mapper.PostMapper;
import flab.project.data.dto.model.BasicPost;
import flab.project.data.dto.model.DebatePost;
import flab.project.data.dto.model.PollPost;
import flab.project.mapper.UserMapper;
import org.apache.ibatis.javassist.NotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThatCode;
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

    @DisplayName("게시물을 추가할 수 있다.")
    @Test
    void addPost() {
        // given
        long userId = 1;

        AddDebatePostRequest addPostRequest = AddDebatePostRequest.builder()
                .postId(1L)
                .content("게시물 내용")
                .hashTagNames(Set.of("#test1", "#test2"))
                .optionContents(Set.of("내용물1", "내용물2"))
                .build();

        // when & then
        postService.addPost(userId, addPostRequest);
        then(postMapper).should().save(userId, addPostRequest);
    }

    @DisplayName("게시물을 추가할 때, userId가 양수가 아니면 InvalidUserInput 예외를 던진다.")
    @Test
    void addPost_negativeUserId() {
        // given
        long userId = -1;

        AddDebatePostRequest addPostRequest = AddDebatePostRequest.builder()
                .postId(1L)
                .content("게시물 내용")
                .hashTagNames(Set.of("#test1", "#test2"))
                .optionContents(Set.of("내용물1", "내용물2"))
                .build();

        // when & then
        assertThatCode(() -> postService.addPost(userId, addPostRequest))
                .isExactlyInstanceOf(InvalidUserInputException.class);
    }

    @DisplayName("일반 게시물 상세 보기를 할 수 있다.")
    @Test
    void getBasicPostDetail() throws NotFoundException {
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
    void getDebatePostDetail() throws NotFoundException {
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
    void getPollPostDetail() throws NotFoundException {
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