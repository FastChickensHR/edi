/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.spec;

import com.fastChickensHR.edi.x834.data.AcknowledgmentRequested;
import com.fastChickensHR.edi.x834.data.ActionCode;
import com.fastChickensHR.edi.x834.data.AuthorizationInformationQualifier;
import com.fastChickensHR.edi.x834.data.DateTimeQualifier;
import com.fastChickensHR.edi.x834.data.EntityIdentifierCode;
import com.fastChickensHR.edi.x834.data.FunctionalIdentifierCode;
import com.fastChickensHR.edi.x834.data.IdentificationCodeQualifier;
import com.fastChickensHR.edi.x834.data.InterchangeControlVersionNumber;
import com.fastChickensHR.edi.x834.data.InterchangeIdQualifier;
import com.fastChickensHR.edi.x834.data.InterchangeUsageIndicator;
import com.fastChickensHR.edi.x834.data.ReferenceIdentificationQualifier;
import com.fastChickensHR.edi.x834.data.ResponsibleAgencyCode;
import com.fastChickensHR.edi.x834.data.SecurityInformationQualifier;
import com.fastChickensHR.edi.x834.data.SecurityLevelCode;
import com.fastChickensHR.edi.x834.data.TimeCode;
import com.fastChickensHR.edi.x834.data.TransactionSetIdentifierCode;
import com.fastChickensHR.edi.x834.data.TransactionSetPurposeCode;
import com.fastChickensHR.edi.x834.data.TransactionTypeCode;
import com.fastChickensHR.edi.x834.loop2000.data.BenefitStatusCode;
import com.fastChickensHR.edi.x834.loop2000.data.COBRAQualifyingEventCode;
import com.fastChickensHR.edi.x834.loop2000.data.ConfidentialityCode;
import com.fastChickensHR.edi.x834.loop2000.data.CoverageLevelCode;
import com.fastChickensHR.edi.x834.loop2000.data.EmploymentStatusCode;
import com.fastChickensHR.edi.x834.loop2000.data.GenderCode;
import com.fastChickensHR.edi.x834.loop2000.data.HandicapIndicator;
import com.fastChickensHR.edi.x834.loop2000.data.IndividualRelationshipCode;
import com.fastChickensHR.edi.x834.loop2000.data.InsuranceLineCode;
import com.fastChickensHR.edi.x834.loop2000.data.MaintenanceReasonCode;
import com.fastChickensHR.edi.x834.loop2000.data.MaintenanceTypeCode;
import com.fastChickensHR.edi.x834.loop2000.data.MedicarePlanCode;
import com.fastChickensHR.edi.x834.loop2000.data.MemberIndicator;
import com.fastChickensHR.edi.x834.loop2000.data.StudentStatusCode;
import com.fastChickensHR.edi.x834.util.EdiCodeEnum;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * The 834's published element metadata, addressable by {@link ElementPosition} — what the standard says
 * about each position this library can write, so a consumer can narrow a code list, render a pick list,
 * or check a length without transcribing any of it.
 *
 * <pre>{@code
 * X834Spec.at("2000 INS08")
 *         .orElseThrow()
 *         .permits(List.of("AC", "RT", "TE"));   // is that trading-partner list ⊆ element 584?
 * }</pre>
 *
 * <h2>What is published</h2>
 * Every element position the library can render — that is, each ordinal of each segment in each loop the
 * generator emits. That surface, not a wider one, because it is the surface a consumer can actually put a
 * value into and the surface a render-time check must cover. Code lists are projected from this library's
 * code enums ({@code Arrays.stream(values()).map(...)}), so there is exactly one copy of every list and
 * the published metadata cannot drift from the codes the builders accept.
 *
 * <h2>What is not published, and why</h2>
 * <ul>
 *   <li><strong>Positions the 220A1 does not use.</strong> {@code 2300 HD02} is the one hole inside an
 *       emitted segment: the library renders it as a permanently empty slot to keep HD03–HD05 in place, so
 *       there is nothing to constrain there.</li>
 *   <li><strong>Segments the generator never emits</strong> (AMT, IDC, LX/LS/LE, PER) — their classes exist
 *       but nothing writes them, so no position of theirs is reachable. They arrive with the loops that
 *       write them.</li>
 *   <li><strong>Element 1250's code list</strong> (the date-time-period format qualifier at
 *       {@code DTP02}, {@code INS11}, {@code DMG01}). Its codes live in
 *       {@link com.fastChickensHR.edi.x834.dates.DateFormat}, which carries a format pattern per code but
 *       no description to publish. Those positions are published as uncoded until that enum can supply
 *       descriptions.</li>
 *   <li><strong>Element 1065, 1067, 156, 116, 26, 309, 1715 and friends</strong> — ID positions this
 *       library has no enum for. They publish type and lengths; {@link ElementSpec#isCoded()} is false and
 *       {@link ElementSpec#permits} refuses, so an absent list can never be mistaken for "anything goes".</li>
 *   <li><strong>A generated JSON export.</strong> {@link #all()} is the export surface; building a JSON
 *       resource from it is a consumer-side convenience and deliberately not part of this seam.</li>
 * </ul>
 *
 * <h2>Repeated segments</h2>
 * A loop that writes the same segment more than once — loop 2000's several REFs — publishes one entry per
 * position, listing the union of the codes any occurrence may carry (i.e. the element's own list). The
 * alternative, keying a position by the occurrence's qualifier, would buy a narrower ring at the cost of a
 * second addressing grammar; a consumer that needs per-occurrence narrowing expresses it in its own
 * placement vocabulary and still checks against the union here.
 */
public final class X834Spec {

    private static final Map<ElementPosition, ElementSpec> TABLE = table();

    private X834Spec() {
    }

    /** The spec at {@code position}, or empty when this library publishes nothing there. */
    public static Optional<ElementSpec> at(ElementPosition position) {
        return Optional.ofNullable(TABLE.get(position));
    }

    /**
     * The spec at a position spelled canonically, e.g. {@code "2000 INS08"}.
     *
     * @throws IllegalArgumentException if the text is not an element position at all — a malformed address
     *                                  is a caller bug, distinct from a well-formed address with no spec
     */
    public static Optional<ElementSpec> at(String position) {
        return at(ElementPosition.parse(position));
    }

    /**
     * The spec for an element ordinal of a segment, <em>without</em> knowing which loop the segment sits
     * in — what a renderer has to work with, since a segment instance carries its identifier and its
     * element order but no loop identity ({@code N1} serves 1000A, 1000B and 1000C; {@code N3}/{@code N4}
     * serve 2100A and 2100C).
     *
     * <p>Answers only when every loop publishing that segment ordinal agrees on the element — same
     * number, name, type, lengths and codes — and empty otherwise. Today they always agree, because a
     * segment's positions are declared once and reused across its loops; a test pins that. Should a
     * future loop narrow one, this returns empty rather than guessing, and the position simply goes
     * unchecked until the caller can supply a loop.
     *
     * <p>A composite element answers with its first component ({@code INS06} → the spec at
     * {@code INS06-1}), which is what the segment renders into that slot while the 834 uses only C052-01.
     */
    public static Optional<ElementSpec> atSegment(String segment, int ordinal) {
        List<ElementSpec> candidates = TABLE.values().stream()
                .filter(spec -> spec.position().segment().equals(segment) && spec.position().ordinal() == ordinal)
                .toList();
        if (candidates.isEmpty()) {
            return Optional.empty();
        }
        ElementSpec first = candidates.getFirst();
        boolean agree = candidates.stream().allMatch(spec -> describes(spec, first));
        return agree ? Optional.of(first) : Optional.empty();
    }

    /** Whether two specs say the same thing about their element, ignoring which position they sit at. */
    private static boolean describes(ElementSpec one, ElementSpec other) {
        return one.elementId().equals(other.elementId())
                && one.name().equals(other.name())
                && one.type() == other.type()
                && one.minLength() == other.minLength()
                && one.maxLength() == other.maxLength()
                && one.codes().equals(other.codes())
                && one.position().component() == other.position().component();
    }

    /** Every published spec, in transaction-set order: envelope, header, loops, trailer. */
    public static List<ElementSpec> all() {
        return List.copyOf(TABLE.values());
    }

    /** Every published position, in the same order as {@link #all()}. */
    public static List<ElementPosition> positions() {
        return List.copyOf(TABLE.keySet());
    }

    private static Map<ElementPosition, ElementSpec> table() {
        Table t = new Table();

        // ---- Interchange envelope (ISA). Element ids are the standard's I-numbers, not integers.
        t.coded("HEADER ISA01", "I01", "Authorization Information Qualifier", 2, 2, AuthorizationInformationQualifier.class);
        t.element("HEADER ISA02", "I02", "Authorization Information", DataType.AN, 10, 10);
        t.coded("HEADER ISA03", "I03", "Security Information Qualifier", 2, 2, SecurityInformationQualifier.class);
        t.element("HEADER ISA04", "I04", "Security Information", DataType.AN, 10, 10);
        t.coded("HEADER ISA05", "I05", "Interchange ID Qualifier", 2, 2, InterchangeIdQualifier.class);
        t.element("HEADER ISA06", "I06", "Interchange Sender ID", DataType.AN, 15, 15);
        t.coded("HEADER ISA07", "I05", "Interchange ID Qualifier", 2, 2, InterchangeIdQualifier.class);
        t.element("HEADER ISA08", "I07", "Interchange Receiver ID", DataType.AN, 15, 15);
        t.element("HEADER ISA09", "I08", "Interchange Date", DataType.DT, 6, 6);
        t.element("HEADER ISA10", "I09", "Interchange Time", DataType.TM, 4, 4);
        t.element("HEADER ISA11", "I65", "Repetition Separator", DataType.AN, 1, 1);
        t.coded("HEADER ISA12", "I11", "Interchange Control Version Number", 5, 5, InterchangeControlVersionNumber.class);
        t.element("HEADER ISA13", "I12", "Interchange Control Number", DataType.N0, 9, 9);
        t.coded("HEADER ISA14", "I13", "Acknowledgment Requested", 1, 1, AcknowledgmentRequested.class);
        t.coded("HEADER ISA15", "I14", "Interchange Usage Indicator", 1, 1, InterchangeUsageIndicator.class);
        t.element("HEADER ISA16", "I15", "Component Element Separator", DataType.AN, 1, 1);

        // ---- Functional group header (GS). GS08 is AN 1/12, not a code list: 005010X220A1 is a
        // version/release string, not an enumerated value.
        t.coded("HEADER GS01", "479", "Functional Identifier Code", 2, 2, FunctionalIdentifierCode.class);
        t.element("HEADER GS02", "142", "Application Sender's Code", DataType.AN, 2, 15);
        t.element("HEADER GS03", "124", "Application Receiver's Code", DataType.AN, 2, 15);
        t.element("HEADER GS04", "373", "Date", DataType.DT, 8, 8);
        t.element("HEADER GS05", "337", "Time", DataType.TM, 4, 8);
        t.element("HEADER GS06", "28", "Group Control Number", DataType.N0, 1, 9);
        t.coded("HEADER GS07", "455", "Responsible Agency Code", 1, 2, ResponsibleAgencyCode.class);
        t.element("HEADER GS08", "480", "Version / Release / Industry Identifier Code", DataType.AN, 1, 12);

        // ---- Transaction set header (ST) and beginning segment (BGN).
        t.coded("HEADER ST01", "143", "Transaction Set Identifier Code", 3, 3, TransactionSetIdentifierCode.class);
        t.element("HEADER ST02", "329", "Transaction Set Control Number", DataType.AN, 4, 9);
        t.element("HEADER ST03", "1705", "Implementation Convention Reference", DataType.AN, 1, 35);
        t.coded("HEADER BGN01", "353", "Transaction Set Purpose Code", 2, 2, TransactionSetPurposeCode.class);
        t.element("HEADER BGN02", "127", "Reference Identification", DataType.AN, 1, 50);
        t.element("HEADER BGN03", "373", "Date", DataType.DT, 8, 8);
        t.element("HEADER BGN04", "337", "Time", DataType.TM, 4, 8);
        t.coded("HEADER BGN05", "623", "Time Code", 2, 2, TimeCode.class);
        t.element("HEADER BGN06", "127", "Reference Identification", DataType.AN, 1, 50);
        t.coded("HEADER BGN07", "640", "Transaction Type Code", 2, 2, TransactionTypeCode.class);
        t.coded("HEADER BGN08", "306", "Action Code", 1, 2, ActionCode.class);
        t.coded("HEADER BGN09", "786", "Security Level Code", 2, 2, SecurityLevelCode.class);

        // ---- Header-level REF (master policy number) and DTP (file effective date).
        reference(t, "HEADER");
        dateTimePeriod(t, "HEADER");

        // ---- 1000A sponsor, 1000B payer, 1000C third-party administrator: the same N1 in three loops.
        for (String loop : List.of("1000A", "1000B", "1000C")) {
            t.coded(loop + " N101", "98", "Entity Identifier Code", 2, 3, EntityIdentifierCode.class);
            t.element(loop + " N102", "93", "Name", DataType.AN, 1, 60);
            t.coded(loop + " N103", "66", "Identification Code Qualifier", 1, 2, IdentificationCodeQualifier.class);
            t.element(loop + " N104", "67", "Identification Code", DataType.AN, 2, 80);
        }

        // ---- 2000 member level detail (INS). INS06 is composite C052; the 834 uses only its first
        // component, so the position published is INS06-1.
        t.coded("2000 INS01", "1073", "Member Indicator", 1, 1, MemberIndicator.class);
        t.coded("2000 INS02", "1069", "Individual Relationship Code", 2, 2, IndividualRelationshipCode.class);
        t.coded("2000 INS03", "875", "Maintenance Type Code", 3, 3, MaintenanceTypeCode.class);
        t.coded("2000 INS04", "1203", "Maintenance Reason Code", 2, 3, MaintenanceReasonCode.class);
        t.coded("2000 INS05", "1216", "Benefit Status Code", 1, 1, BenefitStatusCode.class);
        t.coded("2000 INS06-1", "1218", "Medicare Plan Code", 1, 1, MedicarePlanCode.class);
        t.coded("2000 INS07", "1219", "COBRA Qualifying Event Code", 1, 2, COBRAQualifyingEventCode.class);
        t.coded("2000 INS08", "584", "Employment Status Code", 2, 2, EmploymentStatusCode.class);
        t.coded("2000 INS09", "1220", "Student Status Code", 1, 1, StudentStatusCode.class);
        t.coded("2000 INS10", "1073", "Handicap Indicator", 1, 1, HandicapIndicator.class);
        t.element("2000 INS11", "1250", "Date Time Period Format Qualifier", DataType.ID, 2, 3);
        t.element("2000 INS12", "1251", "Member Individual Death Date", DataType.AN, 1, 35);
        t.coded("2000 INS13", "1165", "Confidentiality Code", 1, 1, ConfidentialityCode.class);
        reference(t, "2000");
        dateTimePeriod(t, "2000");

        // ---- 2100A member name, demographics and residence address.
        t.coded("2100A NM101", "98", "Entity Identifier Code", 2, 3, EntityIdentifierCode.class);
        t.element("2100A NM102", "1065", "Entity Type Qualifier", DataType.ID, 1, 1);
        t.element("2100A NM103", "1035", "Name Last or Organization Name", DataType.AN, 1, 60);
        t.element("2100A NM104", "1036", "Name First", DataType.AN, 1, 35);
        t.element("2100A NM105", "1037", "Name Middle", DataType.AN, 1, 25);
        t.element("2100A NM106", "1038", "Name Prefix", DataType.AN, 1, 10);
        t.element("2100A NM107", "1039", "Name Suffix", DataType.AN, 1, 10);
        t.coded("2100A NM108", "66", "Identification Code Qualifier", 1, 2, IdentificationCodeQualifier.class);
        t.element("2100A NM109", "67", "Identification Code", DataType.AN, 2, 80);
        t.element("2100A DMG01", "1250", "Date Time Period Format Qualifier", DataType.ID, 2, 3);
        t.element("2100A DMG02", "1251", "Member Birth Date", DataType.AN, 1, 35);
        t.coded("2100A DMG03", "1068", "Gender Code", 1, 1, GenderCode.class);
        t.element("2100A DMG04", "1067", "Marital Status Code", DataType.ID, 1, 1);
        t.element("2100A DMG05-1", "1109", "Race or Ethnicity Code", DataType.ID, 1, 1);
        t.element("2100A DMG05-2", "1270", "Code List Qualifier Code", DataType.ID, 1, 3);
        t.element("2100A DMG05-3", "1271", "Industry Code", DataType.AN, 1, 30);
        t.element("2100A DMG06", "1066", "Citizenship Status Code", DataType.ID, 1, 2);
        t.element("2100A DMG07", "26", "Country Code", DataType.ID, 2, 3);
        t.element("2100A DMG08", "659", "Basis of Verification Code", DataType.ID, 1, 2);
        t.element("2100A DMG09", "380", "Quantity", DataType.R, 1, 15);
        t.element("2100A DMG10", "1270", "Code List Qualifier Code", DataType.ID, 1, 3);
        t.element("2100A DMG11", "1271", "Industry Code", DataType.AN, 1, 30);
        address(t, "2100A");

        // ---- 2100C member mailing address (N3/N4 only; the loop's NM1 is not emitted).
        address(t, "2100C");

        // ---- 2300 health coverage. HD02 is Not Used in the 220A1: the library renders it as an empty
        // slot to keep HD03-HD05 in position, so it publishes nothing.
        t.coded("2300 HD01", "875", "Maintenance Type Code", 3, 3, MaintenanceTypeCode.class);
        t.coded("2300 HD03", "1205", "Insurance Line Code", 2, 3, InsuranceLineCode.class);
        t.element("2300 HD04", "1204", "Plan Coverage Description", DataType.AN, 1, 50);
        t.coded("2300 HD05", "1207", "Coverage Level Code", 3, 3, CoverageLevelCode.class);
        dateTimePeriod(t, "2300");

        // ---- Trailers.
        t.element("TRAILER SE01", "96", "Number of Included Segments", DataType.N0, 1, 10);
        t.element("TRAILER SE02", "329", "Transaction Set Control Number", DataType.AN, 4, 9);
        t.element("TRAILER GE01", "97", "Number of Transaction Sets Included", DataType.N0, 1, 6);
        t.element("TRAILER GE02", "28", "Group Control Number", DataType.N0, 1, 9);
        t.element("TRAILER IEA01", "I16", "Number of Included Functional Groups", DataType.N0, 1, 5);
        t.element("TRAILER IEA02", "I12", "Interchange Control Number", DataType.N0, 9, 9);

        return t.build();
    }

    /** REF01-REF03 as the loop writes them: qualifier, identification, description. */
    private static void reference(Table t, String loop) {
        t.coded(loop + " REF01", "128", "Reference Identification Qualifier", 2, 3, ReferenceIdentificationQualifier.class);
        t.element(loop + " REF02", "127", "Reference Identification", DataType.AN, 1, 50);
        t.element(loop + " REF03", "352", "Description", DataType.AN, 1, 80);
    }

    /** DTP01-DTP03 as the loop writes them: qualifier, format qualifier, period. */
    private static void dateTimePeriod(Table t, String loop) {
        t.coded(loop + " DTP01", "374", "Date/Time Qualifier", 3, 3, DateTimeQualifier.class);
        t.element(loop + " DTP02", "1250", "Date Time Period Format Qualifier", DataType.ID, 2, 3);
        t.element(loop + " DTP03", "1251", "Date Time Period", DataType.AN, 1, 35);
    }

    /** The N3/N4 address pair a member-address loop writes. */
    private static void address(Table t, String loop) {
        t.element(loop + " N301", "166", "Address Information", DataType.AN, 1, 55);
        t.element(loop + " N302", "166", "Address Information", DataType.AN, 1, 55);
        t.element(loop + " N401", "19", "City Name", DataType.AN, 2, 30);
        t.element(loop + " N402", "156", "State or Province Code", DataType.ID, 2, 2);
        t.element(loop + " N403", "116", "Postal Code", DataType.ID, 3, 15);
        t.element(loop + " N404", "26", "Country Code", DataType.ID, 2, 3);
        t.element(loop + " N405", "309", "Location Qualifier", DataType.ID, 1, 2);
        t.element(loop + " N406", "310", "Location Identifier", DataType.AN, 1, 30);
        t.element(loop + " N407", "1715", "Country Subdivision Code", DataType.ID, 1, 3);
    }

    /** The published code list of {@code type}, in the order the enum declares it. */
    private static <E extends Enum<E> & EdiCodeEnum> List<CodeValue> codesOf(Class<E> type) {
        List<CodeValue> codes = new ArrayList<>();
        for (E constant : type.getEnumConstants()) {
            codes.add(new CodeValue(constant.getCode(), constant.getDescription()));
        }
        return codes;
    }

    /** Accumulates the table, rejecting a position declared twice. */
    private static final class Table {
        private final Map<ElementPosition, ElementSpec> specs = new LinkedHashMap<>();

        void element(String position, String elementId, String name, DataType type, int minLength, int maxLength) {
            put(new ElementSpec(ElementPosition.parse(position), elementId, name, type, minLength, maxLength, List.of()));
        }

        <E extends Enum<E> & EdiCodeEnum> void coded(
                String position, String elementId, String name, int minLength, int maxLength, Class<E> codes) {
            put(new ElementSpec(
                    ElementPosition.parse(position), elementId, name, DataType.ID, minLength, maxLength, codesOf(codes)));
        }

        private void put(ElementSpec spec) {
            ElementSpec existing = specs.putIfAbsent(spec.position(), spec);
            if (existing != null) {
                throw new IllegalStateException("Duplicate spec for position " + spec.position());
            }
        }

        Map<ElementPosition, ElementSpec> build() {
            return Collections.unmodifiableMap(specs);
        }
    }
}
