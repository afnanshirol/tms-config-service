package com.xyz.tms.controller;

import com.xyz.tms.dto.FieldMappingDto;
import com.xyz.tms.dto.PartnerConfigDto;
import com.xyz.tms.entity.PartnerMaster;
import com.xyz.tms.entity.enums.EntityType;
import com.xyz.tms.service.PartnerConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 *
 * Provides API endpoints specifically for Integration Service and Adapter Services.
 * These endpoints are called by other TMS services for data integration workflows.
 *
 */
@RestController
@RequestMapping("/integration")
@RequiredArgsConstructor
@Slf4j
public class IntegrationController {

    private final PartnerConfigService partnerConfigService;

    /**
     * Get partners ready for data polling
     */
    @GetMapping("/partners")
    public ResponseEntity<List<PartnerMaster>> getPartnersForDataPolling() {
        log.debug("Getting partners for data polling");
        List<PartnerMaster> partners = partnerConfigService.getPartnersForDataPolling();
        return ResponseEntity.ok(partners);
    }

    /**
     * Get partner configuration for integration
     */
    @GetMapping("/partners/{partnerId}/config")
    public ResponseEntity<PartnerConfigDto> getPartnerConfigForIntegration(
            @PathVariable String partnerId) {
        log.debug("Getting partner config for integration: {}", partnerId);
        Optional<PartnerConfigDto> config = partnerConfigService
                .getPartnerConfigForIntegration(partnerId);
        return config.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}