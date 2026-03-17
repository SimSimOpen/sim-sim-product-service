package info.jemsit.product_service.service;

import java.util.List;

public interface LocationService {
    List<RegionsResponseDTO> getRegions();
    List<DistrictsResponseDTO> getDistrictsByRegionId(Long regionId);
    List<PlacesResponseDTO> getPlacesByDistrictId(Long districtId);
}
