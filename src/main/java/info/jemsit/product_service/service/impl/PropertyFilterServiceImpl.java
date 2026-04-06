package info.jemsit.product_service.service.impl;

import info.jemsit.common.clients.auth.AuthServiceClient;
import info.jemsit.common.clients.media.MediaServiceClient;
import info.jemsit.common.dto.response.product.propeprty.PropertyResponseDTO;
import info.jemsit.product_service.controller.PropertyFilterRequestDTO;
import info.jemsit.product_service.data.dao.PropertyDAO;
import info.jemsit.product_service.data.model.property.Property;
import info.jemsit.product_service.data.model.property.PropertyLocation;
import info.jemsit.product_service.mapper.PropertyMapper;
import info.jemsit.product_service.service.PropertyFilterService;
import info.jemsit.product_service.service.RabbitMQService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;


@RequiredArgsConstructor
public class PropertyFilterServiceImpl implements PropertyFilterService {

    private final PropertyDAO propertyDAO;
    private final PropertyMapper propertyMapper;
    private final MediaServiceClient mediaServiceClient;
    private final AuthServiceClient authServiceClient;
    private final RabbitMQService rabbitMQService;

    @Override
    public Page<PropertyResponseDTO> filterProperties(PropertyFilterRequestDTO filterRequest, Pageable pageable) {

        Specification<Property> specification = (root, query, criteriaBuilder) -> {

            if(filterRequest.category() != null) {
                return criteriaBuilder.equal(root.get("category"), filterRequest.category());
            }
            if (filterRequest.type() != null) {
                return criteriaBuilder.equal(root.get("type"), filterRequest.type());
            }
            if (filterRequest.offerType() != null) {
                return criteriaBuilder.equal(root.get("offerType"), filterRequest.offerType());
            }

            if (filterRequest.listingStatus() != null) {
                return criteriaBuilder.equal(root.get("listingStatus"), filterRequest.listingStatus());
            }

            if(filterRequest.occupancyStatus() != null) {
                return criteriaBuilder.equal(root.get("occupancyStatus"), filterRequest.occupancyStatus());
            }

            return criteriaBuilder.conjunction();
        };

        Page<Property> propertiesPage = propertyDAO.filter(specification, pageable);
        return propertiesPage.map((p) -> propertyMapper.toDtoWithShortAddress(p, getLocationList(p.getLocation())));
    }

    public List<String> getLocationList(PropertyLocation location) {
        if (location == null) return List.of("", "", "");
        return propertyDAO.getPropertyAddressShort(location.getId());  // { region, district, place, address }
    }
}
