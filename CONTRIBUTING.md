# Contributing

## Posture

Issues and bug reports are very welcome — that is what a beta is for. For code
contributions, please **open an issue first** before writing a PR: the API is
deliberately in flux ahead of 1.0, and a change that looks reasonable in
isolation may collide with an API move already in progress. Discussing first
saves everyone a rewritten PR.

## Dev setup

- **Java 23** (the build sets `--source`/`--target` 23)
- **Maven** (any recent 3.9+)

Before opening a PR, run the full gate stack locally:

```bash
mvn -B verify
```

That single command runs everything CI runs: compilation with Error Prone,
the unit test suite, the Spotless format check, and the JaCoCo coverage
floors. If `mvn -B verify` passes locally, CI will pass.

## Style

**Spotless + google-java-format is the law.** The build enforces
google-java-format 1.35.0 (Google style: 2-space indent, 100-column lines)
via the Spotless Maven plugin, along with unused-import removal, annotation
formatting, and the license header (a byte-for-byte match against
`license-header.txt`). Never hand-format; to fix any style failure, run:

```bash
mvn spotless:apply
```

**Error Prone runs errors-only.** Error Prone 2.50.0 is wired into
compilation; its default ERROR-severity patterns fail the build. Warnings are
printed but non-fatal — do not add suppressions to silence warnings, and do
not suppress an error without a comment explaining why.

**Frozen quirks** — these look wrong but are settled; do not "fix" them:

- Package names stay `com.fastChickensHR.*` (capital C and HR). The namespace
  is frozen; renaming it breaks every consumer.
- The license header year is a static "2025" — the check is a byte match, not
  a currency claim.

## Quality bar

**Coverage floors are hand-ratcheted, never lowered.** Each module declares
per-module JaCoCo regression floors (`jacoco.line.floor` /
`jacoco.branch.floor` properties in its own `pom.xml`), set just under the
measured coverage at the time they were set. They exist to stop backsliding:
they are ratcheted *upward* by hand as real coverage improves, and are
**never lowered to admit a PR**. The root pom defaults both floors to 1.00,
so a new module fails `verify` until it declares its own floors. Current
floors (line / branch): core 0.99 / 0.90 · x834 0.95 / 0.75 ·
x999 0.95 / 0.80 · flatfile 0.94 / 0.82.

**The golden corpus is the conformance suite.** The fixtures in
`x834/src/test/resources/golden/` are the named conformance suite for emitted
834 output. Any PR that adds or alters emitted surface — a new segment, loop,
ordinal, or format behavior — must extend or update a golden fixture **in the
same PR**.

**Generate + parse ⇒ round-trip.** A module that both generates and parses a
format must round-trip test it (generate, parse the output, assert
equivalence). This codifies flatfile's existing practice and binds any future
parser.

**Test structure:** flat test classes with behavior-sentence method names
(e.g. `unknownCodeThrowsIllegalArgument`). `@DisplayName` is disallowed — it
duplicates the method name. `@Nested` stays unused — a test class that wants
grouping should split into two classes.

**Documentation conventions:**

- README examples must stay fresh: every code example in the README compiles
  against the current API. A PR that changes public API used by a README
  example updates the example in the same PR.
- Every package containing promised public types carries a `package-info.java`
  with one orienting paragraph (plumbing-only packages are exempt). Doclint
  cannot flag a missing package-info, so this is a convention checked at
  tag time, not a CI gate.

## Changelog

User-visible changes update `CHANGELOG.md` in the same PR, in
[Keep a Changelog](https://keepachangelog.com/) format (see
[#228](https://github.com/FastChickensHR/edi/issues/228), which seeds the
file and the versioning policy). Add your entry under the *Unreleased*
heading.

## Idioms

New code follows the idiom guide in [docs/STYLE.md](docs/STYLE.md) —
builders, the Lombok charter, nullness, `X834*` naming, and records vs
beans, as settled in
[#243](https://github.com/FastChickensHR/edi/issues/243).

## Licensing

By submitting a pull request you agree that your contribution is licensed
under the [MIT License](LICENSE) (inbound = outbound). There is no DCO and no
CLA.
