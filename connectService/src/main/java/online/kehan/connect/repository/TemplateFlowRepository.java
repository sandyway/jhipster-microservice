package online.kehan.connect.repository;

import online.kehan.connect.domain.TemplateFlow;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TemplateFlow entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TemplateFlowRepository extends JpaRepository<TemplateFlow, Long> {
}
