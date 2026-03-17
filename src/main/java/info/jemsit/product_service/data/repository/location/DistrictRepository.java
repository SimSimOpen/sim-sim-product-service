package info.jemsit.product_service.data.repository.location;

import info.jemsit.product_service.data.model.location.District;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DistrictRepository extends JpaRepository<District, Long> {
    List<District> findByRegionId(Long regionId);
}
