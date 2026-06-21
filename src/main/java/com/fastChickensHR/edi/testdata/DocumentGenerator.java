/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.testdata;

import com.fastChickensHR.edi.common.exception.ValidationException;
import com.fastChickensHR.edi.x834.header.Header;
import com.fastChickensHR.edi.x834.loop2000.loop3000.HealthCoverage;
import com.fastChickensHR.edi.x834.trailer.Trailer;
import com.fastChickensHR.edi.x834.x834Context;
import com.fastChickensHR.edi.x834.x834Document;
import net.datafaker.Faker;

import java.util.Random;

/**
 * Fluent generator that assembles a complete fake {@link x834Document} containing
 * a header, trailer, and a configurable number of employees. Each generated employee
 * may include a spouse, children, and a default set of Health/Dental/Vision
 * {@link HealthCoverage} segments.
 *
 * <pre>{@code
 * x834Document doc = TestDataFaker.withSeed(1L)
 *         .document()
 *         .withEmployees(100)
 *         .build();
 * String edi = doc.generateDocument().orElseThrow();
 * }</pre>
 */
public final class DocumentGenerator {

    private final TestDataFaker parent;
    private final Faker faker;
    private final Random random;

    private int employeeCount = 1;
    private double spouseRate = 0.6;
    private int maxChildren = 3;
    private x834Context context;

    DocumentGenerator(TestDataFaker parent) {
        this.parent = parent;
        this.faker = parent.faker();
        this.random = parent.random();
    }

    /** Number of subscriber employees to include on the file. */
    public DocumentGenerator withEmployees(int count) {
        if (count < 1) {
            throw new IllegalArgumentException("employee count must be >= 1");
        }
        this.employeeCount = count;
        return this;
    }

    /** Probability (0.0–1.0) that an employee has a spouse. */
    public DocumentGenerator withSpouseRate(double rate) {
        if (rate < 0.0 || rate > 1.0) {
            throw new IllegalArgumentException("spouseRate must be in [0,1]");
        }
        this.spouseRate = rate;
        return this;
    }

    /** Maximum number of children any single employee may have (0 disables children). */
    public DocumentGenerator withMaxChildren(int max) {
        if (max < 0) {
            throw new IllegalArgumentException("maxChildren must be >= 0");
        }
        this.maxChildren = max;
        return this;
    }

    /** Use a pre-built context instead of generating one. */
    public DocumentGenerator withContext(x834Context context) {
        this.context = context;
        return this;
    }

    /** Builds a complete {@link x834Document} populated with fake data. */
    public x834Document build() throws ValidationException {
        x834Context ctx = context != null ? context : parent.context().build();

        Header header = new Header.Builder(ctx)
                .setInterchangeControlNumber(String.format("%09d", faker.number().numberBetween(1, 999_999_999)))
                .setGroupControlNumber(ctx.getGroupControlNumber() != null ? ctx.getGroupControlNumber() : "1")
                .setTransactionSetIdentifierCode("834")
                .setTransactionSetControlNumber(ctx.getTransactionSetControlNumber() != null
                        ? ctx.getTransactionSetControlNumber() : "0001")
                .setReferenceIdentification(faker.numerify("REF#######"))
                .setMasterPolicyNumber(faker.bothify("MP-#####"))
                .setPlanSponsorName(faker.company().name())
                .setPayerName(faker.company().name())
                .build();

        Trailer trailer = new Trailer.Builder(ctx)
                .setTransactionSetControlNumber(ctx.getTransactionSetControlNumber() != null
                        ? ctx.getTransactionSetControlNumber() : "0001")
                .setGroupControlNumber(ctx.getGroupControlNumber() != null ? ctx.getGroupControlNumber() : "1")
                .build();

        x834Document.Builder builder = new x834Document.Builder(ctx)
                .withHeader(header)
                .withTrailer(trailer);

        for (int i = 0; i < employeeCount; i++) {
            MemberGenerator mg = parent.member(ctx);
            if (random.nextDouble() < spouseRate) {
                mg.withSpouse();
            }
            if (maxChildren > 0) {
                int kids = random.nextInt(maxChildren + 1);
                if (kids > 0) {
                    mg.withChildren(kids);
                }
            }
            GeneratedEmployee emp = mg.buildEmployee();
            builder.addMember(emp.member());
            for (HealthCoverage hd : emp.coverages()) {
                builder.addSegment(hd);
            }
        }

        return builder.build();
    }
}
