/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.loop2000;

import com.fastChickensHR.edi.x834.common.segments.Segment;
import com.fastChickensHR.edi.x834.common.exception.ValidationException;
import com.fastChickensHR.edi.x834.loop2000.data.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Abstract class representing the member level detail in Loop 2000 of an EDI 834 document.
 * This segment is commonly referred to as the "INS" segment and includes information about
 * the member such as the member indicator, individual relationship code, maintenance type code,
 * benefit status code, and other member-specific information.
 */
@Getter
public abstract class MemberLevelDetail extends Segment {
    public static final String SEGMENT_ID = "INS";

    private final MemberIndicator ins01;
    private final IndividualRelationshipCode ins02;
    private final MaintenanceTypeCode ins03;
    private final MaintenanceReasonCode ins04;
    private final BenefitStatusCode ins05;
    private final MedicarePlanCode ins06;
    private final COBRAQualifyingEventCode ins07;
    private final EmploymentStatusCode ins08;
    private final StudentStatusCode ins09;
    private final HandicapIndicator ins10;
    private final String ins11; // Death Date
    private final ConfidentialityCode ins12;

    protected MemberLevelDetail(Builder builder) throws ValidationException {
        this.ins01 = builder.ins01;
        this.ins02 = builder.ins02;
        this.ins03 = builder.ins03;
        this.ins04 = builder.ins04;
        this.ins05 = builder.ins05;
        this.ins06 = builder.ins06;
        this.ins07 = builder.ins07;
        this.ins08 = builder.ins08;
        this.ins09 = builder.ins09;
        this.ins10 = builder.ins10;
        this.ins11 = builder.ins11;
        this.ins12 = builder.ins12;

        validateRequiredFields();
    }

    private void validateRequiredFields() throws ValidationException {
        if (ins01 == null) {
            throw new ValidationException("INS01 (Member Indicator) cannot be blank");
        }
        if (ins02 == null) {
            throw new ValidationException("INS02 (Individual Relationship Code) cannot be blank");
        }
        if (ins03 == null) {
            throw new ValidationException("INS03 (Maintenance Type Code) cannot be blank");
        }
        if (ins05 == null) {
            throw new ValidationException("INS05 (Benefit Status Code) cannot be blank");
        }
    }

    @Override
    public String getSegmentIdentifier() {
        return SEGMENT_ID;
    }

    @Override
    public String[] getElementValues() {
        return new String[]{ins01 != null ? ins01.toString() : null, ins02 != null ? ins02.toString() : null, ins03 != null ? ins03.toString() : null, ins04 != null ? ins04.toString() : null, ins05 != null ? ins05.toString() : null, ins06 != null ? ins06.toString() : null, ins07 != null ? ins07.toString() : null, ins08 != null ? ins08.toString() : null, ins09 != null ? ins09.toString() : null, ins10 != null ? ins10.toString() : null, ins11, ins12 != null ? ins12.toString() : null,};
    }

    public String getIns01() {
        return ins01.toString();
    }

    public String getMemberIndicator() {
        return getIns01();
    }

    public String getIns02() {
        return ins02.toString();
    }

    public String getIndividualRelationshipCode() {
        return getIns02();
    }

    public String getIns03() {
        return ins03.toString();
    }

    public String getMaintenanceTypeCode() {
        return getIns03();
    }

    public String getIns04() {
        return ins04.toString();
    }

    public String getMaintenanceReasonCode() {
        return getIns04();
    }

    public String getIns05() {
        return ins05.toString();
    }

    public String getBenefitStatusCode() {
        return getIns05();
    }

    public String getIns06() {
        return ins06.toString();
    }

    public String getMedicarePlanCode() {
        return getIns06();
    }

    public String getIns07() {
        return ins07.toString();
    }

    public String getCOBRAQualifyingEventCode() {
        return getIns07();
    }

    public String getIns08() {
        return ins08.toString();
    }

    public String getEmploymentStatusCode() {
        return getIns08();
    }

    public String getIns09() {
        return ins09.toString();
    }

    public String getStudentStatusCode() {
        return getIns09();
    }

    public String getIns10() {
        return ins10.toString();
    }

    public String getHandicapIndicator() {
        return getIns10();
    }

    public String getDeathDate() {
        return getIns11();
    }

    public String getIns12() {
        return ins12.toString();
    }

    public String getConfidentialityCode() {
        return getIns12();
    }

    @Setter
    @Accessors(chain = true)
    public static class Builder {
        private MemberIndicator ins01;
        private IndividualRelationshipCode ins02;
        private MaintenanceTypeCode ins03;
        private MaintenanceReasonCode ins04;
        private BenefitStatusCode ins05;
        private MedicarePlanCode ins06;
        private COBRAQualifyingEventCode ins07;
        private EmploymentStatusCode ins08;
        private StudentStatusCode ins09;
        private HandicapIndicator ins10;
        private String ins11;
        private ConfidentialityCode ins12;


        public Builder setIns01(String value) {
            this.ins01 = MemberIndicator.fromCode(value);
            return this;
        }

        public Builder setMemberIndicator(String value) {
            return setIns01(value);
        }

        public Builder setIns02(String value) {
            this.ins02 = IndividualRelationshipCode.fromString(value);
            return this;
        }

        public Builder setIndividualRelationshipCode(String value) {
            return setIns02(value);
        }

        public Builder setIns03(String value) {
            this.ins03 = MaintenanceTypeCode.fromString(value);
            return this;
        }

        public Builder setMaintenanceTypeCode(String value) {
            return setIns03(value);
        }

        public Builder setIns04(String value) {
            this.ins04 = MaintenanceReasonCode.fromString(value);
            return this;
        }

        public Builder setMaintenanceReasonCode(String value) {
            return setIns04(value);
        }

        public Builder setIns05(String value) {
            this.ins05 = BenefitStatusCode.fromString(value);
            return this;
        }

        public Builder setBenefitStatusCode(String value) {
            return setIns05(value);
        }

        public Builder setIns06(String value) {
            this.ins06 = MedicarePlanCode.fromString(value);
            return this;
        }

        public Builder setMedicarePlanCode(String value) {
            return setIns06(value);
        }

        public Builder setIns07(String value) {
            this.ins07 = COBRAQualifyingEventCode.fromString(value);
            return this;
        }

        public Builder setCobraQualifyingEventCode(String value) {
            return setIns07(value);
        }

        public Builder setIns08(String value) {
            this.ins08 = EmploymentStatusCode.fromString(value);
            return this;
        }

        public Builder setEmploymentStatusCode(String value) {
            return setIns08(value);
        }

        public Builder setIns09(String value) {
            this.ins09 = StudentStatusCode.fromString(value);
            return this;
        }

        public Builder setStudentStatusCode(String value) {
            return setIns09(value);
        }

        public Builder setIns10(String value) {
            this.ins10 = HandicapIndicator.fromString(value);
            return this;
        }

        public Builder setHandicapIndicator(String value) {
            return setIns10(value);
        }

        public Builder setDeathDate(String date) {
            return setIns11(date);
        }

        public Builder setIns12(String value) {
            this.ins12 = ConfidentialityCode.fromString(value);
            return this;
        }

        public Builder setConfidentialityCode(String value) {
            return setIns12(value);
        }

        /**
         * Builds a new MemberLevelDetail instance
         */
        public MemberLevelDetail build() throws ValidationException {
            return new MemberLevelDetailImpl(this);
        }
    }

    // Concrete implementation class that can be instantiated
    private static class MemberLevelDetailImpl extends MemberLevelDetail {
        private MemberLevelDetailImpl(Builder builder) throws ValidationException {
            super(builder);
        }
    }
}