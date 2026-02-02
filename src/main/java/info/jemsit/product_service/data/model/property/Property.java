package info.jemsit.product_service.data.model.property;

import info.jemsit.common.data.enums.property.*;
import info.jemsit.product_service.data.model.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "properties")
@Getter
@Setter
public class Property extends BaseEntity {
    //general details
    private String title;
    private Double description;
    private BigDecimal price;
    private Integer numberOfRooms;
    private Double area;
    private String publish;

    @Enumerated(EnumType.STRING)
    private PropertyCategory category;
    @Enumerated(EnumType.STRING)
    private PropertyType type;
    @Enumerated(EnumType.STRING)
    private OfferType offerType;
    @Enumerated(EnumType.STRING)
    private ListingStatus listingStatus;
    @Enumerated(EnumType.STRING)
    private OccupancyStatus occupancyStatus;

    // owner or agent details
    private String agent;
    private Long agentID;
    private String ownerOrAgentContact;


    //location details

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private PropertyLocation location;

    //amenities
    @OneToOne(cascade = CascadeType.ALL ,orphanRemoval = true)
    private PropertyAmenities amenities;

    //images
    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PropertyMediaData> medias = new ArrayList<>();

    public void addMedia(PropertyMediaData media) {
        this.medias.add(media);
        media.setProperty(this);
    }


}
