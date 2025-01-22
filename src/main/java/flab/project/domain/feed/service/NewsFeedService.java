package flab.project.domain.feed.service;

import flab.project.common.model.PaginationModel;
import flab.project.domain.feed.model.PostIdsAndHasNext;
import flab.project.domain.post.model.PostWithUser;
import flab.project.domain.post.service.PostService;
import flab.project.domain.user.service.UserService;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class NewsFeedService extends FeedService {

    public NewsFeedService(
            FeedIdsReader newsFeedRedisUtil,
            UserService userService,
            PostService postService
    ) {
        super(newsFeedRedisUtil, userService, postService);
    }

    public PaginationModel<List<PostWithUser>> getFeeds(long userId, Long page) {
        // Todo 비활성화 유저 같은 경우는, NewsFeedCache에 데이터가 없을수도 있어요.
        // Todo 막 가입한 유저.
        PostIdsAndHasNext postIdsAndHasNext = getPostIds(userId, page);

        return getFeeds(postIdsAndHasNext, userId);
    }
}
