package com.nab.dev.taskmanagement.internal.domain;


public class DomainException extends RuntimeException {
    private final DomainError error;

    public DomainException(DomainError error) {
        super(String.format("Domain error: %s | %s", error.code(), error.description()));
        this.error = error;
    }

    public String description() {
        return error.description();
    }

    public String code() {
        return error.code();
    }
}
