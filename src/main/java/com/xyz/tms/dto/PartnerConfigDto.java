package com.xyz.tms.dto;

import com.xyz.tms.entity.enums.IntegrationProtocol;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * Data Transfer Object for partner configuration used by Integration Service.
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PartnerConfigDto {

    private String partnerId;
    private String partnerName;
    private IntegrationProtocol integrationProtocol;
    private Map<String, Object> integrationConfig;
}




