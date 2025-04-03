/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.common.segments;

import com.fastChickensHR.edi.common.data.FunctionalIdentifierCode;
import com.fastChickensHR.edi.common.data.ResponsibleAgencyCode;
import com.fastChickensHR.edi.common.data.VersionCode;
import com.fastChickensHR.edi.common.dates.DateFormat;
import com.fastChickensHR.edi.common.dates.DateFormatter;
import com.fastChickensHR.edi.common.dates.TimeFormat;
import com.fastChickensHR.edi.common.exception.ValidationException;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * Represents the GS (Functional Group Header) segment in EDI formats.
 * This segment initiates and identifies a functional group within an interchange.
 */
@Getter
abstract public class GSSegment extends Segment {
    public static final String SEGMENT_ID = "GS";

    protected final FunctionalIdentifierCode gs01;
    protected final String gs02; // Application Sender's Code 
    protected final String gs03; // Application Receiver's Code
    protected final String gs04; // Date (YYYYMMDD)
    protected final String gs05; // Time (HHMM)
    protected final String gs06; // Group Control Number
    protected final ResponsibleAgencyCode gs07;
    protected final VersionCode gs08;

    protected GSSegment(AbstractBuilder<?> builder) throws ValidationException {
        this.gs01 = builder.gs01;
        this.gs02 = builder.gs02;
        this.gs03 = builder.gs03;
        this.gs04 = builder.gs04;
        this.gs05 = builder.gs05;
        this.gs06 = builder.gs06;
        this.gs07 = builder.gs07;
        this.gs08 = builder.gs08;

        validateRequiredFields();
    }

    private void validateRequiredFields() throws ValidationException {
        if (gs01 == null) {
            throw new ValidationException("Functional Identifier Code (GS01) is required");
        }
        if (gs02 == null || gs02.trim().isEmpty()) {
            throw new ValidationException("Application Sender's Code (GS02) is required");
        }
        if (gs02.length() < 2 || gs02.length() > 15) {
            throw new ValidationException("Application Sender's Code (GS02) must be between 2 and 15 characters");
        }
        if (gs03 == null || gs03.trim().isEmpty()) {
            throw new ValidationException("Application Receiver's Code (GS03) is required");
        }
        if (gs03.length() < 2 || gs03.length() > 15) {
            throw new ValidationException("Application Receiver's Code (GS03) must be between 2 and 15 characters");
        }
        if (gs04 == null || gs04.trim().isEmpty()) {
            throw new ValidationException("Date (GS04) is required");
        }
        if (gs05 == null || gs05.trim().isEmpty()) {
            throw new ValidationException("Time (GS05) is required");
        }
        if (gs06 == null || gs06.trim().isEmpty()) {
            throw new ValidationException("Group Control Number (GS06) is required");
        }
        if (gs06.length() > 9) {
            throw new ValidationException("Group Control Number (GS06) must be 9 or less characters");
        }
        if (gs07 == null) {
            throw new ValidationException("Responsible Agency Code (GS07) is required");
        }
        if (gs08 == null) {
            throw new ValidationException("Version/Release/Industry Identifier Code (GS08) is required");
        }
    }

    @Override
    public String getSegmentIdentifier() {
        return SEGMENT_ID;
    }

    @Override
    public String[] getElementValues() {
        return new String[]{
                gs01.getCode(), gs02, gs03, gs04, gs05, gs06, gs07.getCode(), gs08.getCode()
        };
    }

    public FunctionalIdentifierCode getFunctionalIdentifierCode() {
        return gs01;
    }

    public String getApplicationSenderCode() {
        return gs02;
    }

    public String getApplicationReceiverCode() {
        return gs03;
    }

    public String getTransactionSetCreationDate() {
        return gs04;
    }

    public String getTransactionSetCreationTime() {
        return gs05;
    }

    public String getGroupControlNumber() {
        return gs06;
    }

    public ResponsibleAgencyCode getResponsibleAgencyCode() {
        return gs07;
    }

    public VersionCode getVersionReleaseIndustryCode() {
        return gs08;
    }

    /**
     * Abstract builder for GS segments.
     *
     * @param <T> The concrete builder type for method chaining
     */
    @Accessors(chain = true)
    public abstract static class AbstractBuilder<T extends AbstractBuilder<T>> {
        protected FunctionalIdentifierCode gs01;
        protected String gs02; // Application Sender's Code
        protected String gs03; // Application Receiver's Code
        protected String gs04; // Date
        protected String gs05; // Time
        protected String gs06; // Group Control Number
        protected ResponsibleAgencyCode gs07;
        protected VersionCode gs08;

        protected AbstractBuilder() {
        }

        public T setGs01(String value) {
            this.gs01 = FunctionalIdentifierCode.fromString(value);
            return self();
        }

        public T setGs02(String gs02) {
            this.gs02 = gs02;
            return self();
        }

        public T setGs03(String gs03) {
            this.gs03 = gs03;
            return self();
        }

        public T setGs04(LocalDateTime value, DateFormat dateFormat) {
            this.gs04 = DateFormatter.formatDate(dateFormat, value);
            return self();
        }

        public T setGs05(LocalDateTime value, TimeFormat timeFormat) {
            this.gs05 = DateFormatter.formatTime(timeFormat, value);
            return self();
        }

        public T setGs06(String gs06) {
            this.gs06 = gs06;
            return self();
        }

        public T setGs07(String value) {
            this.gs07 = ResponsibleAgencyCode.fromString(value);
            return self();
        }

        public T setGs08(String value) {
            this.gs08 = VersionCode.fromString(value);
            return self();
        }

        public T setFunctionalIdentifierCode(String code) {
            return setGs01(code);
        }

        public T setApplicationSenderCode(String code) {
            return setGs02(code);
        }

        public T setApplicationReceiverCode(String code) {
            return setGs03(code);
        }

        public T setTransactionSetCreationDate(LocalDateTime date, DateFormat dateFormat) {
            return setGs04(date, dateFormat);
        }

        public T setTransactionSetCreationTime(LocalDateTime value, TimeFormat timeFormat) {
            return setGs05(value, timeFormat);
        }

        public T setGroupControlNumber(String number) {
            return setGs06(number);
        }

        public T setResponsibleAgencyCode(String code) {
            return setGs07(code);
        }

        public T setVersionReleaseIndustryCode(String code) {
            return setGs08(code);
        }

        protected abstract T self();

        public abstract GSSegment build() throws ValidationException;
    }
}