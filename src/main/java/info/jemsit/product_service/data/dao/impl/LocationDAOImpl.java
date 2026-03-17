package info.jemsit.product_service.data.dao.impl;

import info.jemsit.product_service.data.dao.LocationDAO;
import info.jemsit.product_service.data.model.location.District;
import info.jemsit.product_service.data.model.location.Place;
import info.jemsit.product_service.data.model.location.Region;
import info.jemsit.product_service.data.repository.location.DistrictRepository;
import info.jemsit.product_service.data.repository.location.PlaceRepository;
import info.jemsit.product_service.data.repository.location.RegionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class LocationDAOImpl implements LocationDAO {

    private final RegionRepository regionRepository;
    private final DistrictRepository districtRepository;
    private final PlaceRepository placeRepository;

    @Override
    public List<Region> getAllRegions() {
        log.info("Fetching all regions from the database");
        return regionRepository.findAll();
    }

    @Override
    public List<District> getDistrictsByRegionId(Long regionId) {
        log.info("Fetching districts for regionId: {}", regionId);
        return districtRepository.findByRegionId(regionId);
    }

    @Override
    public List<Place> getPlacesByDistrictId(Long districtId) {
        log.info("Fetching places for districtId: {}", districtId);
        return placeRepository.findByDistrictId(districtId);
    }
}
