/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.fixtures;

import com.fastChickensHR.edi.x834.x834Context;
import net.datafaker.Faker;

import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Fluent generator that produces a fully populated {@link x834Context} using realistic data.
 * <p>
 * Every field on the context will be set to a realistic-looking value. Callers may
 * override individual fields via the {@code with*} methods.
 */
public final class X834ContextGenerator {

    private final Faker faker;

    private String senderId;
    private String receiverId;
    private String transactionSetControlNumber;
    private String groupControlNumber;
    private LocalDateTime documentDate;

    X834ContextGenerator(X834Fixtures parent) {
        this.faker = parent.faker();
    }

    public X834ContextGenerator withSenderId(String senderId) {
        this.senderId = senderId;
        return this;
    }

    public X834ContextGenerator withReceiverId(String receiverId) {
        this.receiverId = receiverId;
        return this;
    }

    public X834ContextGenerator withTransactionSetControlNumber(String value) {
        this.transactionSetControlNumber = value;
        return this;
    }

    public X834ContextGenerator withGroupControlNumber(String value) {
        this.groupControlNumber = value;
        return this;
    }

    public X834ContextGenerator withDocumentDate(LocalDateTime documentDate) {
        this.documentDate = documentDate;
        return this;
    }

    /** Builds a fresh {@link x834Context} populated with realistic data. */
    public x834Context build() {
        x834Context ctx = new x834Context();
        ctx.setSenderID(senderId != null ? senderId : pad(faker.company().name(), 15));
        ctx.setReceiverID(receiverId != null ? receiverId : pad(faker.company().name(), 15));
        ctx.setTransactionSetControlNumber(
                transactionSetControlNumber != null
                        ? transactionSetControlNumber
                        : String.format("%04d", faker.number().numberBetween(1, 9999)));
        ctx.setGroupControlNumber(
                groupControlNumber != null
                        ? groupControlNumber
                        : String.format("%09d", faker.number().numberBetween(1, 999_999_999)));
        ctx.setDocumentDate(documentDate != null ? documentDate : randomDateTime());
        return ctx;
    }

    private LocalDateTime randomDateTime() {
        return faker.date().past(365, java.util.concurrent.TimeUnit.DAYS).toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    private static String pad(String value, int max) {
        String cleaned = value.replaceAll("[^A-Za-z0-9]", "").toUpperCase();
        if (cleaned.isEmpty()) {
            cleaned = "EDIPARTNER";
        }
        return cleaned.length() > max ? cleaned.substring(0, max) : cleaned;
    }
}
