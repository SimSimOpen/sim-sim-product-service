package info.jemsit.product_service.service;


import com.fasterxml.jackson.annotation.JsonProperty;

public record RegionsResponseDTO(long id, @JsonProperty("name_en") String nameEn) {
}
