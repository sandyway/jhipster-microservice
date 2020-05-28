package online.kehan.connect.repository;

import online.kehan.connect.domain.ConnectIntent;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ConnectIntent entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConnectIntentRepository extends JpaRepository<ConnectIntent, Long> {
}
