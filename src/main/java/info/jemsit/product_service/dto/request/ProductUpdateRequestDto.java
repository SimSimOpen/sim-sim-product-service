package info.jemsit.product_service.dto.request;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class ProductUpdateRequestDto {
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
    private Long agentId;
    private Long id;
}
