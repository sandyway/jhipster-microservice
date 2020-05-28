package online.kehan.connect.repository;

import online.kehan.connect.domain.Reminder;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Reminder entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReminderRepository extends JpaRepository<Reminder, Long> {
}
