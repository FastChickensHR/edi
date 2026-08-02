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

Tagged beta releases are published to GitHub Packages. Add the repository and
depend on the module you need:

```xml
<repositories>
  <repository>
    <id>github</id>
    <url>https://maven.pkg.github.com/FastChickensHR/edi</url>
  </repository>
</repositories>

<dependency>
  <groupId>com.fastchickenshr</groupId>
  <artifactId>x834</artifactId>
  <version>1.0.0-beta.1</version>
</dependency>
```

GitHub Packages requires authentication even for public downloads: put a
personal access token with the `read:packages` scope in your
`~/.m2/settings.xml` under a server whose id matches the repository id above:

```xml
<servers>
  <server>
    <id>github</id>
    <username>YOUR_GITHUB_USERNAME</username>
    <password>YOUR_READ_PACKAGES_PAT</password>
  </server>
</servers>
```

Alternatively, build from a clone with `mvn install`.

## Usage

### Generate an X12 834

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

### Round-trip a delimited flat file

Generate a CSV member feed and parse it back:

```java
FileContent roster = new FileContent(
        Direction.OUTBOUND,
        List.of(),
        List.of(
                Record.of(List.of(
                        new Field(new Location(RecordLevel.RECORD, "memberId"), "1001"),
                        new Field(new Location(RecordLevel.RECORD, "lastName"), "DOE"),
                        new Field(new Location(RecordLevel.RECORD, "planCode"), "PPO-500"))),
                Record.of(List.of(
                        new Field(new Location(RecordLevel.RECORD, "memberId"), "1002"),
                        new Field(new Location(RecordLevel.RECORD, "lastName"), "ROE"),
                        new Field(new Location(RecordLevel.RECORD, "planCode"), "HMO-250")))));

String csv = new DelimitedFileGenerator().generate(roster);
// memberId,lastName,planCode
// 1001,DOE,PPO-500
// 1002,ROE,HMO-250

FileContent parsed = new DelimitedFileParser().parse(csv);
for (Record member : parsed.records()) {
    for (Field field : member.fields()) {
        System.out.println(field.location().name() + " = " + field.value());
    }
}
```

Each `Record` is a row and each `Field` a cell under the column named by its `Location`;
an empty cell parses to no field at all (absence, not a blank value). The no-argument
constructors speak `DelimitedFormat.csv()` — the one preset guaranteed to survive
parse-then-generate unchanged. To match a foreign feed instead, pass
`DelimitedFormat.builder()` with the knobs the target file differs on (delimiter, quote,
escape, record separator); a headerless format (`.header(false)`) has no column names, so
the parser addresses cells by 1-based position (`"1"`, `"2"`, …). One level of nesting
(e.g. dependents under a subscriber) is flattened into linked rows tagged by a reserved
record-level column, and reconstructed on the way back in.

### Parse a 999 / 997 acknowledgment

Read a functional acknowledgment and check what the carrier said:

```java
FileContent ack = new X999FileParser().parse(Files.readString(Path.of("carrier.999")));

String groupStatus = field(ack.fileFields(), X999.GROUP_STATUS); // AK901
if (X999.ACCEPTED.equals(groupStatus)) {
    System.out.println("functional group accepted");
}
for (Record transactionSet : ack.records()) {
    System.out.println("ST "
            + field(transactionSet.fields(), X999.TRANSACTION_SET_CONTROL_NUMBER)
            + ": " + field(transactionSet.fields(), X999.TRANSACTION_SET_STATUS));
}
```

where `field` is the small helper for reading a value by location name:

```java
static String field(List<Field> fields, String name) {
    return fields.stream()
            .filter(field -> name.equals(field.location().name()))
            .findFirst()
            .map(Field::value)
            .orElse(null);
}
```

An acknowledgment replies to one functional group, so the file-level fields carry the
acknowledged group (`X999.FUNCTIONAL_ID_CODE`, `X999.GROUP_CONTROL_NUMBER` — the original
GS06, your correlation key — and `X999.GROUP_STATUS`), plus the TA1 interchange
acknowledgment when one is present. Each `Record` is one acknowledged transaction set: its
control number (the original ST02) and its status. The parser is deliberately dumb — it
emits the raw X12 codes (`A` accepted, `E` accepted with errors, `P` partially accepted,
`R` rejected; all named on `X999`) and leaves interpretation and correlation to you.
Interior error-detail segments (AK3/AK4/IK3/IK4) are skipped in this pass.

### The core kernel

Every module above meets in `core`, the format-neutral file kernel. A `FileContent` is an
ordered tree — file-level fields (header/trailer, once per file) plus one `Record` per
subject, each field a resolved value at a `Location` — and it is the pivot between your
application and any format's dialect: you speak only the tree, and each format module
interprets the locations into its own tokens (an 834 turns them into loops and segments,
a delimited file into columns). The seam is a pair of single-method interfaces —
`FileGenerator` serializes a `FileContent` to text, `FileParser` reads text back into one
— which is why the flat-file example above round-trips and why an inbound feed and an
outbound 834 can share one representation. You never depend on `core` alone: consumers
meet its types through the format modules, as in the examples above. To orient in the
source, start with `FileContent` and the two seam interfaces; the whole kernel is eight
small types.

## Requirements

Java 23 and Maven.

Formatting is enforced by Spotless (google-java-format); `mvn spotless:apply` fixes a failing
check. The one-shot reformat commit is listed in `.git-blame-ignore-revs` — GitHub blame skips
it automatically, and locally you can do the same with
`git config blame.ignoreRevsFile .git-blame-ignore-revs`.

## License

[MIT](LICENSE)
