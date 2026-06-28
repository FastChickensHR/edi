-- Enable the GiST extension required for the temporal exclusion constraint.
CREATE EXTENSION IF NOT EXISTS btree_gist;

-- Drop rows that were soft-deleted under the old boolean scheme.
-- These have no reliable valid-time boundary, so they are discarded.
DELETE FROM integrations WHERE is_deleted = true;

-- Rename transaction-time start column to its canonical bitemporal name.
ALTER TABLE integrations RENAME COLUMN created_at TO sys_from;

-- Add the three new temporal columns as nullable so we can backfill first.
ALTER TABLE integrations
    ADD COLUMN sys_to     TIMESTAMPTZ,
    ADD COLUMN valid_from TIMESTAMPTZ,
    ADD COLUMN valid_to   TIMESTAMPTZ;

-- Backfill existing rows: all surviving rows are current and open-ended.
-- valid_from is approximated as sys_from (earliest known effective date).
UPDATE integrations
SET sys_to     = '9999-12-31 23:59:59+00',
    valid_from = sys_from,
    valid_to   = '9999-12-31 23:59:59+00';

-- Enforce NOT NULL now that every row has values.
ALTER TABLE integrations
    ALTER COLUMN sys_to    SET NOT NULL,
    ALTER COLUMN valid_from SET NOT NULL,
    ALTER COLUMN valid_to   SET NOT NULL;

-- Set database-level defaults for future inserts.
ALTER TABLE integrations
    ALTER COLUMN sys_to    SET DEFAULT '9999-12-31 23:59:59+00',
    ALTER COLUMN valid_from SET DEFAULT now(),
    ALTER COLUMN valid_to   SET DEFAULT '9999-12-31 23:59:59+00';

-- Drop the old soft-delete column — valid_to replaces it.
ALTER TABLE integrations DROP COLUMN is_deleted;

-- Natural uniqueness: no two rows may share the same (integration_id, sys_from, valid_from).
ALTER TABLE integrations
    ADD CONSTRAINT uq_integrations_bitemp
        UNIQUE (integration_id, sys_from, valid_from);

-- Replace the old lookup index with one covering the new column name.
DROP INDEX idx_integrations_lookup;
CREATE INDEX idx_integrations_lookup ON integrations (integration_id, sys_from DESC);

-- Exclusion constraint: among all "currently-known" rows (sys_to = infinity),
-- no two rows for the same integration may have overlapping valid-time periods.
ALTER TABLE integrations
    ADD CONSTRAINT excl_integrations_valid_no_overlap
        EXCLUDE USING gist (
            integration_id WITH =,
            tstzrange(valid_from, valid_to) WITH &&
        ) WHERE (sys_to = '9999-12-31 23:59:59+00');
