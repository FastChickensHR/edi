package com.fastChickensHR.edi.x834.constants;

public enum SubElementSeparator {
    COLON(':'),
    BACKSLASH('\\'),
    GREATER_THAN('>');

    private final char value;

    SubElementSeparator(char value) {
        this.value = value;
    }

    public char getValue() {
        return value;
    }
}