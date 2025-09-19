package com.xyz.tms.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for partner statistics used by Admin Portal dashboard.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PartnerStatsDto {

    private int totalActivePartners;
    private int approvedPartners;
    private int pendingPartners;
    private int readyForIntegration;
}
