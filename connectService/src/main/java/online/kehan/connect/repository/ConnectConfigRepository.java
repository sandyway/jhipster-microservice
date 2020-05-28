package online.kehan.connect.repository;

import online.kehan.connect.domain.ConnectConfig;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ConnectConfig entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConnectConfigRepository extends JpaRepository<ConnectConfig, Long> {
}
