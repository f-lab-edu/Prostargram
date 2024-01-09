package flab.project.service;

import flab.project.data.dto.PostWithUser;
import flab.project.data.dto.model.BasePost;
import flab.project.data.dto.model.BasicUser;
import flab.project.data.dto.model.Profile;
import flab.project.utils.NewsFeedRedisUtil;
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

        List<Long> postIds = newsFeedRedisUtil.getPostIds(userId);
        List<BasePost> feeds = postService.getPostsByPostIds(postIds);

        List<Long> writerIds = extractWriterIds(feeds);
        List<Profile> profiles = userService.getUsersByUserIds(writerIds);
        Map<Long, Profile> profileMap = convertToProfileMap(profiles);

        return feeds.stream()
            .map(post -> new PostWithUser(post, profileMap.get(post.getUserId())))
            .toList();
    }

    private static Map<Long, Profile> convertToProfileMap(List<Profile> profiles) {
        return profiles.stream()
            .collect(Collectors.toMap(
                BasicUser::getUserId,
                Function.identity()
            ));
    }

    private static List<Long> extractWriterIds(List<BasePost> feeds) {
        return feeds.stream().map(BasePost::getUserId).toList();
    }
}
