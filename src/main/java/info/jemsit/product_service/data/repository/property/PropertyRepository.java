package info.jemsit.product_service.data.repository.property;

import info.jemsit.product_service.data.model.property.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Objects;

public interface PropertyRepository extends JpaRepository<Property, Long> {
    @Query(value = """
            SELECT r.name_uz
            FROM property_locations pl
            LEFT JOIN regions r ON r.id = pl.region_id
            WHERE pl.id = :locationId
            
            UNION ALL
            
            SELECT d.name_uz
            FROM property_locations pl
            LEFT JOIN districts d ON d.id = pl.district_id
            WHERE pl.id = :locationId
            
            UNION ALL
            
            SELECT p.name_uz
            FROM property_locations pl
            LEFT JOIN villages p ON p.id = pl.place_id
            WHERE pl.id = :locationId
            """, nativeQuery = true)
    List<String> findShortAddressById(long locationId);
}