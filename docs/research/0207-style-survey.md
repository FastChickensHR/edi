# 0207 — De facto style and candidate toolchains

Asset for [#207 Survey de facto style and candidate toolchains](https://github.com/FastChickensHR/edi/issues/207), part of map [#202 Beta-release audit](https://github.com/FastChickensHR/edi/issues/202). Surveyed 2026-07-26 at `99dc56d`. Research only — the toolchain/rules decision is [#210](https://github.com/FastChickensHR/edi/issues/210)'s.

**Method.** Part A: source-level measurement over all 278 `.java` files (35,282 lines, main + test, all four modules) — line-length histograms via `awk`, annotation/idiom censuses via `grep`, era dating via `git log --diff-filter=A`, plus close reading of representative files. Part B: current versions/facts from the projects' release pages and Maven Central (URLs inline), **plus a real dry-run of both candidate formatters** on a scratchpad copy of the tree (standalone CLI jars fetched from Maven Central — no pom was touched, no `mvn install` run) to *measure* rather than estimate the reformat diff. Companion docs: [0203 public-API inventory](0203-public-api-inventory.md), [0204 quality baseline](0204-quality-baseline.md).

---

## Part A — De facto style survey

### Headline: one author, one macro-style, two micro-eras

The repo is 52 commits over ~3.5 weeks (2026-07-03 → 07-26), effectively single-author. Macro-formatting is **strikingly consistent**: 4-space indent (8-space continuation), zero tab characters, zero trailing whitespace, K&R/"Egyptian" braces everywhere (`} else` on one line, no Allman braces anywhere), uniform license header on **278/278 files**, javadoc on 154/154 public types. What diverges is not formatting but *idiom*, split along the Gen-1 (typed-builder, added 07-18) vs Gen-2 (domain model + writer + render keys, 07-19 → 07-26) line the 0203 inventory mapped.

### A1 — Formatting

**Line length** (measured, per module and tree):

| Tree | Lines | >100 | >120 | >140 | Max |
|---|---|---|---|---|---|
| core/main | 185 | 6 (3.2%) | 0 | 0 | 103 |
| x834/main | 19,615 | 489 (2.5%) | 86 (0.44%) | 14 | 494 |
| x999/main | 167 | 19 (11.4%) | 0 | 0 | 115 |
| flatfile/main | 476 | 27 (5.7%) | 0 | 0 | 119 |
| x834/test | 14,186 | 540 (3.8%) | 71 (0.50%) | 25 | 316 |
| core/x999/flatfile tests | 655 | 28 | 2 | 0 | 121 |

Reading: the de facto limit is **~120 columns** — three of four modules never exceed it in main, and x834 crosses it on only 0.44% of lines (the 494-char outlier is a data table line). A 100-column standard would touch ~2.5–4% of lines; a 120-column standard is nearly free.

**Indentation**: 4 spaces per level, 8-space continuation indent (leading-whitespace histogram: 4 ≫ 8 > 12 > 16…; the 1/5/9-space bucket is javadoc ` *` lines). No tabs anywhere.

**Imports**: convention is *project imports → lombok → blank line → `java.*`* (IntelliJ-default layout), but **within-group ordering is inconsistent** — e.g. `x834/src/main/java/com/fastChickensHR/edi/x834/X834Document.java:10–19` (spec before exception, unsorted) and `generate/X834FileGenerator.java:10–52` (interleaved groups, `X834Context`/`X834Document` imports dangling at the end). Wildcard imports: **5 in main** (`segments/BGNSegment.java:10`, `segments/ISASegment.java:11`, `header/InterchangeControlHeader.java:11`, `loop2000/INSSegment.java:12` — all `data.*` — plus `loop2000/data/MemberDateQualifier.java:15` `java.util.*`); in tests, 41/113 files use `import static org.junit.jupiter.api.Assertions.*;`.

**Blank lines / headers**: single blank line between members; license block (`Copyright (C) 2025 FastChickensHR…`) byte-identical on every file, then blank, `package`, blank, imports. The root `pom.xml` carries the same header as an XML comment.

**Java feature era**: conservative. Zero `var`, zero text blocks (even in tests, where 834 payloads would be natural candidates — the golden-file pattern is used instead), 4 `switch` statements, 44 `->` occurrences, records only in the newest code (below). The pom targets Java 23 but the code reads like fluent Java 8+ with records at the edges.

### A2 — Lombok census

Lombok is **x834-only**: 128/149 x834 main files import it; core, x999, and flatfile have zero Lombok (core is records/interfaces; flatfile hand-writes its builder). Pinned at 1.18.36 in the root pom.

| Annotation | Count (main) | Where |
|---|---|---|
| `@Getter` | 121 | class-level on nearly every segment/enum/value class |
| `@Setter` | 23 | 14 class-level (Gen-2 value objects, `BaseMember`, `Member`, `X834Context`, `Segment`), 9 on Gen-1 `AbstractBuilder` inner classes |
| `@Accessors(chain = true)` | 20 | Gen-1 builders + `X834Context` |
| `@Data`, `@Builder`, `@NoArgsConstructor`, `@AllArgsConstructor` | 1 each | all four on `loop2000/Address.java:21–24` — the only Lombok-built builder in the library |

Findings worth standardizing:

1. **No-op `@Setter` on AbstractBuilders.** `segments/DMGSegment.java:184–186` puts `@Setter @Accessors(chain = true)` on `AbstractBuilder`, but every field already has a hand-written chained setter (`setDmg01`…`setDmg11` at lines 330–444, each `{ this.dmgNN = value; return self(); }`) — Lombok generates nothing. The annotation is dead weight that *implies* Lombok is producing the API when it isn't; same pattern on the other 8 inner-class `@Setter`s (`INSSegment.java:193`, `IEASegment.java:77`, `GESegment.java:66`, `TPA.java:61`, the three trailer segments, `HDSegment.java:118`).
2. **Visibility accident**: class-level `@Setter` on `segments/Segment.java:28` generates a **public** `setContext(X834Context)` next to a `protected` `getContext()` (0203's finding — the annotation is the cause).
3. **Shadowed getter**: `X834Context.java:29–31` is `@Getter @Setter @Accessors(chain=true)`, then hand-written `public char getElementSeparator()` (line 118) shadows the Lombok enum getter — the enum field becomes write-only from outside (0203, `constants` section).
4. **One-off `@Data @Builder`** on `Address` vs everything else — three builder idioms coexist (next section).

### A3 — Idiom divergences worth standardizing

Each cites a concrete anchor; each reappears in Part B6 as a decidable idiom-guide question.

1. **Three builder patterns.**
   - *Gen-1 self-type*: `AbstractBuilder<T extends AbstractBuilder<T>>` + `protected abstract T self()` — **24 classes** (all abstract segments, e.g. `segments/DMGSegment.java:186–204`).
   - *Plain nested `Builder`*: ~49 files (`public static class Builder`), from `X834Document.java` through every Gen-1 loop shim, and — hand-written, Lombok-free — `flatfile/delimited/DelimitedFormat.java:33–60`, which also carries the only **static preset factory** (`csv()`).
   - *Lombok `@Builder`*: exactly one, `loop2000/Address.java:22`.
   The newest code (Gen-2 value objects `Income`, `Disability`, `Provider`, …) drops builders entirely for mutable `@Getter @Setter` beans.
2. **Three-and-a-half error channels.** Checked `ValidationException`: **140 throw sites** (every Gen-1 `build()`/`validate()`); `IllegalArgumentException`: **38** (all 44 code enums' `fromString`, plus core record guards, e.g. `core/Field.java:16`); `IllegalStateException`: **7** (notably `generate/X834FileGenerator.java` flattening structured errors at the kernel seam, per its #123 comment); and the sealed accumulate-don't-throw `GenerationResult`/`GenerationError` (`x834/GenerationResult.java:21–27`) that 0203 (Q4) already flags as the *documented* single channel. `RuntimeException`/`UnsupportedOperationException`/NPE: zero.
3. **Null-handling is bare.** `Optional` appears in only 5 main files (core `Field.valueIfPresent()`, `X834Document`, `X834Spec`, `X834FileGenerator`, `BaseMember`); the wart 0203 noted is `loop2000/BaseMember.java:136` — `public java.util.Optional<Address> getAddress(...)` fully qualified with no import. There are **zero nullness annotations** of any flavor and only 3 `Objects.requireNonNull` in 35k lines; absence-means-omit is expressed by `null` fields plus `hasStreet()`-style guards (`Address.java:34–43`).
4. **Naming.** `X834*` prefix on 6 root/seam classes (`X834Document`, `X834Context`, `X834FileGenerator`, `X834Location`, `X834MemberWriter`, `X834Spec`) vs bare names for equally-central types (`Header`, `Trailer`, `Member`, `GenerationResult`). `*Segment` suffix marks the abstract X12 bases (`segments/`) *and* some loop shims (`INSSegment`) but not others (`MemberDemographics` extends `DMGSegment`). **Package names break Java conventions twice**: the root `com.fastChickensHR` (capitals) and the loop hierarchy `loop2100A`/`loop2100C` (capitals) — legal Java, but every stock checkstyle `PackageName` rule flags them, and 0203 cross-cutting finding 1 (mono squatting in the namespace) already puts renames on the table.
5. **Records vs classes track era, not role.** 9 records total: core's 4 value types (07-18) and x834's `GenerationError` + 4 `spec/` records (07-24+). The Gen-2 *value objects* (`Address`, `Income`, `Language`, …) are mutable Lombok classes even though several are semantically record-shaped — the writer mutates them field-by-field, so this is a design choice to survey, not an accident.
6. **Two javadoc voices.** Gen-1: getter/setter boilerplate — "Gets the X. @return x" (26×), "Sets the X" (62×), "@return this builder" (78×), e.g. `DMGSegment.java:79–177`. Gen-2/newest: spec-citing prose with heavy `{@code}` (514) / `{@link}` (482), RFC-style intent statements (`spec/package-info.java`, `TestFixtures` class doc). Type-level coverage is 100%, but **100 enum constants are undocumented** (0204's doclint count, all in `data/`).
7. **Static utility-class convention is 4-for-5.** `TextUtils`, `EdiEnumLookup`, `X834Location`, `X999` are all `final` + private ctor; `dates/DateFormatter.java:16` is neither (`public class DateFormatter`) — 0204 also measured it 10% covered; 0203 tiers it internal.
8. **`final` on classes** is meaningful where used (10 classes: parsers/generators/utilities) but not systematic; segments rely on package-private ctors instead.

### A4 — Test style

113 test classes / 3,990 tests (0204). De facto shape:

- **Naming is split three ways**: `testXxx` camel (351 of ~770 methods, Gen-1 era, e.g. `testGetSegmentIdentifierReturnsExpectedValue` ×17), a `testX_ShouldY` underscore variant (e.g. `testToString_ShouldReturnFormattedString`), and behavior-sentence names in the newest suites (`rejectsNullEmptyAndUnknown` ×42, `resolvesFromCodeNameAndDescription` ×40, `codesAreUnique`). No `@Nested`, no `@DisplayName` anywhere.
- **Parameterization is real but concentrated**: 142 `@ParameterizedTest` (58 `@ValueSource`, 42 `@EnumSource`, 39 `@MethodSource`, 3 `@CsvSource`) — the per-enum suites that drive the 3,950 x834 count.
- **Golden-file pattern is the crown jewel**: `x834/src/test/java/com/fastChickensHR/edi/x834/testsupport/TestFixtures.java` — whole-payload `assertMatchesGolden` against 16 on-disk `.834` fixtures, with a documented `-Dupdate.goldens=true` regen mode. Single-file testsupport package, `final` + private ctor, exemplary javadoc. This is the house style to *name* in the idiom guide, not to fix.
- 14 `setUp()` fixture methods; wildcard static `Assertions.*` in 41/113 files vs explicit imports elsewhere.

---

## Part B — Candidate toolchains

Context constraints from 0204: CI is one workflow running `mvn -B test` (~30s); **any pom-gated check requires moving CI to `mvn -B verify`** — that build-work is shared across all candidate gates and already ticketed territory. Build currently has no plugins beyond compiler + surefire. Everything below was checked against current releases on 2026-07-26.

### B1 — Spotless (formatter, the main event)

- **Current**: `com.diffplug.spotless:spotless-maven-plugin` **3.8.0** (2026-06-30) — [Maven Central](https://central.sonatype.com/artifact/com.diffplug.spotless/spotless-maven-plugin), [plugin README](https://github.com/diffplug/spotless/blob/main/plugin-maven/README.md). Requires Maven on JRE 17+ (we run JDK 23 — fine). `spotless:check` binds to **verify** by default; `spotless:apply` fixes in place.
- **Formatter engines** (pinned inside the step config):
  - **google-java-format 1.35.0** ([releases](https://github.com/google/google-java-format/releases)) — requires **JDK 21+ to run** (so our JDK 23 CI is fine), formats current language features (release notes track JDK EA builds). Style: 2-space indent, 100-col.
  - **palantir-java-format 2.96.0** (2026-03-26, [Maven Central](https://central.sonatype.com/artifact/com.palantir.javaformat/palantir-java-format), [repo](https://github.com/palantir/palantir-java-format)) — gjf fork: **120-col, 4-space indent, lambda/chain-friendly**, "optimised for code review".
- **Measured adoption cost** (dry-run of both CLIs over all 278 files, scratchpad copy):

  | Formatter | Files changed | Source lines changed | % of 35,282 |
  |---|---|---|---|
  | google-java-format 1.35.0 (default style) | 278/278 | 28,902 | **82%** |
  | palantir-java-format 2.96.0 (`--palantir`) | 250/278 | 2,435 | **6.9%** |
  | palantir, import sorting/removal skipped | 207/278 | 1,744 | 4.9% |

  The de facto style (4-space, ~120-col, K&R) **is nearly palantir style already**. The palantir residue is import re-sorting (~700 lines), chain wrapping (its 80-col chain rule), and long-line wraps; google-format is a total rewrite of every file. *(Caveat: dry-run used the standalone CLIs; Spotless drives the same engines, so the diff shape is identical.)*
- **Useful extra steps**: `importOrder` (fixes the A1 inconsistency), `removeUnusedImports`, `licenseHeader` (the existing 278/278 header, verbatim — turns convention into a gate), `formatAnnotations`.
- **Incremental adoption**: `<ratchetFrom>origin/master</ratchetFrom>` restricts enforcement to files changed since the ref — but at a measured 7% one-shot diff, ratcheting buys little here; its cost is per-build git work and "old files stay unformatted" limbo.
- **git blame**: do the one-shot reformat as a dedicated commit, record its SHA in `.git-blame-ignore-revs`, and set `git config blame.ignoreRevsFile .git-blame-ignore-revs`; GitHub's blame view picks the file up automatically ([GitHub docs discussion](https://github.com/orgs/community/discussions/5033), [how-to](https://madewithlove.com/blog/ignoring-revisions-when-using-git-blame/)).
- **CI impact**: `check` at verify phase; seconds on a repo this size (Spotless caches up-to-date state). Workflow moves to `mvn -B verify` (shared prereq).

Wiring sketch (root pom, `<build><plugins>`):

```xml
<plugin>
  <groupId>com.diffplug.spotless</groupId>
  <artifactId>spotless-maven-plugin</artifactId>
  <version>3.8.0</version>
  <configuration>
    <java>
      <palantirJavaFormat><version>2.96.0</version><style>PALANTIR</style></palantirJavaFormat>
      <importOrder/>            <!-- default: alphabetical, one group -->
      <removeUnusedImports/>
      <licenseHeader><file>${maven.multiModuleProjectDirectory}/license-header.txt</file></licenseHeader>
    </java>
  </configuration>
  <executions><execution><goals><goal>check</goal></goals></execution></executions>
</plugin>
```

### B2 — Checkstyle

- **Current**: `maven-checkstyle-plugin` **3.6.0** (2024-10, [plugin site](https://maven.apache.org/plugins/maven-checkstyle-plugin/)) — note it **defaults to Checkstyle 9.3**; pin the `com.puppycrawl.tools:checkstyle` dependency to current **13.7.0** (2026-06-28, [checkstyle.org](https://checkstyle.sourceforge.io/)), which needs Java 17+ (fine).
- **Stock rulesets are a bad fit here.** `google_checks.xml` assumes 2-space/100-col (fights palantir format) and its `PackageName`/`AbbreviationAsWordInName` checks would flag **every file** (`com.fastChickensHR`, `loop2100A`) and much of the API (`X834Document`, `DMGSegment`, `TPA`) — hundreds of violations that are *naming decisions*, not drift. `sun_checks` is older and stricter still.
- **Where it earns its keep is as a complement to Spotless** (formatter owns whitespace; checkstyle owns policy): a **custom minimal set** of non-formatting checks, e.g. `HideUtilityClassConstructor` (would have caught `DateFormatter`), `MissingJavadocType`/`JavadocMethod` scoped to public API, `AvoidStarImport`, `OneTopLevelClass`, `MutableException`. Overlap warning: import checks duplicate Spotless's `importOrder`/`removeUnusedImports` — pick one owner.
- **CI impact**: `check` goal at verify; seconds. Adoption cost of the minimal set: near zero after the Spotless pass; adoption cost of stock google_checks: effectively a package-rename project.

### B3 — Error Prone (+ NullAway)

- **Current**: **2.50.0** (newest on the [releases page](https://github.com/google/error-prone/releases), surveyed 2026-07-26). Runs on **JDK 21+** (JDK 23 fine); [Maven install](https://errorprone.info/docs/installation) = compiler-plugin `compilerArgs` (`-XDcompilePolicy=simple`, `--should-stop=ifError=FLOW`, `-Xplugin:ErrorProne`, `-XDaddTypeAnnotationsToSymbol=true`) + `error_prone_core` in `annotationProcessorPaths`; on JDK 16+ the `--add-exports`/`--add-opens` set goes in `.mvn/jvm.config` (or `-J` args when forking).
- **Lombok caveat (x834)**: the pom already routes Lombok through `annotationProcessorPaths` (required on JDK 23, where implicit processor discovery is off — [JDK 23/Lombok notes](https://marmo.dev/lombok-java-23)); with Error Prone added, **list both paths** (lombok + error_prone_core) and keep lombok first. EP then analyzes *post-Lombok* trees, so generated getters/setters surface EP findings; the standard mitigation is `lombok.config` with `lombok.addLombokGeneratedAnnotation = true` plus EP's `-XepDisableWarningsInGeneratedCode:true` so `@lombok.Generated` members are skipped. Expect a triage pass over x834's 121 `@Getter` classes on first enable.
- **NullAway** add-on: **0.13.7** ([releases](https://github.com/uber/NullAway/releases)), JSpecify-mode maturing; needs JDK 22+ (or the `-XDaddTypeAnnotationsToSymbol=true` flag). **Adoption cost here is high**: the codebase has zero nullness annotations and a null-means-omit domain convention (A3-3) — NullAway without an annotation-the-API pass would be all noise. Defer behind an explicit nullness-idiom decision.
- **CI impact**: compile-phase (no workflow change needed for the *gate* itself, but warnings-as-errors policy belongs with the verify move); adds noticeable compile time (~2-5x javac on small builds — still trivial against a 30s wall).

### B4 — Alternatives, one line each

- **PMD** (`maven-pmd-plugin`, PMD 7 line — [tool docs](https://pmd.github.io/pmd/pmd_userdocs_tools_maven.html)): rule overlap with Error Prone but weaker javac integration and more false-positive curation; not worth a third analyzer here.
- **palantir-baseline** ([repo](https://github.com/palantir/baseline)): the full opinionated stack around palantir-java-format — **Gradle-only plugins**, a non-starter for this Maven build (and far too heavy regardless).
- **EditorConfig-only**: a `.editorconfig` (`indent_size = 4`, `max_line_length = 120`, `insert_final_newline`) costs nothing and helps every editor, but enforces nothing — a complement to Spotless, never a substitute.

### B5 — Recommended toolchain (research recommends; #210 decides)

1. **Spotless 3.8.0 + palantir-java-format 2.96.0 (PALANTIR style) + `importOrder` + `removeUnusedImports` + `licenseHeader`**, as one root-pom plugin. Rationale is measured, not aesthetic: the de facto style *is* ~palantir (6.9% one-shot diff, vs 82% for google-java-format), so the reformat commit is small, reviewable, and safe for the 3-week-old history. Skip `ratchetFrom`; instead land the one-shot `spotless:apply` as a dedicated commit recorded in `.git-blame-ignore-revs`.
2. **Move CI to `mvn -B verify`** in the same execution ticket (shared prereq with 0204's JaCoCo/javadoc gate ideas — do it once).
3. **Error Prone 2.50.0 as a second wave**, with the Lombok `@Generated` suppression wiring above; start with the default `ERROR`-severity bug patterns only, warnings off, then tighten. **NullAway deferred** until the idiom guide settles a nullness convention.
4. **Checkstyle: only as a small custom ruleset** (utility-class ctor, star-import ban, public-API javadoc) *if* #210 wants a third tool at all — do **not** adopt stock `google_checks`/`sun_checks` (package-name and abbreviation checks collide with settled naming). A defensible outcome is Spotless + Error Prone and no Checkstyle.
5. **Add `.editorconfig`** regardless — free.

### B6 — Idiom-guide candidate topics (each a decidable question for the guide that graduates from this survey)

1. **Builder idiom**: for new API types, which of the three shapes — self-typed `AbstractBuilder<T>`, plain nested `Builder` (DelimitedFormat-style, with preset factories like `csv()`), or Lombok `@Builder` — and what happens to the odd one out (`Address`)?
2. **Lombok charter**: which annotations are sanctioned (`@Getter`? `@Setter`? `@Data`?), in which modules (today it's x834-only — keep core/x999/flatfile Lombok-free?), and are the no-op `AbstractBuilder` `@Setter`s and the `Segment.setContext` visibility accident to be removed?
3. **Error channel**: is the beta contract `GenerationResult` (accumulate), checked `ValidationException` (throw), or both with a documented boundary — and do enum `fromString`s keep throwing `IllegalArgumentException`? (0203 Q4.)
4. **Null vs Optional**: is `Optional` reserved for query returns (`valueIfPresent`, `getAddress`) with bare nullable fields elsewhere? Adopt JSpecify annotations now, later, or never (gates NullAway)?
5. **Naming — `X834*` prefix**: when does a type earn the prefix (seam classes only?) vs bare domain names?
6. **Naming — packages**: accept `com.fastChickensHR`/`loop2100A` as frozen (consumer imports; checkstyle exclusions) or fold a lowercase rename into 0203's namespace-squatting fix before the API locks?
7. **Records**: are new value types records by default (core/spec precedent) and do Gen-2 mutable value objects (`Address`, `Income`, …) stay beans by design?
8. **Javadoc**: ban "Gets the X" boilerplate in favor of Gen-2 spec-citing prose? Require docs on every public enum constant (the 100 doclint warnings), or suppress constant-level doclint?
9. **Utility classes**: `final` + private ctor mandatory (fix `DateFormatter`) — trivially enforceable via Checkstyle if adopted.
10. **Line length & indent**: ratify 120/4 (palantir) as written policy — the formatter then makes the question moot.
11. **Imports**: explicit-only (kill the 5 main-tree wildcards) with tool-owned ordering; do tests keep wildcard static `Assertions.*`?
12. **Test naming**: behavior-sentence names (`rejectsNullEmptyAndUnknown`) as the standard for new tests; retire `testXxx`/`_Should` styles opportunistically?
13. **Test structure**: name the golden-file pattern (`TestFixtures`, `-Dupdate.goldens=true`) as the required idiom for any new emitted surface (0204 implication 4); position on `@Nested`/`@DisplayName` (currently unused).
14. **License header**: keep verbatim-2025 text or make the year range tool-managed (Spotless supports git-year interpolation)?

### Open questions for #210

1. Formatter: palantir (measured 6.9% diff) vs google (82%) vs none-yet — does anything argue for google style strongly enough to eat a whole-tree rewrite?
2. One-shot reformat vs `ratchetFrom` — is a 2.4k-line formatting commit acceptable this close to the API-curation churn (order it *before* or *after* the 0203 hide/delete pass? After deletion, the diff shrinks further).
3. Is Checkstyle in or out (i.e., two tools or three)? If in, who owns imports — Spotless or Checkstyle?
4. Error Prone severity policy: errors-only at first, or `-Werror` on all warnings from day one while the codebase is small?
5. Does the CI `verify` move land as its own ticket (shared with the 0204 gates) or bundled with the Spotless ticket?
6. Timing vs curation: the idiom guide wants answers to A3's questions (builders, error channel, nullness) that are also 0203 curation questions — does #210 sequence the guide after the x834 curation decisions to avoid deciding twice?
