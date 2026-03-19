package info.jemsit.product_service.service.impl;

import info.jemsit.common.UserContext;
import info.jemsit.common.clients.auth.AuthServiceClient;
import info.jemsit.common.clients.media.MediaServiceClient;
import info.jemsit.common.data.enums.RabbitMQMessages;
import info.jemsit.common.data.enums.Roles;
import info.jemsit.common.data.enums.property.ListingStatus;
import info.jemsit.common.dto.message.MediaUploaded;
import info.jemsit.common.dto.request.product.property.AddPropertyImagesRequestDTO;
import info.jemsit.common.dto.request.product.property.PropertyRequestDTO;
import info.jemsit.common.dto.response.auth.UserDetailsResponseDTO;
import info.jemsit.common.dto.response.product.propeprty.PropertyResponseDTO;
import info.jemsit.common.exceptions.UserException;
import info.jemsit.product_service.data.dao.PropertyDAO;
import info.jemsit.product_service.data.model.property.Property;
import info.jemsit.product_service.data.model.property.PropertyAmenities;
import info.jemsit.product_service.data.model.property.PropertyLocation;
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

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PropertyServiceImpl implements PropertyService {

    private final PropertyDAO propertyDAO;
    private final PropertyMapper propertyMapper;
    private final MediaServiceClient mediaServiceClient;
    private final AuthServiceClient authServiceClient;
    private final RabbitMQService rabbitMQService;

    @Override
    public String add(PropertyRequestDTO request) {
        Property entity = propertyMapper.toEntity(request);
        System.out.println("Entity after mapping: " + entity);
        propertyDAO.save(entity);
        return "Property information added to product successfully.";
    }

    @Override
    public PropertyResponseDTO update(Long id, PropertyRequestDTO request) {
        System.out.println("PropertyRequestDTO received for update: " + request);

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
        if (request.offerType() != null) {
            toUpdate.setOfferType(request.offerType());
        }
        if (request.type() != null) {
            toUpdate.setType(request.type());
        }
        if (request.category() != null) {
            toUpdate.setCategory(request.category());
        }
        if (request.listingStatus() != null) {
            toUpdate.setListingStatus(request.listingStatus());
        }
        if (request.occupancyStatus() != null) {
            toUpdate.setOccupancyStatus(request.occupancyStatus());
        }
        if (request.publish() != null && !request.publish().isEmpty()) {
            toUpdate.setPublish(request.publish());
        }
        if (request.location() != null) {
            var location = getPropertyLocation(request, toUpdate);
            toUpdate.setLocation(location);
        }
        if (request.amenities() != null) {
            var updatedAmenities = setChanges(request, toUpdate);
            toUpdate.setAmenities(updatedAmenities);
        }
        Property updatedProperty = propertyDAO.update(toUpdate);
        return propertyMapper.toDtoWithShortAddress(updatedProperty, getLocationList(updatedProperty.getLocation()));
    }


    @Override
    public Page<PropertyResponseDTO> getAll(Pageable pageable) {
        Page<Property> properties = propertyDAO.findAll(pageable);
        return properties.map((p) -> propertyMapper.toDtoWithShortAddress(p, getLocationList(p.getLocation())));
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
            if (!hasCoverImage) {
                image.setIsCoverImage(true);
                hasCoverImage = true;
            }
            property.addMedia(image);
        }
        log.info("Property after adding images:{} ", property);
        var updated = propertyDAO.update(property);
        var userId =  authServiceClient.getUserDetails().id();
        rabbitMQService.sendMessageToRabbitMQ(new MediaUploaded(userId.toString(), RabbitMQMessages.MEDIA_UPDATE));
        return propertyMapper.toDto(updated);
    }

    @Override
    @Transactional
    public PropertyResponseDTO createPropertyDraft() {
        Property property = new Property();
        property.setListingStatus(ListingStatus.DRAFT);
        UserDetailsResponseDTO userDetails = authServiceClient.getUserDetails();
        property.setAgentID(userDetails.id());
        property.setAgent(userDetails.username());
        propertyDAO.save(property);
        return propertyMapper.toDto(property);
    }

    @Override
    public void deletePropertyImage(Long id) {
        var media = propertyDAO.getPropertyMediaById(id)
                .orElseThrow(() -> new UserException("Property media not found with id: " + id));

        mediaServiceClient.deleteMedia(media.getMediaURL());
        propertyDAO.deletePropertyMediaById(id);
        if (media.getIsCoverImage()) {
            reAssignCoverImage(media.getProperty().getId());
        }
        var userId =  authServiceClient.getUserDetails().id();
        rabbitMQService.sendMessageToRabbitMQ(new MediaUploaded(userId.toString(), RabbitMQMessages.MEDIA_UPDATE));
    }

    @Override
    public Page<PropertyResponseDTO> getAgentsAllProperties(Pageable pageable) {
        var user = authServiceClient.getUserDetails();
        boolean isAdmin = user.roles().stream().anyMatch(role -> role.equals(Roles.ADMIN));
        Page<Property> properties = isAdmin ? propertyDAO.findAll(pageable) : propertyDAO.findByAgentID(user.id(), pageable);
        return properties.map((p) -> propertyMapper.toDtoWithShortAddress(p, getLocationList(p.getLocation())));
    }

    private void reAssignCoverImage(long propertyId) {
        var property = propertyDAO.findById(propertyId)
                .orElseThrow(() -> new UserException("Property not found with id: " + propertyId));
        var mediaList = property.getMedias();
        if (mediaList.isEmpty()) return;
        mediaList.getFirst().setIsCoverImage(true);
        propertyDAO.update(property);
    }

    private static PropertyLocation getPropertyLocation(PropertyRequestDTO request, Property toUpdate) {
        var location = toUpdate.getLocation();
        if (location == null) {
            location = new PropertyLocation();
        }
        location.setAddress(request.location().address());
        location.setCountry(request.location().country());
        location.setRegionID(request.location().regionID());
        location.setDistrictID(request.location().districtID());
        location.setMapLocation(request.location().mapLocation());
        location.setPlaceID(request.location().placeID());
        return location;
    }

    private List<String> getLocationList(PropertyLocation location) {
        if (location == null) return List.of("", "", "");
        return propertyDAO.getPropertyAddressShort(location.getId());
    }

    private PropertyAmenities setChanges(PropertyRequestDTO request, Property toUpdate) {
        var amenities = toUpdate.getAmenities();
        if (amenities == null) {
            amenities = new PropertyAmenities();
        }
        amenities.setHasParking(request.amenities().hasParking());
        amenities.setHasElevator(request.amenities().hasElevator());
        amenities.setHasGarden(request.amenities().hasGarden());
        amenities.setHasSwimmingPool(request.amenities().hasSwimmingPool());
        amenities.setHasSecurity(request.amenities().hasSecurity());
        amenities.setHasGym(request.amenities().hasGym());
        amenities.setHasWashingMachine(request.amenities().hasWashingMachine());
        amenities.setHasAirConditioning(request.amenities().hasAirConditioning());
        amenities.setHasInternet(request.amenities().hasInternet());
        amenities.setHasRefrigerator(request.amenities().hasRefrigerator());
        amenities.setHasDishwasher(request.amenities().hasDishwasher());
        amenities.setHasMicrowave(request.amenities().hasMicrowave());
        amenities.setHasParkingSpace(request.amenities().hasParkingSpace());
        amenities.setHasTV(request.amenities().hasTV());
        amenities.setHasSatellite(request.amenities().hasSatellite());
        amenities.setHasFurniture(request.amenities().hasFurniture());
        return amenities;
    }

}
