package com.ead.notificationhex.adapters.outbound.persistence;

import com.ead.notificationhex.adapters.outbound.persistence.entities.NotificationEntity;
import com.ead.notificationhex.core.domain.NotificationDomain;
import com.ead.notificationhex.core.domain.PageInfo;
import com.ead.notificationhex.core.domain.enums.NotificationStatus;
import com.ead.notificationhex.core.ports.NotificationPersistencePort;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class NotificationPersistencePortImpl implements NotificationPersistencePort {
    private final NotificationJpaRepository notificationJpaRepository;
    private final ModelMapper modelMapper;

    public NotificationPersistencePortImpl(NotificationJpaRepository notificationJpaRepository, ModelMapper modelMapper) {
        this.notificationJpaRepository = notificationJpaRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public NotificationDomain save(NotificationDomain notificationDomain) {
        NotificationEntity notificationEntity = modelMapper.map(notificationDomain, NotificationEntity.class);
        NotificationEntity notification = this.notificationJpaRepository.save(notificationEntity);
        return modelMapper.map(notification, NotificationDomain.class);
    }

    @Override
    public List<NotificationDomain> findAllByUserIdAndNotificationStatus(UUID userId, NotificationStatus notificationStatus, PageInfo pageInfo) {
        Pageable pageable = PageRequest.of(pageInfo.getPageNumber(), pageInfo.getPageSize());
        return this.notificationJpaRepository.findAllByUserIdAndNotificationStatus(userId, NotificationStatus.CRAETED,pageable)
                .map(notificationEntity -> modelMapper.map(notificationEntity, NotificationDomain.class))
                .getContent();
    }

    @Override
    public Optional<NotificationDomain> findByNotificationIdAndUserId(UUID notificationId, UUID userId) {
        return this.notificationJpaRepository.findByNotificationIdAndUserId(notificationId,userId)
                .map(notificationEntity -> modelMapper.map(notificationEntity,NotificationDomain.class));
    }
}
