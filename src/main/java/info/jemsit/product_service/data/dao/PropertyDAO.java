package info.jemsit.product_service.data.dao;

import info.jemsit.product_service.data.model.property.Property;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface PropertyDAO {
    Property save(Property property);
    Property update(Property property);
    Optional<Property> findById(Long id);
    void deleteById(Long id);

    Page<Property> findAll(Pageable pageable);
}
