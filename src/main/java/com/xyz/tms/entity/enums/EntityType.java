package com.xyz.tms.entity.enums;

/**
 * Entity Type Enum
 *
 * Represents the different types of data entities in the theatre domain.
 */
public enum EntityType {
    THEATRE("Theatre/Cinema information"),
    HALL("Hall/Screen information"),
    SHOW("Show/Showtime information"),
    PRICE("Pricing information");

    private final String description;

    EntityType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
