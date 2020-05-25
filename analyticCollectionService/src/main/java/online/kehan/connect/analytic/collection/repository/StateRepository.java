package online.kehan.connect.analytic.collection.repository;

import online.kehan.connect.analytic.collection.domain.State;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the State entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StateRepository extends JpaRepository<State, Long> {
}
