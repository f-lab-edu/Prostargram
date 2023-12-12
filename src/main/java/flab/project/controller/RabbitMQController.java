package flab.project.controller;

import flab.project.config.baseresponse.SuccessResponse;
import flab.project.service.RabbitMQProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class RabbitMQController {

    private final RabbitMQProducer rabbitMQProducer;

    @RequestMapping(value = "/send")
    public SuccessResponse<Message> send(Message message) {
        rabbitMQProducer.sendMessage(message);

        return new SuccessResponse<>(message);
    }
}