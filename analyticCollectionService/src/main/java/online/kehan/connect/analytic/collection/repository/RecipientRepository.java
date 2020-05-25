package online.kehan.connect.analytic.collection.repository;

import online.kehan.connect.analytic.collection.domain.Recipient;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Recipient entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RecipientRepository extends JpaRepository<Recipient, Long> {
}
