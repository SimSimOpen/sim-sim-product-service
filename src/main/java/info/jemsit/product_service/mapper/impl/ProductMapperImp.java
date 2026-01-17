package info.jemsit.product_service.mapper.impl;

import info.jemsit.product_service.data.model.Product;
import info.jemsit.product_service.data.repository.ProductRepository;
import info.jemsit.product_service.dto.request.ProductAddRequestDto;
import info.jemsit.product_service.dto.request.ProductUpdateRequestDto;
import info.jemsit.product_service.dto.request.response.ProductResponseDto;
import info.jemsit.product_service.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductMapperImp implements ProductMapper {
    private final ProductRepository productRepository;


    @Override
    public Product toEntity(ProductAddRequestDto productAddRequestDto) {
        Product product=new Product();
        product.setTitle(productAddRequestDto.getTitle());
        product.setCountry(productAddRequestDto.getCountry());
        product.setRegion(productAddRequestDto.getRegion());
        product.setCity(productAddRequestDto.getCity());
        product.setDistrict(productAddRequestDto.getDistrict());
        product.setAddress(productAddRequestDto.getAddress());
        product.setDescription(productAddRequestDto.getDescription());
        product.setPrice(productAddRequestDto.getPrice());
        product.setCategory(productAddRequestDto.getCategory());
        product.setForSale(productAddRequestDto.getForSale());
        product.setOwnerContact(productAddRequestDto.getOwnerContact());
        product.setAgentId(productAddRequestDto.getAgentId());
        return product;
    }
    @Override
    public Product toEntity(ProductUpdateRequestDto productUpdateRequestDto) {
        Product product=productRepository.findById(productUpdateRequestDto.getId()).orElseThrow(()->new RuntimeException("Product not found"));
        product.setTitle(productUpdateRequestDto.getTitle());
        product.setCountry(productUpdateRequestDto.getCountry());
        product.setRegion(productUpdateRequestDto.getRegion());
        product.setCity(productUpdateRequestDto.getCity());
        product.setDistrict(productUpdateRequestDto.getDistrict());
        product.setAddress(productUpdateRequestDto.getAddress());
        product.setDescription(productUpdateRequestDto.getDescription());
        product.setPrice(productUpdateRequestDto.getPrice());
        product.setCategory(productUpdateRequestDto.getCategory());
        product.setForSale(productUpdateRequestDto.getForSale());
        product.setOwnerContact(productUpdateRequestDto.getOwnerContact());
        product.setAgentId(productUpdateRequestDto.getAgentId());
        return product;
    }

    @Override
    public ProductResponseDto toDto(Product product) {
        ProductResponseDto productResponseDto=new ProductResponseDto();
        productResponseDto.setId(product.getId());
        productResponseDto.setTitle(product.getTitle());
        productResponseDto.setCountry(product.getCountry());
        productResponseDto.setRegion(product.getRegion());
        productResponseDto.setCity(product.getCity());
        productResponseDto.setDistrict(product.getDistrict());
        productResponseDto.setAddress(product.getAddress());
        productResponseDto.setDescription(product.getDescription());
        productResponseDto.setPrice(product.getPrice());
        productResponseDto.setCategory(product.getCategory());
        productResponseDto.setForSale(product.getForSale());
        productResponseDto.setOwnerContact(product.getOwnerContact());
        productResponseDto.setAgentId(product.getAgentId());
        return productResponseDto;
    }
}
