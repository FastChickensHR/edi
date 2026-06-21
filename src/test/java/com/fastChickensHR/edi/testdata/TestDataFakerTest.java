/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.testdata;

import com.fastChickensHR.edi.x834.loop2000.DependentMember;
import com.fastChickensHR.edi.x834.loop2000.Member;
import com.fastChickensHR.edi.x834.loop2000.data.CoverageLevelCode;
import com.fastChickensHR.edi.x834.loop2000.data.IndividualRelationshipCode;
import com.fastChickensHR.edi.x834.loop2000.data.InsuranceLineCode;
import com.fastChickensHR.edi.x834.loop2000.loop3000.HealthCoverage;
import com.fastChickensHR.edi.x834.x834Context;
import com.fastChickensHR.edi.x834.x834Document;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestDataFakerTest {

    private static final long SEED = 42L;

    @Test
    void contextIsFullyPopulated() {
        x834Context ctx = TestDataFaker.withSeed(SEED).context().build();

        assertNotNull(ctx.getSenderID());
        assertNotNull(ctx.getReceiverID());
        assertNotNull(ctx.getTransactionSetControlNumber());
        assertNotNull(ctx.getGroupControlNumber());
        assertNotNull(ctx.getDocumentDate());
        assertNotNull(ctx.getFormattedDocumentDate());
        assertFalse(ctx.getSenderID().isBlank());
        assertFalse(ctx.getReceiverID().isBlank());
    }

    @Test
    void memberIsFullyPopulatedWithFamily() {
        TestDataFaker faker = TestDataFaker.withSeed(SEED);
        x834Context ctx = faker.context().build();
        Member subscriber = faker.member(ctx).withSpouse().withChildren(2).build();

        assertNotNull(subscriber.getFirstName());
        assertNotNull(subscriber.getLastName());
        assertNotNull(subscriber.getBirthDate());
        assertNotNull(subscriber.getGender());
        assertNotNull(subscriber.getAddressLine1());
        assertNotNull(subscriber.getCity());
        assertNotNull(subscriber.getState());
        assertNotNull(subscriber.getZipCode());
        assertNotNull(subscriber.getEmail());
        assertNotNull(subscriber.getMemberId());
        assertEquals(IndividualRelationshipCode.EMPLOYEE, subscriber.getRelationshipCode());

        assertEquals(3, subscriber.getDependents().size(), "1 spouse + 2 children");
        long spouses = subscriber.getDependents().stream()
                .filter(d -> d.getRelationshipCode() == IndividualRelationshipCode.SPOUSE)
                .count();
        long children = subscriber.getDependents().stream()
                .filter(d -> d.getRelationshipCode() == IndividualRelationshipCode.CHILD)
                .count();
        assertEquals(1, spouses);
        assertEquals(2, children);

        // Family shares last name and address
        for (DependentMember dep : subscriber.getDependents()) {
            assertEquals(subscriber.getLastName(), dep.getLastName());
            assertEquals(subscriber.getAddressLine1(), dep.getAddressLine1());
            assertEquals(subscriber.getZipCode(), dep.getZipCode());
        }
    }

    @Test
    void sameSeedProducesIdenticalData() {
        TestDataFaker a = TestDataFaker.withSeed(SEED);
        TestDataFaker b = TestDataFaker.withSeed(SEED);

        x834Context ctxA = a.context().build();
        x834Context ctxB = b.context().build();
        assertEquals(ctxA.getSenderID(), ctxB.getSenderID());
        assertEquals(ctxA.getReceiverID(), ctxB.getReceiverID());
        assertEquals(ctxA.getGroupControlNumber(), ctxB.getGroupControlNumber());

        Member mA = a.member(ctxA).withChildren(1).build();
        Member mB = b.member(ctxB).withChildren(1).build();
        assertEquals(mA.getFirstName(), mB.getFirstName());
        assertEquals(mA.getLastName(), mB.getLastName());
        assertEquals(mA.getEmail(), mB.getEmail());
        assertEquals(mA.getDependents().get(0).getFirstName(),
                mB.getDependents().get(0).getFirstName());
    }

    @Test
    void differentSeedsProduceDifferentData() {
        Member m1 = TestDataFaker.withSeed(1L)
                .member(TestDataFaker.withSeed(1L).context().build()).build();
        Member m2 = TestDataFaker.withSeed(2L)
                .member(TestDataFaker.withSeed(2L).context().build()).build();
        assertNotEquals(m1.getEmail(), m2.getEmail());
    }

    @Test
    void coverageGeneratorProducesValidHdSegment() throws Exception {
        HealthCoverage hd = TestDataFaker.withSeed(SEED).coverage()
                .withInsuranceLine(InsuranceLineCode.HEALTH)
                .withCoverageLevel(CoverageLevelCode.FAMILY)
                .build();
        assertEquals("001", hd.getMaintenanceTypeCode());
        assertEquals("HLT", hd.getInsuranceLineCode());
        assertEquals("FAM", hd.getCoverageLevelCode());
        assertNotNull(hd.getPlanCoverageDescription());
        assertFalse(hd.getPlanCoverageDescription().isBlank());
    }

    @Test
    void buildEmployeeProducesDefaultHealthDentalVision() throws Exception {
        TestDataFaker faker = TestDataFaker.withSeed(SEED);
        x834Context ctx = faker.context().build();
        GeneratedEmployee emp = faker.member(ctx).withSpouse().withChildren(2).buildEmployee();

        assertEquals(3, emp.coverages().size());
        assertEquals("HLT", emp.coverages().get(0).getInsuranceLineCode());
        assertEquals("DEN", emp.coverages().get(1).getInsuranceLineCode());
        assertEquals("VIS", emp.coverages().get(2).getInsuranceLineCode());
        // Family level should be derived from spouse + children
        for (HealthCoverage hd : emp.coverages()) {
            assertEquals("FAM", hd.getCoverageLevelCode());
        }
    }

    @Test
    void documentWith100EmployeesBuildsAndRenders() throws Exception {
        x834Document doc = TestDataFaker.withSeed(SEED)
                .document()
                .withEmployees(100)
                .build();

        assertTrue(doc.isValid(), () -> "Errors: " + doc.getErrors());
        assertEquals(100, doc.getMembers().size());

        String edi = doc.generateDocument().orElseThrow();
        assertFalse(edi.isBlank());

        // 100 INS segments (one per subscriber) — dependents also emit INS,
        // so the total count is at least 100.
        long insCount = edi.lines().filter(l -> l.startsWith("INS*")).count();
        assertTrue(insCount >= 100, "expected >=100 INS segments, got " + insCount);

        // 300 HD segments (3 lines × 100 employees) come from additionalSegments.
        long hdCount = edi.lines().filter(l -> l.startsWith("HD*")).count();
        assertEquals(300, hdCount);
    }
}
