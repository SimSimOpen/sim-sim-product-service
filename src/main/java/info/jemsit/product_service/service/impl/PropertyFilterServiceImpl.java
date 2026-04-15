package info.jemsit.product_service.service.impl;

import info.jemsit.common.UserContext;
import info.jemsit.common.clients.auth.AuthServiceClient;
import info.jemsit.common.clients.media.MediaServiceClient;
import info.jemsit.common.data.enums.Roles;
import info.jemsit.common.dto.request.product.property.PropertyFilterRequestDTO;
import info.jemsit.common.dto.response.product.propeprty.PropertyResponseDTO;
import info.jemsit.product_service.data.dao.PropertyDAO;
import info.jemsit.product_service.data.model.property.Property;
import info.jemsit.product_service.data.model.property.PropertyLocation;
import info.jemsit.product_service.mapper.PropertyMapper;
import info.jemsit.product_service.service.PropertyFilterService;
import info.jemsit.product_service.service.RabbitMQService;
import jakarta.persistence.criteria.Predicate;
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


        if (filterRequest.search() != null && !filterRequest.search().isBlank()) {
            Page<Property> result = propertyDAO.search(
                    filterRequest.search(),
                    filterRequest.listingStatus() != null ? filterRequest.listingStatus().name() : null,
                    filterRequest.type() != null ? filterRequest.type().name() : null,
                    filterRequest.category() != null ? filterRequest.category().name() : null,
                    filterRequest.offerType() != null ? filterRequest.offerType().name() : null,
                    filterRequest.occupancyStatus() != null ? filterRequest.occupancyStatus().name() : null,
                    pageable
            );
            return result.map(p -> propertyMapper.toDtoWithShortAddress(p, getLocationList(p.getLocation())));
        }

        Specification<Property> specification = (root, query, criteriaBuilder) -> {

            List<Predicate> predicates = new java.util.ArrayList<>();

            if (UserContext.getRoles() != null && UserContext.getRoles().contains(Roles.AGENT)) {
                System.out.println("User is Agent");
                predicates.add(criteriaBuilder.equal(root.get("agentID"), UserContext.getUserId()));
            }

            if (filterRequest.category() != null) {
                predicates.add(criteriaBuilder.equal(root.get("category"), filterRequest.category()));
            }
            if (filterRequest.type() != null) {
                predicates.add(criteriaBuilder.equal(root.get("type"), filterRequest.type()));
            }
            if (filterRequest.offerType() != null) {
                predicates.add(criteriaBuilder.equal(root.get("offerType"), filterRequest.offerType()));
            }

            if (filterRequest.listingStatus() != null) {
                predicates.add(criteriaBuilder.equal(root.get("listingStatus"), filterRequest.listingStatus()));
            }

            if (filterRequest.occupancyStatus() != null) {
                predicates.add(criteriaBuilder.equal(root.get("occupancyStatus"), filterRequest.occupancyStatus()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        Page<Property> propertiesPage = propertyDAO.filter(specification, pageable);
        return propertiesPage.map((p) -> propertyMapper.toDtoWithShortAddress(p, getLocationList(p.getLocation())));
    }

    public List<String> getLocationList(PropertyLocation location) {
        if (location == null) return List.of("", "", "");
        return propertyDAO.getPropertyAddressShort(location.getId());  // { region, district, place, address }
    }
}
