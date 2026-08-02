# Style guide

The idioms this library holds itself to. Formatting is not covered here: it
is owned entirely by [google-java-format] via Spotless, enforced at `verify`
(see [#210](https://github.com/FastChickensHR/edi/issues/210)) — run
`mvn spotless:apply` and the mechanical layer is settled. This document only
records what a formatter cannot decide: the shapes new code should take.
Each ruling was settled in
[#243](https://github.com/FastChickensHR/edi/issues/243) (with the error
channel and naming freezes inherited from
[#216](https://github.com/FastChickensHR/edi/issues/216) and
[#210](https://github.com/FastChickensHR/edi/issues/210)).

[google-java-format]: https://github.com/google/google-java-format

## Records vs beans: role, not era

Whether a value type is a record or a mutable bean follows its *role*:

- **Record** when instances are constructed whole and never patched
  afterwards — core's value types, the `spec/` rows, `GenerationError`.
  If it is complete at birth, it is a record.
- **Mutable bean** when instances are populated progressively from sparse
  sources — the x834 member domain model (`Member`, `Income`, `Address`,
  `HealthCoverage`, …), built up field-by-field by callers translating
  HR data under the null-means-omit convention below. These are beans *by
  design*, not legacy awaiting conversion.

## Lombok charter

Lombok is used in **x834 only**; `core`, `x999`, and `flatfile` stay
Lombok-free. Within x834 the sanctioned uses are:

- **Class-level `@Getter` and `@Setter` on the progressive domain beans** —
  the bean idiom above.
- **`@Setter @Accessors(chain = true)` on the internal segment builders** —
  the mechanism behind the frozen `AbstractBuilder` layer (see Builders
  below). Frozen with that layer: no new uses.

Everything else is banned for new code — in particular `@Data` (bundles
`equals`/`hashCode` onto mutable types), `@Value` (those types should be
records), `@Builder` (builders are hand-written), `@SneakyThrows`, and
`@EqualsAndHashCode` on mutable types.

## Builders

A public type that needs a builder gets a **hand-written nested
`Builder`** — the `DelimitedFormat` shape — with static preset factories
where a common configuration has a name (`DelimitedFormat.csv()`).
Hand-written because it needs no Lombok (so it works in every module), its
javadoc is first-class (the doclint gate covers every public method), and
`build()` is the natural home for validation.

Builders are for types constructed whole with enough optional knobs to make
constructors awkward. Progressive beans never get builders — callers set
fields as the data arrives.

The self-typed `AbstractBuilder<T>` pattern in the hidden segment layer is
frozen legacy: it serves that inheritance hierarchy and nothing else. No new
`AbstractBuilder`s, and none on the public surface.

## Null vs `Optional`

`Optional` is a **query-method return type only**, used where absence is an
expected answer — `Field.valueIfPresent()`, `BaseMember.getAddress(type)`.
Never `Optional` fields, never `Optional` parameters.

Everywhere else, absence is a bare `null` field: the domain convention is
**null-means-omit** — an unset field means "don't emit this element" — with
`hasX()` guard methods (`Address.hasStreet()`) as the named companion
pattern where emission needs a multi-field completeness check.

Nullness annotations (JSpecify) are deliberately deferred until after the
beta: annotations are binary-compatible additions, so the API lock loses
nothing by waiting.

## Naming: the `X834` prefix

The prefix marks **transaction-set machinery** — types that name the 834
interaction itself and sit beside core's kernel vocabulary (or a sibling
transaction set's seam) in a consumer's imports: `X834Document`,
`X834Context`, `X834FileGenerator`, `X834Location`, `X834Spec`. The
disambiguation is real: core exports bare `FileGenerator` and `Location`.

Everything that lives *inside* an 834 stays bare — `Header`, `Trailer`,
`Member`, `Income`, `GenerationResult` — the package qualifies it. The test
for a new type: *does it name the 834 machinery (prefix) or a thing inside
an 834 (bare)?*

These names are frozen as of the first beta, as is the root package
`com.fastChickensHR` (capitals and all — consumer imports outlive tidiness).

## Error channel

The document-level contract is **`GenerationResult`: accumulate, never
throw** — from the document inward, generation collects every
`GenerationError` rather than failing fast. Construction of individual
components is the other half: a component's own `build()`/`validate()`
throws checked `ValidationException`. The split is deliberate and
documented on the types themselves.

Code-enum `fromString` lookups keep throwing `IllegalArgumentException` —
an unknown code is a caller bug, not a document condition.

## Miscellany (settled, do not re-litigate)

- Utility classes are `final` with a private constructor.
- Tests: wildcard static import of `Assertions.*` is fine; new test methods
  get behavior-sentence names (`rejectsNullEmptyAndUnknown`), not
  `testXxx`/`_Should` styles.
- The golden-file pattern (`TestFixtures`, `-Dupdate.goldens=true`) is the
  required idiom for any new emitted surface.
