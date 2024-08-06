package flab.project.domain.feed.service;

import flab.project.domain.feed.model.FanOutMessage;
import flab.project.domain.user.service.FollowService;
import flab.project.utils.NewsFeedRedisUtil;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class NewsFeedFanOutService {

    private final NewsFeedRedisUtil newsFeedRedisUtil;
    private final FollowService followService;

    public void fanOut(FanOutMessage fanOutMessage) {
        List<Long> followerIds = followService.getFollowerIds(fanOutMessage.getUserId());

        newsFeedRedisUtil.addForMultipleUsers(followerIds, fanOutMessage.getPostId());
    }
}
