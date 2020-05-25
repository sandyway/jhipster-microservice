package online.kehan.connect.flow.repository;

import online.kehan.connect.flow.domain.ConnectIntent;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ConnectIntent entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConnectIntentRepository extends JpaRepository<ConnectIntent, Long> {
}
