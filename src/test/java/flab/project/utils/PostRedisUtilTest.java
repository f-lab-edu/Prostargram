package flab.project.utils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssertions.tuple;
import static org.junit.jupiter.api.Assertions.*;

import flab.project.data.dto.model.BasePost;
import flab.project.data.dto.model.BasicPost;
import flab.project.data.enums.PostType;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PostRedisUtilTest {

    @Autowired
    private PostRedisUtil postRedisUtil;

    @DisplayName("피드 가져오기.")
    @Test
    void getFeeds() {
        List<Long> postIds = List.of(1L, 2L, 3L);
        List<BasicPost> posts = List.of(
            createPost(1L, 1L),
            createPost(2L, 2L),
            createPost(3L, 3L)
        );

        postRedisUtil.save(posts.get(0));
        postRedisUtil.save(posts.get(1));
        postRedisUtil.save(posts.get(2));

        List<BasePost> retrievedPosts = postRedisUtil.getFeeds(postIds);

        Assertions.assertThat(retrievedPosts)
            .extracting("postId", "userId")
            .contains(
                tuple(1L, 1L),
                tuple(2L, 2L),
                tuple(3L, 3L)
            );
    }

    @DisplayName("피드를 가져올 때, 없는 post가 있으면 제외하고 가져온다.")
    @Test
    void getFeeds_withNonExistPosts() {
        List<Long> postIds = List.of(1L, 2L, 3L);
        List<BasicPost> posts = List.of(
            createPost(1L, 1L),
            createPost(2L, 2L),
            createPost(3L, 3L)
        );

        postRedisUtil.save(posts.get(0));
        postRedisUtil.save(posts.get(2));

        List<BasePost> retrievedPosts = postRedisUtil.getFeeds(postIds);

        assertThat(retrievedPosts.get(0))
            .extracting("postId", "userId")
            .contains(1L, 1L);
        assertThat(retrievedPosts.get(1)).isNull();
        assertThat(retrievedPosts.get(2))
            .extracting("postId", "userId")
            .contains(3L, 3L);
    }

    private BasicPost createPost(long postId, long userId) {
        return BasicPost.builder()
            .postId(postId)
            .userId(userId)
            .content("test")
            .hashTagNames(new HashSet<>())
            .postType(PostType.BASIC)
            .createdAt(new Timestamp(System.currentTimeMillis()))
            .contentImgUrls(Set.of("https://"))
            .build();
    }
}