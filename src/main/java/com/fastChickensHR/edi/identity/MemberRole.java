/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.identity;

/**
 * The access level a User holds within a specific Organization.
 * Stored as a string in the database (not a PostgreSQL enum type)
 * to allow adding new values without a schema migration.
 */
public enum MemberRole {
    /** Full control, including deleting the organization. Only one per org. */
    OWNER,
    /** Manage users and all integrations within the organization. */
    ADMIN,
    /** Create and edit integrations; cannot manage users. */
    EDITOR,
    /** Read-only access to integrations. */
    VIEWER
}
