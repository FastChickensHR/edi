# FastChickensHR EDI

A Java toolkit for benefit-enrollment EDI: generate X12 834 files and delimited flat files (CSV and friends), and parse inbound flat-file member feeds — over one format-neutral file kernel.

## Modules

| Module | Purpose |
| --- | --- |
| `core` | Format-neutral file kernel: the `FileContent` model and the `parse` / `generate` seam. |
| `x834` | X12 834 benefit-enrollment file generator. |
| `flatfile` | Flat-file parser and generator: `delimited` (CSV and other separator formats) today; `fixedwidth` reserved. |
| `x999` | X12 999 / 997 acknowledgment parser. |

## Install

Not yet published. Build and install to your local Maven repository:

```bash
mvn install
```

Then depend on the module you need:

```xml
<dependency>
  <groupId>com.fastchickenshr</groupId>
  <artifactId>x834</artifactId>
  <version>1.0.0-SNAPSHOT</version>
</dependency>
```

## Usage

Generate an 834 from an enrollment:

```java
X834Context context = new X834Context()
        .setSenderID("SENDER01")
        .setReceiverID("RECEIVER1")
        .setDocumentDate(LocalDateTime.of(2024, 1, 1, 0, 0))
        .setInterchangeControlNumber("000000001")
        .setGroupControlNumber("1");

Member member = new Member();
member.setMaintenanceTypeCode(MaintenanceTypeCode.ADDITION);
member.setRelationshipCode(IndividualRelationshipCode.EMPLOYEE);
member.setMemberIndicator(MemberIndicator.INSURED);

GenerationResult result = new X834Document.Builder(context)
        .withHeader(new Header.Builder(context)
                .setReferenceIdentification("834TEST")
                .setMasterPolicyNumber("POL-001")
                .setPlanSponsorName("ACME INC")
                .setPayerName("BLUE CROSS")
                .build())
        .withTrailer(new Trailer.Builder(context))
        .addMember(member)
        .build()
        .generateDocument();

switch (result) {
    case GenerationResult.Success success ->
            Files.writeString(Path.of("enrollment.834"), success.document());
    case GenerationResult.Failure failure -> failure.errors().forEach(error ->
            System.err.println(error.phase() + " " + error.location() + ": " + error.message()));
}
```

Generation reports through exactly one channel: `generateDocument()` returns a
`GenerationResult` — a `Success` carrying the finished X12 834, or a `Failure` carrying
*every* reason it could not be produced. Problems are accumulated, not thrown: build-time
structure/configuration problems and render-time serialization problems all surface as
`GenerationError`s in a single pass, so the source can be fixed in one round-trip.

## Requirements

Java 23 and Maven.

Formatting is enforced by Spotless (google-java-format); `mvn spotless:apply` fixes a failing
check. The one-shot reformat commit is listed in `.git-blame-ignore-revs` — GitHub blame skips
it automatically, and locally you can do the same with
`git config blame.ignoreRevsFile .git-blame-ignore-revs`.

## License

[MIT](LICENSE)
