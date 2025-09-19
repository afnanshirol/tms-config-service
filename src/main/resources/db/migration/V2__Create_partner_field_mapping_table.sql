-- =====================================================
-- V2: Create Partner Field Mappings Table (Simplified)
-- Description: Simple field mappings for data transformation
-- Author: TMS Platform Team
-- =====================================================

CREATE TABLE partner_field_mappings (
    -- Identity
    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    -- Partner & Version
    partner_id VARCHAR(50) NOT NULL,                       -- References partner_master.partner_id
    version INT NOT NULL DEFAULT 1,                        -- Mapping version
    is_active BOOLEAN NOT NULL DEFAULT true,               -- Active version

    -- Mapping Details
    entity_type VARCHAR(20) NOT NULL,                      -- THEATRE, HALL, SHOW, PRICE
    source_field VARCHAR(100) NOT NULL,                    -- Partner's field name
    target_field VARCHAR(100) NOT NULL,                    -- Our standard field name
    is_required BOOLEAN NOT NULL DEFAULT false,            -- Required field

    -- Audit
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    -- Foreign Key
    CONSTRAINT fk_partner_field_mappings
        FOREIGN KEY (partner_id) REFERENCES partner_master(partner_id)
        ON DELETE CASCADE ON UPDATE CASCADE,

    -- Constraints
    CONSTRAINT chk_entity_type
        CHECK (entity_type IN ('THEATRE', 'HALL', 'SHOW', 'PRICE')),

    -- Unique mapping per partner-version-entity-source
    CONSTRAINT uk_partner_field_mapping
        UNIQUE (partner_id, version, entity_type, source_field)
);

-- Simple indexes
CREATE INDEX idx_mappings_lookup ON partner_field_mappings(partner_id, entity_type, is_active);
CREATE INDEX idx_mappings_version ON partner_field_mappings(partner_id, version);

-- Sample mappings for INOX
INSERT INTO partner_field_mappings (
    partner_id, version, entity_type, source_field, target_field, is_required
) VALUES
-- INOX Theatre mappings
('INOX_001', 1, 'THEATRE', 'theater_id', 'externalId', true),
('INOX_001', 1, 'THEATRE', 'theater_name', 'name', true),
('INOX_001', 1, 'THEATRE', 'theater_city', 'city', true),
('INOX_001', 1, 'THEATRE', 'theater_address', 'address', true),

-- INOX Hall mappings
('INOX_001', 1, 'HALL', 'hall_id', 'externalId', true),
('INOX_001', 1, 'HALL', 'hall_name', 'name', true),
('INOX_001', 1, 'HALL', 'capacity', 'totalSeats', true),

-- INOX Show mappings
('INOX_001', 1, 'SHOW', 'show_id', 'externalId', true),
('INOX_001', 1, 'SHOW', 'movie_name', 'movieTitle', true),
('INOX_001', 1, 'SHOW', 'show_time', 'startTime', true),

-- PVR has different field names
('PVR_001', 1, 'THEATRE', 'cinema_id', 'externalId', true),
('PVR_001', 1, 'THEATRE', 'cinema_name', 'name', true),
('PVR_001', 1, 'THEATRE', 'location', 'city', true),

('PVR_001', 1, 'HALL', 'screen_id', 'externalId', true),
('PVR_001', 1, 'HALL', 'screen_name', 'name', true),
('PVR_001', 1, 'HALL', 'seats', 'totalSeats', true);