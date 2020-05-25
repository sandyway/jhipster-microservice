package online.kehan.connect.analytic.collection.repository;

import online.kehan.connect.analytic.collection.domain.Campaign;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Campaign entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CampaignRepository extends JpaRepository<Campaign, Long> {
}
