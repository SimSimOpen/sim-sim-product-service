package info.jemsit.product_service.mapper;

import info.jemsit.product_service.data.model.location.District;
import info.jemsit.product_service.data.model.location.Place;
import info.jemsit.product_service.data.model.location.Region;
import info.jemsit.common.dto.response.product.propeprty.DistrictsResponseDTO;
import info.jemsit.common.dto.response.product.propeprty.PlacesResponseDTO;
import info.jemsit.common.dto.response.product.propeprty.RegionsResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LocationMapper {
    RegionsResponseDTO toRegionResponseDTO(Region region);
    DistrictsResponseDTO toDistrictResponseDTO(District district);
    PlacesResponseDTO toPlaceResponseDTO(Place place);
}
