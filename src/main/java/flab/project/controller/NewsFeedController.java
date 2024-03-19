package flab.project.controller;

import flab.project.common.annotation.LoggedInUserId;
import flab.project.config.baseresponse.SuccessResponse;
import flab.project.data.dto.PostWithUser;
import flab.project.service.NewsFeedService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class NewsFeedController {

    private final NewsFeedService newsFeedService;

    @GetMapping("/feeds")
    public SuccessResponse<List<PostWithUser>> getFeeds(@LoggedInUserId Long userId) {
        List<PostWithUser> feeds = newsFeedService.getFeeds(userId);

        return new SuccessResponse<>(feeds);
    }
}
