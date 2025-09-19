package com.xyz.tms.controller;

import com.xyz.tms.dto.CreatePartnerRequest;
import com.xyz.tms.dto.UpdatePartnerStatusRequest;
import com.xyz.tms.entity.PartnerMaster;
import com.xyz.tms.entity.enums.PartnerStatus;
import com.xyz.tms.service.PartnerConfigService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 *
 * Handles partner management operations.
 *
 */
@RestController
@RequestMapping("/partners")
@RequiredArgsConstructor
@Slf4j
public class PartnerConfigController {

    private final PartnerConfigService partnerConfigService;

    /**
     * Create a new partner (Partner On-boarding)
     */
    @PostMapping
    public ResponseEntity<PartnerMaster> createPartner(@Valid @RequestBody CreatePartnerRequest request) {
        log.info("Creating partner: {}", request.getPartnerId());

        // Convert DTO to Entity
        PartnerMaster partner = PartnerMaster.builder()
                .partnerId(request.getPartnerId())
                .partnerName(request.getPartnerName())
                .integrationType(request.getIntegrationType())
                .integrationProtocol(request.getIntegrationProtocol())
                .contactEmail(request.getContactEmail())
                .contactPhone(request.getContactPhone())
                .build();

        PartnerMaster created = partnerConfigService.createPartner(partner);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Get partner by ID
     */
    @GetMapping("/{partnerId}")
    public ResponseEntity<PartnerMaster> getPartner(@PathVariable String partnerId) {
        log.debug("Getting partner: {}", partnerId);
        Optional<PartnerMaster> partner = partnerConfigService.getPartner(partnerId);
        return partner.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Get all active partners
     */
    @GetMapping
    public ResponseEntity<List<PartnerMaster>> getAllActivePartners() {
        log.debug("Getting all active partners");
        List<PartnerMaster> partners = partnerConfigService.getAllActivePartners();
        return ResponseEntity.ok(partners);
    }

    /**
     * Update partner status (Approval Workflow)
     */
    @PutMapping("/{partnerId}/status")
    public ResponseEntity<PartnerMaster> updatePartnerStatus(
            @PathVariable String partnerId,
            @Valid @RequestBody UpdatePartnerStatusRequest request) {
        log.info("Updating partner status: {} to {}", partnerId, request.getStatus());
        PartnerStatus status = PartnerStatus.valueOf(request.getStatus());
        PartnerMaster updated = partnerConfigService.updatePartnerStatus(partnerId, status);
        return ResponseEntity.ok(updated);
    }

    /**
     * Update partner integration configuration
     */
    @PutMapping("/{partnerId}/config")
    public ResponseEntity<PartnerMaster> updatePartnerConfig(
            @PathVariable String partnerId,
            @RequestBody Map<String, Object> config) {
        log.info("Updating partner config: {}", partnerId);
        PartnerMaster updated = partnerConfigService.updatePartnerConfig(partnerId, config);
        return ResponseEntity.ok(updated);
    }
}