package flab.project.domain.post.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

@Sql("classpath:tableInit.sql")
@ActiveProfiles("test")
@MybatisTest
class PostImageMapperTest {

    @Autowired
    PostImageMapper postImageMapper;

    @DisplayName("postId로 게시물 이미지 목록을 가져올 수 있다.")
    @Test
    void findAllByPostId() {
        // given
        long postId = 1L;
        Set<String> postImageUrls = Set.of("https://content1.com", "https://content2.com");

        postImageMapper.saveAll(postId, postImageUrls);

        // when
        Set<String> retrievedPostImageUrls = postImageMapper.findAllByPostId(postId);

        // then
        assertThat(postImageUrls).isEqualTo(retrievedPostImageUrls);
    }

    @DisplayName("postId로 게시물 이미지를 삭제할 수 있다.")
    @Test
    void removeAll() {
        // given
        long postId = 1L;
        Set<String> postImageUrls = Set.of("https://content1.com", "https://content2.com", "https://content3.com");

        postImageMapper.saveAll(postId, postImageUrls);

        // when
        postImageMapper.removeAll(postId, Set.of("https://content1.com", "https://content2.com"));

        // then
        Set<String> retrievedPostImageUrls = postImageMapper.findAllByPostId(postId);

        assertThat(retrievedPostImageUrls).isEqualTo(Set.of("https://content3.com"));
    }
}