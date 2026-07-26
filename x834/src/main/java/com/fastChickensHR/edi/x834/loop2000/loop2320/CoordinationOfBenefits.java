/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.loop2000.loop2320;

import com.fastChickensHR.edi.x834.data.CoordinationOfBenefitsCode;
import com.fastChickensHR.edi.x834.data.PayerResponsibilitySequenceCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Another plan this member also holds — one occurrence of Loop 2320 (with its 2330) in the X12 834
 * (005010X220A1).
 * <p>
 * {@link #payerResponsibility} says where that plan sits in the payment order and
 * {@link #benefitsCoordination} whether benefits are coordinated at all; together they are the
 * carrier's answer to "does this person have other coverage, and does it pay first?". The rest is
 * detail about that plan: its {@link #policyIdentifier}, the {@link #groupNumber} it is held under,
 * the dates the coordination is {@link #beginDate in force}, and the
 * {@link #relatedEntityName name} of the plan itself.
 * <p>
 * The two shapes profiled carriers ask for sit at opposite ends of the range. BCBSM's Medicare
 * block populates nearly all of it — {@code COB*S*<MBI>*1~}, {@code DTP*344}/{@code 345}, and a
 * 2330 naming {@code MEDICARE PART A}. CareFirst wants only the bare statement, on every medical
 * and dental row: {@code COB*U**6~}.
 * <p>
 * This is a pure domain object; translation to 834 segments is the writer's job.
 */
@Getter
@Setter
public class CoordinationOfBenefits {

    /** REF01 qualifier {@code 6P} — Group Number; the default for the 2320 group-number REF. */
    public static final String DEFAULT_GROUP_NUMBER_QUALIFIER = "6P";

    /** Where the other payer sits in the payment order (COB01) — required. */
    private PayerResponsibilitySequenceCode payerResponsibility;
    /** The other plan's policy identifier (COB02); BCBSM carries the Medicare MBI here. */
    private String policyIdentifier;
    /** Whether benefits are coordinated, and for whom (COB03). */
    private CoordinationOfBenefitsCode benefitsCoordination;
    /** The group number the other plan is held under (2320 {@code REF} REF02). */
    private String groupNumber;
    /** The REF qualifier for {@link #groupNumber} (REF01); defaults to {@code 6P}. */
    private String groupNumberQualifier = DEFAULT_GROUP_NUMBER_QUALIFIER;
    /** When coordination begins (2320 {@code DTP*344}). */
    private LocalDateTime beginDate;
    /** When coordination ends (2320 {@code DTP*345}). */
    private LocalDateTime endDate;
    /** The other plan's name (2330 {@code NM1} NM103), e.g. {@code MEDICARE PART A}. */
    private String relatedEntityName;

    public CoordinationOfBenefits() {
    }

    /**
     * @param payerResponsibility  where the other payer sits in the payment order (COB01)
     * @param benefitsCoordination whether benefits are coordinated, and for whom (COB03)
     */
    public CoordinationOfBenefits(PayerResponsibilitySequenceCode payerResponsibility,
                                  CoordinationOfBenefitsCode benefitsCoordination) {
        this.payerResponsibility = payerResponsibility;
        this.benefitsCoordination = benefitsCoordination;
    }
}
