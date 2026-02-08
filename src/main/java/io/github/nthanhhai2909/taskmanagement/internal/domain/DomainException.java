package io.github.nthanhhai2909.taskmanagement.internal.domain;


public class DomainException extends RuntimeException {
    private final String code;
    private final String description;

    public DomainException(String code, String description) {
        super(String.format("Domain exception: %s | %s", code, description));
        this.code = code;
        this.description = description;
    }

    public String code() {
        return code;
    }

    public String description() {
        return description;
    }
}
