package io.github.nthanhhai2909.taskmanagement.internal.application.exception;

public class DomainRuleViolationException extends RuntimeException {
    private final String code;
    private final String description;

    public DomainRuleViolationException(String code, String description) {
        super(String.format("Domain rule violation: %s | %s", code, description));
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
