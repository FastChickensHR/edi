package com.fastChickensHR.edi.x834.constants;

public enum LineTerminator {
    LF("\n"),
    CRLF("\r\n"),
    NONE("");

    private final String value;

    LineTerminator(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
