/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.loop2000.loop2100A;

import com.fastChickensHR.edi.x834.data.HealthRelatedCode;
import lombok.Getter;
import lombok.Setter;

/**
 * The member's health-related status — the Loop 2100A {@code HLH} of the X12 834 (005010X220A1). A
 * member has at most one.
 * <p>
 * {@link #healthRelatedCode} is what carriers actually ask for: tobacco and substance use, a rating
 * input for individual and small-group products. BCBSM notes it "may be required for specific
 * employer groups", so whether to send it is a config-time answer. The height and weight elements
 * round the segment out.
 * <p>
 * Heights and weights are carried as strings so the caller's own precision reaches the wire
 * unchanged.
 * <p>
 * This is a pure domain object; translation to 834 segments is the writer's job.
 */
@Getter
@Setter
public class HealthInformation {
    /** The member's tobacco/substance status (HLH01). */
    private HealthRelatedCode healthRelatedCode;
    /** The member's height (HLH02). */
    private String height;
    /** The member's current weight (HLH03). */
    private String currentWeight;
    /** The member's previous weight (HLH04). */
    private String previousWeight;
    /** Why the weight changed (HLH05). */
    private String description;

    public HealthInformation() {
    }

    /**
     * @param healthRelatedCode the member's tobacco/substance status (HLH01)
     */
    public HealthInformation(HealthRelatedCode healthRelatedCode) {
        this.healthRelatedCode = healthRelatedCode;
    }
}
