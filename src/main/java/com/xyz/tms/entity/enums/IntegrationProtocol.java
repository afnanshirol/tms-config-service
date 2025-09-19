package com.xyz.tms.entity.enums;


/**
 * Integration Protocol Enum
 *
 * Defines the technical protocol used to integrate with the partner.
 */
public enum IntegrationProtocol {
    REST_API("REST API integration"),
    SOAP_API("SOAP API integration"),
    FTP_FILE("FTP file transfer"),
    MANUAL_PORTAL("Manual data entry via portal");

    private final String description;

    IntegrationProtocol(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Check if this protocol requires API integration
     */
    public boolean isApiProtocol() {
        return this == REST_API || this == SOAP_API;
    }

    /**
     * Check if this protocol requires file transfer
     */
    public boolean isFileProtocol() {
        return this == FTP_FILE;
    }

    /**
     * Check if this protocol is manual
     */
    public boolean isManualProtocol() {
        return this == MANUAL_PORTAL;
    }
}
