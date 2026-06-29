-- Auth token tables: api_keys, refresh_tokens
-- Both tables follow the bitemporal pattern consistent with the rest of the schema.
-- Keys and tokens are stored only as hashes — raw values are never persisted.

-- ─── api_keys ─────────────────────────────────────────────────────────────────

CREATE TABLE api_keys (
    row_id              UUID        PRIMARY KEY DEFAULT gen_random_uuid(),
    api_key_id          UUID        NOT NULL,
    organization_id     UUID        NOT NULL,
    created_by_user_id  UUID        NOT NULL,
    sys_from            TIMESTAMPTZ NOT NULL DEFAULT now(),
    sys_to              TIMESTAMPTZ NOT NULL DEFAULT '9999-12-31 23:59:59+00',
    valid_from          TIMESTAMPTZ NOT NULL DEFAULT now(),
    valid_to            TIMESTAMPTZ NOT NULL DEFAULT '9999-12-31 23:59:59+00',
    name                TEXT        NOT NULL,
    key_hash            TEXT        NOT NULL,
    expires_at          TIMESTAMPTZ,
    revoked_at          TIMESTAMPTZ,
    CONSTRAINT api_keys_natural_key UNIQUE (api_key_id, sys_from, valid_from)
);

CREATE INDEX idx_api_keys_by_org
    ON api_keys (organization_id, sys_from DESC);

-- Lookup by hash is the hot path on every authenticated API request.
CREATE INDEX idx_api_keys_hash_current
    ON api_keys (key_hash)
    WHERE (sys_to = '9999-12-31 23:59:59+00' AND valid_to = '9999-12-31 23:59:59+00');

ALTER TABLE api_keys ADD CONSTRAINT api_keys_no_overlap
    EXCLUDE USING GIST (
        api_key_id WITH =,
        tstzrange(valid_from, valid_to) WITH &&
    ) WHERE (sys_to = '9999-12-31 23:59:59+00');

-- ─── refresh_tokens ───────────────────────────────────────────────────────────

CREATE TABLE refresh_tokens (
    row_id      UUID        PRIMARY KEY DEFAULT gen_random_uuid(),
    token_id    UUID        NOT NULL,
    user_id     UUID        NOT NULL,
    sys_from    TIMESTAMPTZ NOT NULL DEFAULT now(),
    sys_to      TIMESTAMPTZ NOT NULL DEFAULT '9999-12-31 23:59:59+00',
    valid_from  TIMESTAMPTZ NOT NULL DEFAULT now(),
    valid_to    TIMESTAMPTZ NOT NULL DEFAULT '9999-12-31 23:59:59+00',
    token_hash  TEXT        NOT NULL,
    expires_at  TIMESTAMPTZ NOT NULL,
    CONSTRAINT refresh_tokens_natural_key UNIQUE (token_id, sys_from, valid_from)
);

CREATE INDEX idx_refresh_tokens_by_user
    ON refresh_tokens (user_id, sys_from DESC);

-- Lookup by hash is the hot path on every token refresh request.
CREATE INDEX idx_refresh_tokens_hash_current
    ON refresh_tokens (token_hash)
    WHERE (sys_to = '9999-12-31 23:59:59+00' AND valid_to = '9999-12-31 23:59:59+00');

ALTER TABLE refresh_tokens ADD CONSTRAINT refresh_tokens_no_overlap
    EXCLUDE USING GIST (
        token_id WITH =,
        tstzrange(valid_from, valid_to) WITH &&
    ) WHERE (sys_to = '9999-12-31 23:59:59+00');
