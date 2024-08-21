package biz.picosoft.demo.repository;

import biz.picosoft.demo.domain.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUsernameAndSeenFalse(String username);
    List<Notification> findByUsername(String username);
    long countBySeen(boolean readStatus);
    long countByUsernameAndSeenFalse(String username);
}
