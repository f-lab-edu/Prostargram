package flab.project.domain.feed.service;

import flab.project.domain.post.model.PostWithUser;
import flab.project.domain.post.model.BasePost;
import flab.project.domain.user.model.BasicUser;
import flab.project.domain.post.service.PostService;
import flab.project.domain.user.service.UserService;
import flab.project.utils.NewsFeedRedisUtil;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NewsFeedService {

    private final UserService userService;
    private final PostService postService;
    private final NewsFeedRedisUtil newsFeedRedisUtil;

    public List<PostWithUser> getFeeds(long userId) {
        // Todo 비활성화 유저 같은 경우는, NewsFeedCache에 데이터가 없을수도 있어요.
        // Todo 막 가입한 유저.
        try {
            List<Long> postIds = newsFeedRedisUtil.getPostIds(userId);
            List<BasePost> posts = postService.lookAsidePosts(postIds, userId);
            List<Long> writerIds = extractWriterIds(posts);
            Map<Long, BasicUser> profileMap = generateProfileMap(writerIds);

            return posts.stream()
                .map(post -> new PostWithUser(post, profileMap.get(post.getUserId())))
                .toList();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    private Map<Long, BasicUser> generateProfileMap(List<Long> writerIds) {
        List<BasicUser> users = userService.getUsersByUserIds(writerIds);

        return convertToProfileMap(users);
    }

    private Map<Long, BasicUser> convertToProfileMap(List<BasicUser> profiles) {
        return profiles.stream()
            .collect(Collectors.toMap(
                BasicUser::getUserId,
                Function.identity()
            ));
    }

    private List<Long> extractWriterIds(List<BasePost> feeds) {
        return feeds.stream().map(BasePost::getUserId).toList();
    }
}
