package info.jemsit.product_service.service;

import info.jemsit.common.dto.request.product.property.PropertyRequestDTO;
import info.jemsit.common.dto.response.product.propeprty.PropertyResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PropertyService {
    String add(PropertyRequestDTO request);

    PropertyResponseDTO update(Long id, PropertyRequestDTO request);

    Page<PropertyResponseDTO> getAll(Pageable pageable);

    PropertyResponseDTO getById(Long id);

    String deleteById(Long id);

    PropertyResponseDTO addPropertyImage(Long property_id, List<String> urls);

    PropertyResponseDTO createPropertyDraft();
}
