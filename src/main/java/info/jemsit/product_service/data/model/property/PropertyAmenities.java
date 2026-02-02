package info.jemsit.product_service.data.model.property;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "property_amenities")
@Getter
@Setter
public class PropertyAmenities {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Boolean hasParking;
    private Boolean hasGarden;
    private Boolean hasSwimmingPool;
    private Boolean hasGym;
    private Boolean hasSecurity;
    private Boolean hasElevator;

}
