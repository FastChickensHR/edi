-- btree_gist extension already enabled in V2.

CREATE TABLE x834_integration_configs (
    row_id                  UUID        NOT NULL DEFAULT gen_random_uuid(),
    integration_id          UUID        NOT NULL,

    -- Bitemporal columns
    sys_from                TIMESTAMPTZ NOT NULL DEFAULT now(),
    sys_to                  TIMESTAMPTZ NOT NULL DEFAULT '9999-12-31 23:59:59+00',
    valid_from              TIMESTAMPTZ NOT NULL DEFAULT now(),
    valid_to                TIMESTAMPTZ NOT NULL DEFAULT '9999-12-31 23:59:59+00',

    -- EDI envelope
    sender_id               VARCHAR     NOT NULL,
    receiver_id             VARCHAR     NOT NULL,
    element_separator       VARCHAR     NOT NULL,

    -- Enrollment context (per-member fields are handled via field mapping rules)
    policy_number           VARCHAR     NOT NULL,
    member_id_qualifier     VARCHAR     NOT NULL,

    -- Header
    reference_identification VARCHAR,
    master_policy_number    VARCHAR     NOT NULL,
    plan_sponsor_name       VARCHAR     NOT NULL,
    payer_name              VARCHAR     NOT NULL,
    payer_identification    VARCHAR     NOT NULL,

    CONSTRAINT pk_x834_integration_configs PRIMARY KEY (row_id)
);

CREATE INDEX idx_x834_configs_lookup
    ON x834_integration_configs (integration_id, sys_from DESC);

ALTER TABLE x834_integration_configs
    ADD CONSTRAINT uq_x834_configs_bitemp
        UNIQUE (integration_id, sys_from, valid_from);

ALTER TABLE x834_integration_configs
    ADD CONSTRAINT excl_x834_configs_valid_no_overlap
        EXCLUDE USING gist (
            integration_id WITH =,
            tstzrange(valid_from, valid_to) WITH &&
        ) WHERE (sys_to = '9999-12-31 23:59:59+00');
