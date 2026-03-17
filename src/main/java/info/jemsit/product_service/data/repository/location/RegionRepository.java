package info.jemsit.product_service.data.repository.location;

import info.jemsit.product_service.data.model.location.Region;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegionRepository extends JpaRepository<Region, Long> {
}
