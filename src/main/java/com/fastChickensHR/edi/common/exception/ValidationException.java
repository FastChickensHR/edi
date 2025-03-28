/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.common.exception;

/**
 * Exception thrown when EDI document validation fails.
 * This exception is used to signal issues with segment content,
 * required fields, or cross-segment validation failures.
 */
public class ValidationException extends Exception {

    /**
     * Constructs a new validation exception with the specified detail message.
     *
     * @param message the detail message
     */
    public ValidationException(String message) {
        super(message);
    }

    /**
     * Constructs a new validation exception with the specified detail message and cause.
     *
     * @param message the detail message
     * @param cause the cause of the exception
     */
    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new validation exception with the specified cause.
     *
     * @param cause the cause of the exception
     */
    public ValidationException(Throwable cause) {
        super(cause);
    }
}