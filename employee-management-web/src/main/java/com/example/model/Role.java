package com.example.model;

public enum Role {
    ROLE_ADMIN("Admin"),
    ROLE_USER("User"),
    ROLE_MANAGER("Manager");

    private final String displayName;

    Role(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
