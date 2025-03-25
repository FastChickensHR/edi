/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.examples;

import com.fastChickensHR.edi.common.TestDataGenerator;
import com.fastChickensHR.edi.domain.Dependent;
import com.fastChickensHR.edi.domain.Person;
import com.fastChickensHR.edi.x834.common.exception.ValidationException;
import com.fastChickensHR.edi.x834.common.x834Context;
import com.fastChickensHR.edi.x834.constants.ElementSeparator;
import com.fastChickensHR.edi.x834.header.Header;
import com.fastChickensHR.edi.x834.loop2000.DependentMember;
import com.fastChickensHR.edi.x834.loop2000.Member;
import com.fastChickensHR.edi.x834.loop2000.data.IndividualRelationshipCode;
import com.fastChickensHR.edi.x834.loop2000.data.MaintenanceTypeCode;
import com.fastChickensHR.edi.x834.loop2000.data.MemberIndicator;
import com.fastChickensHR.edi.x834.x834Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Example class demonstrating how to create an 834 document for the State of Michigan.
 * This class shows the proper configuration of all required builders with Michigan-specific values.
 * Updated to include member generation using TestDataGenerator.
 */
public class StateOfMichigan834 {

    private static final x834Context context = new x834Context()
            .setSenderID("FASTCHKN")
            .setReceiverID("MICHGVEDI")
            .setElementSeparator(ElementSeparator.PIPE)
            .setDocumentDate(LocalDateTime.of(2023, 8, 1, 0, 0));

    /**
     * Creates a Michigan 834 document with generated members
     *
     * @param memberCount Number of primary members to include in the document
     * @return A fully populated 834 document
     */
    public static x834Document createMichiganDocument(int memberCount) {
        Header header = new Header.Builder(context)
                .setInterchangeControlNumber("000000001")
                .setGroupControlNumber("42789")
                .setReferenceIdentification("220701MI834")
                .setMasterPolicyNumber("MIHHS-EMP-2023")
                .setPlanSponsorName("FASTCHKN")
                .setPayerName("FASTCHKN INSURANCE")
                .setPayerIdentification("123456789")
                .build();

        List<Member> members = generateMembers(memberCount);

        return new x834Document.Builder(context)
                .withHeader(header)
                .withMembers(members)
                .build();
    }

    /**
     * Generates members from test data for inclusion in the 834 document
     *
     * @param count Number of primary members to generate
     * @return List of Member objects ready for EDI 834 use
     */
    private static List<Member> generateMembers(int count) {
        List<Member> members = new ArrayList<>();
        List<Person> persons = TestDataGenerator.generatePersons(count);

        for (Person person : persons) {
            Member primaryMember = createMemberFromPerson(person);

            if (person.getDependents() != null && !person.getDependents().isEmpty()) {
                for (Dependent dependent : person.getDependents()) {
                    DependentMember dependentMember = mapDependentToDependentMember(dependent, person);
                    primaryMember.addDependent(dependentMember);
                }
            }

            // Add the primary member (with dependents) to our list
            members.add(primaryMember);
        }

        return members;
    }

    /**
     * Creates a Member object from a Person domain object
     *
     * @param person The domain person to convert
     * @return An EDI Member object
     */
    private static Member createMemberFromPerson(Person person) {
        Member member = new Member();
        member.setContext(context);

        member.setMemberId(person.getEmployeeId());
        member.setRelationshipCode(IndividualRelationshipCode.SELF);
        member.setMemberIdQualifier("34");
        member.setSubscriberNumber("MI" + person.getEmployeeId().substring(0, 6));
        member.setPolicyNumber("MIPA2023");

        member.setFirstName(person.getFirstName());
        member.setLastName(person.getLastName());
        member.setMiddleName(person.getMiddleName());
        member.setBirthDate(person.getBirthDate().atStartOfDay());
        member.setGender(person.getGender());

        member.setMemberIndicator(MemberIndicator.INSURED);
        member.setMaintenanceTypeCode(MaintenanceTypeCode.ADDITION);
        member.setEnrollmentDate(LocalDateTime.of(2023, 8, 1, 0, 0));
        member.setCoverageStartDate(LocalDateTime.of(2023, 8, 1, 0, 0));

        member.setAddressLine1(person.getAddress().getAddressLine1());
        member.setAddressLine2(person.getAddress().getAddressLine2());
        member.setCity(person.getAddress().getCity());
        member.setState(person.getAddress().getState());
        member.setZipCode(person.getAddress().getZipCode());

        member.setPhoneNumber(person.getPhoneNumber());
        member.setEmail(person.getEmail());

        // Add Michigan-specific benefit information if available
        if (person.getCoverages() != null && !person.getCoverages().isEmpty()) {
        }

        person.getDependents().forEach(dependent -> {
            member.addDependent(mapDependentToDependentMember(dependent, person));
        });

        return member;
    }

    /**
     * Creates a Member object from a Dependent domain object
     *
     * @param dependent The domain dependent to convert
     * @param primary   The primary person this dependent belongs to
     * @return An EDI Member object
     */
    private static DependentMember mapDependentToDependentMember(Dependent dependent, Person primary) {
        DependentMember member = new DependentMember();
        member.setContext(context);

        member.setMemberId(primary.getEmployeeId() + "-D");
        member.setMemberIdQualifier("34");
        member.setSubscriberNumber("MI" + primary.getEmployeeId().substring(0, 6));
        member.setPolicyNumber("MIPA2023");
        member.setFirstName(dependent.getFirstName());
        member.setLastName(dependent.getLastName());
        member.setMiddleName(dependent.getMiddleName());
        member.setBirthDate(dependent.getBirthDate().atStartOfDay());
        member.setGender(dependent.getGender());
        member.setRelationshipCode(IndividualRelationshipCode.SELF);

        member.setMemberIndicator(MemberIndicator.NOT_INSURED);
        member.setMaintenanceTypeCode(MaintenanceTypeCode.ADDITION);
        member.setEnrollmentDate(LocalDateTime.of(2023, 8, 1, 0, 0));
        member.setCoverageStartDate(LocalDateTime.of(2023, 8, 1, 0, 0));

        return member;
    }

    /**
     * Maps domain relationship to EDI relationship code
     *
     * @param relationship The domain relationship value
     * @return The corresponding EDI relationship code
     */
    private static IndividualRelationshipCode mapRelationshipCode(String relationship) {
        return switch (relationship.toUpperCase()) {
            case "SPOUSE" -> IndividualRelationshipCode.SPOUSE;
            case "CHILD" -> IndividualRelationshipCode.CHILD;
            default -> IndividualRelationshipCode.OTHER_RELATED;
        };
    }

    /**
     * Example of using the StateOfMichigan834 creator with generated members
     */
    public static void main(String[] args) throws ValidationException {
        // Create document with 10 primary members (and their dependents)
        x834Document document = createMichiganDocument(10);

        // Generate and print the document
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