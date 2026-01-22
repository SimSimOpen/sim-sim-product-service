package info.jemsit.product_service.data.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "residents")
@Getter@Setter
public class Resident extends BaseEntity {
    private String title;
    private String country;
    private String region;
    private String city;
    private String district;
    private String address;
    private Double description;
    private BigDecimal price;
    private Integer numberOfRooms;
    private Double area;
    private Boolean isForRent;
    private String ownerContact;
    private String publish;

    //mundan pesi gerekmidi
    private String agent;
    private Long agentID;

    private String mapLocation;

    private List<String> pictures;


}
