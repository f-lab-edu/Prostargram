package flab.project.domain.feed.service;

import flab.project.domain.feed.model.FanOutMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FanOutService {

    private final RabbitMQProducer rabbitMQProducer;

    public void fanOut(Long userId, long postId) {
        FanOutMessage fanOutMessage = new FanOutMessage(userId, postId);

        rabbitMQProducer.sendMessage(fanOutMessage);
    }
}
