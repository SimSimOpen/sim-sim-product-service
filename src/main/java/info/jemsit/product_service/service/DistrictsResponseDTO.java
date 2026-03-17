package info.jemsit.product_service.service;

import com.fasterxml.jackson.annotation.JsonProperty;

public record DistrictsResponseDTO(long id, @JsonProperty("name_en") String nameEn, @JsonProperty("region_id") long regionId) {
}
