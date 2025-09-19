-- =====================================================
-- V1: Create Partner Master Table (Simplified)
-- Description: Core partner information and integration details
-- =====================================================

CREATE TABLE partner_master (
    -- Identity
    partner_id VARCHAR(50) PRIMARY KEY,                    -- INOX_001, PVR_001, etc.
    partner_name VARCHAR(200) NOT NULL,                    -- INOX Leisure Ltd, PVR Cinemas

    -- Integration Details
    integration_type VARCHAR(30) NOT NULL,                 -- PARTNER_WITH_IT, PARTNER_WITHOUT_IT
    integration_protocol VARCHAR(30) NOT NULL,             -- REST_API, SOAP_API, FTP_FILE, MANUAL_PORTAL
    integration_config varchar(2000) NOT NULL,                               -- Connection details, auth, scheduling

    -- Business Contact
    contact_email VARCHAR(100),                            -- Primary contact
    contact_phone VARCHAR(20),                             -- Contact phone

    -- Status
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING',         -- PENDING, APPROVED, REJECTED
    is_active BOOLEAN NOT NULL DEFAULT true,               -- Active/Inactive

    -- Audit Fields
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    -- Constraints
    CONSTRAINT chk_integration_type
        CHECK (integration_type IN ('PARTNER_WITH_IT', 'PARTNER_WITHOUT_IT')),

    CONSTRAINT chk_integration_protocol
        CHECK (integration_protocol IN ('REST_API', 'SOAP_API', 'FTP_FILE', 'MANUAL_PORTAL')),

    CONSTRAINT chk_status
        CHECK (status IN ('PENDING', 'APPROVED', 'REJECTED'))
);

-- Simple indexes
CREATE INDEX idx_partner_status ON partner_master(status, is_active);
CREATE INDEX idx_partner_protocol ON partner_master(integration_protocol);

-- Sample data for testing (H2 compatible JSON)
INSERT INTO partner_master (
    partner_id, partner_name, integration_type, integration_protocol,
    integration_config, contact_email, status
) VALUES
(
    'INOX_001',
    'INOX Leisure Limited',
    'PARTNER_WITH_IT',
    'REST_API',
    '{"baseUrl": "https://api.inox.com", "authType": "API_KEY", "apiKey": "test-key-123", "endpoints": {"theatres": "/v1/theatres", "halls": "/v1/halls", "shows": "/v1/shows", "prices": "/v1/prices"}}',
    'contact@inoxmovies.com',
    'APPROVED'
),
(
    'PVR_001',
    'PVR Cinemas Limited',
    'PARTNER_WITH_IT',
    'SOAP_API',
    '{"baseUrl": "https://api.pvrcinemas.com", "wsdlUrl": "https://api.pvrcinemas.com/theatres?wsdl", "username": "tms_user", "password": "encrypted_password", "endpoints": {"theatres": "/api/v2/theatres", "shows": "/api/v2/shows"}}',
    'integration@pvrcinemas.com',
    'APPROVED'
),
(
    'CARNIVAL_001',
    'Carnival Cinemas',
    'PARTNER_WITHOUT_IT',
    'MANUAL_PORTAL',
    '{"uploadFormat": "Excel", "frequency": "Weekly", "supportedEntities": ["theatres", "halls", "shows"]}',
    'data@carnivalcinemas.com',
    'PENDING'
);