package info.jemsit.product_service.data.repository;

import info.jemsit.product_service.data.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
