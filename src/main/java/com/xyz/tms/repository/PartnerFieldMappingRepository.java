package com.xyz.tms.repository;

import com.xyz.tms.entity.PartnerFieldMapping;
import com.xyz.tms.entity.enums.EntityType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Partner Field Mapping Repository
 * Provides essential data access operations for PartnerFieldMapping entities.
 *
 */
@Repository
public interface PartnerFieldMappingRepository extends JpaRepository<PartnerFieldMapping, Long> {

    /**
     * Find all active mappings for a partner
     */
    List<PartnerFieldMapping> findByPartnerPartnerIdAndIsActiveTrue(String partnerId);

    /**
     * Get field mappings for data transformation (What Integration Service and Adapter Services call to transform data)
     */
    @Query("SELECT m FROM PartnerFieldMapping m WHERE " +
            "m.partner.partnerId = :partnerId AND " +
            "m.entityType = :entityType AND " +
            "m.isActive = true AND " +
            "m.partner.isActive = true " +
            "ORDER BY m.sourceField")
    List<PartnerFieldMapping> findActiveMappingsForTransformation(
            @Param("partnerId") String partnerId,
            @Param("entityType") EntityType entityType);

    /**
     * Get the latest version number for a partner (for basic versioning support)
     */
    @Query("SELECT COALESCE(MAX(m.version), 0) FROM PartnerFieldMapping m WHERE " +
            "m.partner.partnerId = :partnerId")
    Integer findLatestVersionByPartner(@Param("partnerId") String partnerId);
}