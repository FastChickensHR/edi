/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.generate;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fastChickensHR.edi.x834.testsupport.TestFixtures;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import org.junit.jupiter.api.Test;

/**
 * Freezes the {@link X834Location} vocabulary — every render-key string literal, and the shape the
 * index helpers stamp onto them. The literals are the wire-level contract between a consuming
 * application's persisted {@code Location.name}s and this library's generator: a Java constant may
 * be renamed freely, but changing the <em>string</em> it holds silently orphans every value already
 * stored under the old key.
 *
 * <p>The key set is collected reflectively and asserted against an on-disk golden of the literal
 * values alone, so the two failure modes separate cleanly: renaming a constant leaves the golden
 * untouched and passes; editing a literal (or adding/removing a key) changes the set and fails. A
 * deliberate vocabulary change is made by regenerating with {@code -Dupdate.goldens=true} and
 * reviewing the diff — the same ritual as the payload goldens.
 */
class X834LocationGoldenTest {

  @Test
  void everyRenderKeyLiteralMatchesTheGolden() {
    List<Field> keys = publicStaticFinalStrings();
    TreeSet<String> literals = new TreeSet<>();
    for (Field key : keys) {
      literals.add(read(key));
    }
    assertEquals(
        keys.size(),
        literals.size(),
        "two X834Location constants carry the same literal — every key must name a distinct location");
    TestFixtures.assertMatchesGolden(
        "golden/x834-location-keys.txt", String.join("\n", literals) + "\n");
  }

  /**
   * The six index helpers all stamp {@code prefix.N.suffix}. The expected strings are spelled out
   * rather than derived from the constants, so a drifted helper cannot certify itself.
   */
  @Test
  void indexHelperFormatsAreFrozen() {
    assertEquals("lui.0.code", X834Location.lui(0, X834Location.LUI_CODE));
    assertEquals("lui.1.description", X834Location.lui(1, X834Location.LUI_DESCRIPTION));
    assertEquals("provider.1.lastName", X834Location.provider(1, X834Location.PROVIDER_LAST_NAME));
    assertEquals("cob.1.beginDate", X834Location.cob(1, X834Location.COB_BEGIN_DATE));
    assertEquals(
        "disability.1.startDate", X834Location.disability(1, X834Location.DISABILITY_START_DATE));
    assertEquals("category.1.value", X834Location.category(1, X834Location.CATEGORY_VALUE));
    assertEquals("hd.0.insuranceLineCode", X834Location.hd(0, X834Location.HD_INSURANCE_LINE_CODE));
    assertEquals("hd.1.benefitBeginDate", X834Location.hd(1, X834Location.HD_BENEFIT_BEGIN_DATE));
  }

  private static List<Field> publicStaticFinalStrings() {
    List<Field> keys = new ArrayList<>();
    for (Field field : X834Location.class.getDeclaredFields()) {
      int mods = field.getModifiers();
      if (Modifier.isPublic(mods)
          && Modifier.isStatic(mods)
          && Modifier.isFinal(mods)
          && field.getType() == String.class) {
        keys.add(field);
      }
    }
    return keys;
  }

  private static String read(Field key) {
    try {
      return (String) key.get(null);
    } catch (IllegalAccessException e) {
      throw new AssertionError("public field must be readable: " + key.getName(), e);
    }
  }
}
