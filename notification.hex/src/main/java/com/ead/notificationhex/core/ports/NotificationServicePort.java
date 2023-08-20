package com.ead.notificationhex.core.ports;


import com.ead.notificationhex.core.domain.NotificationDomain;
import com.ead.notificationhex.core.domain.PageInfo;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface NotificationServicePort {
    NotificationDomain save(NotificationDomain notificationModel);


    List<NotificationDomain> findAllByUserIdAndNotificationStatus(UUID userId, PageInfo page);
    Optional<NotificationDomain> findByIdAndUserId(UUID notificationId, UUID userId);
}
