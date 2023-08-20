package com.ead.notificationhex.adapters.inbound.controllers;

import com.ead.notificationhex.adapters.dtos.NotificationCommandDto;
import com.ead.notificationhex.adapters.dtos.NotificationDto;
import com.ead.notificationhex.adapters.outbound.persistence.entities.NotificationEntity;
import com.ead.notificationhex.core.domain.NotificationDomain;
import com.ead.notificationhex.core.domain.PageInfo;
import com.ead.notificationhex.core.ports.NotificationServicePort;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("api/notification")
public class NotificationController {

    private final NotificationServicePort notificationService;
    private final ModelMapper modelMapper;

    public NotificationController(NotificationServicePort notificationService, ModelMapper modelMapper) {
        this.notificationService = notificationService;
        this.modelMapper = modelMapper;
    }
    @GetMapping
    public void isOnline() {
        log.info("Estamos online !");
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<Page<NotificationCommandDto>> findAll(@PathVariable String userId,
                                                                @PageableDefault(size = 10,page = 0) Pageable page) {
        PageInfo pageInfo = new PageInfo();
        BeanUtils.copyProperties(page,pageInfo);
        List<NotificationCommandDto> notifications = notificationService.findAllByUserIdAndNotificationStatus(UUID.fromString(userId),pageInfo)
                .stream()
                .map(NotificationCommandDto::of)
                .toList();
        return ResponseEntity.ok(new PageImpl<NotificationCommandDto>(notifications,page,notifications.size()));
    }

    @PutMapping("users/{userId}/notifications/{notificationId}")
    public ResponseEntity<Object> updateNotification(@PathVariable(value = "userId") UUID userId,
                                                     @PathVariable(value = "notificationId") UUID notificationId,
                                                     @RequestBody @Valid NotificationDto notificationDto) {

        NotificationEntity notification = this.notificationService.findByIdAndUserId(notificationId,userId)
                .map(notificationDomain -> modelMapper.map(notificationDomain, NotificationEntity.class))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Notification not found !"));



        notification.setNotificationStatus(notificationDto.getNotificationStatus());

        NotificationDomain notificationDomain = notificationService.save(modelMapper.map(notification, NotificationDomain.class));

        return ResponseEntity.ok(modelMapper.map(notificationDomain,NotificationDto.class));
    }
}
