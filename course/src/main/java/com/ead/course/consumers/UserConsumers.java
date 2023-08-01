package com.ead.course.consumers;

import com.ead.course.dto.UserEventDto;
import com.ead.course.enums.ActionType;
import com.ead.course.enums.UserStatus;
import com.ead.course.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserConsumers {
    @Autowired
    private UserService userService;
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "${ead.broker.queue.userEventQueue.name}" , durable = "true"),
            exchange = @Exchange(value = "${ead.broker.exchange.userEvent}",type = ExchangeTypes.FANOUT)
    ))
    public void listenUserEvent(@Payload UserEventDto userEventDto) {
        var userModel = userEventDto.convertToUserModel();
        try {
            switch (ActionType.valueOf(userEventDto.getActionType())) {
                case CREATE:
                    userModel.setUserStatus(UserStatus.ACTIVE.toString());
                    userService.save(userModel);
                    break;
                case UPDATE:
                    userModel.setUserStatus(UserStatus.ACTIVE.toString());
                    userService.save(userModel);
                    break;
                case DELETE:
                    userService.delete(userModel.getUserId());
                    break;
            }
        } catch (Exception e) {
            log.error("Error: create update or delete user : {} " , e.getMessage());
            e.printStackTrace();
        }
    }
}
