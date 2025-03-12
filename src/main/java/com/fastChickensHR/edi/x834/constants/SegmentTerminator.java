package com.fastChickensHR.edi.x834.constants;

public enum SegmentTerminator {
    TILDE('~'),
    LINE_FEED('\n'),
    CARRIAGE_RETURN('\r');

    private final char value;

    SegmentTerminator(char value) {
        this.value = value;
    }

    public char getValue() {
        return value;
    }
}
