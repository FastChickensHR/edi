/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.integration.x834.api;

import com.fastChickensHR.edi.integration.x834.persistence.X834IntegrationConfigEntity;
import com.fastChickensHR.edi.x834.constants.ElementSeparator;
import com.fastChickensHR.edi.x834.loop2000.data.MaintenanceTypeCode;
import com.fastChickensHR.edi.x834.loop2000.data.MemberIndicator;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Component
public class X834IntegrationConfigMapper {

    /**
     * Converts a persisted entity row to a response DTO.
     */
    public X834IntegrationConfigResponse toResponse(X834IntegrationConfigEntity entity) {
        return new X834IntegrationConfigResponse(
                entity.getIntegrationId(),
                entity.getSenderID(),
                entity.getReceiverID(),
                entity.getElementSeparator(),
                entity.getPolicyNumber(),
                entity.getMemberIdQualifier(),
                entity.getMemberIndicator(),
                entity.getMaintenanceTypeCode(),
                entity.getEnrollmentDate(),
                entity.getCoverageStartDate(),
                entity.getCoverageEndDate(),
                entity.getReferenceIdentification(),
                entity.getMasterPolicyNumber(),
                entity.getPlanSponsorName(),
                entity.getPayerName(),
                entity.getPayerIdentification(),
                entity.getSysFrom()
        );
    }

    /**
     * Builds a new (open-ended) entity row for a create or update request.
     */
    public X834IntegrationConfigEntity toNewEntity(UUID integrationId, X834IntegrationConfigRequest request) {
        // Validate enum values eagerly so callers get a clear IllegalArgumentException.
        ElementSeparator.valueOf(request.elementSeparator());
        MemberIndicator.valueOf(request.memberIndicator());
        MaintenanceTypeCode.valueOf(request.maintenanceTypeCode());

        Instant now = Instant.now();
        X834IntegrationConfigEntity entity = new X834IntegrationConfigEntity();
        entity.setIntegrationId(integrationId);
        entity.setSysFrom(now);
        entity.setSysTo(X834IntegrationConfigEntity.TEMPORAL_INFINITY);
        entity.setValidFrom(now);
        entity.setValidTo(X834IntegrationConfigEntity.TEMPORAL_INFINITY);
        entity.setSenderID(request.senderID());
        entity.setReceiverID(request.receiverID());
        entity.setElementSeparator(request.elementSeparator());
        entity.setPolicyNumber(request.policyNumber());
        entity.setMemberIdQualifier(request.memberIdQualifier());
        entity.setMemberIndicator(request.memberIndicator());
        entity.setMaintenanceTypeCode(request.maintenanceTypeCode());
        entity.setEnrollmentDate(request.enrollmentDate());
        entity.setCoverageStartDate(request.coverageStartDate());
        entity.setCoverageEndDate(request.coverageEndDate());
        entity.setReferenceIdentification(request.referenceIdentification());
        entity.setMasterPolicyNumber(request.masterPolicyNumber());
        entity.setPlanSponsorName(request.planSponsorName());
        entity.setPayerName(request.payerName());
        entity.setPayerIdentification(request.payerIdentification());
        return entity;
    }

    /**
     * Closes the system-time period of a row, marking it as superseded.
     */
    public void closeSysTo(X834IntegrationConfigEntity entity) {
        entity.setSysTo(Instant.now());
    }

    /**
     * Closes the valid-time period of a row, marking it as logically deleted.
     */
    public void closeValidTo(X834IntegrationConfigEntity entity) {
        entity.setValidTo(Instant.now());
    }
}
