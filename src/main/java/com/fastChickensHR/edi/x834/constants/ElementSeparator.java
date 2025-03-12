package com.fastChickensHR.edi.x834.constants;

public enum ElementSeparator {
    ASTERISK('*'),
    CARET('^'),
    PIPE('|');

    private final char value;

    ElementSeparator(char value) {
        this.value = value;
    }

    public char getValue() {
        return value;
    }
}
