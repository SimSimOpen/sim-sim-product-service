package info.jemsit.product_service.service.impl;

import info.jemsit.common.dto.request.product.property.PropertyRequestDTO;
import info.jemsit.common.dto.response.product.propeprty.PropertyResponseDTO;
import info.jemsit.common.exceptions.UserException;
import info.jemsit.product_service.data.dao.PropertyDAO;
import info.jemsit.product_service.data.model.property.Property;
import info.jemsit.product_service.data.model.property.PropertyMediaData;
import info.jemsit.product_service.mapper.PropertyMapper;
import info.jemsit.product_service.service.PropertyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PropertyServiceImpl implements PropertyService {

    private final PropertyDAO propertyDAO;

    private final PropertyMapper propertyMapper;

    @Override
    public String add(PropertyRequestDTO request) {
        propertyDAO.save(propertyMapper.toEntity(request));
        return "Property information added to product successfully.";
    }

    @Override
    public PropertyResponseDTO update(Long id, PropertyRequestDTO request) {

        Property toUpdate = propertyDAO.findById(id).orElseThrow(() -> new UserException("Property not found with id: " + id));

        if (request.title() != null && !request.title().isEmpty()) {
            toUpdate.setTitle(request.title());
        }

        if (request.description() != null) {
            toUpdate.setDescription(request.description());
        }
        if (request.price() != null) {
            toUpdate.setPrice(request.price());
        }
        if (request.numberOfRooms() != null) {
            toUpdate.setNumberOfRooms(request.numberOfRooms());
        }
        if (request.area() != null) {
            toUpdate.setArea(request.area());
        }
        if (request.ownerContact() != null && !request.ownerContact().isEmpty()) {
            toUpdate.setOwnerOrAgentContact(request.ownerContact());
        }
        if (request.publish() != null && !request.publish().isEmpty()) {
            toUpdate.setPublish(request.publish());
        }
        Property updatedProperty = propertyDAO.update(toUpdate);
        return propertyMapper.toDto(updatedProperty);
    }

    @Override
    public Page<PropertyResponseDTO> getAll(Pageable pageable) {
        Page<Property> properties = propertyDAO.findAll(pageable);
        return properties.map(propertyMapper::toDto);
    }

    @Override
    public PropertyResponseDTO getById(Long id) {
        Property property = propertyDAO.findById(id)
                .orElseThrow(() -> new UserException("Property not found with id: " + id));
        return propertyMapper.toDto(property);
    }

    @Override
    public String deleteById(Long id) {
        propertyDAO.findById(id)
                .orElseThrow(() -> new UserException("Property not found with id: " + id));
        propertyDAO.deleteById(id);
        return "Property with id " + id + " has been deleted successfully.";
    }

    @Override
    @Transactional
    public PropertyResponseDTO addPropertyImage(Long property_id, List<String> urls) {

        Property property = propertyDAO.findById(property_id)
                .orElseThrow(() -> new UserException("Property not found with id: " + property_id));
        for (String url : urls) {
            PropertyMediaData image = new PropertyMediaData();
            image.setMediaURL(url);
            image.setProperty(property);
            property.getMedias().add(image);
        }

        var updated = propertyDAO.update(property);
        return propertyMapper.toDto(updated);
    }

    @Override
    public PropertyResponseDTO createPropertyDraft() {
        Property property = new Property();
        propertyDAO.save(property);
        return propertyMapper.toDto(property);
    }

}
