/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.segments;

import com.fastChickensHR.edi.x834.data.IdentificationCodeQualifier;
import com.fastChickensHR.edi.x834.exception.ValidationException;
import lombok.Getter;

/**
 * Represents the LUI (Language Use) segment in the X12 834 (005010X220A1) Benefit Enrollment and
 * Maintenance transaction.
 * <p>
 * The segment names a language the member uses, in Loop 2100A. BCBSM's BCN Advantage product
 * publishes a real code protocol for it, and the demand is rising with language-access regulation.
 * <p>
 * Element/position map (data elements per the 834 TR3):
 * <ul>
 *     <li>LUI01 = identification code qualifier (66) — what kind of language code LUI02 is</li>
 *     <li>LUI02 = identification code (67, AN 2/80) — the language code itself</li>
 *     <li>LUI03 = description (352, AN 1/80) — the language named in words</li>
 * </ul>
 * <b>LUI04</b> (use of language indicator) and <b>LUI05</b> (language proficiency indicator) are
 * not modelled: both sit at the tail, neither is demanded by a profiled carrier, and LUI04 carries
 * a relational condition of its own that would need separate grounding.
 * <p>
 * Two published relational conditions are enforced. LUI01 and LUI02 require each other — a code
 * with no qualifier does not say which scheme it belongs to, and a qualifier alone names nothing.
 * And a segment must carry at least one of LUI02 or LUI03, since an LUI naming no language at all
 * is not a statement.
 */
@Getter
public abstract class LUISegment extends Segment {
    public static final String SEGMENT_ID = "LUI";

    /** Element 67 is AN 2/80. */
    public static final int MIN_LANGUAGE_CODE_LENGTH = 2;
    /** Element 67 is AN 2/80. */
    public static final int MAX_LANGUAGE_CODE_LENGTH = 80;
    /** Element 352 is AN 1/80. */
    public static final int MAX_DESCRIPTION_LENGTH = 80;

    protected final IdentificationCodeQualifier lui01;
    protected final String lui02;
    protected final String lui03;

    protected LUISegment(AbstractBuilder<?> builder) throws ValidationException {
        this.lui01 = builder.lui01;
        this.lui02 = builder.lui02;
        this.lui03 = builder.lui03;

        validate();
    }

    private void validate() throws ValidationException {
        boolean codePresent = lui02 != null && !lui02.isEmpty();
        if ((lui01 != null) != codePresent) {
            throw new ValidationException(
                    "LUI: Identification Code Qualifier (LUI01) and Identification Code (LUI02) must be present together");
        }
        boolean descriptionPresent = lui03 != null && !lui03.isEmpty();
        if (!codePresent && !descriptionPresent) {
            throw new ValidationException(
                    "LUI names no language; at least one of Identification Code (LUI02) or Description (LUI03) is required");
        }
        if (codePresent && lui02.length() < MIN_LANGUAGE_CODE_LENGTH) {
            throw new ValidationException("Identification Code (LUI02) must be at least "
                    + MIN_LANGUAGE_CODE_LENGTH + " characters; got '" + lui02 + "'");
        }
        if (codePresent && lui02.length() > MAX_LANGUAGE_CODE_LENGTH) {
            throw new ValidationException("Identification Code (LUI02) must be "
                    + MAX_LANGUAGE_CODE_LENGTH + " characters or less");
        }
        if (descriptionPresent && lui03.length() > MAX_DESCRIPTION_LENGTH) {
            throw new ValidationException("Description (LUI03) must be "
                    + MAX_DESCRIPTION_LENGTH + " characters or less");
        }
    }

    @Override
    public String getSegmentIdentifier() {
        return SEGMENT_ID;
    }

    @Override
    public String[] getElementValues() {
        return new String[]{lui01 == null ? null : lui01.getCode(), lui02, lui03};
    }

    /** @return LUI01 — what kind of language code {@link #getLanguageCode()} is. */
    public IdentificationCodeQualifier getIdentificationCodeQualifier() {
        return getLui01();
    }

    /** @return LUI02 — the language code. */
    public String getLanguageCode() {
        return getLui02();
    }

    /** @return LUI03 — the language named in words. */
    public String getDescription() {
        return getLui03();
    }

    /**
     * Abstract builder for LUI segments.
     *
     * @param <T> the concrete builder type, for chaining
     */
    public abstract static class AbstractBuilder<T extends AbstractBuilder<T>> {
        protected IdentificationCodeQualifier lui01;
        protected String lui02;
        protected String lui03;

        protected abstract T self();

        /**
         * Sets LUI01/LUI02 — the language code and the scheme it belongs to. Both together, since
         * the 834 requires each if the other is present.
         *
         * @param qualifier what kind of code {@code code} is (LUI01)
         * @param code      the language code (LUI02)
         */
        public T setLanguage(IdentificationCodeQualifier qualifier, String code) {
            this.lui01 = qualifier;
            this.lui02 = code;
            return self();
        }

        /** Sets LUI03 (the language named in words). */
        public T setDescription(String value) {
            this.lui03 = value;
            return self();
        }

        /** @return the built segment. */
        public abstract LUISegment build() throws ValidationException;
    }
}
