package info.jemsit.product_service.service.impl;

import info.jemsit.common.dto.request.product.ResidentRequestDTO;
import info.jemsit.common.dto.response.product.ResidentResponseDTO;
import info.jemsit.common.exceptions.UserException;
import info.jemsit.product_service.data.dao.ResidentDAO;
import info.jemsit.product_service.data.model.Resident;
import info.jemsit.product_service.mapper.ResidentMapper;
import info.jemsit.product_service.service.ResidentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ResidentServiceImpl implements ResidentService {

    private final ResidentDAO residentDAO;

    private final ResidentMapper residentMapper;

    @Override
    public String addResidentToProduct(ResidentRequestDTO request) {
        residentDAO.save(residentMapper.toEntity(request));
        return  "Resident information added to product successfully.";
    }

    @Override
    public ResidentResponseDTO update(Long id, ResidentRequestDTO request) {

        Resident toUpdate = residentDAO.findById(id).orElseThrow(()->new UserException("Resident not found with id: " + id));

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
            toUpdate.setIsForRent(request.isForRent());
        }
        if (request.ownerContact() != null && !request.ownerContact().isEmpty()) {
            toUpdate.setOwnerContact(request.ownerContact());
        }
        if (request.publish() != null && !request.publish().isEmpty()) {
            toUpdate.setPublish(request.publish());
        }
        Resident updatedResident = residentDAO.update(toUpdate);
        return residentMapper.toDto(updatedResident);
    }

    @Override
    public Page<ResidentResponseDTO> getAll(Pageable pageable) {
        Page<Resident> residents = residentDAO.findAll(pageable);
        return residents.map(residentMapper::toDto);
    }

    @Override
    public ResidentResponseDTO getById(Long id) {
        Resident resident = residentDAO.findById(id)
                .orElseThrow(() -> new UserException("Resident not found with id: " + id));
        return residentMapper.toDto(resident);
    }

    @Override
    public String deleteById(Long id) {
        residentDAO.findById(id)
                .orElseThrow(() -> new UserException("Resident not found with id: " + id));
        residentDAO.deleteById(id);
        return "Resident with id " + id + " has been deleted successfully.";
    }

}
