package info.jemsit.product_service.controller;

import info.jemsit.common.dto.request.product.property.PropertyRequestDTO;
import info.jemsit.product_service.service.PropertyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/property")
public class PropertyController {

    private final PropertyService propertyService;

    @PostMapping("add")
    public ResponseEntity<?> addProperty(@Valid @RequestBody PropertyRequestDTO request) {
        return ResponseEntity.ok(propertyService.add(request));
    }

    @PostMapping("create-draft")
    public ResponseEntity<?> createPropertyDraft() {
        return ResponseEntity.ok(propertyService.createPropertyDraft());
    }

    @PostMapping(value = "add/images")
    public ResponseEntity<?> addPropertyImage(@RequestParam(value = "property_id") Long property_id, @RequestParam("urls") List<String> urls) {
        return ResponseEntity.ok(propertyService.addPropertyImage(property_id,urls));
    }

    @PutMapping("update/{id}")
    public ResponseEntity<?> updateProperty(@PathVariable("id") Long id, @RequestBody PropertyRequestDTO request) {
        return ResponseEntity.ok(propertyService.update(id, request));
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
