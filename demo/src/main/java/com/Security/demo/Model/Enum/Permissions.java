package com.Security.demo.Model.Enum;

public enum Permissions {
    CREATED,
    READ,
    UPDATE,
    DELETE;

    public String getAuthority() {
        return this.name();
    }
}
