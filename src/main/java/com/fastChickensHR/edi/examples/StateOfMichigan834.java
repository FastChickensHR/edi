/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.examples;

import com.fastChickensHR.edi.x834.common.x834Context;
import com.fastChickensHR.edi.x834.header.*;
import com.fastChickensHR.edi.x834.loop1000A.SponsorName;
import com.fastChickensHR.edi.x834.loop1000B.Payer;
import com.fastChickensHR.edi.x834.x834Document;

import java.time.LocalDate;
import java.util.Optional;

/**
 * Example class demonstrating how to create an 834 document for the State of Michigan.
 * This class shows the proper configuration of all required builders with Michigan-specific values.
 */
public class StateOfMichigan834 {

    private static final x834Context context = new x834Context()
            .setSenderID("FASTCHKN")
            .setReceiverID("MICHGVEDI")
            .setDocumentDate(LocalDate.of(2023, 8, 1));

    public static x834Document createMichiganDocument() {
        InterchangeControlHeader.Builder interchangeBuilder = new InterchangeControlHeader.Builder(context)
                .setInterchangeControlNumber("000000001");

        FunctionalGroupHeader.Builder functionalBuilder = new FunctionalGroupHeader.Builder(context)
                .setGroupControlNumber("42789");

        TransactionSetHeader.Builder transactionSetBuilder = new TransactionSetHeader.Builder()
                .setTransactionSetIdentifierCode("834")
                .setTransactionSetControlNumber("0001");

        BeginningSegment.Builder beginningSegmentBuilder = new BeginningSegment.Builder(context)
                .setReferenceIdentification("220701MI834");

        FileEffectiveDate.Builder fileEffectiveDateBuilder = new FileEffectiveDate.Builder(context);

        TransactionSetPolicyNumber.Builder policyNumberBuilder = new TransactionSetPolicyNumber.Builder()
                .setMasterPolicyNumber("MIHHS-EMP-2023");

        SponsorName.Builder sponsorBuilder = new SponsorName.Builder();

        Payer.Builder payerBuilder = new Payer.Builder();

        return new x834Document.Builder()
                .withInterchangeControlHeader(interchangeBuilder)
                .withFunctionalGroupHeader(functionalBuilder)
                .withTransactionSetHeader(transactionSetBuilder)
                .withBeginningSegment(beginningSegmentBuilder)
                .withFileEffectiveDate(fileEffectiveDateBuilder)
                .withTransactionSetPolicyNumber(policyNumberBuilder)
                .withSponsorName(sponsorBuilder)
                .withPayer(payerBuilder)
                .build();
    }

    /**
     * Example of using the StateOfMichigan834 creator
     */
    public static void main(String[] args) {
        // Create a Michigan 834 document
        x834Document michiganDocument = createMichiganDocument();

        // Check if document is valid
        if (michiganDocument.isValid()) {
            // Generate and print the document
            Optional<String> ediDocument = michiganDocument.generateDocument();
            ediDocument.ifPresent(doc -> {
                System.out.println("Generated Michigan 834 Document:");
                System.out.println(doc);

                // In a real application, you might save this to a file or send it to a service
                // saveToFile(doc, "michigan_enrollment_834.edi");
            });
        } else {
            System.err.println("Document is invalid. Errors:");
            michiganDocument.getErrors().forEach(System.err::println);
        }
    }
}