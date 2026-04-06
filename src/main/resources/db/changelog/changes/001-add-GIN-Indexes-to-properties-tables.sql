CREATE INDEX IF NOT EXISTS idx_property_search ON properties USING GIN(
    to_tsvector('english',
        COALESCE(title, '') || ' ' ||
        COALESCE(description, '')
    )
);
