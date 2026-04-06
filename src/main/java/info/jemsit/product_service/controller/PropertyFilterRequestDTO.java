package info.jemsit.product_service.controller;

import info.jemsit.common.data.enums.property.*;

public record PropertyFilterRequestDTO(
        String search,
        PropertyCategory category,
        PropertyType type,
        OfferType offerType,
        ListingStatus listingStatus,
        OccupancyStatus occupancyStatus
) {
}
