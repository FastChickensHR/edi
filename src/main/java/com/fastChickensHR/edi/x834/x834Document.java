package com.fastChickensHR.edi.x834;

import com.fastChickensHR.edi.x834.common.Segment;
import com.fastChickensHR.edi.x834.constants.*;
import com.fastChickensHR.edi.x834.common.exception.ValidationException;
import com.fastChickensHR.edi.x834.header.TransactionSetHeader;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents an EDI 834 document for benefit enrollment and maintenance.
 * This class handles document structure, formatting, and validation.
 */
public class x834Document {
    // EDI format settings
    private final ElementSeparator elementSeparator;
    private final SubElementSeparator subElementSeparator;
    private final SegmentTerminator segmentTerminator;
    private final LineTerminator lineTerminator;

    // Version and format constants
    public static final String EDI_VERSION = "00501";
    public static final String TRANSACTION_SET_ID = "834";
    public static final String IMPLEMENTATION_CONVENTION_REFERENCE = "005010X220A1";

    // Currently implemented segments
    private final TransactionSetHeader headerSegment;

    // List to maintain segment order
    private final List<Segment> segments = new ArrayList<>();

    private x834Document(Builder builder) {
        this.elementSeparator = builder.elementSeparator;
        this.segmentTerminator = builder.segmentTerminator;
        this.subElementSeparator = builder.subElementSeparator;
        this.lineTerminator = builder.lineTerminator;

        this.headerSegment = builder.headerSegment;

        // Add segments in proper order
        if (headerSegment != null) {
            segments.add(headerSegment);
        }

        // Future: Add other segments as they're implemented
    }

    /**
     * Generates the complete EDI document string
     *
     * @return A properly formatted EDI 834 document as a string
     */
    public String generateDocument() {
        StringBuilder document = new StringBuilder();

        for (Segment segment : segments) {
            document.append(segment.toEdiSegment(elementSeparator, segmentTerminator));
            document.append(lineTerminator.getValue());
        }

        return document.toString();
    }

    /**
     * Gets the header segment (ST)
     *
     * @return The HeaderLoopBeginningSegment
     */
    public TransactionSetHeader getHeaderSegment() {
        return headerSegment;
    }

    /**
     * Gets the element separator used in this document
     *
     * @return The ElementSeparator
     */
    public ElementSeparator getElementSeparator() {
        return elementSeparator;
    }

    /**
     * Gets the segment terminator used in this document
     *
     * @return The SegmentTerminator
     */
    public SegmentTerminator getSegmentTerminator() {
        return segmentTerminator;
    }

    /**
     * Gets the sub-element separator used in this document
     *
     * @return The SubElementSeparator
     */
    public SubElementSeparator getSubElementSeparator() {
        return subElementSeparator;
    }

    /**
     * Gets the line terminator used in this document
     *
     * @return The LineTerminator
     */
    public LineTerminator getLineTerminator() {
        return lineTerminator;
    }

    /**
     * Builder for EightThirtyFourDocument
     */
    public static class Builder {
        private ElementSeparator elementSeparator = ElementSeparator.ASTERISK;
        private SubElementSeparator subElementSeparator = SubElementSeparator.COLON;
        private SegmentTerminator segmentTerminator = SegmentTerminator.TILDE;
        private LineTerminator lineTerminator = LineTerminator.LF;

        private TransactionSetHeader headerSegment;

        /**
         * Sets the element separator for the document
         *
         * @param elementSeparator The element separator to use
         * @return This builder instance
         */
        public Builder withElementSeparator(ElementSeparator elementSeparator) {
            this.elementSeparator = elementSeparator;
            return this;
        }

        /**
         * Sets the sub-element separator for the document
         *
         * @param subElementSeparator The sub-element separator to use
         * @return This builder instance
         */
        public Builder withSubElementSeparator(SubElementSeparator subElementSeparator) {
            this.subElementSeparator = subElementSeparator;
            return this;
        }

        /**
         * Sets the segment terminator for the document
         *
         * @param segmentTerminator The segment terminator to use
         * @return This builder instance
         */
        public Builder withSegmentTerminator(SegmentTerminator segmentTerminator) {
            this.segmentTerminator = segmentTerminator;
            return this;
        }

        /**
         * Sets the line terminator for the document
         *
         * @param lineTerminator The line terminator to use
         * @return This builder instance
         */
        public Builder withLineTerminator(LineTerminator lineTerminator) {
            this.lineTerminator = lineTerminator;
            return this;
        }

        /**
         * Sets the header segment (ST) for the document
         *
         * @param headerSegment The HeaderLoopBeginningSegment to use
         * @return This builder instance
         */
        public Builder withHeaderSegment(TransactionSetHeader headerSegment) {
            this.headerSegment = headerSegment;
            return this;
        }

        /**
         * Builds the EightThirtyFourDocument with validation
         *
         * @return A new EightThirtyFourDocument instance
         * @throws ValidationException if any validation fails
         */
        public x834Document build() throws ValidationException {
            validateRequiredSegments();
            // Currently no cross-segment validation since we only have one segment
            // As more segments are added, we'll add cross-segment validations here

            return new x834Document(this);
        }

        /**
         * Validates that all required segments are present
         *
         * @throws ValidationException if a required segment is missing
         */
        private void validateRequiredSegments() throws ValidationException {
            // For now, only validate that we have a header segment
            if (headerSegment == null) {
                throw new ValidationException("ST segment (Header Loop Beginning Segment) is required");
            }
        }
    }
}