package com.xyz.tms.entity.enums;

/**
 * Partner Status Enum
 *
 * Represents the current status of partner onboarding/approval process.
 */
public enum PartnerStatus {
    PENDING("Pending approval"),
    APPROVED("Approved and active"),
    REJECTED("Rejected");

    private final String description;

    PartnerStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
