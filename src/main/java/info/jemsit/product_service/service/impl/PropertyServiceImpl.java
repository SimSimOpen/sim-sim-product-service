package info.jemsit.product_service.service.impl;

import info.jemsit.common.clients.media.MediaServiceClient;
import info.jemsit.common.data.enums.RabbitMQMessages;
import info.jemsit.common.data.enums.property.ListingStatus;
import info.jemsit.common.dto.message.MediaUploaded;
import info.jemsit.common.dto.request.product.property.AddPropertyImagesRequestDTO;
import info.jemsit.common.dto.request.product.property.PropertyRequestDTO;
import info.jemsit.common.dto.response.product.propeprty.PropertyResponseDTO;
import info.jemsit.common.exceptions.UserException;
import info.jemsit.product_service.data.dao.PropertyDAO;
import info.jemsit.product_service.data.model.property.Property;
import info.jemsit.product_service.data.model.property.PropertyMediaData;
import info.jemsit.product_service.mapper.PropertyMapper;
import info.jemsit.product_service.service.PropertyService;
import info.jemsit.product_service.service.RabbitMQService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PropertyServiceImpl implements PropertyService {

    private final PropertyDAO propertyDAO;
    private final PropertyMapper propertyMapper;
    private final MediaServiceClient mediaServiceClient;
    private final RabbitMQService rabbitMQService;

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
        var property = propertyDAO.findById(id)
                .orElseThrow(() -> new UserException("Property not found with id: " + id));
        for (PropertyMediaData media : property.getMedias()) {
            mediaServiceClient.deleteMedia(media.getMediaURL());
        }
        propertyDAO.deleteById(id);
        return "Property with id " + id + " has been deleted successfully.";
    }

    @Override
    @Transactional
    public PropertyResponseDTO addPropertyImage(AddPropertyImagesRequestDTO request) {
        Long property_id = request.id();

        Property property = propertyDAO.findById(property_id)
                .orElseThrow(() -> new UserException("Property not found with id: " + property_id));
        var hasCoverImage = property.getMedias().stream().anyMatch(PropertyMediaData::getIsCoverImage);
        for (String url : request.urls()) {
            PropertyMediaData image = new PropertyMediaData();
            image.setMediaURL(url);
            image.setProperty(property);
            if (!hasCoverImage){
                image.setIsCoverImage(true);
                hasCoverImage = true;
            }
            property.addMedia(image);
        }
        log.info("Property after adding images:{} ", property);
        var updated = propertyDAO.update(property);
        rabbitMQService.sendMessageToRabbitMQ(new MediaUploaded("1", RabbitMQMessages.MEDIA_UPLOADED));
        return propertyMapper.toDto(updated);
    }

    @Override
    @Transactional
    public PropertyResponseDTO createPropertyDraft() {
        Property property = new Property();
        property.setListingStatus(ListingStatus.DRAFT);
        propertyDAO.save(property);
        return propertyMapper.toDto(property);
    }

    @Override
    public void deletePropertyImage(Long id) {
        var media = propertyDAO.getPropertyMediaById(id)
                .orElseThrow(() -> new UserException("Property media not found with id: " + id));

        mediaServiceClient.deleteMedia(media.getMediaURL());
        propertyDAO.deletePropertyMediaById(id);
        if (media.getIsCoverImage()){
            reAssignCoverImage(media.getProperty().getId());
        }
    }

    private void reAssignCoverImage(long propertyId){
        var property = propertyDAO.findById(propertyId)
                .orElseThrow(() -> new UserException("Property not found with id: " + propertyId));
        var mediaList = property.getMedias();
        if (mediaList.isEmpty()) return;
        mediaList.getFirst().setIsCoverImage(true);
        propertyDAO.update(property);
    }

}
