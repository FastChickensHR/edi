/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.loop2000;

/**
 * The kind of postal address a member can carry.
 * <p>
 * A member may have more than one address of different kinds. Which kinds the X12 834
 * (005010X220A1) can actually serialize is limited by the transaction's structure:
 * <ul>
 *   <li>{@link #RESIDENCE} — the member's residence address, emitted as Loop 2100A N3/N4.</li>
 *   <li>{@link #MAILING} — the member's mailing address, emitted as Loop 2100C (NM1*31, N3, N4).</li>
 *   <li>{@link #WORK}, {@link #BILLING} — accepted on the domain model for completeness, but the
 *       834 member structure has no loop for them, so they are not serialized.</li>
 * </ul>
 */
public enum AddressType {
    /** Residence / home address — Loop 2100A. */
    RESIDENCE,
    /** Mailing address — Loop 2100C. */
    MAILING,
    /** Work address — accepted but not serialized (no 834 member loop). */
    WORK,
    /** Billing address — accepted but not serialized (no 834 member loop). */
    BILLING
}
