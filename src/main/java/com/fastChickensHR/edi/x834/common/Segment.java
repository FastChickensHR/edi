package com.fastChickensHR.edi.x834.common;

import com.fastChickensHR.edi.x834.x834Document;
import com.fastChickensHR.edi.x834.constants.ElementSeparator;
import com.fastChickensHR.edi.x834.constants.SegmentTerminator;

/**
 * Abstract base class for all EDI segments
 */
public abstract class Segment {

    /**
     * Gets the segment identifier code (e.g., "ST", "SE", "BGN", etc.)
     * @return The segment identifier
     */
    public abstract String getSegmentIdentifier();

    /**
     * Gets the ordered list of element values for this segment
     * @return Array of element values in the correct order
     */
    public abstract String[] getElementValues();

    /**
     * Creates the EDI segment string representation
     * @param elementSeparator The separator to use between elements
     * @param segmentTerminator The terminator to use at the end of the segment
     * @return Properly formatted EDI segment string
     */
    public String toEdiSegment(ElementSeparator elementSeparator, SegmentTerminator segmentTerminator) {
        StringBuilder sb = new StringBuilder();
        sb.append(getSegmentIdentifier());

        for (String element : getElementValues()) {
            sb.append(elementSeparator.getValue());
            sb.append(element);
        }

        sb.append(segmentTerminator.getValue());
        return sb.toString();
    }

    /**
     * Convenience method to format a segment using an EightThirtyFourDocument
     *
     * @param document The EDI document containing separators and terminators
     * @return Properly formatted EDI segment string
     */
    public String toEdiSegment(x834Document document) {
        return toEdiSegment(document.getElementSeparator(), document.getSegmentTerminator());
    }
}