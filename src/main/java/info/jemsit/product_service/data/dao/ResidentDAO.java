package info.jemsit.product_service.data.dao;

import info.jemsit.product_service.data.model.Resident;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ResidentDAO {
    Resident save(Resident resident);
    Resident update(Resident resident);
    Optional<Resident> findById(Long id);
    void deleteById(Long id);

    Page<Resident> findAll(Pageable pageable);
}
