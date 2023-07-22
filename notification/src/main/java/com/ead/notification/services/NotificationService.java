package com.ead.notification.services;

import com.ead.notification.enums.NotificationStatus;
import com.ead.notification.models.NotificationModel;
import com.ead.notification.repositories.NotificationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;


    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public NotificationModel save(NotificationModel notificationModel) {
        return this.notificationRepository.save(notificationModel);
    }

    public Page<NotificationModel> findAllByUserIdAndNotificationStatus(UUID userId, Pageable page) {
        return this.notificationRepository.findAllByUserIdAndNotificationStatus(userId, NotificationStatus.CRAETED,page);
    }

    public Optional<NotificationModel> findByIdAndUserId(UUID notificationId, UUID userId) {
        return this.notificationRepository.findByNotificationIdAndUserId(notificationId,userId);
    }
}
