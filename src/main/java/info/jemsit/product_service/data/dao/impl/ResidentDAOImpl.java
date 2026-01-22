package info.jemsit.product_service.data.dao.impl;

import info.jemsit.product_service.data.dao.ResidentDAO;
import info.jemsit.product_service.data.model.Resident;
import info.jemsit.product_service.data.repository.ResidentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
public class ResidentDAOImpl implements ResidentDAO {

    private final ResidentRepository residentRepository;

    @Override
    public Resident save(Resident resident) {
        log.info("Saving resident: {}", resident);
        return residentRepository.save(resident);
    }

    @Override
    public Resident update(Resident resident) {
        log.info("Updating resident: {}", resident);
        return residentRepository.save(resident);
    }

    @Override
    public Optional<Resident> findById(Long id) {
        log.info("Finding resident by ID: {}", id);
        return residentRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {

        log.info("Deleting resident by ID: {}", id);
        residentRepository.deleteById(id);
    }

    @Override
    public Page<Resident> findAll(Pageable pageable) {

        log.info("Finding all residents with pagination: {}", pageable);
        return residentRepository.findAll(pageable);
    }
}
