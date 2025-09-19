package com.xyz.tms.dto;

import com.xyz.tms.entity.enums.IntegrationProtocol;
import com.xyz.tms.entity.enums.IntegrationType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Create Partner Request DTO
 *
 * Data Transfer Object for partner creation requests.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreatePartnerRequest {

    @NotBlank(message = "Partner ID is required")
    private String partnerId;

    @NotBlank(message = "Partner name is required")
    private String partnerName;

    @NotNull(message = "Integration type is required")
    private IntegrationType integrationType;

    @NotNull(message = "Integration protocol is required")
    private IntegrationProtocol integrationProtocol;

    @Email(message = "Valid email is required")
    private String contactEmail;

    private String contactPhone;
}



