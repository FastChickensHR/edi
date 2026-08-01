/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.header;

import com.fastChickensHR.edi.x834.Segment;
import com.fastChickensHR.edi.x834.X834Context;
import com.fastChickensHR.edi.x834.exception.ValidationException;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents the complete header section of an 834 file. This includes Interchange Control Header,
 * Functional Group Header, Transaction Set Header, Beginning Segment, File Effective Date,
 * Transaction Set Policy Number, Sponsor, and Payer information.
 */
@Getter
@Setter
public class Header {
  private final X834Context context;

  // Default fields for simple configuration
  private String transactionSetIdentifierCode;
  private String referenceIdentification;
  private String masterPolicyNumber;
  private String planSponsorName;
  private String payerName;
  private String payerIdentification;

  /**
   * Creates a new Header instance with the specified context
   *
   * @param context The 834 context to use for this header
   */
  public Header(X834Context context) {
    this.context = context;
  }

  /**
   * Validates this header has the minimum required fields
   *
   * @throws ValidationException If validation fails
   */
  public void validate() throws ValidationException {
    if (transactionSetIdentifierCode == null || transactionSetIdentifierCode.isEmpty()) {
      throw new ValidationException("Transaction Set Identifier Code is required");
    }
  }

  /**
   * Generates all the segments for this header
   *
   * @return List of segments in the correct order
   * @throws ValidationException if the transaction set identifier code is missing, or a header
   *     segment builder rejects its inputs
   */
  public List<Segment> generateSegments() throws ValidationException {
    validate();

    List<Segment> segments = new ArrayList<>();
    segments.add(new InterchangeControlHeader.Builder(context).build());
    segments.add(new FunctionalGroupHeader.Builder(context).build());
    segments.add(
        new TransactionSetHeader.Builder(context)
            .setTransactionSetIdentifierCode(transactionSetIdentifierCode)
            .build());
    segments.add(
        new BeginningSegment.Builder(context)
            .setReferenceIdentification(referenceIdentification)
            .build());
    segments.add(new FileEffectiveDate.Builder(context).build());
    segments.add(
        new TransactionSetPolicyNumber.Builder().setMasterPolicyNumber(masterPolicyNumber).build());
    segments.add(new SponsorName.Builder().setPlanSponsorName(planSponsorName).build());
    segments.add(createDefaultPayerBuilder().build());

    return segments;
  }

  private Payer.Builder createDefaultPayerBuilder() {
    Payer.Builder builder = new Payer.Builder();
    if (payerName != null) {
      builder.setPlanSponsorName(payerName);
    }
    if (payerIdentification != null) {
      // Qualifier and identifier are a P0304 pair: emit N103=FI only alongside the id.
      builder
          .setIdentificationCodeQualifier(Payer.DEFAULT_IDENTIFICATION_CODE_QUALIFIER)
          .setSponsorIdentifier(payerIdentification);
    }
    return builder;
  }

  /** Builder for Header class */
  public static class Builder {
    /** Default transaction set identifier code for an 834 (ST01 / BGN transaction set). */
    private static final String DEFAULT_TRANSACTION_SET_IDENTIFIER_CODE = "834";

    private final X834Context context;

    private String transactionSetIdentifierCode = DEFAULT_TRANSACTION_SET_IDENTIFIER_CODE;
    private String referenceIdentification;
    private String masterPolicyNumber;
    private String planSponsorName;
    private String payerName;
    private String payerIdentification;

    /**
     * Creates a new Builder with the required context
     *
     * @param context The 834 context to use for this header
     */
    public Builder(X834Context context) {
      this.context = context;
    }

    // Simple setters
    /**
     * Sets the transaction set identifier code (ST01); defaults to "834".
     *
     * @param transactionSetIdentifierCode the transaction set identifier code
     * @return this builder instance
     */
    public Builder setTransactionSetIdentifierCode(String transactionSetIdentifierCode) {
      this.transactionSetIdentifierCode = transactionSetIdentifierCode;
      return this;
    }

    /**
     * Sets the reference identification used by the Beginning Segment (BGN02).
     *
     * @param referenceIdentification the reference identification
     * @return this builder instance
     */
    public Builder setReferenceIdentification(String referenceIdentification) {
      this.referenceIdentification = referenceIdentification;
      return this;
    }

    /**
     * Sets the master policy number (REF*38).
     *
     * @param masterPolicyNumber the master policy number
     * @return this builder instance
     */
    public Builder setMasterPolicyNumber(String masterPolicyNumber) {
      this.masterPolicyNumber = masterPolicyNumber;
      return this;
    }

    /**
     * Sets the plan sponsor name (Loop 1000A, N1*P5).
     *
     * @param planSponsorName the plan sponsor name
     * @return this builder instance
     */
    public Builder setPlanSponsorName(String planSponsorName) {
      this.planSponsorName = planSponsorName;
      return this;
    }

    /**
     * Sets the payer name (Loop 1000B, N1*IN).
     *
     * @param payerName the payer name
     * @return this builder instance
     */
    public Builder setPayerName(String payerName) {
      this.payerName = payerName;
      return this;
    }

    /**
     * Sets the payer identification (Loop 1000B, N104).
     *
     * @param payerIdentification the payer identification
     * @return this builder instance
     */
    public Builder setPayerIdentification(String payerIdentification) {
      this.payerIdentification = payerIdentification;
      return this;
    }

    /**
     * Builds a new Header instance
     *
     * @return The configured Header instance
     */
    public Header build() {
      Header header = new Header(context);

      header.transactionSetIdentifierCode = this.transactionSetIdentifierCode;
      header.referenceIdentification = this.referenceIdentification;
      header.masterPolicyNumber = this.masterPolicyNumber;
      header.planSponsorName = this.planSponsorName;
      header.payerName = this.payerName;
      header.payerIdentification = this.payerIdentification;

      return header;
    }
  }
}
