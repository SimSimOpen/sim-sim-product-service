package info.jemsit.product_service.dto.request.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Data
public class ProductResponseDto {
    private String title;
    private String country;
    private String region;
    private String city;
    private String district;
    private String address;
    private String description;
    private BigDecimal price;
    private String category;
    private Boolean ForSale;
    private String ownerContact;
    private String publish;
    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long agentId; //ilan beren kullanici id


}
