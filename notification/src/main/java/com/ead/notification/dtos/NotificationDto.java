package com.ead.notification.dtos;

import com.ead.notification.enums.NotificationStatus;
import lombok.Data;

@Data
public class NotificationDto {
    private NotificationStatus notificationStatus;
}
