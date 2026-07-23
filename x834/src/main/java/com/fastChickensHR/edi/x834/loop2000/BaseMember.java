/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.loop2000;

import com.fastChickensHR.edi.x834.exception.ValidationException;
import com.fastChickensHR.edi.x834.loop2000.data.IndividualRelationshipCode;
import com.fastChickensHR.edi.x834.loop2000.data.MaintenanceTypeCode;
import com.fastChickensHR.edi.x834.loop2000.data.MemberIndicator;
import com.fastChickensHR.edi.x834.loop2000.loop2700.ReportingCategory;
import com.fastChickensHR.edi.x834.segments.Segment;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Base domain class for both primary members and dependents.
 * <p>
 * This is a plain data object describing a person. It is intentionally
 * decoupled from any specific EDI transaction format; serialization to
 * the X12 834 wire format is the responsibility of a dedicated writer
 * (see {@link X834MemberWriter}).
 */
@Getter
@Setter
public abstract class BaseMember {
    protected String memberId;
    protected String memberIdQualifier;
    protected String subscriberNumber;
    protected String policyNumber;
    protected String firstName;
    protected String lastName;
    protected String middleName;
    /**
     * NM108 — identification code qualifier carried in the member name segment (Loop 2100A NM1),
     * e.g. {@code 34} for a Social Security Number. Must be paired with {@link #nameId}.
     */
    protected String nameIdQualifier;
    /**
     * NM109 — identification code carried in the member name segment (Loop 2100A NM1), e.g. the
     * member's SSN when {@link #nameIdQualifier} is {@code 34}. Must be paired with the qualifier.
     */
    protected String nameId;
    protected LocalDateTime birthDate;
    protected String gender;
    protected MemberIndicator memberIndicator;
    protected MaintenanceTypeCode maintenanceTypeCode;
    protected LocalDateTime enrollmentDate;
    protected LocalDateTime coverageStartDate;
    protected LocalDateTime coverageEndDate;
    protected IndividualRelationshipCode relationshipCode;
    protected String phoneNumber;
    protected String email;

    /**
     * All of this member's postal addresses, keyed by {@link AddressType}. A member may carry a
     * residence, a mailing address, and others; the writer serializes the types the 834 supports
     * (residence → Loop 2100A, mailing → Loop 2100C).
     */
    private final List<Address> addresses = new ArrayList<>();

    /**
     * Adds a typed address to this member. Multiple types may coexist; adding a second address of
     * a type that already exists replaces the previous one so a member has at most one of each kind.
     *
     * @param address the address to add (ignored if null or has no {@link AddressType})
     */
    public void addAddress(Address address) {
        if (address == null || address.getType() == null) {
            return;
        }
        addresses.removeIf(existing -> existing.getType() == address.getType());
        addresses.add(address);
    }

    /**
     * @param type the address kind to look up
     * @return this member's address of that type, if any
     */
    public java.util.Optional<Address> getAddress(AddressType type) {
        return addresses.stream().filter(a -> a.getType() == type).findFirst();
    }

    private Address residenceOrCreate() {
        return getAddress(AddressType.RESIDENCE).orElseGet(() -> {
            Address residence = new Address();
            residence.setType(AddressType.RESIDENCE);
            addresses.add(residence);
            return residence;
        });
    }

    // --- Backward-compatible flat accessors for the residence address (Loop 2100A) ---
    // Retained so existing callers (setAddressLine1/setCity/...) keep working; each reads/writes
    // the member's RESIDENCE address within {@link #addresses}.

    public String getAddressLine1() { return getAddress(AddressType.RESIDENCE).map(Address::getLine1).orElse(null); }
    public String getAddressLine2() { return getAddress(AddressType.RESIDENCE).map(Address::getLine2).orElse(null); }
    public String getCity() { return getAddress(AddressType.RESIDENCE).map(Address::getCity).orElse(null); }
    public String getState() { return getAddress(AddressType.RESIDENCE).map(Address::getState).orElse(null); }
    public String getZipCode() { return getAddress(AddressType.RESIDENCE).map(Address::getZipCode).orElse(null); }

    public void setAddressLine1(String value) { residenceOrCreate().setLine1(value); }
    public void setAddressLine2(String value) { residenceOrCreate().setLine2(value); }
    public void setCity(String value) { residenceOrCreate().setCity(value); }
    public void setState(String value) { residenceOrCreate().setState(value); }
    public void setZipCode(String value) { residenceOrCreate().setZipCode(value); }

    /**
     * Loop 2300 (and other trailing) segments that belong to <em>this</em> member — most
     * notably health coverage (HD) and any custom REF extensions. They are emitted by
     * {@link X834MemberWriter} at the end of this member's own segment stream, so a member's
     * coverage stays nested inside its own loop rather than being batched after every member.
     */
    private final List<Segment> additionalSegments = new ArrayList<>();

    /**
     * Appends a trailing (Loop 2300) segment to this member — e.g. an HD coverage segment
     * or a custom REF extension. Order is preserved.
     *
     * @param segment the segment to emit within this member's loop
     */
    public void addSegment(Segment segment) {
        additionalSegments.add(segment);
    }

    /**
     * This member's reporting categories (Loop 2700/2710/2750). Emitted by
     * {@link X834MemberWriter} as an {@code LS*2700} … {@code LE*2700} block after the
     * member's 2300 segments, one {@code LX}/{@code N1*75}/{@code REF} occurrence per entry.
     * Empty for a member that carries none, in which case no block is emitted.
     */
    private final List<ReportingCategory> reportingCategories = new ArrayList<>();

    /**
     * Adds a reporting category (one Loop 2710/2750 occurrence) to this member. Order is
     * preserved; the {@code LX} assigned number is set at render time over the emitted occurrences.
     *
     * @param category the reporting category to emit (ignored if null)
     */
    public void addReportingCategory(ReportingCategory category) {
        if (category != null) {
            reportingCategories.add(category);
        }
    }

    /**
     * Validates this member has the minimum required fields.
     *
     * @throws ValidationException If validation fails
     */
    public abstract void validate() throws ValidationException;
}
