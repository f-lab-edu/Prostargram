package flab.project.domain.post.mapper;

import static org.assertj.core.api.Assertions.assertThat;

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
class PostHashTagMapperTest {

    @Autowired
    PostHashTagMapper postHashTagMapper;

    @DisplayName("게시물 id를 통해 해시태그 id 목록을 가져올 수 있다.")
    @Test
    void findAllByPostId() {
        // given
        long postId = 1L;
        Set<Long> hashTagIds = Set.of(1L, 2L);

        postHashTagMapper.saveAll(postId, hashTagIds);

        // when
        Set<Long> retrievedHashTagIds = postHashTagMapper.findAllByPostId(postId);

        // then
        assertThat(retrievedHashTagIds).isEqualTo(hashTagIds);
    }

    @DisplayName("게시물 해시태그를 삭제한다.")
    @Test
    void removeAll() {
        // given
        long postId = 1L;
        Set<Long> currentHashTagIds = Set.of(1L, 2L, 3L);
        Set<Long> shouldRemoveHashTagIds = Set.of(1L, 2L);
        Set<Long> resultHashTagIds = Set.of(3L);

        postHashTagMapper.saveAll(postId, currentHashTagIds);

        // when
        postHashTagMapper.removeAll(postId, shouldRemoveHashTagIds);

        // then
        Set<Long> retrievedHashTagIds = postHashTagMapper.findAllByPostId(postId);

        assertThat(retrievedHashTagIds).isEqualTo(resultHashTagIds);
    }
}