package info.jemsit.product_service.data.dao;

import info.jemsit.product_service.data.model.location.District;
import info.jemsit.product_service.data.model.location.Place;
import info.jemsit.product_service.data.model.location.Region;

import java.util.List;

public interface LocationDAO {
    List<Region> getAllRegions();
    List<District> getDistrictsByRegionId(Long regionId);
    List<Place> getPlacesByDistrictId(Long districtId);
}
