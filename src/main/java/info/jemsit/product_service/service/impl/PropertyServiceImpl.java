package info.jemsit.product_service.service.impl;

import info.jemsit.common.dto.request.product.PropertyRequestDTO;
import info.jemsit.common.dto.response.product.PropertyResponseDTO;
import info.jemsit.common.exceptions.UserException;
import info.jemsit.product_service.data.dao.PropertyDAO;
import info.jemsit.product_service.data.model.Property;
import info.jemsit.product_service.mapper.PropertyMapper;
import info.jemsit.product_service.service.PropertyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PropertyServiceImpl implements PropertyService {

    private final PropertyDAO propertyDAO;

    private final PropertyMapper propertyMapper;

    @Override
    public String add(PropertyRequestDTO request) {
        propertyDAO.save(propertyMapper.toEntity(request));
        return  "Property information added to product successfully.";
    }

    @Override
    public PropertyResponseDTO update(Long id, PropertyRequestDTO request) {

        Property toUpdate = propertyDAO.findById(id).orElseThrow(()->new UserException("Property not found with id: " + id));

        if(request.title() != null && !request.title().isEmpty()){
            toUpdate.setTitle(request.title());
        }
        if(request.country() != null && !request.country().isEmpty()){
            toUpdate.setCountry(request.country());
        }

        if (request.region() != null && !request.region().isEmpty()) {
            toUpdate.setRegion(request.region());
        }
        if (request.city() != null && !request.city().isEmpty()) {
            toUpdate.setCity(request.city());
        }
        if (request.district() != null && !request.district().isEmpty()) {
            toUpdate.setDistrict(request.district());
        }
        if (request.address() != null && !request.address().isEmpty()) {
            toUpdate.setAddress(request.address());
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
        if (request.isForRent() != null) {
            toUpdate.setIsActive(request.isForRent());
        }
        if (request.ownerContact() != null && !request.ownerContact().isEmpty()) {
            toUpdate.setOwnerContact(request.ownerContact());
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

}
