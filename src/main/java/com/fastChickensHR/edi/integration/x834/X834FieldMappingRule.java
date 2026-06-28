/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.integration.x834;

import lombok.Builder;
import lombok.Value;

import java.util.Map;
import java.util.UUID;

/**
 * Domain object representing a single field mapping rule for an X12 834 integration.
 *
 * <p>A rule maps an arbitrary HR system payload key ({@code sourceField}) to a
 * specific EDI field ({@code targetEdiField}). For enum-valued EDI fields,
 * {@code valueMappings} translates the HR system's raw values to EDI enum names.
 * For date fields, {@code valueMappings} is empty and the source value passes through.</p>
 *
 * <p>Example — mapping employment status to maintenance type code:</p>
 * <pre>
 *   sourceField    = "emp_status"
 *   targetEdiField = MAINTENANCE_TYPE_CODE
 *   valueMappings  = { "A": "ADDITION", "T": "CANCELLATION", "L": "CHANGE" }
 * </pre>
 *
 * <p>Example — mapping a date field:</p>
 * <pre>
 *   sourceField    = "benefit_start_dt"
 *   targetEdiField = COVERAGE_START_DATE
 *   valueMappings  = {}
 * </pre>
 */
@Value
@Builder
public class X834FieldMappingRule {
    UUID integrationId;
    String sourceField;
    X834MappableField targetEdiField;
    Map<String, String> valueMappings;
}
