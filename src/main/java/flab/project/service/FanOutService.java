package flab.project.service;

import flab.project.data.dto.FanOutMessage;
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
