package flab.project.service;

import flab.project.data.dto.FanOutMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class FanOutService {

    private final FollowService followService;
    private final RabbitMQProducer rabbitMQProducer;

    public void fanOut(Long userId, long postId) {
        List<Long> followerIds = followService.getFollowerIds(userId);
        FanOutMessage fanOutMessage = new FanOutMessage(postId, followerIds);

        rabbitMQProducer.sendMessage(fanOutMessage);
    }
}
