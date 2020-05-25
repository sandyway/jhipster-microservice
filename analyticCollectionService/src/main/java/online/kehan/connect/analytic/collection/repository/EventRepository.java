package online.kehan.connect.analytic.collection.repository;

import online.kehan.connect.analytic.collection.domain.Event;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Event entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
}
