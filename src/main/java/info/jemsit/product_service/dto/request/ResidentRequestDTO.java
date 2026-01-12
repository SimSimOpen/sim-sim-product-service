package info.jemsit.product_service.dto.request;

import java.math.BigDecimal;

public record ResidentRequestDTO(
         String title,
         String country,
         String region,
         String city,
         String district,
         String address,
         Double description,
         BigDecimal price,
         Integer numberOfRooms,
         Double area,
         Boolean isForRent,
         String ownerContact,
         String publish
) {
}
