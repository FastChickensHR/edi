CREATE TABLE integrations (
    row_id            UUID        NOT NULL DEFAULT gen_random_uuid(),
    integration_id    UUID        NOT NULL,
    created_at        TIMESTAMPTZ NOT NULL DEFAULT now(),
    is_deleted        BOOLEAN     NOT NULL DEFAULT FALSE,
    name              VARCHAR     NOT NULL,
    owner_id          UUID        NOT NULL,
    from_system_type  VARCHAR     NOT NULL,
    from_system_value VARCHAR     NOT NULL,
    to_system_type    VARCHAR     NOT NULL,
    to_system_value   VARCHAR     NOT NULL,
    format            VARCHAR     NOT NULL,

    CONSTRAINT pk_integrations PRIMARY KEY (row_id)
);

CREATE INDEX idx_integrations_lookup
    ON integrations (integration_id, created_at DESC);
