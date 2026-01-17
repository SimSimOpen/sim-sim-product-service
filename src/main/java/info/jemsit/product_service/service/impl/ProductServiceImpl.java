package info.jemsit.product_service.service.impl;

import info.jemsit.product_service.data.model.Product;
import info.jemsit.product_service.data.repository.ProductRepository;
import info.jemsit.product_service.dto.request.ProductAddRequestDto;
import info.jemsit.product_service.dto.request.ProductUpdateRequestDto;
import info.jemsit.product_service.dto.request.response.ProductResponseDto;
import info.jemsit.product_service.mapper.impl.ProductMapperImp;
import info.jemsit.product_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;
    private final ProductMapperImp mapper;


    @Override
    public ProductResponseDto create(ProductAddRequestDto productAddRequestDto) {
        Product savedProduct = repository.save(mapper.toEntity(productAddRequestDto));
        return mapper.toDto(savedProduct);
    }

    @Override
    public List<ProductResponseDto> findAll() {
        List<Product> products = repository.findAll();
        if (products.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No products found");
        }else {
            return products.stream().map(mapper::toDto).toList();
        }
    }

    @Override
    public ProductResponseDto findById(Long id) {
        Product product = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
        return mapper.toDto(product);
    }

    @Override
    public ProductResponseDto update(ProductUpdateRequestDto productUpdateRequestDto) {
        Product updatedProduct = repository.save(mapper.toEntity(productUpdateRequestDto));
        return mapper.toDto(updatedProduct);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
