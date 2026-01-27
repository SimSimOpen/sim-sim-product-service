package info.jemsit.product_service.service;

import info.jemsit.common.dto.request.product.PropertyRequestDTO;
import info.jemsit.common.dto.response.product.PropertyResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PropertyService {
    String add(PropertyRequestDTO request);

    PropertyResponseDTO update(Long id, PropertyRequestDTO request);

    Page<PropertyResponseDTO> getAll(Pageable pageable);

    PropertyResponseDTO getById(Long id);

    String deleteById(Long id);
}
