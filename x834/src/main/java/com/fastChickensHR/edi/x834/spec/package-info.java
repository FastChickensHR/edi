/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */

/**
 * What the 005010X220A1 834 says about each element position it writes — published so a consumer
 * can narrow a code list, render a pick list, or check a length without transcribing any of the
 * standard.
 *
 * <p>{@link com.fastChickensHR.edi.x834.spec.X834Spec} is the entry point: it maps an {@link
 * com.fastChickensHR.edi.x834.spec.ElementPosition} (loop + segment + element ordinal, e.g. {@code
 * "2000 INS08"}) to an {@link com.fastChickensHR.edi.x834.spec.ElementSpec} (element number, name,
 * {@link com.fastChickensHR.edi.x834.spec.DataType}, length bounds, permitted codes). {@link
 * com.fastChickensHR.edi.x834.spec.ElementSpec#permits} answers the one question a consumer
 * narrowing the standard needs answered: is this proposed list of codes really a subset of what the
 * position permits?
 *
 * <p>Three properties make this package safe to build a ratchet on:
 *
 * <ol>
 *   <li><strong>One copy of every list.</strong> Code lists are projected from this library's code
 *       enums, so what is published and what the builders accept are the same list by construction.
 *   <li><strong>Strict membership.</strong> Nothing here reaches {@link
 *       com.fastChickensHR.edi.x834.util.EdiEnumLookup}, whose job is the opposite one — resolving
 *       loose human input to a code. A conformance check that accepted {@code "fired"} as a member
 *       of element 584 would not be a conformance check.
 *   <li><strong>No trading-partner vocabulary.</strong> This package speaks only loop, segment,
 *       element, code, length and character set. Which trading partner demands which subset is the
 *       consumer's business; this library never learns of it.
 * </ol>
 */
package com.fastChickensHR.edi.x834.spec;
