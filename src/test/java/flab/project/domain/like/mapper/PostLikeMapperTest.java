package flab.project.domain.like.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import flab.project.domain.like.model.PostLike;
import flab.project.domain.post.mapper.PostMapper;
import flab.project.domain.post.model.AddBasicPostRequest;
import flab.project.domain.user.enums.LoginType;
import flab.project.domain.user.mapper.SignUpMapper;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

@Sql("classpath:tableInit.sql")
@ActiveProfiles("test")
@MybatisTest
class PostLikeMapperTest {

    @Autowired
    SignUpMapper signUpMapper;

    @Autowired
    PostMapper postMapper;

    @Autowired
    PostLikeMapper postLikeMapper;

    @DisplayName("게시물에 좋아요를 할 수 있다.")
    @Test
    void addPostLike() {
        // given
        long postId = 1L;
        long userId = 1L;

        signUpMapper.addUser("no-reply@test.com", "username", "password", LoginType.NORMAL);

        AddBasicPostRequest basicPost = AddBasicPostRequest.builder()
                .postId(postId)
                .content("content")
                .hashTagNames(Set.of("hashtag"))
                .build();
        postMapper.save(postId, basicPost);

        // when
        postLikeMapper.addPostLike(postId, userId);

        // then
        boolean hasLike = postLikeMapper.hasLike(postId, userId);
        Assertions.assertTrue(hasLike);
    }

    @DisplayName("게시물에 좋아요 취소를 할 수 있다.")
    @Test
    void cancelPostLike() {
        // given
        long postId = 1L;
        long userId = 1L;

        signUpMapper.addUser("no-reply@test.com", "username", "password", LoginType.NORMAL);

        AddBasicPostRequest basicPost = AddBasicPostRequest.builder()
                .postId(postId)
                .content("content")
                .hashTagNames(Set.of("hashtag"))
                .build();
        postMapper.save(postId, basicPost);

        postLikeMapper.addPostLike(postId, userId);

        // when
        postLikeMapper.cancelLike(postId, userId);

        // then
        boolean hasLike = postLikeMapper.hasLike(postId, userId);
        Assertions.assertFalse(hasLike);
    }

    @DisplayName("좋아요 상태의 게시물 id만 리턴한다.")
    @Test
    void getPostsHavingLike() {
        // given
        long postId1 = 1L;
        long postId2 = 1L;
        long userId = 1L;

        signUpMapper.addUser("no-reply@test.com", "username", "password", LoginType.NORMAL);

        AddBasicPostRequest basicPost1 = AddBasicPostRequest.builder()
                .postId(postId1)
                .content("content")
                .hashTagNames(Set.of("hashtag"))
                .build();
        AddBasicPostRequest basicPost2 = AddBasicPostRequest.builder()
                .postId(postId2)
                .content("content")
                .hashTagNames(Set.of("hashtag"))
                .build();

        postMapper.save(userId, basicPost1);
        postMapper.save(userId, basicPost2);

        postLikeMapper.addPostLike(postId1, userId);

        Set<Long> postsHavingLike = postLikeMapper.getPostsHavingLike(List.of(postId1, postId2), userId);

        // then
        assertThat(postsHavingLike).hasSize(1).contains(1L);
    }
}