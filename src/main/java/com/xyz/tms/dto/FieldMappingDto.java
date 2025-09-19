package com.xyz.tms.dto;

import com.xyz.tms.entity.enums.EntityType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for field mappings used by Adapter Services.
 * Contains mapping information for data transformation.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FieldMappingDto {

    private Long id;
    private String partnerId;
    private EntityType entityType;
    private String sourceField;
    private String targetField;
    private Boolean isRequired;
    private Integer version;
}
