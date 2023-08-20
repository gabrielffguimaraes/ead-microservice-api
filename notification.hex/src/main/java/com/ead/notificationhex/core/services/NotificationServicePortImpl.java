package com.ead.notificationhex.core.services;

import com.ead.notificationhex.core.domain.NotificationDomain;
import com.ead.notificationhex.core.domain.PageInfo;
import com.ead.notificationhex.core.domain.enums.NotificationStatus;
import com.ead.notificationhex.core.ports.NotificationPersistencePort;
import com.ead.notificationhex.core.ports.NotificationServicePort;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class NotificationServicePortImpl implements NotificationServicePort {

    private final NotificationPersistencePort notificationPersistencePort;

    public NotificationServicePortImpl(NotificationPersistencePort notificationPersistencePort) {
        this.notificationPersistencePort = notificationPersistencePort;
    }

    public NotificationDomain save(NotificationDomain notificationModel) {
        return this.notificationPersistencePort.save(notificationModel);
    }

    public List<NotificationDomain> findAllByUserIdAndNotificationStatus(UUID userId, PageInfo pageInfo) {
        return this.notificationPersistencePort.findAllByUserIdAndNotificationStatus(userId, NotificationStatus.CRAETED,pageInfo);
    }

    public Optional<NotificationDomain> findByIdAndUserId(UUID notificationId, UUID userId) {
        return this.notificationPersistencePort.findByNotificationIdAndUserId(notificationId,userId);
    }
}
