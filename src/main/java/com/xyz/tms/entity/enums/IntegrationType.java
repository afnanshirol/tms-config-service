package com.xyz.tms.entity.enums;

/**
 * Integration Type Enum
 *
 * Defines whether the partner has IT capabilities for API integration
 * or relies on manual processes.
 */
public enum IntegrationType {
    PARTNER_WITH_IT("Partner with IT capabilities - API integration possible"),
    PARTNER_WITHOUT_IT("Partner without IT capabilities - Manual processes only");

    private final String description;

    IntegrationType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
