/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.integration.x834.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface X834FieldMappingRuleRepository extends JpaRepository<X834FieldMappingRuleEntity, UUID> {

    /**
     * Returns all currently-valid, currently-known rules for a given integration.
     */
    @Query(value = """
            SELECT * FROM x834_field_mapping_rules
            WHERE integration_id = :integrationId
              AND sys_to   = '9999-12-31 23:59:59+00'
              AND valid_from <= now()
              AND valid_to   >  now()
            ORDER BY source_field
            """, nativeQuery = true)
    List<X834FieldMappingRuleEntity> findAllCurrentByIntegrationId(@Param("integrationId") UUID integrationId);

    /**
     * Returns the currently-valid, currently-known rule for a given integration and target EDI field.
     */
    @Query(value = """
            SELECT * FROM x834_field_mapping_rules
            WHERE integration_id   = :integrationId
              AND target_edi_field  = :targetEdiField
              AND sys_to   = '9999-12-31 23:59:59+00'
              AND valid_from <= now()
              AND valid_to   >  now()
            LIMIT 1
            """, nativeQuery = true)
    Optional<X834FieldMappingRuleEntity> findCurrentByIntegrationIdAndTargetField(
            @Param("integrationId") UUID integrationId,
            @Param("targetEdiField") String targetEdiField);
}
