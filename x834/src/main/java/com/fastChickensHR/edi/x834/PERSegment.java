/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834;

import com.fastChickensHR.edi.x834.data.CommunicationNumberQualifier;
import com.fastChickensHR.edi.x834.exception.ValidationException;
import com.fastChickensHR.edi.x834.loop2000.loop2100A.MemberCommunication;
import lombok.Getter;

/**
 * Represents the PER (Administrative Communications Contact) segment in the X12 834
 * (005010X220A1) Benefit Enrollment and Maintenance transaction.
 * <p>
 * The segment carries the communication numbers for the party named in the enclosing loop:
 * a contact function code, then up to three qualifier/number pairs. In the 834 it appears in
 * Loop 2100A as the member's communications numbers (see
 * {@code MemberCommunicationsNumbers}).
 * <p>
 * Element/position map (data elements per the 834 TR3):
 * <ul>
 *     <li>PER01 = contact function code (366)</li>
 *     <li>PER02 = contact name (93)</li>
 *     <li>PER03 = communication number qualifier (365)</li>
 *     <li>PER04 = communication number (364)</li>
 *     <li>PER05 = communication number qualifier (365)</li>
 *     <li>PER06 = communication number (364)</li>
 *     <li>PER07 = communication number qualifier (365)</li>
 *     <li>PER08 = communication number (364)</li>
 * </ul>
 * PER09 is Not Used in the 834 and is not modelled.
 * <p>
 * X12 syntax rules P0304, P0506 and P0708 make each qualifier/number a <em>paired presence</em>:
 * within a pair, if either side is present the other is required. A lone qualifier would render
 * as a dangling {@code *HP} tail and a lone number as {@code **555...}, both non-conformant, so
 * both are rejected at build time.
 */
@Getter
abstract class PERSegment extends Segment {
    public static final String SEGMENT_ID = "PER";

    /** Element 364 is AN 1/256 — a longer communication number cannot be represented. */
    public static final int MAX_COMMUNICATION_NUMBER_LENGTH = 256;

    protected final String per01;
    protected final String per02;
    protected final CommunicationNumberQualifier per03;
    protected final String per04;
    protected final CommunicationNumberQualifier per05;
    protected final String per06;
    protected final CommunicationNumberQualifier per07;
    protected final String per08;

    protected PERSegment(AbstractBuilder<?> builder) throws ValidationException {
        this.per01 = builder.per01;
        this.per02 = builder.per02;
        this.per03 = builder.per03;
        this.per04 = builder.per04;
        this.per05 = builder.per05;
        this.per06 = builder.per06;
        this.per07 = builder.per07;
        this.per08 = builder.per08;

        validate();
    }

    private void validate() throws ValidationException {
        if (per01 == null || per01.isEmpty()) {
            throw new ValidationException("Contact Function Code (PER01) is required");
        }
        validatePair(per03, per04, "P0304", "PER03", "PER04");
        validatePair(per05, per06, "P0506", "PER05", "PER06");
        validatePair(per07, per08, "P0708", "PER07", "PER08");
        if (per03 == null && per05 == null && per07 == null) {
            throw new ValidationException(
                    "PER carries no communication number; at least one qualifier/number pair is required");
        }
    }

    private static void validatePair(CommunicationNumberQualifier qualifier, String number,
                                     String rule, String qualifierPosition, String numberPosition)
            throws ValidationException {
        boolean numberPresent = number != null && !number.isEmpty();
        if ((qualifier != null) != numberPresent) {
            throw new ValidationException("PER rule " + rule + ": Communication Number Qualifier ("
                    + qualifierPosition + ") and Communication Number (" + numberPosition
                    + ") must be present together");
        }
        if (numberPresent && number.length() > MAX_COMMUNICATION_NUMBER_LENGTH) {
            throw new ValidationException("Communication Number (" + numberPosition + ") must be "
                    + MAX_COMMUNICATION_NUMBER_LENGTH + " characters or less");
        }
    }

    @Override
    public String getSegmentIdentifier() {
        return SEGMENT_ID;
    }

    @Override
    public String[] getElementValues() {
        return new String[]{
                per01,
                per02,
                per03 == null ? null : per03.getCode(),
                per04,
                per05 == null ? null : per05.getCode(),
                per06,
                per07 == null ? null : per07.getCode(),
                per08};
    }

    /** @return PER01 — contact function code. */
    public String getContactFunctionCode() {
        return getPer01();
    }

    /** @return PER02 — contact name. */
    public String getContactName() {
        return getPer02();
    }

    /**
     * Abstract builder for PER segments.
     *
     * @param <T> the concrete builder type, for chaining
     */
    public abstract static class AbstractBuilder<T extends AbstractBuilder<T>> {
        protected String per01;
        protected String per02;
        protected CommunicationNumberQualifier per03;
        protected String per04;
        protected CommunicationNumberQualifier per05;
        protected String per06;
        protected CommunicationNumberQualifier per07;
        protected String per08;

        protected abstract T self();

        /** Sets PER01 (contact function code). */
        public T setContactFunctionCode(String value) {
            this.per01 = value;
            return self();
        }

        /** Sets PER02 (contact name). */
        public T setContactName(String value) {
            this.per02 = value;
            return self();
        }

        /**
         * Sets the next free qualifier/number pair — PER03/04, then PER05/06, then PER07/08.
         * <p>
         * Callers state <em>which</em> channels a member has, not which element positions they
         * occupy, so the pairs fill in call order. A PER carries at most
         * {@link MemberCommunication#MAX_PER_MEMBER} pairs; a fourth is rejected rather than
         * dropped, because silently discarding a communication number is the data loss this
         * segment exists to end.
         *
         * @param qualifier the communication number qualifier (PER03/05/07)
         * @param number    the communication number it qualifies (PER04/06/08)
         * @throws ValidationException if the segment already carries {@link MemberCommunication#MAX_PER_MEMBER} pairs
         */
        public T addCommunicationNumber(CommunicationNumberQualifier qualifier, String number)
                throws ValidationException {
            if (per03 == null) {
                per03 = qualifier;
                per04 = number;
            } else if (per05 == null) {
                per05 = qualifier;
                per06 = number;
            } else if (per07 == null) {
                per07 = qualifier;
                per08 = number;
            } else {
                throw new ValidationException("A PER segment carries at most "
                        + MemberCommunication.MAX_PER_MEMBER + " communication numbers");
            }
            return self();
        }

        /** @return the built segment. */
        public abstract PERSegment build() throws ValidationException;
    }
}
