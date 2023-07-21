package com.ead.notification.services;

import com.ead.notification.models.NotificationModel;
import com.ead.notification.repositories.NotificationRepository;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;


    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public NotificationModel save(NotificationModel notificationModel) {
        return this.notificationRepository.save(notificationModel);
    }
}
