package com.xyz.tms.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.xyz.tms.entity.enums.EntityType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Partner Field Mapping Entity
 *
 * Represents field mappings between partner's data format and TMS standard format.
 * This entity stores how to transform partner field names to our standard field names.
 *
 */
@Entity
@Table(name = "partner_field_mappings")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(exclude = {"partner"})
@ToString(exclude = {"partner"})
public class PartnerFieldMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "partner_id", referencedColumnName = "partner_id", nullable = false)
    @JsonBackReference
    private PartnerMaster partner;

    @Positive(message = "Version must be positive")
    @Column(name = "version", nullable = false)
    @Builder.Default
    private Integer version = 1;

    @Column(name = "is_active", nullable = false)
    @Builder.Default
    private Boolean isActive = true;

    @NotNull(message = "Entity type is required")
    @Enumerated(EnumType.STRING)
    @Column(name = "entity_type", length = 20, nullable = false)
    private EntityType entityType;

    @NotBlank(message = "Source field is required")
    @Column(name = "source_field", length = 100, nullable = false)
    private String sourceField;

    @NotBlank(message = "Target field is required")
    @Column(name = "target_field", length = 100, nullable = false)
    private String targetField;

    @Column(name = "is_required", nullable = false)
    @Builder.Default
    private Boolean isRequired = false;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    /**
     * Get the partner ID from the associated partner
     */
    public String getPartnerId() {
        return partner != null ? partner.getPartnerId() : null;
    }

}
