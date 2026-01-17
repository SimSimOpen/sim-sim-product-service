package info.jemsit.product_service.data.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "products")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product extends BaseEntity {
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

    private Long agentId;

}
