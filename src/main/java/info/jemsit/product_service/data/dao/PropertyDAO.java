package info.jemsit.product_service.data.dao;

import info.jemsit.common.dto.response.product.propeprty.PropertiesStats;
import info.jemsit.product_service.data.model.property.Property;
import info.jemsit.product_service.data.model.property.PropertyMediaData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

public interface PropertyDAO {
    Property save(Property property);
    Property update(Property property);
    Optional<Property> findById(Long id);
    void deleteById(Long id);

    Page<Property> findAll(Pageable pageable);

    Optional<PropertyMediaData> getPropertyMediaById(Long id);

    void deletePropertyMediaById(Long id);
    List<String> getPropertyAddressShort(long locationId);

    Page<Property> findByAgentID(Long id, Pageable pageable);

    Page<Property> findAllPublished(Pageable pageable);

    PropertiesStats getPropertiesStats();
    PropertiesStats getPropertiesStatsByAgentId(Long agentId);

    Page<Property> filter(Specification<Property> specification, Pageable pageable);

    Page<Property> search(String search, String listingStatus, String type, String category, String offerType, String occupancyStatus, Pageable pageable);

    void updateViewCount(long propertyId);

    long getViewCount(Long id);
}
