/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */

/**
 * The X12 834 (005010X220A1) document model. {@link com.fastChickensHR.edi.x834.X834Document}
 * assembles a benefit enrollment file from {@link com.fastChickensHR.edi.x834.X834Context} plus
 * header, member loop and trailer, and renders it through the accumulate-never-throw {@link
 * com.fastChickensHR.edi.x834.GenerationResult} contract; construction-phase failures throw {@link
 * com.fastChickensHR.edi.x834.exception.ValidationException} at each component's own
 * build/validate. Start at {@link com.fastChickensHR.edi.x834.X834Document}.
 */
package com.fastChickensHR.edi.x834;
