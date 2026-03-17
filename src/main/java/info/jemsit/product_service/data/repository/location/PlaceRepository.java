package info.jemsit.product_service.data.repository.location;

import info.jemsit.product_service.data.model.location.Place;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlaceRepository extends JpaRepository<Place, Long> {
    List<Place> findByDistrictId(Long districtId);
}
