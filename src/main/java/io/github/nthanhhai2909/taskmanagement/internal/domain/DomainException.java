package io.github.nthanhhai2909.taskmanagement.internal.domain;


public class DomainException extends RuntimeException {
    private final int code;
    private final String description;

    public DomainException(int code, String description) {
        super(String.format("Domain exception: %d | %s", code, description));
        this.code = code;
        this.description = description;
    }

    public int code() {
        return code;
    }

    public String description() {
        return description;
    }
}