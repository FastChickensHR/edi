# FastChickens EDI X834 Library

## Overview

The FastChickens EDI X834 Library is a Java-based solution for generating HIPAA-compliant X12 834 Benefit Enrollment and Maintenance electronic data interchange (EDI) files. This library simplifies the complex process of creating, validating, and formatting standardized EDI documents required for health insurance enrollment transactions.

## Purpose

The healthcare industry relies on standardized electronic formats for transmitting enrollment data between employers, insurance carriers, and government agencies. The X12 834 transaction set is the industry standard for communicating benefit enrollment and maintenance information.

Our library abstracts the complexities of EDI formatting, validation, and compliance requirements, allowing developers to focus on business logic rather than technical EDI specifications.

## Key Benefits

- **Type-Safe Builder Pattern**: Eliminates common EDI syntax errors with strongly-typed builders
- **Automatic Validation**: Built-in validation ensures compliance with X12 standards
- **Fluent API**: Intuitive, chainable methods for clear and concise code
- **Context Management**: Centralized context object maintains document-level information
- **Formatting Consistency**: Automatic handling of separators, terminators, and line feeds
- **Compliance Focus**: Built specifically for HIPAA X12 834 5010 standard
- **Extensible Architecture**: Easily add new segments as needed
- **Error Reporting**: Detailed error messaging for quick problem resolution

## Getting Started

### Installation
N/A Right now. Reach out if you are interested and we will publish.

### Basic Usage

Creating an X834 document involves configuring a document context and using builders for each required segment:

## Example Usage

Below is an example showing how to create an 834 document for the State of Michigan:

```java
    public static x834Document createMichiganDocument(int memberCount) throws ValidationException {
    final x834Context context = new x834Context()
            .setSenderID("FASTCHKN")
            .setReceiverID("MICHGVEDI")
            .setElementSeparator(ElementSeparator.PIPE)
            .setDocumentDate(LocalDateTime.of(2023, 8, 1, 0, 0));

    Header header = new Header.Builder(context)
            .setInterchangeControlNumber("000000001")
            .setGroupControlNumber("42789")
            .setReferenceIdentification("220701MI834")
            .setMasterPolicyNumber("MIHHS-EMP-2023")
            .setPlanSponsorName("FASTCHKN")
            .setPayerName("FASTCHKN INSURANCE")
            .setPayerIdentification("123456789")
            .build();

    List<Member> members = generateMembers(memberCount, context);

    Trailer trailer = new Trailer.Builder(context).build();

    return new x834Document.Builder(context)
            .withHeader(header)
            .withMembers(members)
            .withTrailer(trailer)
            .build();
}
```

## Documentation
***IN PROGRESS***

The library includes comprehensive documentation for each segment type:

- **ISA**: Interchange Control Header
- **GS**: Functional Group Header
- **ST**: Transaction Set Header
- **BGN**: Beginning Segment
- **DTP**: Date/Time Reference
- **REF**: Reference Information
- [Additional segments documented]

## Compliance

***IN PROGRESS***

This library adheres to the X12 834 5010 standard as defined in the ASC X12N/005010X220 Implementation Guide. Regular updates ensure continued compliance with industry standards.

## Development Setup

### Git Hook Installation

This project uses Git hooks to ensure all files have proper license headers.
After cloning the repository, run the following command to set up the hooks:

```bash
./bin/setup-git-hooks.sh
```

This will configure Git to run our license header check before each commit.

## License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Contributing

Contributions are welcome.

---
