package com.fastChickensHR.edi.x834;

import com.fastChickensHR.edi.x834.common.Segment;
import com.fastChickensHR.edi.x834.common.x834Context;
import com.fastChickensHR.edi.x834.header.TransactionSetHeader;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an EDI 834 document for benefit enrollment and maintenance.
 * This class handles document structure, formatting, and validation.
 * Now uses x834Context to share document-level information with segments.
 */
public class x834Document {

    // Currently implemented segments
    private final TransactionSetHeader headerSegment;

    // List to maintain segment order
    private final List<Segment> segments = new ArrayList<>();

    public x834Document(Builder builder) {
        x834Context context = new x834Context();

        this.headerSegment = builder.headerSegment;
        if (headerSegment != null) {
            headerSegment.setContext(context);
            segments.add(headerSegment);
        }

        // Add other segments and set context for them
        if (builder.additionalSegments != null) {
            for (Segment segment : builder.additionalSegments) {
                segment.setContext(context);
                segments.add(segment);
            }
        }
    }

    /**
     * Generates the complete EDI document string
     *
     * @return A properly formatted EDI 834 document as a string
     */
    public String generateDocument() {
        StringBuilder document = new StringBuilder();

        for (Segment segment : segments) {
            document.append(segment.render());
        }

        return document.toString();
    }

    /**
     * Gets the header segment (ST)
     *
     * @return The HeaderLoopBeginningSegment
     */
    public TransactionSetHeader getHeaderSegment() {
        return headerSegment;
    }

    public static class Builder {
        private TransactionSetHeader headerSegment;
        private final List<Segment> additionalSegments = new ArrayList<>();

        public Builder() {
        }

        public Builder setHeaderSegment(TransactionSetHeader headerSegment) {
            this.headerSegment = headerSegment;
            return this;
        }

        public Builder addSegment(Segment segment) {
            this.additionalSegments.add(segment);
            return this;
        }

        public x834Document build() {
            return new x834Document(this);
        }
    }
}