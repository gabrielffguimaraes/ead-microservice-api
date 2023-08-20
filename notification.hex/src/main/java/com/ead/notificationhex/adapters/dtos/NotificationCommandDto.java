package com.ead.notificationhex.adapters.dtos;

import com.ead.notificationhex.core.domain.NotificationDomain;
import lombok.Data;
import org.modelmapper.ModelMapper;

import java.util.UUID;

@Data
public class NotificationCommandDto {
    private String title;
    private String message;
    private UUID userId;

    public static NotificationCommandDto of(NotificationDomain notificationModel) {
        return new ModelMapper().map(notificationModel,NotificationCommandDto.class);
    }
}
