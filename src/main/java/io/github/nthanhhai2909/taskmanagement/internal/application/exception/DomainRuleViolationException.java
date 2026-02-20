package io.github.nthanhhai2909.taskmanagement.internal.application.exception;

public class DomainRuleViolationException extends RuntimeException {
    private final int code;
    private final String description;

    public DomainRuleViolationException(int code, String description) {
        super(String.format("Domain rule violation: %d | %s", code, description));
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
