package io.github.nthanhhai2909.taskmanagement.internal.application.exception;

public class DomainRuleViolationException extends RuntimeException {
    private final String description;

    public DomainRuleViolationException(String description) {
        super(description);
        this.description = description;
    }
    public String description() {
        return description;
    }
}
