package com.equipe4.audace.repository.notification;

import com.equipe4.audace.model.notification.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findAllByUserId(Long userId);
    void deleteAllByUserId(Long userId);
}
