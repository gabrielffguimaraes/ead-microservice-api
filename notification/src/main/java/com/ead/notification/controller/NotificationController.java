package com.ead.notification.controller;

import com.ead.notification.dtos.NotificationCommandDto;
import com.ead.notification.dtos.NotificationDto;
import com.ead.notification.enums.NotificationStatus;
import com.ead.notification.models.NotificationModel;
import com.ead.notification.services.NotificationService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("api/notification")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<Page<NotificationCommandDto>> findAll(@PathVariable String userId,
                                                                @PageableDefault(size = 10,page = 0) Pageable page) {
        return ResponseEntity.ok(notificationService.findAllByUserIdAndNotificationStatus(UUID.fromString(userId),page).map(NotificationCommandDto::of));
    }

    @PutMapping("users/{userId}/notifications/{notificationId}")
    public ResponseEntity<Object> updateNotification(@PathVariable(value = "userId") UUID userId,
                                                     @PathVariable(value = "notificationId") UUID notificationId,
                                                     @RequestBody @Valid NotificationDto notificationDto) {
        Optional<NotificationModel> notificationOptional = this.notificationService.findByIdAndUserId(notificationId,userId);
        NotificationModel notification = notificationOptional
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Notification not found !"));

        notification.setNotificationStatus(notificationDto.getNotificationStatus());
        return ResponseEntity.ok(notificationService.save(notification));
    }
}
