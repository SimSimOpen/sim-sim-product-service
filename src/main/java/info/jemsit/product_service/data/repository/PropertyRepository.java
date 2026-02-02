package info.jemsit.product_service.data.repository;

import info.jemsit.product_service.data.model.property.Property;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropertyRepository extends JpaRepository<Property, Long> {
}