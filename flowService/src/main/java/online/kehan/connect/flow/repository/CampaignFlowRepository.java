package online.kehan.connect.flow.repository;

import online.kehan.connect.flow.domain.CampaignFlow;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CampaignFlow entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CampaignFlowRepository extends JpaRepository<CampaignFlow, Long> {
}
