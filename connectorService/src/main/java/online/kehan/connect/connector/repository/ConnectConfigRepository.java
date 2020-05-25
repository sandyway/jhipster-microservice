package online.kehan.connect.connector.repository;

import online.kehan.connect.connector.domain.ConnectConfig;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ConnectConfig entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConnectConfigRepository extends JpaRepository<ConnectConfig, Long> {
}
