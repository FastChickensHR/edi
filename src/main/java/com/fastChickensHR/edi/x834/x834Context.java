/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834;

import com.fastChickensHR.edi.common.dates.DateFormat;
import com.fastChickensHR.edi.common.dates.DateFormatter;
import com.fastChickensHR.edi.common.dates.TimeFormat;
import com.fastChickensHR.edi.x834.constants.ElementSeparator;
import com.fastChickensHR.edi.x834.constants.LineTerminator;
import com.fastChickensHR.edi.x834.constants.SegmentTerminator;
import com.fastChickensHR.edi.x834.constants.SubElementSeparator;
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
public class x834Context {
    // Document formatting
    private ElementSeparator elementSeparator;
    private SubElementSeparator subElementSeparator;
    private SegmentTerminator segmentTerminator;
    private LineTerminator lineTerminator;

    // Document metadata
    private String transactionSetControlNumber;
    private String groupControlNumber;
    private String senderID;
    private String receiverID;
    private LocalDateTime documentDate;
    private DateFormat dateFormat;
    private TimeFormat timeFormat;
    private String formattedDocumentDate;
    private String formattedDocumentTime;

    // Constants
    private static final String EDI_VERSION = "00501";
    private static final String TRANSACTION_SET_ID = "834";
    private static final String IMPLEMENTATION_CONVENTION_REFERENCE = "005010X220A1";

    /**
     * Creates a new X834Context with default values.
     */
    public x834Context() {
        this.documentDate = LocalDateTime.now();
        this.dateFormat = DateFormat.DATE;
        this.timeFormat = TimeFormat.TIME;
        this.formattedDocumentDate = formatDate(documentDate);
        this.formattedDocumentTime = formatTime(documentDate);
        this.elementSeparator = ElementSeparator.ASTERISK;
        this.segmentTerminator = SegmentTerminator.TILDE;
        this.subElementSeparator = SubElementSeparator.GREATER_THAN;
        this.lineTerminator = LineTerminator.LF;
    }

    public char getElementSeparator() {
        return elementSeparator.getValue();
    }

    public char getSubElementSeparator() {
        return subElementSeparator.getValue();
    }

    public char getSegmentTerminator() {
        return segmentTerminator.getValue();
    }

    public String getLineTerminator() {
        return lineTerminator.getValue();
    }

    public String getEdiVersion() {
        return EDI_VERSION;
    }

    public String getTransactionSetId() {
        return TRANSACTION_SET_ID;
    }

    public String getImplementationConventionReference() {
        return IMPLEMENTATION_CONVENTION_REFERENCE;
    }

    public x834Context setDocumentDate(LocalDateTime date) {
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