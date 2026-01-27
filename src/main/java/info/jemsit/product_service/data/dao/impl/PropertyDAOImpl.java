package info.jemsit.product_service.data.dao.impl;

import info.jemsit.product_service.data.dao.PropertyDAO;
import info.jemsit.product_service.data.model.Property;
import info.jemsit.product_service.data.repository.PropertyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
public class PropertyDAOImpl implements PropertyDAO {

    private final PropertyRepository propertyRepository;

    @Override
    public Property save(Property property) {
        log.info("Saving resident: {}", property);
        return propertyRepository.save(property);
    }

    @Override
    public Property update(Property property) {
        log.info("Updating resident: {}", property);
        return propertyRepository.save(property);
    }

    @Override
    public Optional<Property> findById(Long id) {
        log.info("Finding resident by ID: {}", id);
        return propertyRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {

        log.info("Deleting resident by ID: {}", id);
        propertyRepository.deleteById(id);
    }

    @Override
    public Page<Property> findAll(Pageable pageable) {

        log.info("Finding all residents with pagination: {}", pageable);
        return propertyRepository.findAll(pageable);
    }
}
