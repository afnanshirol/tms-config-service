package com.xyz.tms.controller;

import com.xyz.tms.dto.CreateFieldMappingRequest;
import com.xyz.tms.dto.FieldMappingDto;
import com.xyz.tms.entity.PartnerFieldMapping;
import com.xyz.tms.entity.enums.EntityType;
import com.xyz.tms.service.PartnerConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;

/**
 *
 * Handles field mapping operations for data transformation.
 * Focuses only on essential mapping CRUD operations for MVP.
 */
@RestController
@RequestMapping("/mappings")
@RequiredArgsConstructor
@Slf4j
public class FieldMappingController {

    private final PartnerConfigService partnerConfigService;

    /**
     * Create field mapping for a partner
     */
    @PostMapping
    public ResponseEntity<PartnerFieldMapping> createFieldMapping(
            @RequestParam String partnerId,
            @Valid @RequestBody CreateFieldMappingRequest request) {
        log.info("Creating field mapping for partner: {}", partnerId);

        // Convert DTO to Entity
        PartnerFieldMapping mapping = PartnerFieldMapping.builder()
                .entityType(request.getEntityType())
                .sourceField(request.getSourceField())
                .targetField(request.getTargetField())
                .isRequired(request.getIsRequired())
                .version(request.getVersion())
                .build();

        PartnerFieldMapping created = partnerConfigService.createFieldMapping(partnerId, mapping);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Get field mappings for a partner and entity type
     */
    @GetMapping
    public ResponseEntity<List<FieldMappingDto>> getFieldMappings(
            @RequestParam String partnerId,
            @RequestParam EntityType entityType) {
        log.debug("Getting field mappings: {} - {}", partnerId, entityType);
        List<FieldMappingDto> mappings = partnerConfigService
                .getFieldMappingsForTransformation(partnerId, entityType);
        return ResponseEntity.ok(mappings);
    }

    /**
     * Get all field mappings for a partner
     */
    @GetMapping("/{partnerId}")
    public ResponseEntity<List<FieldMappingDto>> getAllFieldMappingsForPartner(
            @PathVariable String partnerId) {
        log.debug("Getting all field mappings for partner: {}", partnerId);
        List<FieldMappingDto> mappings = partnerConfigService.getAllFieldMappingsForPartner(partnerId);
        return ResponseEntity.ok(mappings);
    }
}