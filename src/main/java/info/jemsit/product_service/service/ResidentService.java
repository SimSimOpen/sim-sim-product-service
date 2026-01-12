package info.jemsit.product_service.service;

import info.jemsit.product_service.dto.request.ResidentRequestDTO;

public interface ResidentService {
    void addResidentToProduct(ResidentRequestDTO request);
}
