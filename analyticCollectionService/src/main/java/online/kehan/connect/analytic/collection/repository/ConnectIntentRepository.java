package online.kehan.connect.analytic.collection.repository;

import online.kehan.connect.analytic.collection.domain.ConnectIntent;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ConnectIntent entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConnectIntentRepository extends JpaRepository<ConnectIntent, Long> {
}
