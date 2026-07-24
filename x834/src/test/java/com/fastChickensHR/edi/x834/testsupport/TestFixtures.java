/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.testsupport;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Golden-fixture support for whole-payload assertions.
 *
 * <p>A <em>golden</em> is an expected rendered payload — a full 834, a full 999, a multi-row flat
 * file — kept on disk under {@code src/test/resources/golden/} with a real extension, instead of a
 * multi-kilobyte inline text block that hides trailing-whitespace and segment-terminator bugs. Tests
 * assert the entire generated string against its golden with {@link #assertMatchesGolden}, which
 * catches segment reordering, dropped segments, and wrong element positions that scattered
 * {@code contains(...)} substring checks cannot.
 *
 * <p>Regenerate the goldens after an <em>intentional</em> format change by running the suite with
 * {@code -Dupdate.goldens=true}: in that mode {@link #assertMatchesGolden} writes the actual payload
 * back to its source golden file and skips the assertion, so refreshing every golden is one command
 * rather than a hand-edit. Review the resulting diff before committing — regen mode trusts the
 * generator, so a real regression would be silently baked in if the diff isn't read.
 */
public final class TestFixtures {

    /** Resources live under the module's {@code src/test/resources}; that is surefire's working dir. */
    private static final Path SOURCE_RESOURCE_ROOT = Path.of("src", "test", "resources");

    private TestFixtures() {
    }

    /**
     * Read a classpath resource (e.g. {@code "golden/subscriber-with-dependent.834"}) as a UTF-8
     * string. Fails loudly, naming the regen command, when the fixture is missing.
     */
    public static String load(String resourcePath) {
        try (InputStream in = TestFixtures.class.getClassLoader().getResourceAsStream(resourcePath)) {
            if (in == null) {
                throw new IllegalStateException("golden fixture not found on classpath: " + resourcePath
                        + " — create it by running the suite with -Dupdate.goldens=true");
            }
            return new String(in.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new UncheckedIOException("failed to read golden fixture: " + resourcePath, e);
        }
    }

    /**
     * Assert that {@code actual} equals the golden at {@code resourcePath}. Under
     * {@code -Dupdate.goldens=true} this instead writes {@code actual} to the source golden file and
     * returns without asserting, so goldens can be regenerated in bulk after a deliberate change.
     */
    public static void assertMatchesGolden(String resourcePath, String actual) {
        if (Boolean.getBoolean("update.goldens")) {
            writeGolden(resourcePath, actual);
            return;
        }
        assertEquals(load(resourcePath), actual, () -> "generated payload does not match golden "
                + resourcePath + " — if this change is intentional, regenerate with -Dupdate.goldens=true");
    }

    private static void writeGolden(String resourcePath, String content) {
        Path target = SOURCE_RESOURCE_ROOT.resolve(resourcePath);
        try {
            Files.createDirectories(target.getParent());
            Files.writeString(target, content, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new UncheckedIOException("failed to write golden fixture: " + target
                    + " (working dir must be the module root)", e);
        }
    }
}
