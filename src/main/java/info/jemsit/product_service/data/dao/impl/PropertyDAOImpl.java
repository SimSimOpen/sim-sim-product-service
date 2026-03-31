package info.jemsit.product_service.data.dao.impl;

import info.jemsit.product_service.data.dao.PropertyDAO;
import info.jemsit.product_service.data.model.property.Property;
import info.jemsit.product_service.data.model.property.PropertyMediaData;
import info.jemsit.product_service.data.repository.property.PropertyMediaDataRepository;
import info.jemsit.product_service.data.repository.property.PropertyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
public class PropertyDAOImpl implements PropertyDAO {

    private final PropertyRepository propertyRepository;

    private final PropertyMediaDataRepository propertyMediaDataRepository;

    @Override
    public Property save(Property property) {
        log.info("Saving property: {}", property);
        return propertyRepository.save(property);
    }

    @Override
    @Transactional
    public Property update(Property property) {
        log.info("Updating property: {}", property.getTitle() !=null ? property.getTitle() : property.getId());
        return propertyRepository.save(property);
    }

    @Override
    public Optional<Property> findById(Long id) {
        log.info("Finding property by ID: {}", id);
        return propertyRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {

        log.info("Deleting property by ID: {}", id);
        propertyRepository.deleteById(id);
    }

    @Override
    public Page<Property> findAll(Pageable pageable) {

        log.info("Finding all properties with pagination: {}", pageable);
        return propertyRepository.findAll(pageable);
    }

    @Override
    public Optional<PropertyMediaData> getPropertyMediaById(Long id) {
        log.info("Finding property media by ID: {}", id);
        return propertyMediaDataRepository.findById(id);
    }

    @Override
    public void deletePropertyMediaById(Long id) {
        log.info("Deleting property media by ID: {}", id);
        propertyMediaDataRepository.deleteById(id);
    }

    @Override
    public List<String> getPropertyAddressShort(long locationId) {

        log.info("Getting short address for property ID: {}", locationId);
        return propertyRepository.findShortAddressById(locationId);
    }

    @Override
    public Page<Property> findByAgentID(Long id, Pageable pageable) {
        log.info("Finding properties by agent ID: {} with pagination: {}", id, pageable);
        return propertyRepository.findByAgentID(id, pageable);
    }

    @Override
    public Page<Property> findAllPublished(Pageable pageable) {
        log.info("Finding all published properties with pagination: {}", pageable);
        return propertyRepository.findAllPublished(pageable);
    }
}
