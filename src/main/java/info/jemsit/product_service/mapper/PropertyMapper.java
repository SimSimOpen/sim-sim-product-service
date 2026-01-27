package info.jemsit.product_service.mapper;

import info.jemsit.common.dto.request.product.PropertyRequestDTO;
import info.jemsit.common.dto.response.product.PropertyResponseDTO;
import info.jemsit.product_service.data.model.Property;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PropertyMapper {

    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "pictures", ignore = true)
    @Mapping(target = "mapLocation", ignore = true)
    @Mapping(target = "isDeleted", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "agentID", ignore = true)
    @Mapping(target = "agent", ignore = true)
    Property toEntity(PropertyRequestDTO resident);

    @Mapping(target = "category", ignore = true)
    @Mapping(target = "agentId", ignore = true)
    @Mapping(target = "ForSale", ignore = true)
    PropertyResponseDTO toDto(Property property);
}
