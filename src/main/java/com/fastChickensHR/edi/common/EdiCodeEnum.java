/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.common;

/**
 * Interface for EDI code enumerations.
 * Provides common methods for all EDI code enums.
 */
public interface EdiCodeEnum {

    /**
     * Gets the EDI code value.
     *
     * @return the code string
     */
    String getCode();

    /**
     * Gets the description of the EDI code.
     *
     * @return the description string
     */
    String getDescription();
}