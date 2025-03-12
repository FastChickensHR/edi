package com.fastChickensHR.edi.x834.header;

import com.fastChickensHR.edi.x834.common.Segment;
import com.fastChickensHR.edi.x834.common.exception.ValidationException;

/**
 * Abstract class representing the beginning segment of the header loop in an EDI document.
 * This segment is commonly referred to as the "ST" segment and includes key fields such as
 * the transaction set identifier code (ST01), transaction set control number (ST02),
 * and implementation convention reference (ST03).
 */
public abstract class TransactionSetHeader extends Segment {
    // Constants for segment and field identifiers
    public static final String SEGMENT_ID = "ST";
    public static final String DEFAULT_TRANSACTION_SET_ID = "834";
    public static final String DEFAULT_CONTROL_NUMBER = "0001";
    public static final String DEFAULT_CONVENTION_REFERENCE = "005010X220A1";

    private final String st01; // Transaction Set Identifier Code
    private String st02; // Transaction Set Control Number
    private final String st03; // Implementation Convention Reference

    protected TransactionSetHeader(Builder builder) {
        super();
        this.st01 = builder.st01;
        this.st02 = builder.st02;
        this.st03 = builder.st03;
    }

    @Override
    public String getSegmentIdentifier() {
        return SEGMENT_ID;
    }

    @Override
    public String[] getElementValues() {
        return new String[]{st01, st02, st03};
    }

    /**
     * @return The Transaction Set Identifier Code (ST01) value
     */
    public String getSt01() {
        return st01;
    }

    /**
     * @return The Transaction Set Identifier Code (ST01) value
     */
    public String getTransactionSetIdentifierCode() {
        return st01;
    }

    /**
     * @return The Transaction Set Control Number (ST02) value
     */
    public String getSt02() {
        return st02;
    }

    /**
     * @return The Transaction Set Control Number (ST02) value
     */
    public String getTransactionSetControlNumber() {
        return st02;
    }

    /**
     * Sets the Transaction Set Control Number.
     *
     * @param value The control number to set (must match SE02)
     */
    public void setSt02(String value) {
        this.st02 = value;
    }

    /**
     * Sets the Transaction Set Control Number.
     *
     * @param value The control number to set (must match SE02)
     */
    public void setTransactionSetControlNumber(String value) {
        this.st02 = value;
    }

    /**
     * @return The Implementation Convention Reference (ST03) value
     */
    public String getSt03() {
        return st03;
    }

    /**
     * @return The Implementation Convention Reference (ST03) value
     */
    public String getImplementationConventionReference() {
        return st03;
    }

    public static class Builder {
        // Using default values from constants
        private String st01 = DEFAULT_TRANSACTION_SET_ID;
        private String st02 = DEFAULT_CONTROL_NUMBER;
        private String st03 = DEFAULT_CONVENTION_REFERENCE;

        /**
         * Sets the ST01 field value (Transaction Set Identifier Code)
         */
        public Builder setSt01(String st01) {
            this.st01 = st01;
            return this;
        }

        /**
         * Sets the ST02 field value (Transaction Set Control Number)
         */
        public Builder setSt02(String st02) {
            this.st02 = st02;
            return this;
        }

        /**
         * Sets the ST03 field value (Implementation Convention Reference)
         */
        public Builder setSt03(String st03) {
            this.st03 = st03;
            return this;
        }

        /**
         * Sets the Transaction Set Identifier Code (ST01)
         */
        public Builder setTransactionSetIdentifierCode(String code) {
            this.st01 = code;
            return this;
        }

        /**
         * Sets the Transaction Set Control Number (ST02)
         */
        public Builder setTransactionSetControlNumber(String number) {
            this.st02 = number;
            return this;
        }

        /**
         * Sets the Implementation Convention Reference (ST03)
         */
        public Builder setImplementationConventionReference(String reference) {
            this.st03 = reference;
            return this;
        }

        public TransactionSetHeader build() throws ValidationException {
            validateRequiredFields();
            return new ConcreteTransactionSetHeaderSegment(this);
        }

        private void validateRequiredFields() throws ValidationException {
            if (st01 == null || st01.trim().isEmpty()) {
                throw new ValidationException("ST01 (Transaction Set Identifier Code) is required");
            }
            if (st02 == null || st02.trim().isEmpty()) {
                throw new ValidationException("ST02 (Transaction Set Control Number) is required");
            }
        }
    }

    /**
     * Concrete implementation of the HeaderLoopBeginningSegment
     */
    private static class ConcreteTransactionSetHeaderSegment extends TransactionSetHeader {
        public ConcreteTransactionSetHeaderSegment(Builder builder) {
            super(builder);
        }
    }
}