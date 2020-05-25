package online.kehan.connect.analytic.collection.repository;

import online.kehan.connect.analytic.collection.domain.ConnectConfig;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ConnectConfig entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConnectConfigRepository extends JpaRepository<ConnectConfig, Long> {
}
