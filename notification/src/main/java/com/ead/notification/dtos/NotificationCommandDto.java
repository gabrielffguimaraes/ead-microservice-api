package com.ead.notification.dtos;

import com.ead.notification.models.NotificationModel;
import lombok.Data;
import org.modelmapper.ModelMapper;

import java.util.UUID;

@Data
public class NotificationCommandDto {
    private String title;
    private String message;
    private UUID userId;

    public static NotificationCommandDto of(NotificationModel notificationModel) {
        return new ModelMapper().map(notificationModel,NotificationCommandDto.class);
    }
}
