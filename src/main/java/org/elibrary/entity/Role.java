package org.elibrary.entity;

import java.io.Serializable;

public enum Role implements Serializable {
    READER("reader"), LIBRARIAN("librarian"), ADMIN("admin");

    private final String value;

    Role(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
