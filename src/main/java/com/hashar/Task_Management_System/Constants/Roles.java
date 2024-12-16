package com.hashar.Task_Management_System.Constants;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Roles {
    USER, ADMIN;

    @JsonCreator
    public static Roles fromString(String role) {
        try {
            return Roles.valueOf(role.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid role: '" + role + "'. Accepted values are: USER, ADMIN.");
        }
    }
}
