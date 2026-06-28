/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.fixtures;

import net.datafaker.Faker;

import java.util.Locale;
import java.util.Random;

/**
 * Entry point for generating realistic, reproducible test fixtures for the EDI x834 library.
 * <p>
 * Wraps a seeded {@link Faker} so that any test or demo can request a deterministic
 * stream of values simply by reusing the same seed.
 *
 * <pre>{@code
 * X834Fixtures fixtures = X834Fixtures.seeded(42L);
 * x834Context ctx       = fixtures.context().build();
 * Member subscriber     = fixtures.member(ctx).withSpouse().withChildren(2).build();
 * }</pre>
 */
public final class X834Fixtures {

    private final Faker faker;
    private final Random random;
    private final long seed;

    private X834Fixtures(long seed, Locale locale) {
        this.seed = seed;
        this.random = new Random(seed);
        this.faker = new Faker(locale, this.random);
    }

    /**
     * Creates a new fixture set that uses a random, non-deterministic seed.
     */
    public static X834Fixtures random() {
        return new X834Fixtures(new Random().nextLong(), Locale.ENGLISH);
    }

    /**
     * Creates a new fixture set that always produces the same sequence of values
     * for the supplied {@code seed}. Use this for reproducible tests.
     */
    public static X834Fixtures seeded(long seed) {
        return new X834Fixtures(seed, Locale.ENGLISH);
    }

    /**
     * Creates a new fixture set with a specific locale and seed.
     */
    public static X834Fixtures seeded(long seed, Locale locale) {
        return new X834Fixtures(seed, locale);
    }

    /** The underlying {@link Faker} — package-private, implementation detail. */
    Faker faker() {
        return faker;
    }

    /** The seeded {@link Random} that backs the faker — package-private, implementation detail. */
    Random rng() {
        return random;
    }

    /** The seed used to initialize this fixture set. */
    public long seed() {
        return seed;
    }

    /** Returns a generator for a fully-populated {@code x834Context}. */
    public X834ContextGenerator context() {
        return new X834ContextGenerator(this);
    }

    /** Returns a generator for a fully-populated subscriber {@code Member}. */
    public MemberGenerator member(com.fastChickensHR.edi.x834.x834Context context) {
        return new MemberGenerator(this, context);
    }

    /** Returns a generator for a single {@code HealthCoverage} (HD) segment. */
    public HealthCoverageGenerator coverage() {
        return new HealthCoverageGenerator(this);
    }

    /** Returns a generator for a complete {@code x834Document}. */
    public DocumentGenerator document() {
        return new DocumentGenerator(this);
    }

    /** Returns a generator for a census-calibrated domain {@code Person}. */
    public PersonGenerator person() {
        return new PersonGenerator(this);
    }
}
