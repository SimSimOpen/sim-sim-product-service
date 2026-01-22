package info.jemsit.product_service.service;

import info.jemsit.common.dto.request.product.ResidentRequestDTO;
import info.jemsit.common.dto.response.product.ResidentResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ResidentService {
    String addResidentToProduct(ResidentRequestDTO request);

    ResidentResponseDTO update(Long id, ResidentRequestDTO request);

    Page<ResidentResponseDTO> getAll(Pageable pageable);

    ResidentResponseDTO getById(Long id);

    String deleteById(Long id);
}
