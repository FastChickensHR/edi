/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.integration.x834.persistence;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "x834_field_mapping_rules")
@Getter
@Setter
@NoArgsConstructor
public class X834FieldMappingRuleEntity {

    public static final Instant TEMPORAL_INFINITY = Instant.parse("9999-12-31T23:59:59Z");

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "row_id")
    private UUID rowId;

    @Column(name = "integration_id", nullable = false)
    private UUID integrationId;

    /** System time start — when this row was recorded in the database. */
    @Column(name = "sys_from", nullable = false)
    private Instant sysFrom;

    /** System time end — {@link #TEMPORAL_INFINITY} until superseded by a correction. */
    @Column(name = "sys_to", nullable = false)
    private Instant sysTo;

    /** Valid time start — when this rule became effective. */
    @Column(name = "valid_from", nullable = false)
    private Instant validFrom;

    /** Valid time end — {@link #TEMPORAL_INFINITY} for currently-valid rows. */
    @Column(name = "valid_to", nullable = false)
    private Instant validTo;

    /** Arbitrary key from the HR system payload, e.g. {@code "emp_status"}. */
    @Column(name = "source_field", nullable = false)
    private String sourceField;

    /** The EDI field being populated; stored as {@link com.fastChickensHR.edi.integration.x834.X834MappableField} name. */
    @Column(name = "target_edi_field", nullable = false)
    private String targetEdiField;

    /**
     * HR value → EDI value lookup table.
     * Empty for date pass-through rules; populated for enum-valued fields.
     * e.g. {@code {"T": "CANCELLATION", "A": "ADDITION"}}.
     */
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "value_mappings", columnDefinition = "jsonb", nullable = false)
    private Map<String, String> valueMappings;
}
