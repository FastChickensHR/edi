/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.loop2000.data;

import lombok.Getter;

@Getter
public enum MemberIndicator {
    INSURED("Y", "Insured"),
    NOT_INSURED("N", "Not Insured");

    private final String code;
    private final String description;

    MemberIndicator(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public static MemberIndicator fromCode(String code) {
        for (MemberIndicator value : values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        throw new IllegalArgumentException("Invalid Member Indicator code: " + code);
    }

    @Override
    public String toString() {
        return code;
    }
}