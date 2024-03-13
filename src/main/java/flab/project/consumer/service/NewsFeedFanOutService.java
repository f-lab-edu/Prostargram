package flab.project.consumer.service;

import flab.project.data.dto.FanOutMessage;
import flab.project.utils.NewsFeedRedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class NewsFeedFanOutService {

    private final NewsFeedRedisUtil newsFeedRedisUtil;

    public void fanOut(FanOutMessage fanOutMessage) {
        newsFeedRedisUtil.addForMultipleUsers(fanOutMessage.getFollowerIds(), fanOutMessage.getPostId());
    }
}
