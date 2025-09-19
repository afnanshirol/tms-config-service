package com.xyz.tms.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.xyz.tms.entity.enums.IntegrationProtocol;
import com.xyz.tms.entity.enums.IntegrationType;
import com.xyz.tms.entity.enums.PartnerStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Partner Master Entity
 *
 * Represents a theatre partner with their integration configuration.
 * This entity stores the core partner information and how to connect to them.
 *
 */
@Entity
@Table(name = "partner_master")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(exclude = {"fieldMappings"})
@ToString(exclude = {"fieldMappings"})
public class PartnerMaster {

    @Id
    @Column(name = "partner_id", length = 50)
    private String partnerId;

    @NotBlank(message = "Partner name is required")
    @Column(name = "partner_name", length = 200, nullable = false)
    private String partnerName;

    @NotNull(message = "Integration type is required")
    @Enumerated(EnumType.STRING)
    @Column(name = "integration_type", length = 30, nullable = false)
    private IntegrationType integrationType;

    @NotNull(message = "Integration protocol is required")
    @Enumerated(EnumType.STRING)
    @Column(name = "integration_protocol", length = 30, nullable = false)
    private IntegrationProtocol integrationProtocol;

    @Column(name = "integration_config", length = 2000)
    private String integrationConfig;

    @Email(message = "Valid email is required")
    @Column(name = "contact_email", length = 100)
    private String contactEmail;

    @Column(name = "contact_phone", length = 20)
    private String contactPhone;

    @NotNull(message = "Status is required")
    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20, nullable = false)
    @Builder.Default
    private PartnerStatus status = PartnerStatus.PENDING;

    @Column(name = "is_active", nullable = false)
    @Builder.Default
    private Boolean isActive = true;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    /**
     * One-to-Many relationship with field mappings
     * Using JsonManagedReference to avoid infinite recursion in JSON serialization
     */
    @OneToMany(mappedBy = "partner", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    @Builder.Default
    private List<PartnerFieldMapping> fieldMappings = new ArrayList<>();

}