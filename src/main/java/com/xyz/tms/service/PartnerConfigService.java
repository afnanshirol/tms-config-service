package com.xyz.tms.service;

import com.xyz.tms.dto.FieldMappingDto;
import com.xyz.tms.dto.PartnerConfigDto;
import com.xyz.tms.entity.PartnerFieldMapping;
import com.xyz.tms.entity.PartnerMaster;
import com.xyz.tms.entity.enums.EntityType;
import com.xyz.tms.entity.enums.PartnerStatus;
import com.xyz.tms.repository.PartnerFieldMappingRepository;
import com.xyz.tms.repository.PartnerRepository;
import com.xyz.tms.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 *
 * Provides business logic for partner configuration management.
 * Handles partner on-boarding, field mappings, and integration support.
 *
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class PartnerConfigService {

    private final PartnerRepository partnerRepository;

    private final PartnerFieldMappingRepository fieldMappingRepository;

    private final ObjectUtils utils;

    /**
     * Create a new partner (Partner On-boarding)
     */
    @Transactional
    public PartnerMaster createPartner(PartnerMaster partner) {
        log.info("Creating new partner: {}", partner.getPartnerId());

        // Validate partner doesn't already exist
        if (partnerRepository.findByPartnerId(partner.getPartnerId()).isPresent()) {
            throw new IllegalArgumentException("Partner already exists: " + partner.getPartnerId());
        }

        // Set default status if not provided
        if (partner.getStatus() == null) {
            partner.setStatus(PartnerStatus.PENDING);
        }

        PartnerMaster saved = partnerRepository.save(partner);
        log.info("Partner created successfully: {}", saved.getPartnerId());

        return saved;
    }

    /**
     * Get partner by ID
     */
    public Optional<PartnerMaster> getPartner(String partnerId) {
        log.debug("Getting partner: {}", partnerId);
        return partnerRepository.findByPartnerId(partnerId);
    }

    /**
     * Get all active partners
     */
    public List<PartnerMaster> getAllActivePartners() {
        log.debug("Getting all active partners");
        return partnerRepository.findByIsActiveTrue();
    }


    /**
     * Update partner status (Approval workflow)
     */
    @Transactional
    public PartnerMaster updatePartnerStatus(String partnerId, PartnerStatus status) {
        log.info("Updating partner status: {} to {}", partnerId, status);

        PartnerMaster partner = partnerRepository.findByPartnerId(partnerId)
                .orElseThrow(() -> new IllegalArgumentException("Partner not found: " + partnerId));

        partner.setStatus(status);
        PartnerMaster updated = partnerRepository.save(partner);

        log.info("Partner status updated: {} -> {}", partnerId, status);
        return updated;
    }

    /**
     * Update partner integration configuration
     */
    @Transactional
    public PartnerMaster updatePartnerConfig(String partnerId, Map<String, Object> configMap) {
        log.info("Updating partner config: {}", partnerId);

        PartnerMaster partner = partnerRepository.findByPartnerId(partnerId)
                .orElseThrow(() -> new IllegalArgumentException("Partner not found: " + partnerId));

        // Convert Map to JSON string for H2 compatibility
        String configJson = utils.convertMapToJson(configMap);
        partner.setIntegrationConfig(configJson);

        PartnerMaster updated = partnerRepository.save(partner);
        log.info("Partner config updated: {}", partnerId);

        return updated;
    }

    /**
     * Get partners ready for data polling (Used by Integration Service)
     */
    public List<PartnerMaster> getPartnersForDataPolling() {
        log.debug("Getting partners for data polling");
        return partnerRepository.findByStatusAndIsActiveTrue(PartnerStatus.APPROVED);
    }

    /**
     * Get partner configuration for integration (Used by Integration Service)
     */
    public Optional<PartnerConfigDto> getPartnerConfigForIntegration(String partnerId) {
        log.debug("Getting partner config for integration: {}", partnerId);

        Optional<PartnerMaster> partnerOpt = partnerRepository.findPartnerConfigForIntegration(partnerId);

        if (partnerOpt.isEmpty()) {
            return Optional.empty();
        }

        PartnerMaster partner = partnerOpt.get();

        // Parse JSON config to Map
        Map<String, Object> configMap = utils.parseJsonToMap(partner.getIntegrationConfig());

        PartnerConfigDto dto = PartnerConfigDto.builder()
                .partnerId(partner.getPartnerId())
                .partnerName(partner.getPartnerName())
                .integrationProtocol(partner.getIntegrationProtocol())
                .integrationConfig(configMap)
                .build();

        return Optional.of(dto);
    }


    /**
     * Create field mapping for a partner
     */
    @Transactional
    public PartnerFieldMapping createFieldMapping(String partnerId, PartnerFieldMapping mapping) {
        log.info("Creating field mapping for partner: {} - {} -> {}",
                partnerId, mapping.getSourceField(), mapping.getTargetField());

        PartnerMaster partner = partnerRepository.findByPartnerId(partnerId)
                .orElseThrow(() -> new IllegalArgumentException("Partner not found: " + partnerId));

        mapping.setPartner(partner);

        // Set version if not provided (get latest + 1)
        if (mapping.getVersion() == null) {
            Integer latestVersion = fieldMappingRepository.findLatestVersionByPartner(partnerId);
            mapping.setVersion(latestVersion + 1);
        }

        PartnerFieldMapping saved = fieldMappingRepository.save(mapping);
        log.info("Field mapping created: {}", saved.getId());

        return saved;
    }

    /**
     * Get field mappings for data transformation (Used by Adapter Services)
     */
    public List<FieldMappingDto> getFieldMappingsForTransformation(String partnerId, EntityType entityType) {
        log.debug("Getting field mappings for transformation: {} - {}", partnerId, entityType);

        List<PartnerFieldMapping> mappings = fieldMappingRepository
                .findActiveMappingsForTransformation(partnerId, entityType);

        return mappings.stream()
                .map(utils::convertToDto)
                .toList();
    }

    /**
     * Get all active field mappings for a partner
     */
    public List<FieldMappingDto> getAllFieldMappingsForPartner(String partnerId) {
        log.debug("Getting all field mappings for partner: {}", partnerId);

        List<PartnerFieldMapping> mappings = fieldMappingRepository
                .findByPartnerPartnerIdAndIsActiveTrue(partnerId);

        return mappings.stream()
                .map(utils::convertToDto)
                .toList();
    }

}