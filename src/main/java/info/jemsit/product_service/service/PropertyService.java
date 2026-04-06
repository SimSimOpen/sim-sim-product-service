package info.jemsit.product_service.service;

import info.jemsit.common.dto.request.product.property.AddPropertyImagesRequestDTO;
import info.jemsit.common.dto.request.product.property.PropertyRequestDTO;
import info.jemsit.common.dto.response.product.propeprty.PropertiesStats;
import info.jemsit.common.dto.response.product.propeprty.PropertyResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PropertyService extends PropertyFilterService {
    String add(PropertyRequestDTO request);

    PropertyResponseDTO update(Long id, PropertyRequestDTO request);

    Page<PropertyResponseDTO> getAll(Pageable pageable);

    PropertyResponseDTO getById(Long id);

    String deleteById(Long id);

    PropertyResponseDTO addPropertyImage(AddPropertyImagesRequestDTO request);

    PropertyResponseDTO createPropertyDraft();

    void deletePropertyImage(Long id);

    Page<PropertyResponseDTO> getAgentsAllProperties(Pageable pageable);

    Page<PropertyResponseDTO> getAllPublished(Pageable pageable);

    Integer getPropertyMediaCount(Long propertyId);

    PropertiesStats getPropertiesStats();
}
