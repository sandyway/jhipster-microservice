package online.kehan.connect.analytic.collection.repository;

import online.kehan.connect.analytic.collection.domain.ConnectEvent;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ConnectEvent entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConnectEventRepository extends JpaRepository<ConnectEvent, Long> {
}
