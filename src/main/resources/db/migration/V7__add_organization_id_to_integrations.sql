-- Add organization_id to integrations table.
-- Nullable to allow safe schema evolution on existing rows;
-- the application layer enforces presence on all new writes.
-- Also rename owner_id to created_by_user_id to clarify its role as audit metadata.

ALTER TABLE integrations
    ADD COLUMN organization_id UUID,
    ADD COLUMN created_by_user_id UUID;

-- Backfill created_by_user_id from the existing owner_id column
-- so historical rows retain their creator reference.
UPDATE integrations SET created_by_user_id = owner_id;

-- owner_id is superseded by created_by_user_id; drop it.
ALTER TABLE integrations DROP COLUMN owner_id;

CREATE INDEX idx_integrations_by_org
    ON integrations (organization_id, sys_from DESC)
    WHERE organization_id IS NOT NULL;
