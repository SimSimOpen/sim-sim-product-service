package info.jemsit.product_service.data.model.property;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "property_locations")
@Getter
@Setter
public class PropertyLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String mapLocation;
    private String country;
    private String region;
    private String city;
    private String district;
    private String address;

}
