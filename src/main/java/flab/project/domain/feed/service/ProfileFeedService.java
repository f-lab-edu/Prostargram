package flab.project.domain.feed.service;

import flab.project.domain.post.service.PostService;
import flab.project.domain.user.service.UserService;
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
}
