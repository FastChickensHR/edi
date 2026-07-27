# 0206 — Publishing options for the beta artifact

Ticket: [#206](https://github.com/FastChickensHR/edi/issues/206) · Map: [#202](https://github.com/FastChickensHR/edi/issues/202) (beta-release audit)
Date: 2026-07-26

Research only — this survey compares options and recommends; the decision belongs to
[#209](https://github.com/FastChickensHR/edi/issues/209). The versioning-policy ticket
[#205](https://github.com/FastChickensHR/edi/issues/205) is still open, so every option below is
presented compatibly with both a `0.x` scheme and a `1.0.0-beta.N` scheme — nothing here assumes
one.

## Method

- Read the mono side of the status quo directly from the consumer repo: `.edi-version`,
  `bin/sync-edi.sh`, `.github/workflows/maven-test.yml`, `.github/workflows/e2e.yml`
  (FastChickensHR/mono, local checkout).
- Read this repo's parent `pom.xml` and `LICENSE` for what Central-grade metadata already exists
  (almost none — see §3).
- Verified current external facts (July 2026) against primary docs via web search/fetch:
  GitHub Packages Maven auth model (docs.github.com + GitHub community discussions), the
  post-OSSRH Central Portal process (central.sonatype.org), and JitPack behavior
  (docs.jitpack.io `BUILDING.md` + jitpack/jitpack.io issue tracker). Sources are cited inline.

## 1. Baseline — status quo (SHA pin + build-from-source)

What exists today (mono #849):

- mono pins a full 40-char edi commit SHA in a root `.edi-version` file. Both
  `maven-test.yml` and `e2e.yml` read it, `git init` + `fetch --depth 1 origin <sha>` +
  `checkout FETCH_HEAD` a throwaway clone, and `mvn install -DskipTests` the four modules into the
  runner's `~/.m2` — on **every backend-relevant CI run, in both workflows**.
- Locally, `bin/sync-edi.sh` does the same into the developer's shared `~/.m2`.
- `edi-pin-bump.yml` opens (and auto-merges when green) a pin-bump PR whenever edi master moves.

What it costs — the baseline any option must beat:

- **Shared `~/.m2` cross-contamination.** Everything is `1.0.0-SNAPSHOT`, and `~/.m2` is shared
  across worktrees and terminals: whichever edi checkout ran `mvn install` last defines every mono
  build on the machine. `sync-edi.sh`'s own header says this makes recovery one command but does
  **not** make `~/.m2` safe — "that needs a published, versioned artifact." This is the direct
  cause of mid-session "compile error naming an edi class that isn't yours" incidents.
- **CI rebuild tax.** Two mono workflows each rebuild edi from source per run (JDK setup + 4-module
  Maven build). It works, but it is repeated compute, another network dependency (a git fetch of a
  second repo), and extra YAML that must stay in sync (the `.edi-version` entry in
  `maven-test.yml`'s relevance filter is load-bearing — dropping it rubber-stamps pin bumps).
- **No artifact immutability.** The "artifact" is whatever a given machine last built; nothing ties
  bytes in `~/.m2` to the pinned SHA. Reproducibility rests on everyone remembering to re-run
  `sync-edi.sh`.
- One real upside to preserve: the pin makes mono commits reproducible and makes absorbing a
  breaking edi change a deliberate mono commit. Any published-artifact option keeps that property —
  the pin just becomes a version number in mono's `pom.xml` instead of a SHA in a text file.

**Caveat for "just delete the fetch-and-build steps":** `maven-test.yml`'s *edi API reference
drift check* greps the **source clone** (`$RUNNER_TEMP/edi/*/src/main/java`), not the jar. Any
option that removes the Maven build still needs a shallow clone (cheap, no `mvn install`) for that
step, or the check moves into this repo.

## 2. Option A — GitHub Packages (Maven registry)

### Publishing

Minimal setup on the edi side:

```xml
<distributionManagement>
  <repository>
    <id>github</id>
    <url>https://maven.pkg.github.com/FastChickensHR/edi</url>
  </repository>
</distributionManagement>
```

A tag-triggered GitHub Actions workflow runs `mvn -B deploy` using the workflow's own
`GITHUB_TOKEN` (job permission `packages: write`) — no PAT, no GPG, no external account.
Docs: [Working with the Apache Maven registry](https://docs.github.com/en/packages/working-with-a-github-packages-registry/working-with-the-apache-maven-registry).

- **`-SNAPSHOT` publishing: supported.** The docs state GitHub Packages supports Maven `SNAPSHOT`
  versions (consumers enable `<snapshots>` on the repository entry). A master-push workflow could
  keep publishing `1.0.0-SNAPSHOT` builds alongside tagged betas.
- Versions are **deletable** by admins (public packages only become undeletable past 5,000
  downloads), so immutability is policy, not enforcement — still far better than `~/.m2`.

### The consumer story — the critical fact

**GitHub Packages requires authentication to download even PUBLIC Maven packages.** The docs are
explicit: "You need an access token to publish, install, and delete private, internal, and public
packages" ([docs.github.com](https://docs.github.com/en/packages/working-with-a-github-packages-registry/working-with-the-apache-maven-registry)).
Long-standing community threads confirm no anonymous Maven access exists and that only the
container registry (ghcr.io) allows anonymous pulls of public images
([discussion #38939](https://github.com/orgs/community/discussions/38939),
[discussion #172571](https://github.com/orgs/community/discussions/172571)).

Concretely:

- **mono CI**: add the `https://maven.pkg.github.com/FastChickensHR/edi` repository to the build
  and a `<server>` credential. The workflow's `GITHUB_TOKEN` can read packages in the same
  org/repo scope (grant the mono repo access to the package, or use an org secret holding a PAT
  with `read:packages`). Either way it is a settings.xml/`GITHUB_TOKEN` wiring exercise, not a
  blocker — mono is private and already token-authenticated to GitHub everywhere.
- **Local dev**: every developer needs a classic PAT with `read:packages` in
  `~/.m2/settings.xml`. One-time per machine, but it is a new secret to provision and rotate.
- **Anonymous open-source consumers**: cannot resolve the artifact at all. `mvn` fails with 401
  unless they mint a GitHub PAT and wire it into their settings.xml. For a public MIT "open-source
  toolkit," this is a genuinely bad third-party story — GitHub Packages is effectively a
  *private-ecosystem* registry that happens to host public bits.

### What changes in mono

- Coordinates **unchanged** (`com.fastchickenshr:core/x834/x999/flatfile`).
- `.edi-version` (SHA) → a pinned release version in `pom.xml`; `edi-pin-bump.yml` becomes a
  version-property bump; `bin/sync-edi.sh` and both fetch-and-build CI steps are deleted
  (keep a shallow source clone for the drift check, per §1).
- New: repository + server auth in mono's build and a PAT per developer.

## 3. Option B — Maven Central (Central Portal, central.sonatype.com)

### Current (2025/2026) process

OSSRH was sunset June 30, 2025; publishing now goes through the **Central Publisher Portal**
([Central Portal guide](https://central.sonatype.org/publish/publish-portal-guide/)).

1. **Account + namespace.** Register at central.sonatype.com. For the existing groupId
   `com.fastchickenshr`, verify domain ownership of `fastchickenshr.com` via a DNS TXT record
   ([Register a namespace](https://central.sonatype.org/register/namespace/)) — the org already
   uses `contact@fastchickenshr.com`, so the domain appears to be owned; this keeps mono's
   coordinates **byte-for-byte unchanged**. Fallback: `io.github.<username>` is auto-verified when
   signing up with GitHub (org-form `io.github.FastChickensHR` is *not* automatic — it's verified
   via a temporary public repo named with the verification key) — but that changes the groupId, so
   DNS verification of `com.fastchickenshr` is the path that matters here.
2. **POM requirements** ([Central requirements](https://central.sonatype.org/publish/requirements/)):
   name, description, **url**, **license**, **developers**, **scm** blocks; a `-sources.jar` and
   `-javadoc.jar` for every module jar; MD5/SHA1 checksums; **GPG `.asc` signatures on every
   file**. Today's parent pom has name + description only — no url/license/developers/scm, no
   source/javadoc/gpg plugins. That is the bulk of the one-time work: pom metadata + three plugins
   (`maven-source-plugin`, `maven-javadoc-plugin`, `maven-gpg-plugin`) + a GPG key pair stored as
   Actions secrets.
3. **Publish** with `org.sonatype.central:central-publishing-maven-plugin`
   ([Publishing via the Maven plugin](https://central.sonatype.org/publish/publish-portal-maven/)):
   a portal token (user token) in CI secrets, `mvn deploy`, and either manual "Publish" in the
   portal UI or `autoPublish=true`. Tag-triggered Actions workflow, same shape as Option A plus
   the GPG step.
- **`-SNAPSHOT` publishing: supported since 2025-01-14** via the portal
  ([announcement](https://central.sonatype.org/news/20250114_snapshot_publishing_via_portal/),
  [Portal snapshots](https://central.sonatype.org/publish/publish-portal-snapshots/)) — requires
  plugin ≥ 0.7.0, snapshots land at `https://central.sonatype.com/repository/maven-snapshots/`,
  and consumers must add that repository explicitly (snapshots are *not* on repo1/maven central
  proper). So SNAPSHOT flow exists but reintroduces a settings/pom repository entry on the
  consumer side — tagged betas are the natural fit here.

### Constraints

- **Releases are immutable — no deletes, no re-uploads.** Every published beta exists forever.
  Compatible with either #205 scheme (`0.x` and `1.0.0-beta.N` are both valid non-SNAPSHOT
  versions), but a botched publish can only be superseded, never removed.
- Validation is strict (all requirements above enforced at upload), and there's a short
  human-noticeable delay before artifacts appear on search/mirrors.

### Effort estimate

Highest one-time cost of the three registries: portal account + DNS TXT verification + GPG key
management + pom overhaul + publish plugin ≈ a focused day, most of it pom metadata that is good
hygiene anyway. Recurring cost ≈ zero (tag → workflow → publish).

### What changes in mono

The best consumer story of all options: coordinates unchanged, **no settings.xml, no
credentials, nothing** — Maven resolves Central by default. Delete both fetch-and-build steps,
`bin/sync-edi.sh`, and the SHA pin (→ version pin). Anonymous open-source consumers get the
normal `<dependency>` experience.

## 4. Option C — JitPack

### The zero-setup story

Nothing to publish: consumers add `https://jitpack.io` as a repository and JitPack builds the repo
**on demand from a git tag** (or branch/commit) the first time anyone requests it
([docs](https://docs.jitpack.io/building/), [multi-module example](https://github.com/jitpack/maven-modular)).

### The coordinate change

For a multi-module project the groupId becomes **`com.github.FastChickensHR.edi`** with the module
name as artifactId (e.g. `com.github.FastChickensHR.edi:x834:<tag>`); JitPack also publishes an
aggregate artifact of all modules. mono would edit every edi dependency's groupId, and the
library's public identity forks from `com.fastchickenshr` — a migration cost you pay *again* if
you later move to Central.

### Java 23 — a real risk

JitPack's own docs still say builds default to **OpenJDK 8**, with `jitpack.yml` selecting a jdk
(`jdk: - openjdk11`-style) and an SDKMAN `before_install` escape hatch for anything newer
([BUILDING.md](https://github.com/jitpack/jitpack.io/blob/master/BUILDING.md)). Java 21 was only
ever confirmed by community trial-and-error (`openjdk21` reported working in
[jitpack/jitpack.io#6479](https://github.com/jitpack/jitpack.io/issues/6479), which was closed by
a stale-bot, not by a fix). **Java 23 support is undocumented**; this repo would rely on either
`jdk: - openjdk23` happening to exist on their images or an SDKMAN install per build. Must be
proven with a throwaway tag before this option is even viable.

### Reliability caveats

- First resolution of a new tag blocks on a live remote build (minutes; failures surface as
  opaque 401/timeout errors on the consumer side).
- Third-party hosted service with no SLA; periodic outages and slow builds are a recurring theme
  on their tracker. Artifacts are cached but the cache is not a contractual immutability
  guarantee, and rebuild-on-demand means a build-environment drift can change what a tag yields.
- `-SNAPSHOT`-ish flow exists (`master-SNAPSHOT`, commit SHAs) but inherits all of the above.

### What changes in mono

Delete the fetch-and-build steps, add the jitpack.io repository (no credentials), **change every
edi groupId**, pin a tag. CI now depends on a third party's build farm being up and building
Java 23 correctly.

## 5. Side-by-side

| | Status quo | GitHub Packages | Maven Central (Portal) | JitPack |
|---|---|---|---|---|
| One-time setup | — | ~1–2 h (distMgmt + tag workflow) | ~1 day (namespace DNS, GPG, pom metadata, plugins) | ~0 (tag + maybe jitpack.yml) |
| Release automation | edi-pin-bump PR bot | tag → Actions `mvn deploy` (GITHUB_TOKEN) | tag → Actions `mvn deploy` (portal token + GPG) | git tag *is* the release |
| `-SNAPSHOT` publishing | it's all snapshots (the problem) | yes | yes (since 2025-01, separate snapshots repo) | branch-SNAPSHOT pseudo-versions |
| Coordinates in mono | `com.fastchickenshr:*` | unchanged | unchanged | **changes** to `com.github.FastChickensHR.edi:*` |
| mono CI | keeps fetch-and-build ×2 | delete build steps; add repo + token | delete build steps; nothing added | delete build steps; add repo |
| Local dev | `sync-edi.sh` + `~/.m2` roulette | PAT (`read:packages`) per developer | nothing | nothing |
| Anonymous OSS consumer | clone & build | **impossible** (auth required even for public) | perfect | works (with repo entry) |
| Immutability | none | deletable by admins | **immutable, forever** | cache, not a guarantee |
| Java 23 | fine (we build it) | fine | fine | **undocumented, unproven** |
| Kills `~/.m2` contamination | no | yes | yes | yes |

## 6. Recommendation (research recommends; #209 decides)

**Publish tagged betas to GitHub Packages now; treat Maven Central as the destination for the
first public/stable release — and do the Central-grade pom metadata work as part of the beta
regardless.**

Rationale:

- The pains #849 documented — `~/.m2` cross-contamination, per-run CI rebuilds, no artifact
  identity — are solved by *any* real registry. GitHub Packages solves them for an afternoon's
  work with no new accounts, no GPG, no coordinate change, and `GITHUB_TOKEN`-native publishing.
- GitHub Packages' auth-for-public-downloads flaw is real but bites *third parties*, and today
  the sole consumer is mono — a private, already-token-authenticated repo. For a beta whose
  audience is ourselves, the flaw is a paper cut (one PAT per dev machine), not a blocker.
- Maven Central is unambiguously the right end state for a public MIT library (anonymous
  consumption, immutability, unchanged `com.fastchickenshr` coordinates), but it front-loads
  namespace DNS verification, GPG custody, and strict validation onto a beta whose version scheme
  (#205) isn't even settled — and its immutability means every experimental beta lives forever.
  Better to arrive there once the artifact and versioning are worth committing to.
- JitPack is not recommended: Java 23 support is undocumented (docs still default to JDK 8), the
  coordinates fork to `com.github.…` and would need migrating *again*, and it swaps a
  deterministic in-house build for a third-party on-demand build farm.
- Sequencing bonus: the pom metadata/sources/javadoc work required for Central is harmless under
  GitHub Packages, so doing it during the beta makes the eventual Central cutover a
  plugin-and-credentials change only.

## 7. Open questions for #209

1. **Does the beta need anonymous third-party consumption?** If yes, GitHub Packages is
   disqualified and Central (or interim JitPack) moves up despite the cost.
2. **Domain control**: can we place a DNS TXT record on `fastchickenshr.com` to verify the
   `com.fastchickenshr` namespace on the Central Portal? (If not, the Central path forces a
   groupId change — a much bigger decision.)
3. **Version scheme dependency (#205)**: `0.x` vs `1.0.0-beta.N` doesn't change registry choice,
   but Central's immutability raises the bar on whichever is chosen; and does master keep
   publishing `-SNAPSHOT`s (supported on both registries) or do we go tags-only?
4. **mono migration mechanics**: `.edi-version` + `edi-pin-bump.yml` become a version bump in
   mono's pom — same automation, new target. Who reworks the bump bot, and does the pin's
   "relevance filter" guarantee carry over?
5. **The drift check**: mono's edi API reference check greps edi *sources*. Keep a shallow clone
   in mono CI just for it, or move the check into this repo's CI?
6. **Credential custody**: org-level PAT/secret strategy for GitHub Packages reads (per-dev PATs
   vs a shared org secret), and — for Central later — who holds the GPG key and portal token.
