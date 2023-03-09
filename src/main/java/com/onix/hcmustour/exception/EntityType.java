package com.onix.hcmustour.exception;

public enum EntityType {
    USER("user"),
    CATEGORY("category"),
    SCOPE("scope"),
    COSTUME("costume"),
    ;

    private final String value;

    EntityType(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
