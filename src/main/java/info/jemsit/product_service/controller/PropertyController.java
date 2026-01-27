package info.jemsit.product_service.controller;

import info.jemsit.common.dto.request.product.PropertyRequestDTO;
import info.jemsit.product_service.service.PropertyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("property")
public class PropertyController {

    private final PropertyService propertyService;

    @PostMapping ("add")
    public ResponseEntity<?> addProperty(@Valid @RequestBody PropertyRequestDTO request) {
        return ResponseEntity.ok(propertyService.add(request));
    }

    @PutMapping("update/{id}")
    public ResponseEntity<?> updateProperty(@PathVariable("id") Long id,  @RequestBody PropertyRequestDTO request) {
        return ResponseEntity.ok(propertyService.update(id,request));
    }

    @GetMapping("all")
    public ResponseEntity<?> getAllProperty(Pageable pageable) {
        return ResponseEntity.ok(propertyService.getAll(pageable));
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getPropertyById(@PathVariable Long id) {
        return ResponseEntity.ok(propertyService.getById(id));
    }
    @DeleteMapping("{id}")
    public ResponseEntity<?> deletePropertyById(@PathVariable Long id) {
        return ResponseEntity.ok(propertyService.deleteById(id));
    }
}
