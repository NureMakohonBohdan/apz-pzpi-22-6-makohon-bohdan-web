package com.nure.makohon.bohdan.arkpz_pzpi_22_6_makohon_bohdan.controller;
import com.nure.makohon.bohdan.arkpz_pzpi_22_6_makohon_bohdan.dto.NotificationDTO;
import com.nure.makohon.bohdan.arkpz_pzpi_22_6_makohon_bohdan.entity.Notification;
import com.nure.makohon.bohdan.arkpz_pzpi_22_6_makohon_bohdan.service.NotificationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    // GET: Retrieve all notifications
    @GetMapping
    public List<Notification> getAllNotifications() {
        return notificationService.findAllNotifications();
    }

    // GET: Retrieve a notification by ID
    @GetMapping("/{id}")
    public ResponseEntity<Notification> getNotificationById(@PathVariable Long id) {
        return notificationService.findNotificationById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET: Retrieve notifications by User ID
    @GetMapping("/user/{userId}")
    public List<Notification> getNotificationsByUserId(@PathVariable Integer userId) {
        return notificationService.findNotificationsByUserId(userId);
    }

    // GET: Retrieve notifications by Sensor ID
    @GetMapping("/sensor/{sensorId}")
    public List<Notification> getNotificationsBySensorId(@PathVariable Integer sensorId) {
        return notificationService.findNotificationsBySensorId(sensorId);
    }

    // POST: Create a new notification
    @PostMapping
    public ResponseEntity<Notification> createNotification(@RequestBody Notification notification) {
        Notification savedNotification = notificationService.saveNotification(notification);
        return ResponseEntity.ok(savedNotification);
    }

    // PUT: Update an existing notification
    @PutMapping("/{id}")
    public ResponseEntity<Notification> updateNotification(@PathVariable Long id, @RequestBody Notification notificationDetails) {
        return notificationService.findNotificationById(id).map(notification -> {
            notification.setNotificationType(notificationDetails.getNotificationType());
            notification.setValue(notificationDetails.getValue());
            notification.setThreshold(notificationDetails.getThreshold());
            notification.setStatus(notificationDetails.getStatus());
            Notification updatedNotification = notificationService.saveNotification(notification);
            return ResponseEntity.ok(updatedNotification);
        }).orElse(ResponseEntity.notFound().build());
    }

    // DELETE: Remove a notification by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotification(@PathVariable Long id) {
        if (notificationService.findNotificationById(id).isPresent()) {
            notificationService.deleteNotificationById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // POST: Create a new notification for a specific user and sensor
    @PostMapping("/user/{userId}/sensor/{sensorId}")
    public ResponseEntity<Notification> createNotification(
            @PathVariable Integer userId,
            @PathVariable Integer sensorId,
            @Valid @RequestBody NotificationDTO notificationDTO) {
        Notification savedNotification = notificationService.createNotification(userId, sensorId, notificationDTO);
        return ResponseEntity.ok(savedNotification);
    }
}

