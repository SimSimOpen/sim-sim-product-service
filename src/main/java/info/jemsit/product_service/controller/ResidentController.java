package info.jemsit.product_service.controller;

import info.jemsit.common.dto.request.product.ResidentRequestDTO;
import info.jemsit.product_service.service.ResidentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("resident")
public class ResidentController {

    private final ResidentService residentService;

    @PostMapping ("add")
    public ResponseEntity<?> addResident(@Valid @RequestBody ResidentRequestDTO request) {
        return ResponseEntity.ok(residentService.addResidentToProduct(request));
    }

    @PutMapping("update/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable("id") Long id,  @RequestBody ResidentRequestDTO request) {
        return ResponseEntity.ok(residentService.update(id,request));
    }

    @GetMapping("all")
    public ResponseEntity<?> getAllProducts(Pageable pageable) {
        return ResponseEntity.ok(residentService.getAll(pageable));
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(residentService.getById(id));
    }
    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteProductById(@PathVariable Long id) {
        return ResponseEntity.ok(residentService.deleteById(id));
    }
}
