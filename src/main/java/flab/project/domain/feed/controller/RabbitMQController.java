package flab.project.domain.feed.controller;

import flab.project.common.annotation.LoggedInUserId;
import flab.project.config.baseresponse.SuccessResponse;
import flab.project.domain.feed.model.FanOutMessage;
import flab.project.domain.feed.service.RabbitMQProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class RabbitMQController {

    private final RabbitMQProducer rabbitMQProducer;

    @PostMapping(value = "/send")
    public SuccessResponse<FanOutMessage> send(
            @LoggedInUserId Long userId,
            @RequestParam FanOutMessage fanOutMessage
    ) {
        rabbitMQProducer.sendMessage(fanOutMessage);

        return new SuccessResponse<>(fanOutMessage);
    }
}