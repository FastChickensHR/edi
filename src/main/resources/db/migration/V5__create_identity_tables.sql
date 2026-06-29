-- Identity tables: organizations, users, organization_members
-- All tables follow the bitemporal pattern (sys_from/sys_to + valid_from/valid_to)
-- consistent with the rest of the schema.

CREATE EXTENSION IF NOT EXISTS btree_gist;

-- ─── organizations ────────────────────────────────────────────────────────────

CREATE TABLE organizations (
    row_id          UUID        PRIMARY KEY DEFAULT gen_random_uuid(),
    organization_id UUID        NOT NULL,
    sys_from        TIMESTAMPTZ NOT NULL DEFAULT now(),
    sys_to          TIMESTAMPTZ NOT NULL DEFAULT '9999-12-31 23:59:59+00',
    valid_from      TIMESTAMPTZ NOT NULL DEFAULT now(),
    valid_to        TIMESTAMPTZ NOT NULL DEFAULT '9999-12-31 23:59:59+00',
    name            TEXT        NOT NULL,
    CONSTRAINT organizations_natural_key UNIQUE (organization_id, sys_from, valid_from)
);

CREATE INDEX idx_organizations_lookup
    ON organizations (organization_id, sys_from DESC);

ALTER TABLE organizations ADD CONSTRAINT organizations_no_overlap
    EXCLUDE USING GIST (
        organization_id WITH =,
        tstzrange(valid_from, valid_to) WITH &&
    ) WHERE (sys_to = '9999-12-31 23:59:59+00');

-- ─── users ────────────────────────────────────────────────────────────────────

CREATE TABLE users (
    row_id        UUID        PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id       UUID        NOT NULL,
    sys_from      TIMESTAMPTZ NOT NULL DEFAULT now(),
    sys_to        TIMESTAMPTZ NOT NULL DEFAULT '9999-12-31 23:59:59+00',
    valid_from    TIMESTAMPTZ NOT NULL DEFAULT now(),
    valid_to      TIMESTAMPTZ NOT NULL DEFAULT '9999-12-31 23:59:59+00',
    email         TEXT        NOT NULL,
    password_hash TEXT        NOT NULL,
    CONSTRAINT users_natural_key UNIQUE (user_id, sys_from, valid_from)
);

CREATE INDEX idx_users_lookup
    ON users (user_id, sys_from DESC);

-- Email uniqueness applies only to current rows (sys_to = infinity, valid_to = infinity).
-- Enforced at the application layer; a partial unique index is here as a guard.
CREATE UNIQUE INDEX idx_users_email_current
    ON users (email)
    WHERE (sys_to = '9999-12-31 23:59:59+00' AND valid_to = '9999-12-31 23:59:59+00');

ALTER TABLE users ADD CONSTRAINT users_no_overlap
    EXCLUDE USING GIST (
        user_id WITH =,
        tstzrange(valid_from, valid_to) WITH &&
    ) WHERE (sys_to = '9999-12-31 23:59:59+00');

-- ─── organization_members ────────────────────────────────────────────────────

CREATE TABLE organization_members (
    row_id          UUID        PRIMARY KEY DEFAULT gen_random_uuid(),
    organization_id UUID        NOT NULL,
    user_id         UUID        NOT NULL,
    role            TEXT        NOT NULL
                                CHECK (role IN ('OWNER', 'ADMIN', 'EDITOR', 'VIEWER')),
    sys_from        TIMESTAMPTZ NOT NULL DEFAULT now(),
    sys_to          TIMESTAMPTZ NOT NULL DEFAULT '9999-12-31 23:59:59+00',
    valid_from      TIMESTAMPTZ NOT NULL DEFAULT now(),
    valid_to        TIMESTAMPTZ NOT NULL DEFAULT '9999-12-31 23:59:59+00',
    CONSTRAINT organization_members_natural_key
        UNIQUE (organization_id, user_id, sys_from, valid_from)
);

CREATE INDEX idx_org_members_by_org
    ON organization_members (organization_id, sys_from DESC);

CREATE INDEX idx_org_members_by_user
    ON organization_members (user_id, sys_from DESC);

ALTER TABLE organization_members ADD CONSTRAINT organization_members_no_overlap
    EXCLUDE USING GIST (
        organization_id WITH =,
        user_id         WITH =,
        tstzrange(valid_from, valid_to) WITH &&
    ) WHERE (sys_to = '9999-12-31 23:59:59+00');
