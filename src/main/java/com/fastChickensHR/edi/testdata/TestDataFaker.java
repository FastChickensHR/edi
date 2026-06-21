/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.testdata;

import net.datafaker.Faker;

import java.util.Locale;
import java.util.Random;

/**
 * Entry point for generating realistic, reproducible test data for the EDI x834 library.
 * <p>
 * Wraps a seeded {@link Faker} so that any test or demo can request a deterministic
 * stream of fake values simply by reusing the same seed.
 *
 * <pre>{@code
 * TestDataFaker faker = TestDataFaker.withSeed(42L);
 * x834Context ctx     = faker.context().build();
 * Member subscriber   = faker.member(ctx).withSpouse().withChildren(2).build();
 * }</pre>
 */
public final class TestDataFaker {

    private final Faker faker;
    private final Random random;
    private final long seed;

    private TestDataFaker(long seed, Locale locale) {
        this.seed = seed;
        this.random = new Random(seed);
        this.faker = new Faker(locale, this.random);
    }

    /**
     * Creates a new generator that uses a random, non-deterministic seed.
     */
    public static TestDataFaker create() {
        return new TestDataFaker(new Random().nextLong(), Locale.ENGLISH);
    }

    /**
     * Creates a new generator that always produces the same sequence of values
     * for the supplied {@code seed}. Use this for reproducible tests.
     */
    public static TestDataFaker withSeed(long seed) {
        return new TestDataFaker(seed, Locale.ENGLISH);
    }

    /**
     * Creates a new generator with a specific locale and seed.
     */
    public static TestDataFaker withSeed(long seed, Locale locale) {
        return new TestDataFaker(seed, locale);
    }

    /** The underlying {@link Faker}, exposed for advanced/ad-hoc usage. */
    public Faker faker() {
        return faker;
    }

    /** The seeded {@link Random} that backs the faker. */
    public Random random() {
        return random;
    }

    /** The seed used to initialize this generator. */
    public long seed() {
        return seed;
    }

    /** Returns a builder for generating a fully-populated {@code x834Context}. */
    public x834ContextGenerator context() {
        return new x834ContextGenerator(this);
    }

    /** Returns a builder for generating a fully-populated subscriber {@code Member}. */
    public MemberGenerator member(com.fastChickensHR.edi.x834.x834Context context) {
        return new MemberGenerator(this, context);
    }

    /** Returns a builder for generating a single {@code HealthCoverage} (HD) segment. */
    public HealthCoverageGenerator coverage() {
        return new HealthCoverageGenerator(this);
    }

    /** Returns a builder for generating a complete fake {@code x834Document}. */
    public DocumentGenerator document() {
        return new DocumentGenerator(this);
    }
}
