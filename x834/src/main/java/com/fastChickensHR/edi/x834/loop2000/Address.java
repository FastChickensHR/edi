/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.loop2000;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A single typed postal address for a member (residence, mailing, work, ...).
 * <p>
 * This is a pure domain object; how (and whether) a given {@link AddressType} is serialized into
 * the X12 834 wire format is decided by the writer — see {@link AddressType} for the mapping.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    private AddressType type;
    private String line1;
    private String line2;
    private String city;
    private String state;
    private String zipCode;

    /** @return true when this address carries a street line (the minimum needed to emit an N3). */
    public boolean hasStreet() {
        return line1 != null && !line1.isEmpty();
    }

    /** @return true when city, state and postal code are all present (the minimum to emit an N4). */
    public boolean hasCityStateZip() {
        return city != null && !city.isEmpty()
                && state != null && !state.isEmpty()
                && zipCode != null && !zipCode.isEmpty();
    }
}
