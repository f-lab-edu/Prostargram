package flab.project.consumer.controller;

import flab.project.consumer.service.NewsFeedFanOutService;
import flab.project.data.dto.FanOutMessage;
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