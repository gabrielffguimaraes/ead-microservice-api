package com.ead.authuser.publishers;

import com.ead.authuser.dtos.UserEventDto;
import com.ead.authuser.enums.ActionType;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
public class UserEventPublisher {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${ead.broker.exchange.userEvent}")
    private String userEvent;


    public void publishUserEvent(UserEventDto userEventDto,ActionType actionType) {
        userEventDto.setActionType(ActionType.CREATE.toString());
        rabbitTemplate.convertAndSend(userEvent,"",userEventDto);
    }
}
