package info.jemsit.product_service.controller;

import info.jemsit.product_service.dto.request.ResidentRequestDTO;
import info.jemsit.product_service.service.ResidentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ResidentController {

    private final ResidentService residentService;

    @PostMapping ("add/resident")
    public ResponseEntity<?> addResident(@Valid @RequestBody ResidentRequestDTO request) {
        residentService.addResidentToProduct(request);
        return ResponseEntity.ok().build();
    }
}
