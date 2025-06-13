package com.nure.makohon.bohdan.arkpz_pzpi_22_6_makohon_bohdan.service;
import com.nure.makohon.bohdan.arkpz_pzpi_22_6_makohon_bohdan.dto.NotificationDTO;
import com.nure.makohon.bohdan.arkpz_pzpi_22_6_makohon_bohdan.entity.Notification;
import com.nure.makohon.bohdan.arkpz_pzpi_22_6_makohon_bohdan.entity.Sensor;
import com.nure.makohon.bohdan.arkpz_pzpi_22_6_makohon_bohdan.entity.User;
import com.nure.makohon.bohdan.arkpz_pzpi_22_6_makohon_bohdan.repository.NotificationRepository;
import com.nure.makohon.bohdan.arkpz_pzpi_22_6_makohon_bohdan.repository.SensorRepository;
import com.nure.makohon.bohdan.arkpz_pzpi_22_6_makohon_bohdan.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    public List<Notification> findAllNotifications() {
        return notificationRepository.findAll();
    }

    public Optional<Notification> findNotificationById(Long id) {
        return notificationRepository.findById(id);
    }

    public List<Notification> findNotificationsByUserId(Integer userId) {
        return notificationRepository.findByUserUserId(userId);
    }

    public List<Notification> findNotificationsBySensorId(Integer sensorId) {
        return notificationRepository.findBySensorId(sensorId);
    }

    public Notification saveNotification(Notification notification) {
        return notificationRepository.save(notification);
    }

    public void deleteNotificationById(Long id) {
        notificationRepository.deleteById(id);
    }

    @Autowired
    private SensorRepository sensorRepository;

    @Autowired
    private UserRepository userRepository;

    public Notification createNotification(Integer userId, Integer sensorId, NotificationDTO notificationDTO) {
        // Fetch the user
        User user = userRepository.findById(userId).orElseThrow(() ->
                new IllegalArgumentException("User with ID " + userId + " not found"));

        // Fetch the sensor
        Sensor sensor = sensorRepository.findById(sensorId).orElseThrow(() ->
                new IllegalArgumentException("Sensor with ID " + sensorId + " not found"));

        // Convert DTO to Entity
        Notification notification = new Notification();
        notification.setUser(user);
        notification.setSensor(sensor);
        notification.setNotificationType(Notification.NotificationType.valueOf(notificationDTO.getNotificationType().toUpperCase()));
        notification.setValue(notificationDTO.getValue());
        notification.setThreshold(notificationDTO.getThreshold());

        // Save the notification
        return notificationRepository.save(notification);
    }
}



