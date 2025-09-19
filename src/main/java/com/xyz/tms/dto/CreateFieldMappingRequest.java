package com.xyz.tms.dto;

import com.xyz.tms.entity.enums.EntityType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Create Field Mapping Request DTO
 *
 * Data Transfer Object for field mapping creation requests.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateFieldMappingRequest {

    @NotNull(message = "Entity type is required")
    private EntityType entityType;

    @NotBlank(message = "Source field is required")
    private String sourceField;

    @NotBlank(message = "Target field is required")
    private String targetField;

    private Boolean isRequired = false;

    private Integer version;
}
