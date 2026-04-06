package info.jemsit.product_service.service;

import info.jemsit.common.dto.response.product.propeprty.PropertyResponseDTO;
import info.jemsit.product_service.controller.PropertyFilterRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PropertyFilterService {
    Page<PropertyResponseDTO> filterProperties(PropertyFilterRequestDTO filterRequest, Pageable pageable);

}
