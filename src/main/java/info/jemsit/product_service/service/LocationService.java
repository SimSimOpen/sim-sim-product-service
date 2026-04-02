package info.jemsit.product_service.service;

import java.util.List;
import info.jemsit.common.dto.response.product.propeprty.DistrictsResponseDTO;
import info.jemsit.common.dto.response.product.propeprty.PlacesResponseDTO;
import info.jemsit.common.dto.response.product.propeprty.RegionsResponseDTO;

public interface LocationService {
    List<RegionsResponseDTO> getRegions();
    List<DistrictsResponseDTO> getDistrictsByRegionId(Long regionId);
    List<PlacesResponseDTO> getPlacesByDistrictId(Long districtId);
}
