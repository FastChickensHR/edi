/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.examples;

import com.fastChickensHR.edi.fixtures.X834Fixtures;
import com.fastChickensHR.edi.x834.exception.ValidationException;
import com.fastChickensHR.edi.x834.converters.EnrollmentContext;
import com.fastChickensHR.edi.x834.converters.PersonToMemberConverter;
import com.fastChickensHR.edi.x834.x834Context;
import com.fastChickensHR.edi.x834.constants.ElementSeparator;
import com.fastChickensHR.edi.x834.header.Header;
import com.fastChickensHR.edi.x834.loop2000.Member;
import com.fastChickensHR.edi.x834.loop2000.data.MaintenanceTypeCode;
import com.fastChickensHR.edi.x834.loop2000.data.MemberIndicator;
import com.fastChickensHR.edi.x834.trailer.Trailer;
import com.fastChickensHR.edi.x834.x834Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Example class demonstrating how to create an 834 document for the State of Michigan.
 * Domain {@link com.fastChickensHR.edi.domain.Person} objects are generated via
 * {@link X834Fixtures} and converted to X12 834 {@link Member} records via
 * {@link PersonToMemberConverter} using a Michigan-specific {@link EnrollmentContext}.
 */
public class StateOfMichigan834 {

    private static final EnrollmentContext MICHIGAN_ENROLLMENT = EnrollmentContext.builder()
            .policyNumber("MIPA2023")
            .memberIdQualifier("34")
            .memberIndicator(MemberIndicator.INSURED)
            .maintenanceTypeCode(MaintenanceTypeCode.ADDITION)
            .enrollmentDate(LocalDateTime.of(2023, 8, 1, 0, 0))
            .coverageStartDate(LocalDateTime.of(2023, 8, 1, 0, 0))
            .build();

    /**
     * Creates a Michigan 834 document with generated members.
     *
     * @param memberCount Number of primary members to include in the document
     * @return A fully populated 834 document
     */
    public static x834Document createMichiganDocument(int memberCount) throws ValidationException {
        final x834Context context = new x834Context()
                .setSenderID("FASTCHKN")
                .setReceiverID("MICHGVEDI")
                .setElementSeparator(ElementSeparator.PIPE)
                .setDocumentDate(LocalDateTime.of(2023, 8, 1, 0, 0));

        Header header = new Header.Builder(context)
                .setInterchangeControlNumber("000000001")
                .setGroupControlNumber("42789")
                .setReferenceIdentification("220701MI834")
                .setMasterPolicyNumber("MIHHS-EMP-2023")
                .setPlanSponsorName("FASTCHKN")
                .setPayerName("FASTCHKN INSURANCE")
                .setPayerIdentification("123456789")
                .build();

        X834Fixtures fixtures = X834Fixtures.random();
        List<Member> members = new ArrayList<>();
        for (int i = 0; i < memberCount; i++) {
            members.add(PersonToMemberConverter.convert(
                    fixtures.person().build(),
                    MICHIGAN_ENROLLMENT));
        }

        return new x834Document.Builder(context)
                .withHeader(header)
                .withMembers(members)
                .withTrailer(new Trailer.Builder(context))
                .build();
    }

    /**
     * Example of using the StateOfMichigan834 creator with generated members.
     */
    public static void main(String[] args) throws ValidationException {
        x834Document document = createMichiganDocument(10);

        try {
            Optional<String> generatedDocument = document.generateDocument();
            if (generatedDocument.isPresent()) {
                System.out.println("Generated Michigan 834 Document with test members:");
                System.out.println(generatedDocument.get());
            } else {
                System.out.println("Failed to generate document due to validation errors:");
                document.getErrors().forEach(System.out::println);
            }
        } catch (ValidationException e) {
            System.out.println("Failed to generate document due to validation errors:");
        }
    }
}
