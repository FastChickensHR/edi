# FastChickensHR EDI

A Java toolkit for benefit-enrollment EDI: generate X12 834 files and CSV, and parse inbound CSV member feeds — over one format-neutral file kernel.

## Modules

| Module | Purpose |
| --- | --- |
| `core` | Format-neutral file kernel: the `FileContent` model and the `parse` / `generate` seam. |
| `x834` | X12 834 benefit-enrollment file generator. |
| `csv`  | CSV parser and generator. |
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

Optional<String> document = new X834Document.Builder(context)
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
```

## Requirements

Java 23 and Maven.

## License

[MIT](LICENSE)
