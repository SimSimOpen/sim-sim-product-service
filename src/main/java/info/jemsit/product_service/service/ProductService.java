package info.jemsit.product_service.service;

import info.jemsit.product_service.dto.request.ProductAddRequestDto;
import info.jemsit.product_service.dto.request.ProductUpdateRequestDto;
import info.jemsit.product_service.dto.request.response.ProductResponseDto;

import java.util.List;

public interface ProductService {
    ProductResponseDto create(ProductAddRequestDto productAddRequestDto);
    List<ProductResponseDto> findAll();
    ProductResponseDto findById(Long id);
    ProductResponseDto update(ProductUpdateRequestDto productUpdateRequestDto);
    void delete(Long id);
}
