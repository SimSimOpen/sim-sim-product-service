package info.jemsit.product_service.mapper;

import info.jemsit.common.dto.request.product.property.PropertyRequestDTO;
import info.jemsit.common.dto.response.product.propeprty.PropertyMedia;
import info.jemsit.common.dto.response.product.propeprty.PropertyResponseDTO;
import info.jemsit.product_service.data.model.property.Property;
import info.jemsit.product_service.data.model.property.PropertyMediaData;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface PropertyMapper {

    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "isDeleted", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "agentID", ignore = true)
    @Mapping(target = "agent", ignore = true)
    Property toEntity(PropertyRequestDTO resident);

    @Mapping(target = "medias", expression = "java(mapMedia(property.getMedias()))")
    PropertyResponseDTO toDto(Property property);

    default List<PropertyMedia> mapMedia(List<PropertyMediaData> media) {
        if (media == null) {
            return null;
        }
        return media.stream()
                .map(p->{
                    return new PropertyMedia(p.getId(), "http://localhost:9000"+p.getMediaURL());
                })
                .collect(Collectors.toList());
    }
}
