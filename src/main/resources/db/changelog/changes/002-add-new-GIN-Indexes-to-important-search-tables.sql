--liquibase formatted sql

--changeset jemsit:003-add-location-gin-indexes
CREATE INDEX IF NOT EXISTS idx_property_search ON properties
USING GIN(
    to_tsvector('simple',
        COALESCE(title, '') || ' ' ||
        COALESCE(description, '')
    )
);

CREATE INDEX IF NOT EXISTS idx_location_search ON property_locations
USING GIN(
    to_tsvector('simple',
        COALESCE(address, '') || ' ' ||
        COALESCE(country, '')
    )
);

CREATE INDEX IF NOT EXISTS idx_regions_search ON regions
USING GIN(
    to_tsvector('simple',
        COALESCE(name_ru, '') || ' ' ||
        COALESCE(name_uz, '') || ' ' ||
        COALESCE(name_oz, '')
    )
);

CREATE INDEX IF NOT EXISTS idx_districts_search ON districts
USING GIN(
    to_tsvector('simple',
        COALESCE(name_ru, '') || ' ' ||
        COALESCE(name_uz, '') || ' ' ||
        COALESCE(name_oz, '')
    )
);

CREATE INDEX IF NOT EXISTS idx_villages_search ON villages
USING GIN(
    to_tsvector('simple',
        COALESCE(name_ru, '') || ' ' ||
        COALESCE(name_uz, '') || ' ' ||
        COALESCE(name_oz, '')
    )
);