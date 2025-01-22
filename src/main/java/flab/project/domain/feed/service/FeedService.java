package flab.project.domain.feed.service;

import flab.project.common.model.PaginationModel;
import flab.project.domain.feed.model.PostIdsAndHasNext;
import flab.project.domain.post.model.BasePost;
import flab.project.domain.post.model.PostWithUser;
import flab.project.domain.post.service.PostService;
import flab.project.domain.user.model.BasicUser;
import flab.project.domain.user.service.UserService;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class FeedService {

    protected final FeedIdsReader feedIdsReader;
    private final UserService userService;
    private final PostService postService;

    protected PaginationModel<List<PostWithUser>> getFeeds(PostIdsAndHasNext postIdsAndHasNext, long myUserId) {
        try {
            if (postIdsAndHasNext.isEmpty()) {
                return new PaginationModel<>(Collections.emptyList(), false);
            }

            List<BasePost> posts = postService.lookAsidePosts(postIdsAndHasNext.getPostIds(), myUserId);
            List<Long> writerIds = extractWriterIds(posts);
            Map<Long, BasicUser> profileMap = generateProfileMap(writerIds);
            List<PostWithUser> feeds = posts.stream()
                    .map(post -> new PostWithUser(post, profileMap.get(post.getUserId())))
                    .sorted(sortByPostIdDesc())
                    .toList();

            return new PaginationModel<>(feeds, postIdsAndHasNext.hasNext());
        } catch (Exception e) {
            return new PaginationModel<>(Collections.emptyList(), false);
        }
    }

    protected PostIdsAndHasNext getPostIds(long userId, Long paginationKey){
        return feedIdsReader.getPostIds(userId, paginationKey);
    }

    private static Comparator<PostWithUser> sortByPostIdDesc() {
        return Comparator.comparingLong(PostWithUser::getPostId).reversed();
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
