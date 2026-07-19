/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834;

import com.fastChickensHR.edi.x834.dates.DateFormat;
import com.fastChickensHR.edi.x834.dates.DateFormatter;
import com.fastChickensHR.edi.x834.dates.TimeFormat;
import com.fastChickensHR.edi.x834.constants.ElementSeparator;
import com.fastChickensHR.edi.x834.constants.LineTerminator;
import com.fastChickensHR.edi.x834.constants.SegmentTerminator;
import com.fastChickensHR.edi.x834.constants.SubElementSeparator;
import com.fastChickensHR.edi.x834.exception.ValidationException;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * Context object that contains document-level information needed by segments.
 * This reduces direct coupling between segments and the X834Document.
 */
@Getter
@Setter
@Accessors(chain = true)
public class X834Context {
    // Document formatting
    private ElementSeparator elementSeparator;
    private SubElementSeparator subElementSeparator;
    private SegmentTerminator segmentTerminator;
    private LineTerminator lineTerminator;

    // Document metadata
    private String interchangeControlNumber;
    private String transactionSetControlNumber;
    private String groupControlNumber;
    private String senderID;
    private String receiverID;
    /** ISA14 — Interchange Acknowledgment Requested ("1" to request a TA1/999). Null defaults to "0". */
    private String acknowledgmentRequested;
    private LocalDateTime documentDate;
    private DateFormat dateFormat;
    private TimeFormat timeFormat;
    private String formattedDocumentDate;
    private String formattedDocumentTime;

    // Constants
    /** Interchange control version (ISA12) for the 5010 release: {@code "00501"}. */
    private static final String EDI_VERSION = "00501";
    /** Transaction set identifier for benefit enrollment and maintenance: {@code "834"} (ST01). */
    private static final String TRANSACTION_SET_ID = "834";
    /** Implementation convention reference identifying the 834 guide 005010X220A1 (ST03 / GS08). */
    private static final String IMPLEMENTATION_CONVENTION_REFERENCE = "005010X220A1";

    /**
     * Creates a new X834Context with default values: current timestamp as the document
     * date, {@link DateFormat#DATE}/{@link TimeFormat#TIME} formats, the conventional 834
     * delimiters ('*' element, '~' segment, '&gt;' sub-element) and LF line terminator,
     * and a transaction set control number of {@code "0001"}.
     */
    public X834Context() {
        this.documentDate = LocalDateTime.now();
        this.dateFormat = DateFormat.DATE;
        this.timeFormat = TimeFormat.TIME;
        this.formattedDocumentDate = formatDate(documentDate);
        this.formattedDocumentTime = formatTime(documentDate);
        this.elementSeparator = ElementSeparator.ASTERISK;
        this.segmentTerminator = SegmentTerminator.TILDE;
        this.subElementSeparator = SubElementSeparator.GREATER_THAN;
        this.lineTerminator = LineTerminator.LF;
        this.transactionSetControlNumber = "0001";
    }

    /**
     * Validates that all required context fields are set.
     *
     * @throws ValidationException if interchangeControlNumber or groupControlNumber is null
     */
    public void validate() throws ValidationException {
        if (interchangeControlNumber == null || interchangeControlNumber.isEmpty()) {
            throw new ValidationException("Interchange Control Number is required on X834Context");
        }
        if (!interchangeControlNumber.matches("\\d{9}")) {
            throw new ValidationException("Interchange Control Number must be exactly 9 numeric digits");
        }
        if (groupControlNumber == null || groupControlNumber.isEmpty()) {
            throw new ValidationException("Group Control Number is required on X834Context");
        }
    }

    /**
     * @return the element separator character (the character value, not the enum)
     */
    public char getElementSeparator() {
        return elementSeparator.getValue();
    }

    /**
     * @return the sub-element (component) separator character
     */
    public char getSubElementSeparator() {
        return subElementSeparator.getValue();
    }

    /**
     * @return the segment terminator character
     */
    public char getSegmentTerminator() {
        return segmentTerminator.getValue();
    }

    /**
     * @return the line terminator string appended after each rendered segment
     */
    public String getLineTerminator() {
        return lineTerminator.getValue();
    }

    /**
     * @return the interchange control version number {@value #EDI_VERSION} (ISA12)
     */
    public String getEdiVersion() {
        return EDI_VERSION;
    }

    /**
     * @return the transaction set identifier {@value #TRANSACTION_SET_ID}
     */
    public String getTransactionSetId() {
        return TRANSACTION_SET_ID;
    }

    /**
     * @return the implementation convention reference {@value #IMPLEMENTATION_CONVENTION_REFERENCE}
     */
    public String getImplementationConventionReference() {
        return IMPLEMENTATION_CONVENTION_REFERENCE;
    }

    /**
     * Sets the document date and refreshes the cached formatted document date.
     *
     * @param date the document date/time
     * @return this context instance for chaining
     */
    public X834Context setDocumentDate(LocalDateTime date) {
        this.documentDate = date;
        this.formattedDocumentDate = formatDate(date);
        return this;
    }

    /**
     * Formats a date according to the context's date format
     *
     * @param date The date to format
     * @return Formatted date string
     */
    public String formatDate(LocalDateTime date) {
        return DateFormatter.formatDate(getDateFormat(), date);
    }

    /**
     * Formats a time according to the context's time format
     *
     * @param time The time to format
     * @return Formatted time string
     */
    public String formatTime(LocalDateTime time) {
        return DateFormatter.formatTime(getTimeFormat(), time);
    }
}