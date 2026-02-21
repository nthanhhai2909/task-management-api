package io.github.nthanhhai2909.taskmanagement.internal.domain;


public class DomainException extends RuntimeException {
    private final String description;

    public DomainException( String description) {
        super(description);
        this.description = description;
    }

    public String description() {
        return description;
    }
}