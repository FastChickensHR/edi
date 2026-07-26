# 0203 — Public API surface inventory (beta-release audit)

Asset for [#203 Inventory the public API surface per module](https://github.com/FastChickensHR/edi/issues/203), part of map [#202 Beta-release audit](https://github.com/FastChickensHR/edi/issues/202). Surveyed 2026-07-26 at `f5e2475` against mono `master`.

**Method.** Source-level enumeration of every top-level type in each module's `src/main/java` (Appendix A holds the full listing); mono usage from `import com.fastChickensHR.edi.*` across mono's `src` (main + test); classification from reading javadoc, reference graphs (`src/main` referencing files per type), and git history.

## Headline numbers

| Module | Java files | Public top-level types | Package-private | Distinct types mono imports |
|---|---|---|---|---|
| core | 8 | 8 | 0 | 7 |
| x834 | 149 | 141 | 7 | 20 |
| x999 | 2 | 2 | 0 | 2 |
| flatfile | 5 | 3 | 1 (+1 placeholder pkg) | 3 |
| **total** | **164** | **154** | **8** | **32** |

Mono names only **32 of 154** public types. The other 122 split into supporting types a caller touches indirectly, deliberately published-but-unused surface (`x834.spec`), and a large accidentally-public tail (details below).

## Cross-cutting findings

1. **Mono squats in edi's package namespace.** Mono's own source tree declares 5 files under `com.fastChickensHR.edi.x834.converters` (`EnrollmentContext`, `EmployeeToMemberConverter`, `DependentToMemberConverter`, `CoverageToHealthCoverageConverter`, plus a `package-info`). They are mono-domain→edi-model adapters that sensibly live in mono, but the split package breaks the artifact boundary (JPMS-hostile, and package-private access would cross artifacts). Rename target for the execution backlog.
2. **No visibility machinery exists yet.** No `module-info.java`, no `@ApiStatus`-style annotations, no javadoc exclusions anywhere; exactly one `package-info.java` in x834 (`spec/`) and one in flatfile (`fixedwidth/`). Public-vs-internal intent lives only in prose javadoc — every one of the 154 types is equally reachable.
3. **A hiding precedent already exists.** x834's seven envelope segments (`ISA/GS/BGN/ST/SE/GE/IEA` in `segments/`) and flatfile's `LinkedRows` are already package-private — the pattern the curation tickets extend.

---

## core — 8 types, all intended public, clean

The format-neutral kernel: a value tree plus two one-method seams. This is the pivot every other module implements or produces.

| Type | Kind | Role | Mono use |
|---|---|---|---|
| `FileContent` | record | The fully-resolved file: `direction` + file-level `fields` + one `Record` per subject | ✅ |
| `Record` | record | Ordered fields + child records (`RecordLevel.RECORD`/`SUBRECORD`) | ✅ |
| `Field` | record | `Location` + value; `isOmitted()`/`valueIfPresent()` | ✅ |
| `Location` | record | `RecordLevel` + dotted name — the vocabulary key | ✅ |
| `RecordLevel` | enum | `FILE`/`RECORD`/`SUBRECORD` | ✅ |
| `Direction` | enum | `INBOUND`/`OUTBOUND` | ✅ |
| `FileGenerator` | interface | `String generate(FileContent)` | ✅ |
| `FileParser` | interface | `FileContent parse(String raw)` | (implemented via `X999FileParser`/`DelimitedFileParser`) |

**Verdict: the entire module is entry-point surface.** Nothing to hide; this is the contract to lock hardest.

## x999 — 2 types, both used, clean

| Type | Role | Mono use |
|---|---|---|
| `X999` | `final` constants holder: the `Location` names a 999's `FileContent` uses, plus ack-status code constants (`A`/`E`/`P`/`R`…) | ✅ |
| `X999FileParser` | `final`, implements `core.FileParser` | ✅ |

**Verdict: entire module is entry-point surface.** Parse-only by design (999s are received, not generated — generation half is post-beta roadmap).

## flatfile — 3 public types + a designed-for-not-built placeholder

| Type | Role | Mono use |
|---|---|---|
| `delimited.DelimitedFormat` (+ nested `Builder`) | Format config; `csv()` factory | ✅ |
| `delimited.DelimitedFileGenerator` | `final`, implements `core.FileGenerator` | ✅ |
| `delimited.DelimitedFileParser` | `final`, implements `core.FileParser` | ✅ |
| `delimited.LinkedRows` | *already package-private* — internal row linking | — |
| `fixedwidth` (package-info only) | Reserved sibling variant; "designed-for, not built… graduates when a real fixed-width feed + fixture appears" (v1 shape settled in edi #136) | — |

**Verdict: delimited surface is entry-point and already well-encapsulated.** Open beta question: does an empty `fixedwidth` package ship in a locked-API release, or does the placeholder package-info move to docs until #136 graduates?

---

## x834 — 141 public types; the whole curation problem lives here

### Headline: two generations of API coexist, and both are live

| | Gen 1 — "typed builder" API | Gen 2 — "domain model + writer + render keys" |
|---|---|---|
| Entry point | `X834Document.Builder`, `Header.Builder`, `Trailer.Builder`, per-segment `*.builder()` | `X834FileGenerator` (implements `core.FileGenerator`) driven by `X834Location` string keys over `core.FileContent` |
| Member representation | segment subclasses (`MemberName`, `MemberDemographics`, …) built one at a time | `Member`/`BaseMember`/`DependentMember` + value objects (`Address`, `Income`, `Language`, `Disability`, `Provider`, `CoordinationOfBenefits`, `ReportingCategory`, `HealthInformation`, `MemberCommunication`) |
| Rendering | caller assembles segments | `X834MemberWriter.toSegments(Member)` |
| Spec/conformance | none | `spec/` (`X834Spec`, `ElementSpec`, `ElementPosition`, `CharacterClass`) checked at render in `X834Document.generateDocument()` |

They are **not** parallel stacks — Gen 2 is layered *on top of* Gen 1 and funnels into it: `X834FileGenerator.generate()` builds an `X834Context`, `Header.Builder`, `Trailer.Builder`, `Member`s and `RefSegment`/`HealthCoverage` segments, then calls `document.build().generateDocument()`. So Gen 1's `X834Document`/`Header`/`Trailer` are load-bearing for both.

What *has* been superseded is the **Loop 2000 hand-assembly path**. Every one of the 27 `*Segment`-extending loop classes has **exactly one referencing file in main: `X834MemberWriter`** (or `X834FileGenerator` for `HealthCoverageDates`). Consumers now set fields on `BaseMember`/value objects and the writer builds those segments. The classes remain public but are, in practice, the writer's private construction kit.

The #197/#198 "render keys + spec" commits (`ed072f9`, `f40d43d`, `f5e2475`) are the completion of Gen 2: each new loop got (a) `X834Location` string constants + indexed accessors (`disability(i, field)`, `category(i, field)`, `hd(i, field)`) and (b) `X834Spec` entries. `X834Location` now carries **100 `public static final String` keys** plus 3 index helpers. Notably `RENDERED_ARITY` (the map asserting spec covers every emitted ordinal) lives only in the test `X834SpecTest` — not part of the shipped surface.

### Per-package classification

**`x834` root (4): all ENTRY-POINT.** `X834Document`, `X834Context`, `GenerationResult` (sealed interface + `Success`/`Failure` records), `GenerationError` (record + nested `Phase` enum). The document facade and its single failure-reporting channel: `generateDocument()` assembles header → members (via `X834MemberWriter`) → additional segments → trailer, computes SE01/GE01/IEA01, then runs two accumulate-don't-throw passes (`delimiterViolations`, `specViolations`). Coupling: `X834Document.Builder.addSegment(Segment)` and `withHeader/withTrailer` put `segments.Segment`, `header.Header`, `trailer.Trailer` permanently in the public surface; `X834Context.get/setCharacterClass()` does the same for `spec.CharacterClass`.

**`constants` (4): SUPPORTING.** `ElementSeparator` (mono-imported), `SubElementSeparator`, `SegmentTerminator`, `LineTerminator` — all four are `X834Context` setter parameter types. Wrinkle: `X834Context` has both a Lombok `getElementSeparator()` (enum) and a hand-written `getElementSeparator()` returning `char`; only the `char` overload survives, so the enum field is write-only from outside.

**`data` (25): SUPPORTING as a family; envelope-only subset INTERNAL-LOOKING.** All one shape: `public enum X implements EdiCodeEnum` with `getCode()`/`getDescription()`, a static `EdiEnumLookup`, and `fromString(String)`. The Gen-2 value objects take them as parameters (`Income`←`FrequencyCode`, `HealthInformation`←`HealthRelatedCode`, `Language`←`IdentificationCodeQualifier`, `Disability`←`DisabilityTypeCode`, `CoordinationOfBenefits`←`CoordinationOfBenefitsCode`+`PayerResponsibilitySequenceCode`, `MemberCommunication`←`CommunicationNumberQualifier`, `Provider`←`ActionCode`+`MaintenanceReasonCode`+`IdentificationCodeQualifier`), so a consumer names those by necessity. But 13 envelope-only enums (`AuthorizationInformationQualifier`, `SecurityInformationQualifier`, `SecurityLevelCode`, `InterchangeIdQualifier`, `InterchangeUsageIndicator`, `InterchangeControlVersionNumber`, `FunctionalIdentifierCode`, `ResponsibleAgencyCode`, `VersionCode`, `TransactionSetIdentifierCode`, `TransactionSetPurposeCode`, `TransactionTypeCode`, `TimeCode`) are referenced only from envelope segments, `header/*`, and `X834Spec` — the library fixes their values; a consumer never picks one.

**`dates` (3).** `DateFormat`/`TimeFormat` SUPPORTING (`X834Context` setters). `DateFormatter` INTERNAL-LOOKING (static helper behind `X834Context.formatDate/formatTime`; also missing `final` + private ctor). `X834Spec`'s javadoc flags `DateFormat` as a spec gap (carries pattern per code but no publishable description).

**`exception` (1): ENTRY-POINT, but see failure-channels question.** `ValidationException` (checked) — mono-imported; thrown by every `*.build()`, `validate()`, and `X834MemberWriter.toSegments`. In tension with `GenerationResult`: the facade converts it to `GenerationError`, but the builder-level API still throws.

**`generate` (2): ENTRY-POINT — the Gen-2 kernel seam.** `X834FileGenerator` and `X834Location` (100 string keys + 3 index helpers), both mono-imported, the only package with a "vocabulary contract" framing in its javadoc. Leak: `generate(FileContent)` returns `String` and on failure flattens `GenerationError.formatted()` into an `IllegalStateException` message — the structured channel is discarded at this seam by design (comment cites #123).

**`header` (7) / `loop1000A`/`B`/`C` (3) / `trailer` (4).** `Header` and `Trailer` ENTRY-POINT (mono-imported; note `X834Document` takes `Trailer.Builder`, not `Trailer`, to inject SE01 at render time). The 6 header segment subclasses, `SponsorName`, `Payer`, and the 3 trailer subclasses are **SUPPORTING-by-escape-hatch**: public solely because `Header.Builder`/`Trailer.Builder` expose 11 `set*/with*Builder(...)` setters no test or consumer in either repo exercises. **`loop1000C.TPA` is orphaned** — zero `src/main` references; `Header.generateSegments()` never emits Loop 1000C (fixed in `5cdcb45` yet still unreachable).

**`loop2000` (11).** ENTRY-POINT: `Member`, `BaseMember`, `DependentMember` (all mono-imported; "pure domain object" javadoc). SUPPORTING: `Address`, `AddressType`. INTERNAL-LOOKING: `X834MemberWriter` (constructed only inside `X834Document`; public `MAX_PROVIDERS`/`MAX_COORDINATION_OF_BENEFITS` are `{@value}`-linked from `BaseMember` javadoc) plus 5 segment shims (`INSSegment`, `MemberLevelDates`, `MemberIdentificationNumber`, `MemberPolicyNumber`, `SubscriberNumber`). Warts: `BaseMember.getAddress` returns an unimported fully-qualified `Optional`; `BaseMember` carries `List<Segment> additionalSegments` + `addSegment(Segment)` — a second `segments.Segment` leak.

**`loop2000/data` (19).** ENTRY-POINT (mono-confirmed): `MaintenanceTypeCode`, `InsuranceLineCode`, `CoverageLevelCode`, `MemberIndicator`, `IndividualRelationshipCode` — used as a code dictionary (`fromString(...).getCode()`) feeding `HealthCoverage.Builder`'s *String* setters. SUPPORTING: `MaintenanceReasonCode`, `EmploymentStatusCode`, `HealthCoverageDateQualifier`. INTERNAL-LOOKING: `BenefitStatusCode`, `COBRAQualifyingEventCode`, `ConfidentialityCode`, `HandicapIndicator`, `MedicarePlanCode`, `StudentStatusCode`, `MemberDateQualifier`, `GenderCode` (only `X834Spec` — `BaseMember.gender` is a raw `String`!). **Orphaned: `AmountQualifierCode`, `HealthCoverageReferenceQualifier`, `IdentificationCardTypeCode`.**

**Loop sub-packages 2100A/2100C/2200/2310/2320/2700 (25).** Pattern per loop: one SUPPORTING value object (`Income`, `Language`, `HealthInformation`, `MemberCommunication`, `Disability`, `Provider`, `CoordinationOfBenefits`, `ReportingCategory` — all `BaseMember` field/collection types) + INTERNAL-LOOKING segment shims (one referencing file each: `X834MemberWriter`). The clearest pairwise old/new duplication: `Language`↔`MemberLanguage`, `Income`↔`MemberIncome`, `HealthInformation`↔`MemberHealthInformation`, `MemberCommunication`↔`MemberCommunicationsNumbers`. Per `f5e2475`, the 2700 LS/LX/LE wrapper is deliberately *not* addressable — the writer owns it.

**`loop2000/loop3000` (2).** `HealthCoverage extends HDSegment` — ENTRY-POINT (mono-imported) and **the single worst coupling in the module**: every setter mono calls is inherited from `segments.HDSegment.AbstractBuilder`, so `HDSegment` cannot be hidden without breaking the known consumer. Package name is a misnomer — the class is Loop **2300** per `HDSegment` javadoc and `X834Spec`. `HealthCoverageDates` INTERNAL-LOOKING.

**`segments` (28).** `Segment` (abstract base, SUPPORTING — unavoidable: `addSegment`, `generateSegments()`, `toSegments` all traffic in it) + 27 per-X12-segment classes. Seven envelope segments already package-private (`ISA/GS/BGN/ST/SE/GE/IEA`). `RefSegment` SUPPORTING (custom `ref.<qual>` extensions); `HDSegment` de-facto SUPPORTING (via `HealthCoverage`). The remaining abstract bases are INTERNAL-LOOKING — subclassed only inside the module. **Orphaned: `AMTSegment`, `IDCSegment`** (`X834Spec` javadoc: "segments the generator never emits… which no writer produces at all"). Accident: `Segment.getContext()` is `protected` but Lombok `@Setter` generates a **public** `setContext(X834Context)` — only `X834Document`'s render loop calls it.

**`spec` (8): ENTRY-POINT by intent, unexercised in practice.** The only package with a `package-info.java` intent statement — explicitly consumer-facing ("published so a consumer can narrow a code list, render a pick list, or check a length without transcribing any of the standard"; boundary: "Nothing here reaches `EdiEnumLookup`, whose job is the opposite one"). `X834Spec` (static `at(...)`/`atSegment(...)`/`all()`/`positions()`), `ElementSpec`, `ElementPosition`, `CodeValue`, `SubsetResult` records, `DataType`/`CharacterClass` enums. **Mono does not reference `spec.*` at all.** Coupling: `X834Spec` imports 38 code enums cross-package to project their lists; `CharacterClass` sits in two seams at once (spec publication and `X834Context` configuration).

**`util` (3).** `EdiCodeEnum` ENTRY-POINT (mono-imported — generic access to code enums). `EdiEnumLookup` INTERNAL-LOOKING but structurally trapped: instantiated in `static {}` blocks of all 44 code enums, so it must stay public (or the enums gain a package-private factory); its javadoc is maintainer prose about lossy normalization tiers. `TextUtils` INTERNAL-LOOKING (ISA fixed-width padding).

### Hide-before-beta candidates, by confidence tier

- **Tier 1 — dead code (0 `src/main` references; tests only): 6 types.** `loop1000C.TPA`, `segments.AMTSegment`, `segments.IDCSegment`, `loop2000.data.AmountQualifierCode`, `loop2000.data.HealthCoverageReferenceQualifier`, `loop2000.data.IdentificationCardTypeCode`. Delete or package-private.
- **Tier 2 — Gen-1 loop segment shims: 23 types**, one referencing file each. None appear in any public signature (`toSegments` returns `List<Segment>`) — cleanly hideable.
- **Tier 3 — rendering plumbing: 5 items.** `X834MemberWriter` (inline the `{@value}` links first), `Segment.setContext` visibility, `DateFormatter`, `TextUtils`, `EdiEnumLookup` (hideable only via enum-factory rework; at minimum mark internal).
- **Tier 4 — abstract segment bases (~24): hideable only with Tier 2**, because cross-package `protected` inheritance forces the base public. `HDSegment`, `RefSegment`, `Segment` must stay.
- **Tier 5 — envelope-only + INS-only code enums (21): blocked on `X834Spec`'s cross-package code-list projection** — hiding them requires the projection to move or a package-private `codes()` hook.
- **Tier 6 — escape-hatch-only types (11):** decide whether `Header.Builder`/`Trailer.Builder`'s 11 sub-builder setters are a beta commitment; if not, 11 more types go internal.

### Open questions for the x834 curation ticket

1. **Is `spec/` shipping in beta?** Most deliberately designed seam, zero consumers. Publishing commits to `at("2000 INS08")` position spellings, `ElementSpec`'s shape, and `CharacterClass` membership.
2. **Are the Gen-1 escape hatches (11 sub-builder setters on `Header.Builder`/`Trailer.Builder`) supported or leftover?** They alone keep 11 types public; nothing in either repo exercises them.
3. **`HealthCoverage`/`HDSegment`:** convert `HealthCoverage` to a Gen-2 value object (like `Disability`) so `HDSegment` can hide? Breaks mono's `CoverageToHealthCoverageConverter`; `X834Location` already publishes `hd.*` keys covering HD01/03/04/05.
4. **Two failure channels:** `GenerationResult` is documented as "the single failure-reporting channel", yet `ValidationException` is thrown by every builder `build()` and `X834FileGenerator` flattens both into `IllegalStateException`. Which is the beta contract?
5. **`loop3000` package name vs Loop 2300** — rename before lock, or frozen by the consumer import?
6. **`BaseMember.gender` is a raw `String`** while `GenderCode` exists unwired — intended?
7. **`X834Location` contract:** are the literal string values (`"hd.benefitBeginDate"`) the contract, or only the constant names? Consumers building `FileContent` from config will depend on the literals.
8. **`README.md` is stale** — shows `Optional<String>` from `generateDocument()`, which returns `GenerationResult` since `1549794`. First doc anyone reads; fix before beta.
9. **`AddressType.WORK`/`BILLING`** are accepted but silently not serialized — publish, document, or drop?
10. **Public limit constants** (`X834MemberWriter.MAX_*`, `PERSegment.MAX_COMMUNICATION_PAIRS`): consumer-facing limits (belonging on the domain type or in `spec/`) or writer internals?

---

## Appendix A — full type listing

Format: `package | Type | kind | visibility`. Source: `src/main/java` at `f5e2475`; `package-info.java` files excluded.

```

### core
core                         Direction                                     enum       public
core                         Field                                         record     public
core                         FileContent                                   record     public
core                         FileGenerator                                 interface  public
core                         FileParser                                    interface  public
core                         Location                                      record     public
core                         Record                                        record     public
core                         RecordLevel                                   enum       public

### x834
x834.constants               ElementSeparator                              enum       public
x834.constants               LineTerminator                                enum       public
x834.constants               SegmentTerminator                             enum       public
x834.constants               SubElementSeparator                           enum       public
x834.data                    AcknowledgmentRequested                       enum       public
x834.data                    ActionCode                                    enum       public
x834.data                    AuthorizationInformationQualifier             enum       public
x834.data                    CommunicationNumberQualifier                  enum       public
x834.data                    CoordinationOfBenefitsCode                    enum       public
x834.data                    DateTimeQualifier                             enum       public
x834.data                    DisabilityTypeCode                            enum       public
x834.data                    EntityIdentifierCode                          enum       public
x834.data                    FrequencyCode                                 enum       public
x834.data                    FunctionalIdentifierCode                      enum       public
x834.data                    HealthRelatedCode                             enum       public
x834.data                    IdentificationCodeQualifier                   enum       public
x834.data                    InterchangeControlVersionNumber               enum       public
x834.data                    InterchangeIdQualifier                        enum       public
x834.data                    InterchangeUsageIndicator                     enum       public
x834.data                    PayerResponsibilitySequenceCode               enum       public
x834.data                    ReferenceIdentificationQualifier              enum       public
x834.data                    ResponsibleAgencyCode                         enum       public
x834.data                    SecurityInformationQualifier                  enum       public
x834.data                    SecurityLevelCode                             enum       public
x834.data                    TimeCode                                      enum       public
x834.data                    TransactionSetIdentifierCode                  enum       public
x834.data                    TransactionSetPurposeCode                     enum       public
x834.data                    TransactionTypeCode                           enum       public
x834.data                    VersionCode                                   enum       public
x834.dates                   DateFormat                                    enum       public
x834.dates                   DateFormatter                                 class      public
x834.dates                   TimeFormat                                    enum       public
x834.exception               ValidationException                           class      public
x834.generate                X834FileGenerator                             class      public
x834.generate                X834Location                                  class      public
x834                         GenerationError                               record     public
x834                         GenerationResult                              interface  public
x834.header                  BeginningSegment                              class      public
x834.header                  FileEffectiveDate                             class      public
x834.header                  FunctionalGroupHeader                         class      public
x834.header                  Header                                        class      public
x834.header                  InterchangeControlHeader                      class      public
x834.header                  TransactionSetHeader                          class      public
x834.header                  TransactionSetPolicyNumber                    class      public
x834.loop1000A               SponsorName                                   class      public
x834.loop1000B               Payer                                         class      public
x834.loop1000C               TPA                                           class      public
x834.loop2000                Address                                       class      public
x834.loop2000                AddressType                                   enum       public
x834.loop2000                BaseMember                                    class      public
x834.loop2000.data           AmountQualifierCode                           enum       public
x834.loop2000.data           BenefitStatusCode                             enum       public
x834.loop2000.data           COBRAQualifyingEventCode                      enum       public
x834.loop2000.data           ConfidentialityCode                           enum       public
x834.loop2000.data           CoverageLevelCode                             enum       public
x834.loop2000.data           EmploymentStatusCode                          enum       public
x834.loop2000.data           GenderCode                                    enum       public
x834.loop2000.data           HandicapIndicator                             enum       public
x834.loop2000.data           HealthCoverageDateQualifier                   enum       public
x834.loop2000.data           HealthCoverageReferenceQualifier              enum       public
x834.loop2000.data           IdentificationCardTypeCode                    enum       public
x834.loop2000.data           IndividualRelationshipCode                    enum       public
x834.loop2000.data           InsuranceLineCode                             enum       public
x834.loop2000.data           MaintenanceReasonCode                         enum       public
x834.loop2000.data           MaintenanceTypeCode                           enum       public
x834.loop2000.data           MedicarePlanCode                              enum       public
x834.loop2000.data           MemberDateQualifier                           enum       public
x834.loop2000.data           MemberIndicator                               enum       public
x834.loop2000.data           StudentStatusCode                             enum       public
x834.loop2000                DependentMember                               class      public
x834.loop2000                INSSegment                                    class      public
x834.loop2000.loop2100A      HealthInformation                             class      public
x834.loop2000.loop2100A      Income                                        class      public
x834.loop2000.loop2100A      Language                                      class      public
x834.loop2000.loop2100A      MemberCommunication                           class      public
x834.loop2000.loop2100A      MemberCommunicationsNumbers                   class      public
x834.loop2000.loop2100A      MemberDemographics                            class      public
x834.loop2000.loop2100A      MemberHealthInformation                       class      public
x834.loop2000.loop2100A      MemberIncome                                  class      public
x834.loop2000.loop2100A      MemberLanguage                                class      public
x834.loop2000.loop2100A      MemberName                                    class      public
x834.loop2000.loop2100A      MemberResidenceCityStateZipCode               class      public
x834.loop2000.loop2100A      MemberResidenceStreetAddress                  class      public
x834.loop2000.loop2100C      MemberMailingAddress                          class      public
x834.loop2000.loop2100C      MemberMailingCityStateZipCode                 class      public
x834.loop2000.loop2100C      MemberMailingStreetAddress                    class      public
x834.loop2000.loop2200       Disability                                    class      public
x834.loop2000.loop2200       MemberDisability                              class      public
x834.loop2000.loop2310       ProviderChange                                class      public
x834.loop2000.loop2310       Provider                                      class      public
x834.loop2000.loop2310       ProviderName                                  class      public
x834.loop2000.loop2320       CoordinationOfBenefits                        class      public
x834.loop2000.loop2320       CoordinationOfBenefitsRelatedEntityName       class      public
x834.loop2000.loop2320       MemberCoordinationOfBenefits                  class      public
x834.loop2000.loop2700       MemberReportingCategoryName                   class      public
x834.loop2000.loop2700       ReportingCategory                             class      public
x834.loop2000.loop3000       HealthCoverageDates                           class      public
x834.loop2000.loop3000       HealthCoverage                                class      public
x834.loop2000                MemberIdentificationNumber                    class      public
x834.loop2000                Member                                        class      public
x834.loop2000                MemberLevelDates                              class      public
x834.loop2000                MemberPolicyNumber                            class      public
x834.loop2000                SubscriberNumber                              class      public
x834.loop2000                X834MemberWriter                              class      public
x834.segments                AMTSegment                                    class      public
x834.segments                BGNSegment                                    class      pkg-private
x834.segments                COBSegment                                    class      public
x834.segments                DMGSegment                                    class      public
x834.segments                DSBSegment                                    class      public
x834.segments                DTPSegment                                    class      public
x834.segments                GESegment                                     class      pkg-private
x834.segments                GSSegment                                     class      pkg-private
x834.segments                HDSegment                                     class      public
x834.segments                HLHSegment                                    class      public
x834.segments                ICMSegment                                    class      public
x834.segments                IDCSegment                                    class      public
x834.segments                IEASegment                                    class      pkg-private
x834.segments                ISASegment                                    class      pkg-private
x834.segments                LESegment                                     class      public
x834.segments                LSSegment                                     class      public
x834.segments                LUISegment                                    class      public
x834.segments                LXSegment                                     class      public
x834.segments                N1Segment                                     class      public
x834.segments                N3Segment                                     class      public
x834.segments                N4Segment                                     class      public
x834.segments                NM1Segment                                    class      public
x834.segments                PERSegment                                    class      public
x834.segments                PLASegment                                    class      public
x834.segments                RefSegment                                    class      public
x834.segments                Segment                                       class      public
x834.segments                SESegment                                     class      pkg-private
x834.segments                STSegment                                     class      pkg-private
x834.spec                    CharacterClass                                enum       public
x834.spec                    CodeValue                                     record     public
x834.spec                    DataType                                      enum       public
x834.spec                    ElementPosition                               record     public
x834.spec                    ElementSpec                                   record     public
x834.spec                    SubsetResult                                  record     public
x834.spec                    X834Spec                                      class      public
x834.trailer                 FunctionalGroupTrailer                        class      public
x834.trailer                 InterchangeControlTrailer                     class      public
x834.trailer                 Trailer                                       class      public
x834.trailer                 TransactionSetTrailer                         class      public
x834.util                    EdiCodeEnum                                   interface  public
x834.util                    EdiEnumLookup                                 class      public
x834.util                    TextUtils                                     class      public
x834                         X834Context                                   class      public
x834                         X834Document                                  class      public

### x999
x999                         X999FileParser                                class      public
x999                         X999                                          class      public

### flatfile
flatfile.delimited           DelimitedFileGenerator                        class      public
flatfile.delimited           DelimitedFileParser                           class      public
flatfile.delimited           DelimitedFormat                               class      public
flatfile.delimited           LinkedRows                                    class      pkg-private
```

