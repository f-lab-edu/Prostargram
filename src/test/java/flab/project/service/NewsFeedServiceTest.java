package flab.project.service;

import static flab.project.data.enums.PostType.BASIC;
import static flab.project.data.enums.PostType.DEBATE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import flab.project.data.dto.PostWithUser;
import flab.project.data.dto.model.BasePost;
import flab.project.data.dto.model.BasicPost;
import flab.project.data.dto.model.BasicUser;
import flab.project.data.dto.model.DebatePost;
import flab.project.data.dto.model.Option;
import flab.project.utils.NewsFeedRedisUtil;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class NewsFeedServiceTest {

    @InjectMocks
    NewsFeedService newsFeedService;
    @Mock
    UserService userService;
    @Mock
    PostService postService;
    @Mock
    NewsFeedRedisUtil newsFeedRedisUtil;

    @DisplayName("유저의 피드를 가져올 수 있다.")
    @Nested
    class getFeeds {

        @DisplayName("postService로부터 게시물 데이터를 userService로부터 유저 데이터를 가져와 PostWithUser객체를 만들어 반환한다.")
        @Test
        void getFeeds() {
            //given
            long userId = 3L;
            List<Long> postIds = List.of(1L, 2L);

            BasicPost post1 = createBasicPost(1L, 1L);
            DebatePost post2 = createDebatePost(2L, 2L);
            List<BasePost> posts = List.of(post1, post2);

            BasicUser user1 = createUser(1L);
            BasicUser user2 = createUser(2L);
            List<BasicUser> users = List.of(user1, user2);

            given(newsFeedRedisUtil.getPostIds(userId)).willReturn(postIds);
            given(postService.lookAsidePosts(postIds, userId)).willReturn(posts);
            given(userService.getUsersByUserIds(List.of(1L, 2L))).willReturn(users);

            //when
            List<PostWithUser> feeds = newsFeedService.getFeeds(userId);

            //then
            assertThat(feeds.stream().map(PostWithUser::getBasicUser).toList()).extracting("userId").contains(1L, 2L);
            assertThat(feeds.stream().map(PostWithUser::getPost).toList()).extracting("postId").contains(1L, 2L);
        }
    }

    private static BasicUser createUser(long userId) {
        return BasicUser.builder()
            .userId(userId)
            .userName("tester")
            .profileImgUrl("dummyProfile")
            .build();
    }

    private DebatePost createDebatePost(long postId, long userId) {
        return DebatePost.builder()
            .postId(postId)
            .userId(userId)
            .content("dd")
            .createdAt(Timestamp.valueOf(LocalDateTime.now()))
            .hashTagNames(Set.of("test1"))
            .postType(DEBATE)
            .options(Set.of(new Option(1L, "dummy", 0)))
            .selectedOptionId(1)
            .likeCount(1)
            .commentCount(1)
            .isFollow(true)
            .isLike(true)
            .build();
    }

    private BasicPost createBasicPost(long postId, long userId) {
        return BasicPost.builder()
            .postId(postId)
            .userId(userId)
            .content("dummy")
            .createdAt(Timestamp.valueOf(LocalDateTime.now()))
            .hashTagNames(Set.of("test1"))
            .contentImgUrls(Set.of("imageUrl"))
            .postType(BASIC)
            .isLike(true)
            .isFollow(true)
            .commentCount(1)
            .build();
    }
}