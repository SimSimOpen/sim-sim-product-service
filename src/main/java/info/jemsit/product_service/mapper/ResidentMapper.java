package info.jemsit.product_service.mapper;

import info.jemsit.common.dto.request.product.ResidentRequestDTO;
import info.jemsit.common.dto.response.product.ResidentResponseDTO;
import info.jemsit.product_service.data.model.Resident;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ResidentMapper {

    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "pictures", ignore = true)
    @Mapping(target = "mapLocation", ignore = true)
    @Mapping(target = "isDeleted", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "agentID", ignore = true)
    @Mapping(target = "agent", ignore = true)
    Resident toEntity(ResidentRequestDTO resident);

    @Mapping(target = "category", ignore = true)
    @Mapping(target = "agentId", ignore = true)
    @Mapping(target = "ForSale", ignore = true)
    ResidentResponseDTO toDto(Resident resident);
}
