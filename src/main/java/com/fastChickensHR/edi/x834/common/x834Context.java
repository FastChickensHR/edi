package com.fastChickensHR.edi.x834.common;

import com.fastChickensHR.edi.x834.common.dates.DateFormat;
import com.fastChickensHR.edi.x834.common.dates.DateFormatter;
import com.fastChickensHR.edi.x834.common.dates.TimeFormat;
import com.fastChickensHR.edi.x834.constants.ElementSeparator;
import com.fastChickensHR.edi.x834.constants.LineTerminator;
import com.fastChickensHR.edi.x834.constants.SegmentTerminator;
import com.fastChickensHR.edi.x834.constants.SubElementSeparator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Context object that contains document-level information needed by segments.
 * This reduces direct coupling between segments and the X834Document.
 */
public class x834Context {
    // Document formatting
    private ElementSeparator elementSeparator;
    private SubElementSeparator subElementSeparator;
    private SegmentTerminator segmentTerminator;
    private LineTerminator lineTerminator;

    // Document metadata
    private String transactionSetControlNumber;
    private String groupControlNumber;
    private String senderCode;
    private String receiverCode;
    private LocalDate documentDate;
    private LocalTime documentTime;
    private DateFormat dateFormat;
    private TimeFormat timeFormat;

    // Constants
    private static final String EDI_VERSION = "00501";
    private static final String TRANSACTION_SET_ID = "834";
    private static final String IMPLEMENTATION_CONVENTION_REFERENCE = "005010X220A1";

    /**
     * Creates a new X834Context with default values.
     */
    public x834Context() {
        this.documentDate = LocalDate.now();
        this.documentTime = LocalTime.now();
        this.dateFormat = DateFormat.DATE;
        this.timeFormat = TimeFormat.TIME;
        this.elementSeparator = ElementSeparator.ASTERISK;
        this.segmentTerminator = SegmentTerminator.TILDE;
        this.subElementSeparator = SubElementSeparator.GREATER_THAN;
        this.lineTerminator = LineTerminator.LF;
    }

    // Getters and setters

    public char getElementSeparator() {
        return elementSeparator.getValue();
    }

    public void setElementSeparator(ElementSeparator elementSeparator) {
        this.elementSeparator = elementSeparator;
    }

    public char getSubElementSeparator() {
        return subElementSeparator.getValue();
    }

    public void setSubElementSeparator(SubElementSeparator subElementSeparator) {
        this.subElementSeparator = subElementSeparator;
    }

    public char getSegmentTerminator() {
        return segmentTerminator.getValue();
    }

    public void setSegmentTerminator(SegmentTerminator segmentTerminator) {
        this.segmentTerminator = segmentTerminator;
    }

    public String getLineTerminator() {
        return lineTerminator.getValue();
    }

    public void setLineTerminator(LineTerminator lineTerminator) {
        this.lineTerminator = lineTerminator;
    }

    public String getTransactionSetControlNumber() {
        return transactionSetControlNumber;
    }

    public void setTransactionSetControlNumber(String transactionSetControlNumber) {
        this.transactionSetControlNumber = transactionSetControlNumber;
    }

    public String getGroupControlNumber() {
        return groupControlNumber;
    }

    public void setGroupControlNumber(String groupControlNumber) {
        this.groupControlNumber = groupControlNumber;
    }

    public String getSenderCode() {
        return senderCode;
    }

    public void setSenderCode(String senderCode) {
        this.senderCode = senderCode;
    }

    public String getReceiverCode() {
        return receiverCode;
    }

    public void setReceiverCode(String receiverCode) {
        this.receiverCode = receiverCode;
    }

    public LocalDate getDocumentDate() {
        return documentDate;
    }

    public void setDocumentDate(LocalDate documentDate) {
        this.documentDate = documentDate;
    }

    public LocalTime getDocumentTime() {
        return documentTime;
    }

    public void setDocumentTime(LocalTime documentTime) {
        this.documentTime = documentTime;
    }

    public DateFormat getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(DateFormat dateFormat) {
        this.dateFormat = dateFormat;
    }

    public TimeFormat getTimeFormat() {
        return timeFormat;
    }

    public void setTimeFormat(TimeFormat timeFormat) {
        this.timeFormat = timeFormat;
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

    /**
     * Formats a date according to the context's date format
     *
     * @param date The date to format
     * @return Formatted date string
     */
    public String formatDate(LocalDate date) {
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