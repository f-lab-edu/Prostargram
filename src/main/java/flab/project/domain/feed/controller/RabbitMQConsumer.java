package flab.project.domain.feed.controller;

import flab.project.domain.feed.service.NewsFeedFanOutService;
import flab.project.domain.feed.model.FanOutMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class RabbitMQConsumer {

    private final NewsFeedFanOutService newsFeedFanOutService;

    @RabbitListener(queues = "${spring.rabbitmq.template.default-receive-queue}")
    public void receiveMessage(FanOutMessage fanOutMessage) {
        newsFeedFanOutService.fanOut(fanOutMessage);

        log.error("### message => {}", fanOutMessage);
    }
}