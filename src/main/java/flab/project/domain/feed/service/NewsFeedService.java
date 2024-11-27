package flab.project.domain.feed.service;

import flab.project.common.model.PaginationModel;
import flab.project.domain.feed.model.PostIdsAndHasNext;
import flab.project.domain.post.model.PostWithUser;
import flab.project.domain.post.model.BasePost;
import flab.project.domain.user.model.BasicUser;
import flab.project.domain.post.service.PostService;
import flab.project.domain.user.service.UserService;
import flab.project.utils.NewsFeedRedisUtil;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
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

    public PaginationModel<List<PostWithUser>> getFeeds(long userId) {
        // Todo 비활성화 유저 같은 경우는, NewsFeedCache에 데이터가 없을수도 있어요.
        // Todo 막 가입한 유저.
        try {
            PostIdsAndHasNext postIdsAndHasNext = newsFeedRedisUtil.getPostIds(userId);
            if (postIdsAndHasNext.isEmpty()) {
                return new PaginationModel<>(Collections.emptyList(), false);
            }

            List<BasePost> posts = postService.lookAsidePosts(postIdsAndHasNext.getPostIds(), userId);
            List<Long> writerIds = extractWriterIds(posts);
            Map<Long, BasicUser> profileMap = generateProfileMap(writerIds);
            List<PostWithUser> feeds = posts.stream()
                    .map(post -> new PostWithUser(post, profileMap.get(post.getUserId())))
                    .toList();

            return new PaginationModel<>(feeds, postIdsAndHasNext.hasNext());
        } catch (Exception e) {
            e.printStackTrace();
            return new PaginationModel<>(Collections.emptyList(), false);
        }
    }

    private Map<Long, BasicUser> generateProfileMap(List<Long> writerIds) {
        Set<BasicUser> users = userService.getUsersByUserIds(writerIds);

        return convertToProfileMap(users);
    }

    private Map<Long, BasicUser> convertToProfileMap(Set<BasicUser> profiles) {
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
