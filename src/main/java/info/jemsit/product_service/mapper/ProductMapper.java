package info.jemsit.product_service.mapper;

import info.jemsit.product_service.data.model.Product;
import info.jemsit.product_service.dto.request.ProductAddRequestDto;
import info.jemsit.product_service.dto.request.ProductUpdateRequestDto;
import info.jemsit.product_service.dto.request.response.ProductResponseDto;

public interface ProductMapper {
    Product toEntity(ProductAddRequestDto productRequestDto);
    Product toEntity(ProductUpdateRequestDto productUpdateRequestDto);
    ProductResponseDto toDto(Product product);
}
