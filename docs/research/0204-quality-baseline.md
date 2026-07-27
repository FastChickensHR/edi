# 0204 — Quality baseline: tests, CI gates, docs (beta-release audit)

Asset for [#204 Baseline the current quality](https://github.com/FastChickensHR/edi/issues/204), part of map [#202 Beta-release audit](https://github.com/FastChickensHR/edi/issues/202). Measured 2026-07-26 at `dfa4597`.

**Method.** Full `mvn test` with a one-off JaCoCo agent (0.8.12, no pom changes) for coverage; `maven-javadoc-plugin:3.11.2:javadoc -Ddoclint=all` for javadoc lint; surefire XML for test counts; ruleset via `gh api repos/FastChickensHR/edi/rulesets`.

## CI & gates

**One workflow, `maven.yml`** ("Java CI with Maven"): on every PR and push to master, `mvn -B test` on JDK 23 temurin with Maven cache, then `mikepenz/action-junit-report@v3` (posts even on failure via `if: always()`). Deliberately **no path fast-exit** — the header comment notes the suite always runs so both required checks always post. Concurrency group cancels superseded runs. Wall time ≈ 30s.

**Protection is a ruleset, not classic branch protection** (`GET /branches/master/protection` 404s). Ruleset `protectMaster` (id 4173639, enforcement `active`): `non_fast_forward` + `pull_request` + `required_status_checks` = **`Run Unit Tests`, `JUnit Test Report`**.

**What CI does *not* gate today:** coverage (no JaCoCo in the pom), javadoc lint (no javadoc plugin), formatting/style (no Spotless/Checkstyle — [#207](https://github.com/FastChickensHR/edi/issues/206)'s territory), API compatibility (no japicmp/revapi), `mvn verify`/`package` (only `test` runs — nothing exercises jar assembly in CI), dependency/enforcer rules. The build has **no plugins beyond compiler + surefire**, and no version pinning for either beyond the parent pom.

## Tests

| Module | Test classes | Tests | Notes |
|---|---|---|---|
| core | 1 | 6 | value-tree invariants |
| x834 | 108 | 3,950 | see distribution below |
| x999 | 1 | 11 | parser |
| flatfile | 3 | 23 | delimited generate/parse round-trips |
| **total** | **113** | **3,990** | ≈ 30s in CI |

**x834 distribution** (test classes per package): data 23, segments 21, loop2000/data 19, loop2000 7, loop2100A 7, header 7, trailer 4, spec 4, loop2100C 3, util 2, loop3000 2, document/context 2, loop2310/2320 2. The bulk is per-enum and per-segment unit tests; the high count (3,950) is heavily parameterized enum/code testing.

**Golden-file conformance corpus exists**: `x834/src/test/resources/golden/` holds **16 `.834` fixtures** (full-document renders: subscriber-with-dependent, two-HD-loops, 2310/2320, 2100A tail, …). There is also a `testsupport` package in x834's test tree. `X834SpecTest` carries `RENDERED_ARITY` — the spec-covers-every-emitted-ordinal assertion lives only in tests.

## Coverage (JaCoCo, whole-module)

| Module | Line | Branch | Method |
|---|---|---|---|
| core | **100%** (30/30) | 95.0% (19/20) | — |
| x834 | **96.3%** (6,420/6,667) | **77.1%** (858/1,113) | — |
| x999 | **97.6%** (40/41) | 85.2% (23/27) | — |
| flatfile | **95.8%** (159/166) | 86.5% (64/74) | — |

Classes under 80% line coverage (>10 lines), all x834:

| Class | Line % | Note |
|---|---|---|
| `dates.DateFormatter` | **10%** (5/48) | inventory Tier 3 "internal-looking" — dead plumbing, not a test gap worth closing |
| `util.TextUtils` | **39%** (7/18) | same tier |
| `segments.ISASegment.AbstractBuilder` | 69% | |
| `segments.DTPSegment` / its `AbstractBuilder` | 70% / 76% | |
| `loop3000.HealthCoverageDates` | 71% | inventory: internal-looking |
| `X834Document.Builder` | 72% | untested paths are likely the Gen-1 escape hatches |
| `header.Header.Builder` | 75% | ditto — the 11 unexercised sub-builder setters |
| `segments.DSBSegment` | 78% | |

Reading: **the uncovered tail correlates with the 0203 inventory's hide-before-beta candidates**, not with entry-point surface. If the internal tail is hidden/deleted per curation, coverage of the *public* surface approaches the ceiling without writing a single new test.

## Javadoc

- **Type-level: 154/154 public types have javadoc** (100%, all four modules).
- **Doclint (`-Ddoclint=all`): exactly 100 warnings, all in x834, zero elsewhere.** They are member-level "no comment" warnings clustered in `data/` code-enum constants (e.g. `ActionCode` constants) — the enums document the type but not each constant. core, x999, flatfile are doclint-clean.

## Docs inventory

- **`README.md` (root, 68 lines) is the only prose doc** — and its central example is **stale**: line 49 shows `Optional<String> document = new X834Document.Builder(...)`, but `generateDocument()` has returned `GenerationResult` since `1549794` (inventory question 8).
- No per-module READMEs, no CHANGELOG/release notes, no site/javadoc publication.
- `docs/` contains only `research/0203-public-api-inventory.md` (this map's assets).
- Two intent-bearing `package-info.java` files: `x834.spec` (consumer-facing contract statement) and `flatfile.fixedwidth` (designed-for-not-built placeholder).
- Community files (CONTRIBUTING, SECURITY, templates): none — [Settle the OSS community hygiene checklist](https://github.com/FastChickensHR/edi/issues/213)'s territory.

## Implications for the bar-setting tickets

1. **The baseline is already high** — a beta bar can be set at or near current numbers and enforced immediately, rather than aspirationally: e.g. JaCoCo check at line ≥95%/module, doclint-clean at `-Ddoclint=all` minus the 100 enum-constant warnings (fix or suppress once), and both would pass today within noise.
2. **x834 branch coverage (77%) is the only weak headline number**, and much of the gap sits in internal-looking classes the curation pass may hide or delete — sequence the coverage gate *after* API curation to avoid testing doomed code.
3. **CI runs `test`, not `verify`** — any future gate (jacoco check, javadoc, japicmp, spotless) needs the workflow to move to `mvn -B verify` and the pom to grow those plugins; that is a single execution ticket's worth of build work.
4. **The golden corpus (16 fixtures) is the library's de facto conformance suite** — worth naming in the test bar as the thing that must grow with any new emitted surface.
