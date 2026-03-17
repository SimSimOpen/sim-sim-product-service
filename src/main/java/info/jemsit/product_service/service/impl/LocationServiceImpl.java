package info.jemsit.product_service.service.impl;

import info.jemsit.product_service.data.dao.LocationDAO;
import info.jemsit.product_service.data.model.location.Place;
import info.jemsit.product_service.mapper.LocationMapper;
import info.jemsit.product_service.service.DistrictsResponseDTO;
import info.jemsit.product_service.service.LocationService;
import info.jemsit.product_service.service.PlacesResponseDTO;
import info.jemsit.product_service.service.RegionsResponseDTO;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class LocationServiceImpl implements LocationService {
    private final LocationDAO locationDAO;
    private final LocationMapper locationMapper;

    @Override
    public List<RegionsResponseDTO> getRegions() {
        return locationDAO.getAllRegions().stream().map(locationMapper::toRegionResponseDTO).toList();
    }

    @Override
    public List<DistrictsResponseDTO> getDistrictsByRegionId(Long regionId) {
        return locationDAO.getDistrictsByRegionId(regionId).stream().map(locationMapper::toDistrictResponseDTO).toList();
    }

    @Override
    public List<PlacesResponseDTO> getPlacesByDistrictId(Long districtId) {
        List<Place> placesByDistrictId = locationDAO.getPlacesByDistrictId(districtId);
        System.out.println("Places for districtId " + districtId + ": " + placesByDistrictId);
        return placesByDistrictId.stream().map(locationMapper::toPlaceResponseDTO).toList();
    }
}
