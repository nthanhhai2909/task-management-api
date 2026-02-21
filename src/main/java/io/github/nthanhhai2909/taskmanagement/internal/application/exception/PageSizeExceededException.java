package io.github.nthanhhai2909.taskmanagement.internal.application.exception;

public class PageSizeExceededException extends RuntimeException {
    private final String description;

    public PageSizeExceededException(String description) {
        super(description);
        this.description = description;
    }


    public String description() {
        return description;
    }
}

