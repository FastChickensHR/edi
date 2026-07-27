/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.loop2000;

import com.fastChickensHR.edi.x834.data.CommunicationNumberQualifier;
import com.fastChickensHR.edi.x834.exception.ValidationException;
import com.fastChickensHR.edi.x834.loop2000.data.EmploymentStatusCode;
import com.fastChickensHR.edi.x834.loop2000.data.GenderCode;
import com.fastChickensHR.edi.x834.loop2000.data.IndividualRelationshipCode;
import com.fastChickensHR.edi.x834.loop2000.data.MaintenanceReasonCode;
import com.fastChickensHR.edi.x834.loop2000.data.MaintenanceTypeCode;
import com.fastChickensHR.edi.x834.loop2000.data.MemberIndicator;
import com.fastChickensHR.edi.x834.loop2000.loop2100A.HealthInformation;
import com.fastChickensHR.edi.x834.loop2000.loop2100A.Income;
import com.fastChickensHR.edi.x834.loop2000.loop2100A.Language;
import com.fastChickensHR.edi.x834.loop2000.loop2100A.MemberCommunication;
import com.fastChickensHR.edi.x834.loop2000.loop2200.Disability;
import com.fastChickensHR.edi.x834.loop2000.loop2300.HealthCoverage;
import com.fastChickensHR.edi.x834.loop2000.loop2310.Provider;
import com.fastChickensHR.edi.x834.loop2000.loop2320.CoordinationOfBenefits;
import com.fastChickensHR.edi.x834.loop2000.loop2700.ReportingCategory;
import com.fastChickensHR.edi.x834.Segment;
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
 * (see {@code X834MemberWriter}).
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
    /** DMG03 — the member's gender, drawn from the element-1068 code list. */
    protected GenderCode gender;
    protected MemberIndicator memberIndicator;
    protected MaintenanceTypeCode maintenanceTypeCode;
    /**
     * INS04 — why this maintenance is happening (element 1203), e.g. termination of benefits. Optional:
     * the 834 requires a maintenance <em>type</em> on every member, never a reason.
     */
    protected MaintenanceReasonCode maintenanceReasonCode;
    /**
     * INS08 — the member's employment status (element 584). Optional, and member-level: the 220A1 HD
     * segment carries no employment status.
     */
    protected EmploymentStatusCode employmentStatusCode;
    protected LocalDateTime enrollmentDate;
    protected LocalDateTime coverageStartDate;
    protected LocalDateTime coverageEndDate;
    protected IndividualRelationshipCode relationshipCode;
    /**
     * The member's telephone number, emitted in the Loop 2100A {@code PER} under
     * {@link CommunicationNumberQualifier#HOME_PHONE} — the qualifier carriers most often ask for
     * (Anthem's worked examples send {@code PER*IP**HP*<phone>}).
     *
     * <p>A convenience for the common case. To send the number under a different qualifier — work,
     * cellular, alternate — add a {@link MemberCommunication} instead; an explicit communication
     * for a qualifier takes precedence over this field.
     */
    protected String phoneNumber;
    /**
     * The member's email address, emitted in the Loop 2100A {@code PER} under
     * {@link CommunicationNumberQualifier#ELECTRONIC_MAIL}.
     *
     * <p>The same convenience as {@link #phoneNumber}, and overridden the same way by an explicit
     * {@link MemberCommunication}.
     */
    protected String email;
    /**
     * What this member earns (Loop 2100A {@code ICM}). Emitted by {@code X834MemberWriter} after the
     * member's {@code DMG}, and only when present — the 834 sends income only when the sponsor's
     * contract with the payer requires it. A member has at most one, the 834 permitting a single
     * {@code ICM}.
     */
    protected Income income;
    /**
     * This member's health-related status (Loop 2100A {@code HLH}) — tobacco and substance use, plus
     * height and weight. Emitted by {@code X834MemberWriter} after the {@code ICM}, and only when
     * present. A member has at most one, the 834 permitting a single {@code HLH}.
     */
    protected HealthInformation healthInformation;

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
     * Trailing segments that belong to <em>this</em> member — notably custom REF extensions.
     * They are emitted by {@code X834MemberWriter} at the end of this member's own segment
     * stream (just before the {@link #addHealthCoverage(HealthCoverage) 2300 coverage block}),
     * so they stay nested inside the member's own loop rather than being batched after every
     * member.
     */
    private final List<Segment> additionalSegments = new ArrayList<>();

    /**
     * Appends a trailing segment to this member — e.g. a custom REF extension. Order is
     * preserved. Health coverage is not a segment here: it is modeled by
     * {@link #addHealthCoverage(HealthCoverage)} and rendered by the writer.
     *
     * @param segment the segment to emit within this member's loop
     */
    public void addSegment(Segment segment) {
        additionalSegments.add(segment);
    }

    /**
     * This member's reporting categories (Loop 2700/2710/2750). Emitted by
     * {@code X834MemberWriter} as an {@code LS*2700} … {@code LE*2700} block after the
     * member's 2300 segments, one {@code LX}/{@code N1*75}/{@code REF} occurrence per entry.
     * Empty for a member that carries none, in which case no block is emitted.
     */
    private final List<ReportingCategory> reportingCategories = new ArrayList<>();

    /**
     * This member's communication numbers (Loop 2100A {@code PER}). Emitted by
     * {@code X834MemberWriter} as a single {@code PER} carrying one qualifier/number pair per
     * entry, after the member's {@code NM1}. Empty for a member that carries none, in which case
     * no {@code PER} is emitted.
     *
     * @see #addCommunication(MemberCommunication)
     */
    private final List<MemberCommunication> communications = new ArrayList<>();

    /**
     * Adds a way to reach this member (Loop 2100A {@code PER}).
     * <p>
     * Order is preserved and determines which element pair each occupies — the first added
     * becomes PER03/04, then PER05/06, then PER07/08. An explicit communication takes precedence
     * over the {@link #phoneNumber}/{@link #email} conveniences when both name the same
     * qualifier. The 834 permits at most
     * {@value com.fastChickensHR.edi.x834.loop2000.loop2100A.MemberCommunication#MAX_PER_MEMBER} per
     * member; a member carrying more is rejected when written, not silently truncated.
     *
     * @param communication the communication number to emit (ignored if null)
     */
    public void addCommunication(MemberCommunication communication) {
        if (communication != null) {
            communications.add(communication);
        }
    }

    /**
     * Adds a way to reach this member — convenience for
     * {@link #addCommunication(MemberCommunication)}.
     *
     * @param qualifier what kind of number this is (PER03/05/07)
     * @param number    the number itself (PER04/06/08)
     */
    public void addCommunication(CommunicationNumberQualifier qualifier, String number) {
        addCommunication(new MemberCommunication(qualifier, number));
    }

    /**
     * The languages this member uses (Loop 2100A {@code LUI}). Emitted by {@code X834MemberWriter}
     * after the {@code HLH}, one {@code LUI} per entry, in the order added. Empty for a member with
     * none, in which case nothing is emitted.
     *
     * @see #addLanguage(Language)
     */
    private final List<Language> languages = new ArrayList<>();

    /**
     * Adds a language this member uses (Loop 2100A {@code LUI}). Order is preserved.
     *
     * @param language the language to emit (ignored if null)
     */
    public void addLanguage(Language language) {
        if (language != null) {
            languages.add(language);
        }
    }

    /**
     * The disabilities this member has (Loop 2200). Emitted by {@code X834MemberWriter} after the
     * 2100 loops and before the 2300 coverage segments, one {@code DSB}(/{@code DTP}) block per
     * entry. Empty for a member with none, in which case nothing is emitted.
     *
     * @see #addDisability(Disability)
     */
    private final List<Disability> disabilities = new ArrayList<>();

    /**
     * Adds a disability this member has (Loop 2200). Order is preserved.
     *
     * @param disability the disability to emit (ignored if null)
     */
    public void addDisability(Disability disability) {
        if (disability != null) {
            disabilities.add(disability);
        }
    }

    /**
     * The health coverages this member carries (Loop 2300). Emitted by {@code X834MemberWriter}
     * after the member's trailing segments ({@link #addSegment(Segment)}) and before the 2310
     * block, one {@code HD}(/{@code DTP*348}/{@code DTP*349}) block per entry. Empty for a member
     * with none, in which case nothing is emitted.
     *
     * @see #addHealthCoverage(HealthCoverage)
     */
    private final List<HealthCoverage> healthCoverages = new ArrayList<>();

    /**
     * Adds a health coverage this member carries (Loop 2300). Order is preserved.
     *
     * @param healthCoverage the coverage to emit (ignored if null)
     */
    public void addHealthCoverage(HealthCoverage healthCoverage) {
        if (healthCoverage != null) {
            healthCoverages.add(healthCoverage);
        }
    }

    /**
     * The providers this member is assigned to (Loop 2310) — typically a primary care physician.
     * Emitted by {@code X834MemberWriter} after the member's 2300 coverage segments and before the
     * 2320 block, one {@code LX}/{@code NM1}(/{@code PLA}) occurrence per entry. Empty for a member
     * with no provider assignment, in which case nothing is emitted.
     *
     * @see #addProvider(Provider)
     */
    private final List<Provider> providers = new ArrayList<>();

    /**
     * Adds a provider this member is assigned to (Loop 2310).
     * <p>
     * Order is preserved, and the {@code LX} assigned number is counted over the emitted
     * occurrences. The 834 permits at most
     * {@value com.fastChickensHR.edi.x834.loop2000.loop2310.Provider#MAX_PER_MEMBER} per member; a
     * member carrying more is rejected when written, not silently truncated.
     *
     * @param provider the provider to emit (ignored if null)
     */
    public void addProvider(Provider provider) {
        if (provider != null) {
            providers.add(provider);
        }
    }

    /**
     * The other plans this member also holds (Loop 2320/2330). Emitted by {@code X834MemberWriter}
     * after the member's 2300 coverage segments, one {@code COB}(/{@code REF}/{@code DTP}/2330
     * {@code NM1}) block per entry. Empty for a member with no other coverage, in which case
     * nothing is emitted.
     *
     * @see #addCoordinationOfBenefits(CoordinationOfBenefits)
     */
    private final List<CoordinationOfBenefits> coordinationOfBenefits = new ArrayList<>();

    /**
     * Adds another plan this member holds (Loop 2320).
     * <p>
     * Order is preserved. The 834 permits at most
     * {@value com.fastChickensHR.edi.x834.loop2000.loop2320.CoordinationOfBenefits#MAX_PER_MEMBER}
     * occurrences per member; a member carrying more is rejected when written, not silently
     * truncated.
     *
     * @param cob the other coverage to emit (ignored if null)
     */
    public void addCoordinationOfBenefits(CoordinationOfBenefits cob) {
        if (cob != null) {
            coordinationOfBenefits.add(cob);
        }
    }

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
