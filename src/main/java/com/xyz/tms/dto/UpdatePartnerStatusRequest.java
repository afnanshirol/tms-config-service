package com.xyz.tms.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Update Partner Status Request DTO
 *
 * Data Transfer Object for partner status update requests.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePartnerStatusRequest {

    @NotBlank(message = "Status is required")
    private String status;
}
