package com.buzzshelter.Model;

/**
 * Enum for account type
 */
public enum AccountType {
    ADMIN("ADMIN"),
    USER("USER");

    private final String innerCode;

    AccountType(String innerCode) {
        this.innerCode = innerCode;
    }

    public String toString() {
        return innerCode;
    }
}
