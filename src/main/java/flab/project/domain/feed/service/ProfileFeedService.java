package flab.project.domain.feed.service;

import flab.project.common.model.PaginationModel;
import flab.project.domain.feed.model.PostIdsAndHasNext;
import flab.project.domain.post.model.PostWithUser;
import flab.project.domain.post.service.PostService;
import flab.project.domain.user.service.UserService;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ProfileFeedService extends FeedService {

    public ProfileFeedService(
            FeedIdsReader profileFeedReader,
            UserService userService,
            PostService postService
    ) {
        super(profileFeedReader, userService, postService);
    }

    public PaginationModel<List<PostWithUser>> getFeeds(long myUserId, long targetUserId, Long lasyPostId) {
        PostIdsAndHasNext postIdsAndHasNext = getPostIds(targetUserId, lasyPostId);

        return getFeeds(postIdsAndHasNext, myUserId);
    }
}
