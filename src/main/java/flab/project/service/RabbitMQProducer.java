package flab.project.service;

import flab.project.data.dto.FanOutMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class RabbitMQProducer {

    @Value("{spring.rabbitmq.template.exchange}")
    private String exchange;

    @Value("{spring.rabbitmq.template.routing-key}")
    private String routingKey;

    private final RabbitTemplate rabbitTemplate;

    public void sendMessage(FanOutMessage fanOutMessage) {
        rabbitTemplate.convertAndSend(exchange, routingKey, fanOutMessage);
        log.info("postMessage = " + fanOutMessage);
    }
}