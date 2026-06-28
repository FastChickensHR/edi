CREATE TABLE x834_field_mapping_rules (
    row_id              UUID        NOT NULL DEFAULT gen_random_uuid(),
    integration_id      UUID        NOT NULL,

    -- Bitemporal columns
    sys_from            TIMESTAMPTZ NOT NULL DEFAULT now(),
    sys_to              TIMESTAMPTZ NOT NULL DEFAULT '9999-12-31 23:59:59+00',
    valid_from          TIMESTAMPTZ NOT NULL DEFAULT now(),
    valid_to            TIMESTAMPTZ NOT NULL DEFAULT '9999-12-31 23:59:59+00',

    -- Rule definition
    source_field        VARCHAR     NOT NULL,
    target_edi_field    VARCHAR     NOT NULL,
    value_mappings      JSONB       NOT NULL DEFAULT '{}',

    CONSTRAINT pk_x834_field_mapping_rules PRIMARY KEY (row_id)
);

CREATE INDEX idx_x834_mapping_rules_lookup
    ON x834_field_mapping_rules (integration_id, target_edi_field, sys_from DESC);

-- Natural uniqueness: no two rows may share the same (integration_id, target_edi_field, sys_from, valid_from).
ALTER TABLE x834_field_mapping_rules
    ADD CONSTRAINT uq_x834_mapping_rules_bitemp
        UNIQUE (integration_id, target_edi_field, sys_from, valid_from);

-- Exclusion constraint: among all "currently-known" rows (sys_to = infinity),
-- no two rows for the same integration + target field may have overlapping valid-time periods.
ALTER TABLE x834_field_mapping_rules
    ADD CONSTRAINT excl_x834_mapping_rules_valid_no_overlap
        EXCLUDE USING gist (
            integration_id   WITH =,
            target_edi_field WITH =,
            tstzrange(valid_from, valid_to) WITH &&
        ) WHERE (sys_to = '9999-12-31 23:59:59+00');
