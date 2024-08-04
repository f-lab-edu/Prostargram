package flab.project.domain.like.mapper;

import flab.project.domain.post.mapper.PostMapper;
import flab.project.domain.post.model.AddBasicPostRequest;
import flab.project.domain.user.enums.LoginType;
import flab.project.domain.user.mapper.SignUpMapper;
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
class LikeMapperTest {

    @Autowired
    SignUpMapper signUpMapper;

    @Autowired
    PostMapper postMapper;

    @Autowired
    LikeMapper likeMapper;

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
        likeMapper.addPostLike(postId, userId);

        // then
        boolean hasLike = likeMapper.hasLike(postId, userId);
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

        likeMapper.addPostLike(postId, userId);

        // when
        likeMapper.cancelLike(postId, userId);

        // then
        boolean hasLike = likeMapper.hasLike(postId, userId);
        Assertions.assertFalse(hasLike);
    }
}