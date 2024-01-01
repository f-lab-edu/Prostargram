package flab.project.controller;

import flab.project.config.baseresponse.SuccessResponse;
import flab.project.service.RabbitMQProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class RabbitMQController {

    private final RabbitMQProducer rabbitMQProducer;

    @PostMapping(value = "/send")
    public SuccessResponse<String> send(
            @RequestParam String message
    ) {
        rabbitMQProducer.sendMessage(message);

        return new SuccessResponse<>(message);
    }
}