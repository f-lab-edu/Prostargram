package flab.project.service;

import flab.project.config.baseresponse.SuccessResponse;
import flab.project.mapper.LikeMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ActiveProfiles("test")
@RequiredArgsConstructor
@Transactional
@SpringBootTest
public class LikeServiceIntegrationTest {

    @Autowired
    private LikeMapper likeMapper;

    @Autowired
    private LikeService likeService;

    @DisplayName("게시물에 좋아요 추가")
    @Test
    public void addLikeToPost() {
        // given
        long postId = 1L;
        long userId = 2L;

        // when
        SuccessResponse response = likeService.addPostLike(postId, userId);

        // then
        assertThat(response).extracting("isSuccess").isEqualTo(true);
    }

    @DisplayName("존재하지 않는 게시물에 좋아요 추가")
    @Test
    public void addLikeToNonExistentPost() {
        // given
        long nonExistPostId = 9999L;
        long userId = 1L;

        // when then
        assertThatThrownBy(() -> likeService.addPostLike(userId, nonExistPostId)).isInstanceOf(RuntimeException.class);
    }

    @DisplayName("삭제된 게시물에 좋아요 추가")
    @Test
    public void addLikeToDeletedPost() {
        // given
        long deletedPostId = 100L;
        long userId = 1L;

        // when then
        assertThatThrownBy(() -> likeService.addPostLike(userId, deletedPostId)).isInstanceOf(RuntimeException.class);
    }

    @DisplayName("사용자가 여러 피드에 좋아요 추가")
    @Test
    public void userLikeToFeed() {
        // given
        long userId = 1L;
        long postId1 = 1L;
        long postId2 = 2L;
        long postId3 = 3L;

        likeService.addPostLike(userId, postId1);
        likeService.addPostLike(userId, postId2);
        likeService.addPostLike(userId, postId3);

        // when
        SuccessResponse response = likeService.addPostLike(userId, postId1);

        // then
        assertThat(response).extracting("isSuccess").isEqualTo(true);

    }
}
