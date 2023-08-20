package com.ead.notificationhex.adapters.dtos;

import com.ead.notificationhex.core.domain.enums.NotificationStatus;
import lombok.Data;

@Data
public class NotificationDto {
    private NotificationStatus notificationStatus;
}
