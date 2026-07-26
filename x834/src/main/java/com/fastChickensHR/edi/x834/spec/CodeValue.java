/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.spec;

/**
 * One member of a published code list: the code that goes on the wire and the standard's description
 * of it. Descriptions exist so a consumer can render a pick list without copying the list itself.
 */
public record CodeValue(String code, String description) {

    public CodeValue {
        if (code == null || code.isBlank()) {
            throw new IllegalArgumentException("Code cannot be null or blank");
        }
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Description cannot be null or blank for code: " + code);
        }
    }
}
