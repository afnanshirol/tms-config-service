package com.xyz.tms.repository;

import com.xyz.tms.entity.PartnerMaster;
import com.xyz.tms.entity.enums.PartnerStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Provides essential data access operations for PartnerMaster entities.
 */
@Repository
public interface PartnerRepository extends JpaRepository<PartnerMaster, String> {

    /**
     * Find partner by partner ID
     */
    Optional<PartnerMaster> findByPartnerId(String partnerId);

    /**
     * Find all active partners
     */
    List<PartnerMaster> findByIsActiveTrue();

    /**
     * Find all approved and active partners (ready for integration)
     * Used by Integration Service to get partners for data polling
     */
    List<PartnerMaster> findByStatusAndIsActiveTrue(PartnerStatus status);

    /**
     * Get partner configuration for integration service
     * (What the Integration Service calls to get connection details)
     */
    @Query("SELECT p FROM PartnerMaster p WHERE " +
            "p.partnerId = :partnerId AND p.isActive = true AND p.status = 'APPROVED'")
    Optional<PartnerMaster> findPartnerConfigForIntegration(@Param("partnerId") String partnerId);

}