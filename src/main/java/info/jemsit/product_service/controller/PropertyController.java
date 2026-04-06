package info.jemsit.product_service.controller;

import info.jemsit.common.dto.request.product.property.AddPropertyImagesRequestDTO;
import info.jemsit.common.dto.request.product.property.PropertyRequestDTO;
import info.jemsit.product_service.service.PropertyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/property")
public class PropertyController {

    private final PropertyService propertyService;

    @PostMapping("add")
    public ResponseEntity<?> addProperty(@Valid @RequestBody PropertyRequestDTO request) {
        return ResponseEntity.ok(propertyService.add(request));
    }

    @PutMapping("update-draft/{id}")
    public ResponseEntity<?> updateDraftProperty(@PathVariable Long id, @Valid @RequestBody PropertyRequestDTO request) {
        return ResponseEntity.ok(propertyService.update(id, request));
    }

    @PostMapping("create-draft")
    public ResponseEntity<?> createPropertyDraft() {
        return ResponseEntity.ok(propertyService.createPropertyDraft());
    }

    @PostMapping(value = "add/images")
    public ResponseEntity<?> addPropertyImage(@RequestBody AddPropertyImagesRequestDTO request) {
        return ResponseEntity.ok(propertyService.addPropertyImage(request));
    }

    @DeleteMapping("delete/image/{id}")
    public ResponseEntity<?> deletePropertyImage(@PathVariable Long id) {
        propertyService.deletePropertyImage(id);
        return ResponseEntity.ok("Image deleted successfully");
    }

    @PutMapping("update/{id}")
    public ResponseEntity<?> updateProperty(@PathVariable("id") Long id, @RequestBody PropertyRequestDTO request) {
        return ResponseEntity.ok(propertyService.update(id, request));
    }


    //This method for client landing page
    @GetMapping("all")
    public ResponseEntity<?> getAllProperty(Pageable pageable) {
        return ResponseEntity.ok(propertyService.getAll(pageable));
    }
    @GetMapping("all/published")
    public ResponseEntity<?> getAllPublishedProperty(Pageable pageable) {
        return ResponseEntity.ok(propertyService.getAllPublished(pageable));
    }


    //This method for admin panel
    @GetMapping("agents-all")
    public ResponseEntity<?> getAllAgentProperty(Pageable pageable) {
        return ResponseEntity.ok(propertyService.getAgentsAllProperties(pageable));
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getPropertyById(@PathVariable Long id) {
        return ResponseEntity.ok(propertyService.getById(id));
    }
    @GetMapping("property-media-count/{property-id}")
    public Integer getPropertyMediaCount(@PathVariable("property-id") Long propertyId) {
        return propertyService.getPropertyMediaCount(propertyId);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deletePropertyById(@PathVariable Long id) {
        return ResponseEntity.ok(propertyService.deleteById(id));
    }
    @GetMapping("stats")
    public ResponseEntity<?> getPropertyStats() {
        return ResponseEntity.ok(propertyService.getPropertiesStats());
    }
    @GetMapping("filter")
    public ResponseEntity<?> filterProperties(
            PropertyFilterRequestDTO filterRequest,
            Pageable pageable
    ) {
        System.out.println("Filter Request: " + filterRequest);
        System.out.println("Pageable: " + pageable);
        return ResponseEntity.ok(propertyService.filterProperties(filterRequest, pageable));
    }
}
