package online.kehan.connect.repository;

import online.kehan.connect.domain.ConnectEvent;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ConnectEvent entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConnectEventRepository extends JpaRepository<ConnectEvent, Long> {
}
