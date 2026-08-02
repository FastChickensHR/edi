# Changelog

All notable changes to this project are documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/).
This file is curated by hand — it is not generated from commit messages.
Conventional commits (with `!` for breaking changes) remain the raw feed at
commit level; this file is the canonical, human-readable record.

## Conventions

- One section per released version, newest first.
- Every release section carries a **mandatory Breaking subsection** — written
  explicitly as "none" when there are no breaking changes. This subsection is
  the canonical notice that the compatibility promise below refers to.
- Standard Keep-a-Changelog subsections (Added, Changed, Deprecated, Removed,
  Fixed, Security) are used as applicable.
- On each release cut, the version's section is mirrored into the GitHub
  Release notes.

## Versioning and deprecation policy

Decided in [#205](https://github.com/FastChickensHR/edi/issues/205); recorded
here so consumers can find it.

### Version scheme

- Versions run `1.0.0-beta.N` → `1.0.0`. There is no 0.x line — the curated
  public surface *is* the 1.0 candidate, and the version string names that
  promise. Graduation is a rename (`beta.N` → `1.0.0`), not a renumbering.
- **Lockstep versioning:** the whole reactor shares one version — the
  parent's. There are no per-module versions; a major driven by one module
  re-releases the others unchanged. The empty `fixedwidth` placeholder ships
  at the reactor version, promise-free.
- **Graduation rule:** `1.0.0` is a binary-identical re-release of the final
  beta — nothing but the version string changes. Any other change ships as one
  more `beta.N` first, which becomes the new candidate.

### Compatibility promise

- **Beta → beta: none-with-notice, measured on the binary axis.** Any
  binary-incompatible change is allowed between betas, but never silently:
  every binary break must be enumerated in the release's Breaking subsection
  (a short, explicit list — possibly "none").
- **From `1.0.0` on:** binary compatibility holds within a major version, per
  semver.

### Deprecation policy (post-1.0)

- Removal is **release-based**: a public API is removed only at a major
  version, and only after being marked
  `@Deprecated(since = "1.x", forRemoval = true)` — with a `@deprecated`
  javadoc tag naming the replacement (or stating there is none) — in at least
  one shipped minor release.
- During the beta, deprecation is **optional**: betas may break directly under
  the none-with-notice promise.

## [Unreleased]

### Breaking

- none

## [1.0.0-beta.1] - 2026-08-02

The first tagged release: the curated 1.0 candidate surface, published to
GitHub Packages.

### Breaking

- none (first release)

### Added

- `core` — the format-neutral file kernel: `FileContent`/`Record`/`Field`/
  `Location` and the `FileGenerator`/`FileParser` seam.
- `x834` — the X12 834 (005010X220A1) benefit-enrollment document model:
  builder-based envelope and member loop, the accumulate-never-throw
  `GenerationResult` contract, frozen `X834Location` render keys, the
  `spec/` element dictionary and X12 code-set enums.
- `x999` — X12 999 implementation-acknowledgment parsing (envelope and
  AK1/IK5/AK9 verdict segments).
- `flatfile` — delimited flat-file generate/parse round-trip with a pinned
  CSV convention; `fixedwidth` ships as an empty placeholder package.
- Build gates enforced at `verify`: Spotless (google-java-format), Error
  Prone (errors-only), JaCoCo regression floors, doclint-clean javadoc
  jars, and the japicmp binary-compatibility gate this release baselines.
