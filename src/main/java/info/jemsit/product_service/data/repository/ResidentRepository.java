package info.jemsit.product_service.data.repository;

import info.jemsit.product_service.data.model.Resident;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResidentRepository extends JpaRepository<Resident, Long> {
}