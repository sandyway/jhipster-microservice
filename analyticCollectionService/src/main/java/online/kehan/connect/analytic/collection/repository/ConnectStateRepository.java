package online.kehan.connect.analytic.collection.repository;

import online.kehan.connect.analytic.collection.domain.ConnectState;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ConnectState entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConnectStateRepository extends JpaRepository<ConnectState, Long> {
}
