package info.jemsit.product_service.controller;

import info.jemsit.product_service.dto.request.ProductAddRequestDto;
import info.jemsit.product_service.dto.request.ProductUpdateRequestDto;
import info.jemsit.product_service.dto.request.response.ProductResponseDto;
import info.jemsit.product_service.service.impl.ProductServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController {
    private  final ProductServiceImpl productService;

    @PostMapping("add")
    public ResponseEntity<ProductResponseDto> addProduct(@RequestBody ProductAddRequestDto request) {
        ProductResponseDto response = productService.create(request);
        return ResponseEntity.ok(response);
    }
    @PutMapping("update")
    public ResponseEntity<ProductResponseDto> updateProduct( @RequestBody ProductUpdateRequestDto request) {
        ProductResponseDto response = productService.update(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("all")
    public ResponseEntity<List<ProductResponseDto>> getAllProducts() {
        List<ProductResponseDto> response = productService.findAll();
        return ResponseEntity.ok(response);
    }
    @GetMapping("product/{id}")
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable Long id) {
        ProductResponseDto response = productService.findById(id);
        return ResponseEntity.ok(response);
    }
    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteProductById(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
