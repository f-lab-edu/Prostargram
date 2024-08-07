package flab.project.mapper;

import flab.project.domain.like.mapper.PostLikeMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@Sql("classpath:tableInit.sql")
@ActiveProfiles("test")
@MybatisTest
class PostLikeMapperTest {

    @Autowired
    private PostLikeMapper postLikeMapper;

    @DisplayName("게시물에 좋아요를 추가할 수 있다.")
    @Test
    void addPostLike() {
        // given
        long postId = 1L;
        long userId = 1L;

        // when
        int result = postLikeMapper.addPostLike(postId, userId);

        // then
        assertThat(result).isEqualTo(1);
    }

    @DisplayName("특정 사용자가 게시물에 좋아요를 했는지 확인할 수 있다.")
    @Test
    void hasLike() {
        // given
        long postId = 1L;
        long userId = 1L;
        postLikeMapper.addPostLike(postId, userId);

        // when
        boolean result = postLikeMapper.hasLike(postId, userId);

        // then
        assertThat(result).isTrue();
    }

    @DisplayName("게시물의 좋아요를 취소할 수 있다.")
    @Test
    void cancelLike() {
        // given
        long postId = 1L;
        long userId = 1L;
        postLikeMapper.addPostLike(postId, userId);

        // when
        postLikeMapper.cancelLike(postId, userId);

        // then
        boolean result = postLikeMapper.hasLike(postId, userId);
        assertThat(result).isFalse();
    }
}
