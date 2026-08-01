/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.exception;

/**
 * The construction-phase half of the failure contract: thrown — checked, at the call site — by a
 * component's {@code build()} or {@code validate()} when that one component is mis-constructed (a
 * missing required element, an over-length value, a broken cross-element rule).
 *
 * <p>The split is deliberate. A construction failure is local to the component being built and is
 * the caller's own coding or data error, so it surfaces immediately, as a checked exception, at the
 * call that caused it. Document-level generation, by contrast, never throws for content problems:
 * once components reach {@link com.fastChickensHR.edi.x834.X834Document}, every problem —
 * build-time and render-time alike — accumulates into a {@link
 * com.fastChickensHR.edi.x834.GenerationResult.Failure} so one {@code generateDocument()} call
 * reports them all.
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
