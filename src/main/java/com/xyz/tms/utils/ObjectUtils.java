package com.xyz.tms.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xyz.tms.dto.FieldMappingDto;
import com.xyz.tms.entity.PartnerFieldMapping;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class ObjectUtils {

    private final ObjectMapper objectMapper;
    /**
     * Convert Map to JSON string for database storage
     */
    public String convertMapToJson(Map<String, Object> configMap) {
        try {
            return objectMapper.writeValueAsString(configMap);
        } catch (JsonProcessingException e) {
            log.error("Error converting config map to JSON", e);
            throw new RuntimeException("Failed to convert configuration to JSON", e);
        }
    }

    /**
     * Parse JSON string to Map for application use
     */
    public Map<String, Object> parseJsonToMap(String jsonString) {
        if (jsonString == null || jsonString.trim().isEmpty()) {
            return new HashMap<>();
        }

        try {
            return objectMapper.readValue(jsonString, new TypeReference<Map<String, Object>>() {});
        } catch (JsonProcessingException e) {
            log.error("Error parsing JSON config: {}", jsonString, e);
            // Return empty map as fallback
            return new HashMap<>();
        }
    }

    /**
     * Convert PartnerFieldMapping entity to DTO
     */
    public FieldMappingDto convertToDto(PartnerFieldMapping mapping) {
        return FieldMappingDto.builder()
                .id(mapping.getId())
                .partnerId(mapping.getPartner().getPartnerId())
                .entityType(mapping.getEntityType())
                .sourceField(mapping.getSourceField())
                .targetField(mapping.getTargetField())
                .isRequired(mapping.getIsRequired())
                .version(mapping.getVersion())
                .build();
    }
}
